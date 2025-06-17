from datetime import datetime
import joblib
import pandas as pd
from grpc_interface.ai_analyze_cpf import ai_analyze_cpf_pb2
from grpc_interface.ai_analyze_cpf import ai_analyze_cpf_pb2_grpc

score_model = joblib.load('data/cpf/confidence_model.pkl')
fraud_model = joblib.load('data/cpf/fraud_model.pkl')
mlb = joblib.load('data/cpf/fraud_labels_encoder.pkl')

def dias_desde(data_str):
    if not data_str:
        return None
    try:
        dt = datetime.fromisoformat(data_str)
        delta = datetime.now() - dt
        return delta.days
    except Exception:
        return None

def calcular_confiabilidade(row):
    df_input = pd.DataFrame([row])
    predicted_score = score_model.predict(df_input)[0]
    fraud_prediction = fraud_model.predict(df_input)
    predicted_labels = mlb.inverse_transform(fraud_prediction)[0]

    return {
        'confidence_score': float(predicted_score),
        'fraud_reasons': list(predicted_labels)
    }

class AiAnalyzeCpfService(ai_analyze_cpf_pb2_grpc.AiAnalyzeCpfServiceServicer):
    def AiAnalyzeCpf(self, request, context):
        dados = {
            'id': request.id,
            'origin_client_id': request.origin_client_id,
            'destination_key_value': request.destination_key_value,
            'amount': request.amount,

            # Datas originais
            'timestamp': request.timestamp,
            'birth_date': request.birth_date,
            'registration_date': request.registration_date,

            # Dias desde as datas
            'dias_desde_timestamp': dias_desde(request.timestamp),
            'dias_desde_birth_date': dias_desde(request.birth_date),
            'dias_desde_registration_date': dias_desde(request.registration_date),

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
            'status': request.status,
            'common_transfers_client': request.common_transfers_client,
            'all_transfers': request.all_transfers,
        }

        resultado = calcular_confiabilidade(dados)

        return ai_analyze_cpf_pb2.AiAnalyzeCpfResponse(
            confidence_score=resultado['confidence_score'],
            fraud_reasons=resultado['fraud_reasons']
        )
