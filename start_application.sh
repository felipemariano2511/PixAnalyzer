#!/usr/bin/env bash
set -e

echo "## 🧱 Build dos Módulos Java"

echo "-> dict-api/DictApi"
pushd dict-api/DictApi
./mvnw clean package -DskipTests
popd

echo "-> java-service/PixAnalyzer"
pushd java-service/PixAnalyzer
./mvnw clean package -DskipTests
popd

echo "-> java-service/KeysFrontApi"
pushd java-service/KeysFrontApi
./mvnw clean package -DskipTests
popd

echo "-> registro-nacional/DadosGov"
pushd registro-nacional/DadosGov
./mvnw clean package -DskipTests
popd

echo "-> registro-nacional/ReceitaFederamCNPJ"
pushd registro-nacional/ReceitaFederamCNPJ
./mvnw clean package -DskipTests
popd

echo
echo "## 🤖 Treino dos Modelos de IA"

# PJ
if [ -f "./python-ia/ai_analyze/data/cnpj/confidence_model.pkl" ]; then
  echo "Modelo Pessoa Jurídica já existe, pulando treino..."
else
  echo "Executando treino do modelo de Pessoa Jurídica..."
  pushd python-ia/ai_analyze/data/cnpj
  python train_model_pj.py
  popd
fi

# PF
if [ -f "./python-ia/ai_analyze/data/cpf/confidence_model.pkl" ]; then
  echo "Modelo Pessoa Física já existe, pulando treino..."
else
  echo "Executando treino do modelo de Pessoa Física..."
  pushd python-ia/ai_analyze/data/cpf
  python train_model_pf.py
  popd
fi

echo
echo "## 🐳 Subindo containers Docker"

echo "-> db-postgres"
docker compose up db-postgres -d

echo "-> todos os serviços"
docker compose up -d

echo
echo "### ✅ Tudo concluído com sucesso!"
