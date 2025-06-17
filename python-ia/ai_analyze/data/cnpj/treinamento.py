import pandas as pd
from datetime import datetime

def calcular_confiabilidade(row):
    motivos_bons = []
    motivos_ruins = []

    def verifica_status(status, status_date_str):
        if status != 'ATIVO':
            motivos_ruins.append('Empresa com status inativo')
            if status_date_str:
                try:
                    status_date = datetime.strptime(status_date_str.strip(), "%Y-%m-%d")
                    dias_desde_status = (datetime.now() - status_date).days
                    if dias_desde_status < 365:
                        motivos_ruins.append('Mudança de status recente')
                except:
                    motivos_ruins.append('Data de alteração de status inválida')
        else:
            motivos_bons.append('Empresa ativa')

    def analisa_data_registro(data_registro_str):
        if not isinstance(data_registro_str, str) or not data_registro_str.strip():
            motivos_ruins.append('Data de registro inválida')
            return None
        try:
            data_registro = datetime.strptime(data_registro_str.strip(), "%Y-%m-%d")
            idade = (datetime.now() - data_registro).days / 365
            if idade >= 15:
                motivos_bons.append('Empresa com mais de 15 anos de existência')
            elif idade >= 10:
                motivos_bons.append('Empresa com mais de 10 anos de existência')
            elif idade >= 5:
                motivos_bons.append('Empresa com mais de 5 anos de existência')
            else:
                motivos_ruins.append('Empresa muito recente (menos de 5 anos)')
            return idade
        except:
            motivos_ruins.append('Data de registro inválida')
            return None

    def analisa_capital_social(capital):
        if capital is None:
            motivos_ruins.append('Capital social ausente')
        elif capital < 100000:
            motivos_ruins.append('Capital social muito baixo (<100k)')
        elif capital < 500000:
            motivos_ruins.append('Capital social baixo (100k - 500k)')
        elif capital < 2000000:
            motivos_bons.append('Capital social médio (500k - 2M)')
        else:
            motivos_bons.append('Capital social elevado (>2M)')

    def analisa_transacoes_comuns(quantidade):
        if quantidade is None:
            motivos_ruins.append('Quantidade de transações comuns ausente')
        elif quantidade == 0:
            motivos_ruins.append('Nenhuma transação comum com este cliente')
        elif quantidade >= 10:
            motivos_bons.append('Várias transações anteriores com este cliente (>=10)')
        elif quantidade >= 3:
            motivos_bons.append('Algumas transações anteriores com este cliente (3 a 9)')

    def analisa_movimentacao_anual(all_transfers, idade_anos):
        if all_transfers is None or idade_anos is None or idade_anos == 0:
            motivos_ruins.append('Dados insuficientes para análise de movimentação anual')
            return
        media_transacoes_ano = all_transfers / idade_anos
        if media_transacoes_ano > 10000:
            motivos_bons.append('Altíssima movimentação anual (>10k/ano)')
        elif media_transacoes_ano > 5000:
            motivos_bons.append('Alta movimentação anual (5k-10k/ano)')
        elif media_transacoes_ano > 1000:
            motivos_bons.append('Movimentação anual razoável (1k-5k/ano)')
        else:
            motivos_ruins.append('Baixa movimentação anual (<1k/ano)')

    verifica_status(row.get('status'), row.get('status_date'))
    idade_anos = analisa_data_registro(row.get('registration_date'))
    analisa_capital_social(row.get('share_capital'))
    analisa_transacoes_comuns(row.get('common_transfers_client'))
    analisa_movimentacao_anual(row.get('all_transfers'), idade_anos)

    pesos_bons = {
        'Empresa ativa': 0.2,
        'Empresa com mais de 15 anos de existência': 0.3,
        'Empresa com mais de 10 anos de existência': 0.2,
        'Empresa com mais de 5 anos de existência': 0.1,
        'Capital social médio (500k - 2M)': 0.2,
        'Capital social elevado (>2M)': 0.3,
        'Várias transações anteriores com este cliente (>=10)': 0.4,
        'Algumas transações anteriores com este cliente (3 a 9)': 0.25,
        'Altíssima movimentação anual (>10k/ano)': 0.3,
        'Alta movimentação anual (5k-10k/ano)': 0.2,
        'Movimentação anual razoável (1k-5k/ano)': 0.1,
    }

    pesos_ruins = {
        'Empresa com status inativo': 0.4,
        'Mudança de status recente': 0.2,
        'Empresa muito recente (menos de 5 anos)': 0.3,
        'Data de registro inválida': 0.3,
        'Capital social ausente': 0.2,
        'Capital social muito baixo (<100k)': 0.3,
        'Capital social baixo (100k - 500k)': 0.15,
        'Nenhuma transação comum com este cliente': 0.3,
        'Quantidade de transações comuns ausente': 0.3,
        'Baixa movimentação anual (<1k/ano)': 0.3,
        'Dados insuficientes para análise de movimentação anual': 0.2,
        'Sem transações totais para análise de proporção': 0.2,
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

df = pd.read_csv('informacao_cnpj_lapidado.csv')

resultados = df.apply(calcular_confiabilidade, axis=1, result_type='expand')

df_resultado = pd.concat([df, resultados], axis=1)

df_resultado.to_csv('resultado_confiabilidade.csv', index=False)
