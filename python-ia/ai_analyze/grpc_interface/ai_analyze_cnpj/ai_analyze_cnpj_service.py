import grpc
from concurrent import futures
import pandas as pd
from datetime import datetime
from grpc_interface.ai_analyze_cnpj import ai_analyze_cnpj_pb2
from grpc_interface.ai_analyze_cnpj import ai_analyze_cnpj_pb2_grpc

def calcular_confiabilidade(row):
    motivos_bons = []
    motivos_ruins = []

    def verifica_status(status):
        if status != 'ATIVA':
            motivos_ruins.append('Empresa com status inativo')
        else:
            motivos_bons.append('Empresa ativa')

    def analisa_data_registro(data_registro_str):
        if not isinstance(data_registro_str, str) or not data_registro_str.strip():
            motivos_ruins.append('Data de registro inválida')
            return None
        try:
            data_registro = datetime.strptime(data_registro_str.strip(), "%Y-%m-%d")
            idade = (datetime.now() - data_registro).days / 365
            if idade >= 10:
                motivos_bons.append('Empresa com mais de 10 anos de existência')
            elif idade < 10:
                motivos_ruins.append('Empresa muito recente')
            return idade
        except Exception:
            motivos_ruins.append('Data de registro inválida')
            return None

    def analisa_capital_social(capital):
        if capital is None:
            motivos_ruins.append('Capital social ausente')
        elif capital < 500000:
            motivos_ruins.append('Capital social muito baixo')
        elif capital > 500000:
            motivos_bons.append('Capital social elevado')

    def analisa_transacoes_comuns(quantidade):
        if quantidade is None:
            motivos_ruins.append('Quantidade de transações comuns ausente')
        elif quantidade == 0:
            motivos_ruins.append('Nenhuma transação comum com este cliente')
        elif quantidade > 10:
            motivos_bons.append('Várias transações anteriores com este cliente')

    def analisa_movimentacao_anual(all_transfers, idade_anos):
        if all_transfers is None or idade_anos is None or idade_anos == 0:
            motivos_ruins.append('Dados insuficientes para análise de movimentação anual')
            return
        media_transacoes_ano = all_transfers / idade_anos
        if media_transacoes_ano > 5000:
            motivos_bons.append('Alta movimentação anual')
        elif media_transacoes_ano > 1000:
            motivos_bons.append('Média movimentação anual')
        elif media_transacoes_ano <= 1000:
            motivos_ruins.append('Muito baixa movimentação anual')

    def analisa_proporcao(common_transfers, all_transfers):
        if all_transfers is None or all_transfers == 0:
            motivos_ruins.append('Sem transações totais para análise de proporção')
            return
        proporcao = (common_transfers or 0) / all_transfers
        if proporcao > 0.5:
            motivos_bons.append('Proporção alta de transações com este cliente')
        elif proporcao < 0.1:
            motivos_ruins.append('Proporção baixa de transações com este cliente')

    verifica_status(row.get('status'))
    idade_anos = analisa_data_registro(row.get('registration_date'))
    analisa_capital_social(row.get('share_capital'))
    analisa_transacoes_comuns(row.get('common_transfers_client'))
    analisa_proporcao(row.get('common_transfers_client'), row.get('all_transfers'))
    analisa_movimentacao_anual(row.get('all_transfers'), idade_anos)

    pesos_bons = {
        'Empresa ativa': 0.3,
        'Empresa com mais de 5 anos de existência': 0.2,
        'Capital social elevado': 0.15,
        'Várias transações anteriores com este cliente': 0.3,
        'Alta movimentação anual': 0.2,
        'Média movimentação anual': 0.05,
        'Proporção alta de transações com este cliente': 0.15,
    }

    pesos_ruins = {
        'Empresa com status inativo': 0.4,
        'Empresa muito recente': 0.3,
        'Capital social muito baixo': 0.3,
        'Nenhuma transação comum com este cliente': 0.25,
        'Muito baixa movimentação anual': 0.35,
        'Proporção baixa de transações com este cliente': 0.25,
        'Data de registro inválida': 0.4,
        'Capital social ausente': 0.2,
        'Quantidade de transações comuns ausente': 0.2,
        'Dados insuficientes para análise de movimentação anual': 0.3,
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

    return {'confidence_score': round(score, 2), 'fraud_reasons': motivos}


class AiAnalyzeCnpjService(ai_analyze_cnpj_pb2_grpc.AiAnalyzeCnpjServiceServicer):
    def AiAnalyzeCnpj(self, request, context):
        dados = {
            'id': request.id,
            'originClientId': request.origin_client_id,
            'destinationKeyValue': request.destination_key_value,
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
            'cnpj': request.cnpj,
            'company_name': request.company_name,
            'registration_date': request.registration_date,
            'status': request.status,
            'status_date': request.status_date,
            'share_capital': request.share_capital,
            'branch_type': request.branch_type,
            'common_transfers_client': request.common_transfers_client,
            'all_transfers': request.all_transfers,
        }

        resultado = calcular_confiabilidade(dados)

        return ai_analyze_cnpj_pb2.AiAnalyzeCnpjResponse(
            confidence_score=resultado['confidence_score'],
            fraud_reasons=resultado['fraud_reasons']
        )