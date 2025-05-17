import grpc
from concurrent import futures
from grpc_interface import ai_analyze_pb2, ai_analyze_pb2_grpc


class AiAnalyzeService(ai_analyze_pb2_grpc.AiAnalyzeServiceServicer):
    def AiAnalyze(self, request, context):
        print("===== Dados recebidos via gRPC =====")
        print("ID:", request.id)
        print("EndToEnd ID:", request.endToEndId)
        print("Origin Client ID:", request.originClientId)
        print("Destination Key:", request.destinationKeyValue)
        print("Amount:", request.amount)
        print("Timestamp:", request.timestamp)
        print("Key:", request.key)
        print("Key Type:", request.key_type)
        print("Institution:", request.institution)
        print("Branch:", request.branch)
        print("Account Number:", request.account_number)
        print("Account Type:", request.account_type)
        print("Owner Type:", request.owner_type)
        print("Owner Name:", request.owner_name)
        print("CNPJ:", request.cnpj)
        print("Company Name:", request.company_name)
        print("Status:", request.status)
        print("Share Capital:", request.share_capital)
        print("Branch Type:", request.branch_type)
        print("Common Transfers Client:", request.common_transfers_client)
        print("All Transfers:", request.all_transfers)
        print("====================================")

        return ai_analyze_pb2.AiAnalyzeResponse(
            confidence_score=0.23,
            fraud_reasons=["CNPJ inválido", "ramo de atividade incoerente"]
        )

def serve():
    print("Iniciando servidor gRPC...")
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    ai_analyze_pb2_grpc.add_AiAnalyzeServiceServicer_to_server(AiAnalyzeService(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    print("Servidor gRPC iniciado na porta 50051.")
    server.wait_for_termination()


if __name__ == "__main__":
    serve()
