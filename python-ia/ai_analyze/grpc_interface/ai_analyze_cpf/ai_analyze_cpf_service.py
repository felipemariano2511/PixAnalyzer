import grpc
from concurrent import futures
from datetime import datetime
from grpc_interface.ai_analyze_cpf import ai_analyze_cpf_pb2
from grpc_interface.ai_analyze_cpf import ai_analyze_cpf_pb2_grpc

def calcular_confiabilidade(row):
    motivos_bons = []
    motivos_ruins = []

    def verifica_status(status):
        if status != 'ATIVO':
            motivos_ruins.append('Pessoa com status inativo')
        else:
            motivos_bons.append('Pessoa ativa')

    def analisa_idade(birth_date_str):
        try:
            birth_date = datetime.strptime(birth_date_str, "%Y-%m-%d")
            idade = (datetime.now() - birth_date).days // 365
            if idade >= 60:
                motivos_bons.append('Pessoa com mais de 60 anos')
            elif idade < 18:
                motivos_ruins.append('Pessoa muito jovem')
            return idade
        except:
            motivos_ruins.append('Data de nascimento inválida')
            return None

    def analisa_data_registro(data_str):
        try:
            data = datetime.strptime(data_str, "%Y-%m-%d")
            anos = (datetime.now() - data).days / 365
            if anos >= 10:
                motivos_bons.append('Registro com mais de 10 anos')
            elif anos < 2:
                motivos_ruins.append('Registro muito recente')
            return anos
        except:
            motivos_ruins.append('Data de registro inválida')
            return None

    def analisa_transacoes_comuns(qtd):
        if qtd is None or qtd == 0:
            motivos_ruins.append('Nenhuma transação comum com este cliente')
        elif qtd > 10:
            motivos_bons.append('Várias transações anteriores com este cliente')

    def analisa_proporcao(common, total):
        if total == 0:
            motivos_ruins.append('Sem transações totais para análise de proporção')
            return
        proporcao = (common or 0) / total
        if proporcao > 0.5:
            motivos_bons.append('Alta proporção de transações com este cliente')
        elif proporcao < 0.1:
            motivos_ruins.append('Baixa proporção de transações com este cliente')

    def analisa_movimentacao_anual(total, anos):
        if total is None or anos is None or anos == 0:
            motivos_ruins.append('Dados insuficientes para movimentação anual')
            return
        media = total / anos
        if media > 5000:
            motivos_bons.append('Alta movimentação anual')
        elif media <= 1000:
            motivos_ruins.append('Movimentação anual muito baixa')

    verifica_status(row.get('status'))
    idade = analisa_idade(row.get('birth_date'))
    idade_registro = analisa_data_registro(row.get('registration_date'))
    analisa_transacoes_comuns(row.get('common_transfers_client'))
    analisa_proporcao(row.get('common_transfers_client'), row.get('all_transfers'))
    analisa_movimentacao_anual(row.get('all_transfers'), idade_registro)

    pesos_bons = {
        'Pessoa ativa': 0.3,
        'Pessoa com mais de 60 anos': 0.15,
        'Registro com mais de 10 anos': 0.2,
        'Várias transações anteriores com este cliente': 0.3,
        'Alta proporção de transações com este cliente': 0.15,
        'Alta movimentação anual': 0.2,
    }

    pesos_ruins = {
        'Pessoa com status inativo': 0.4,
        'Pessoa muito jovem': 0.25,
        'Data de nascimento inválida': 0.3,
        'Data de registro inválida': 0.3,
        'Registro muito recente': 0.25,
        'Nenhuma transação comum com este cliente': 0.25,
        'Baixa proporção de transações com este cliente': 0.25,
        'Sem transações totais para análise de proporção': 0.2,
        'Dados insuficientes para movimentação anual': 0.3,
        'Movimentação anual muito baixa': 0.35,
    }

    score = 0.5 + sum(pesos_bons.get(m, 0) for m in motivos_bons) - sum(pesos_ruins.get(m, 0) for m in motivos_ruins)
    score = max(0, min(1, score))

    if score < 0.4:
        motivos = motivos_ruins
    elif score < 0.7:
        motivos = motivos_ruins + motivos_bons
    else:
        motivos = motivos_bons

    return {'confidence_score': round(score, 2), 'fraud_reasons': motivos}

class AiAnalyzeCpfService(ai_analyze_cpf_pb2_grpc.AiAnalyzeCpfServiceServicer):
    def AiAnalyzeCpf(self, request, context):
        dados = {
            'id': request.id,
            'origin_client_id': request.origin_client_id,
            'destination_key_value': request.destination_key_value,
            'amount': request.amount,
            'timestamp': request.timestamp,
            'key': request.key,
            'key_type': request.key_type,
            'institution': request.institution,
            'branch': request.branch,
            'account_number': request.account_number,
            'account_type': request.account_type,
            'owner_type': request.owner_type,
            'owner_name': request.owner_name,
            'cpf': request.cpf,
            'full_name': request.full_name,
            'birth_date': request.birth_date,
            'registration_date': request.registration_date,
            'status': request.status,
            'common_transfers_client': request.common_transfers_client,
            'all_transfers': request.all_transfers,
        }

        print(dados)

        resultado = calcular_confiabilidade(dados)

        print("Resultado ===================")
        print(resultado)

        return ai_analyze_cpf_pb2.AiAnalyzeCpfResponse(
            confidence_score=resultado['confidence_score'],
            fraud_reasons=resultado['fraud_reasons']
        )
