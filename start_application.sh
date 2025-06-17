#!/bin/bash

# Habilitar erro imediato se algum comando falhar
set -e

echo "Verificando modelo Pessoa Juridica..."

if [ -f "./python-ia/ai_analyze/data/cnpj/confidence_model.pkl" ]; then
    echo "Modelo Pessoa Jurídica ja existe, pulando treino..."
else
    echo "Executando treino do modelo de Pessoa Juridica..."
    cd "./python-ia/ai_analyze/data/cnpj"
    python3 train_model_pj.py
    if [ $? -ne 0 ]; then
        echo "Erro no treino do modelo Pessoa Juridica. Abortando."
        exit 1
    fi
    cd - > /dev/null
fi

echo "Verificando modelo Pessoa Fisica..."

if [ -f "./python-ia/ai_analyze/data/cpf/confidence_model.pkl" ]; then
    echo "Modelo Pessoa Fisica ja existe, pulando treino..."
else
    echo "Executando treino do modelo de Pessoa Fisica..."
    cd "./python-ia/ai_analyze/data/cpf"
    python3 train_model_pf.py
    if [ $? -ne 0 ]; then
        echo "Erro no treino do modelo Pessoa Fisica. Abortando."
        exit 1
    fi
    cd - > /dev/null
fi

echo "Subindo container db-postgres..."
docker compose up db-postgres -d
if [ $? -ne 0 ]; then
    echo "Erro ao subir db-postgres. Abortando."
    exit 1
fi

echo "Subindo todos os containers em modo detach..."
docker compose up -d
if [ $? -ne 0 ]; then
    echo "Erro ao subir os containers. Abortando."
    exit 1
fi

echo "Tudo concluido com sucesso."
