import grpc
from concurrent import futures
from grpc_interface.ai_analyze_cnpj import ai_analyze_cnpj_pb2_grpc
from grpc_interface.ai_analyze_cpf import ai_analyze_cpf_pb2_grpc

from grpc_interface.ai_analyze_cnpj.ai_analyze_cnpj_service import AiAnalyzeCnpjService
from grpc_interface.ai_analyze_cpf.ai_analyze_cpf_service import AiAnalyzeCpfService

def serve():
    print("Iniciando servidor gRPC...")
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    
    # Registrar os serviços
    ai_analyze_cnpj_pb2_grpc.add_AiAnalyzeCnpjServiceServicer_to_server(AiAnalyzeCnpjService(), server)
    ai_analyze_cpf_pb2_grpc.add_AiAnalyzeCpfServiceServicer_to_server(AiAnalyzeCpfService(), server)
    
    server.add_insecure_port('[::]:50051')
    server.start()
    print("Servidor gRPC iniciado na porta 50051.")
    server.wait_for_termination()

if __name__ == "__main__":
    serve()
