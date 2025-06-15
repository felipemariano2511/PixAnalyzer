import pandas as pd
from datetime import datetime

def calcular_confiabilidade(row):
    motivos_bons = []
    motivos_ruins = []

    def validar_status(status):
        if status and status.strip().upper() == 'ATIVO':
            motivos_bons.append('CPF ativo na Receita Federal')
        else:
            motivos_ruins.append('CPF inativo, irregular ou inexistente na Receita')

    def analisar_idade_e_nascimento(birth_date):
        try:
            nascimento = datetime.strptime(birth_date, "%Y-%m-%d")
            idade = (datetime.now() - nascimento).days // 365
            if idade < 0:
                motivos_ruins.append('Data de nascimento futura (inconsistente)')
            elif idade > 120:
                motivos_ruins.append('Idade superior a 120 anos (inconsistente)')
            elif idade < 18:
                motivos_ruins.append('Menor de idade (perfil comum de laranja)')
            elif idade > 70:
                motivos_bons.append('Idade superior a 70 anos (baixo risco de laranja)')
            return idade
        except:
            motivos_ruins.append('Data de nascimento inválida ou ausente')
            return None

    def analisar_registro(registration_date, idade):
        try:
            data_registro = datetime.strptime(registration_date.split(" ")[0], "%Y-%m-%d")
            anos_registro = (datetime.now() - data_registro).days / 365
            if anos_registro < 0:
                motivos_ruins.append('Data de registro futura (inconsistente)')
            elif anos_registro >= 20:
                motivos_bons.append('CPF com mais de 20 anos de registro')
            elif anos_registro < 1:
                motivos_ruins.append('CPF recém-registrado (menos de 1 ano)')
            elif anos_registro < 5:
                motivos_ruins.append('CPF com menos de 5 anos de registro')
            if idade is not None and anos_registro > idade:
                motivos_ruins.append('Idade inconsistente com data de registro (registro antes de nascer)')
            return anos_registro
        except:
            motivos_ruins.append('Data de registro inválida ou ausente')
            return None

    def analisar_nome(nome):
        if not nome or nome.strip() == '':
            motivos_ruins.append('Nome do titular ausente (possível CPF falso)')

    def analisar_movimentacao(total, anos_registro, idade):
        try:
            total = int(total) if total is not None else 0
            if anos_registro is None or anos_registro <= 0:
                motivos_ruins.append('Sem base temporal para análise de movimentação')
                return
            media_anual = total / anos_registro

            if media_anual >= 1000:
                motivos_bons.append('Alta movimentação média anual (>=1k)')
            elif media_anual >= 200:
                motivos_ruins.append('Movimentação anual normal (>=200)')
            elif media_anual > 50:
                motivos_ruins.append('Movimentação anual baixa (>50)')
            else: 
                 motivos_ruins.append('Movimentação anual muito baixa (<=50)')   

            # Penalizar CPF muito recente com movimentação alta (fraude típica)
            if anos_registro < 3 and total > 7000:
                motivos_ruins.append('Movimentação alta com CPF recém-criado (potencial fraude)')

            # Penalizar menor com movimentação alta
            if idade is not None and idade < 18 and total > 1000:
                motivos_ruins.append('Movimentação incompatível com menor de idade')
        except:
            motivos_ruins.append('Erro ao calcular movimentação anual')

    def analisa_transacoes_comuns(quantidade):
        try:
            quantidade = int(quantidade) if quantidade is not None else None
            if quantidade is None:
                motivos_ruins.append('Quantidade de transações comuns ausente')
            elif quantidade == 0:
                motivos_ruins.append('Nenhuma transação comum com este cliente')
            elif quantidade >= 10:
                motivos_bons.append('Várias transações anteriores com este cliente (>=10)')
            elif quantidade >= 3:
                motivos_bons.append('Algumas transações anteriores com este cliente (3 a 9)')
        except:
            motivos_ruins.append('Erro ao analisar quantidade de transações comuns')

    def analisar_perfil_atipico(total, common, anos_registro):
        try:
            total = int(total) if total is not None else 0
            common = int(common) if common is not None else 0
            if total > 7000 and common < 3 and anos_registro and anos_registro < 3:
                motivos_ruins.append('Movimentação alta sem histórico e com pouco tempo de CPF (perfil atípico)')
        except:
            pass

    validar_status(row.get('status'))
    idade = analisar_idade_e_nascimento(row.get('birth_date'))
    anos_registro = analisar_registro(row.get('registration_date'), idade)
    analisar_nome(row.get('full_name'))
    analisar_movimentacao(row.get('all_transfers'), anos_registro, idade)
    analisa_transacoes_comuns(row.get('cocommon_transfers_client'))
    analisar_perfil_atipico(row.get('all_transfers'), row.get('common_transfers_client'), anos_registro)

    pesos_bons = {
        'CPF ativo na Receita Federal': 0.2,
        'Idade superior a 70 anos (baixo risco de laranja)': 0.35,
        'CPF com mais de 20 anos de registro': 0.4,
        'Várias transações anteriores com este cliente (>=10)': 0.5,
        'Algumas transações anteriores com este cliente (3 a 9)': 0.35,
        'Alta movimentação média anual (>=1k)': 0.25,
        'Movimentação anual consistente com idade e tempo de registro': 0.3,
        'Histórico longo e estável de movimentações financeiras': 0.35,
        'Movimentação compatível com perfil etário e tempo de CPF': 0.3,
        'CPF com registro antigo e histórico de movimentação estável': 0.35,
        'Idade e tempo de CPF indicam baixa probabilidade de fraude': 0.35,
    }


    pesos_ruins = {
        'CPF inativo, irregular ou inexistente na Receita': 0.55,
        'Menor de idade (perfil comum de laranja)': 0.35,
        'Idade superior a 120 anos (incomun)': 0.35,
        'Data de nascimento futura (inconsistente)': 0.5,
        'Data de nascimento inválida ou ausente': 0.4,
        'Data de registro inválida ou ausente': 0.4,
        'Data de registro futura (inconsistente)': 0.4,
        'Idade inconsistente com data de registro (registro antes de nascer)': 0.55,
        'CPF recém-registrado (menos de 1 ano)': 0.25,
        'CPF com menos de 5 anos de registro': 0.15,
        'Quantidade de transações comuns ausente': 0.3,
        'Nenhuma transação comum com este cliente': 0.3,
        'Sem base temporal para análise de movimentação': 0.4,
        'Movimentação anual muito baixa (<50)': 0.15,
        'Movimentação alta com CPF recém-criado (potencial fraude)': 0.45,
        'Movimentação incompatível com menor de idade': 0.45,
        'Movimentação alta sem histórico e com pouco tempo de CPF (perfil atípico)': 0.5,
        'Nome do titular ausente (possível CPF falso)': 0.45,
    }

    score_base = 0.5
    score = score_base + sum(pesos_bons.get(m, 0) for m in motivos_bons) - sum(pesos_ruins.get(m, 0) for m in motivos_ruins)
    score = max(0, min(1, score))

    if score < 0.4:
        motivos = motivos_ruins
    elif score < 0.7:
        motivos = motivos_ruins + motivos_bons
    else:
        motivos = motivos_bons

    return {
        'confidence_score': round(score, 2),
        'fraud_reasons': ', '.join(motivos)  # <- Aqui já gera como string separada por vírgula
    }

df = pd.read_csv('informacao_cpf_lapidado.csv')

resultados = df.apply(calcular_confiabilidade, axis=1, result_type='expand')

df_resultado = pd.concat([df, resultados], axis=1)

df_resultado.to_csv('resultado_confiabilidade.csv', index=False)
