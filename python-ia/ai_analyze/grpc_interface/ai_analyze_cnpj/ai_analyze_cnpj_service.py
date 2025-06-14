import joblib
import pandas as pd
from grpc_interface.ai_analyze_cnpj import ai_analyze_cnpj_pb2
from grpc_interface.ai_analyze_cnpj import ai_analyze_cnpj_pb2_grpc

# Carregar os modelos treinados
score_model = joblib.load('/app/ai_analyze/data/cnpj/confidence_model.pkl')
fraud_model = joblib.load('/app/ai_analyze/data/cnpj/fraud_model.pkl')
mlb = joblib.load('/app/ai_analyze/data/cnpj/fraud_labels_encoder.pkl')


def calcular_confiabilidade(row):
    df_input = pd.DataFrame([row])

    predicted_score = score_model.predict(df_input)[0]
    fraud_prediction = fraud_model.predict(df_input)
    predicted_labels = mlb.inverse_transform(fraud_prediction)[0]

    return {
        'confidence_score': float(predicted_score),
        'fraud_reasons': list(predicted_labels)
    }

class AiAnalyzeCnpjService(ai_analyze_cnpj_pb2_grpc.AiAnalyzeCnpjServiceServicer):
    def AiAnalyzeCnpj(self, request, context):
        dados = {
            'id': request.id,
            'originClientId': request.origin_client_id,
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