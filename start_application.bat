@echo off
setlocal enabledelayedexpansion

echo ====================================
echo Build dos Modulos Java
echo ====================================

echo.
echo -> dict-api\DictApi
cd dict-api\DictApi || (echo Erro ao acessar dict-api\DictApi & exit /b)
call mvnw.cmd clean package -DskipTests
cd ..\..

echo.
echo -> java-service\PixAnalyzer
cd java-service\PixAnalyzer || (echo Erro ao acessar java-service\PixAnalyzer & exit /b)
call mvnw.cmd clean package -DskipTests
cd ..\..

echo.
echo -> java-service\KeysFrontApi
cd java-service\KeysFrontApi || (echo Erro ao acessar java-service\KeysFrontApi & exit /b)
call mvnw.cmd clean package -DskipTests
cd ..\..

echo.
echo -> registro-nacional\DadosGov
cd registro-nacional\DadosGov || (echo Erro ao acessar registro-nacional\DadosGov & exit /b)
call mvnw.cmd clean package -DskipTests
cd ..\..

echo.
echo -> registro-nacional\ReceitaFederamCNPJ
cd registro-nacional\ReceitaFederamCNPJ || (echo Erro ao acessar registro-nacional\ReceitaFederamCNPJ & exit /b)
call mvnw.cmd clean package -DskipTests
cd ..\..

echo ====================================
echo Treino dos Modelos de IA
echo ====================================

REM Treino Pessoa Juridica (PJ)
IF EXIST python-ia\ai_analyze\data\cnpj\confidence_model.pkl (
    echo Modelo Pessoa Juridica ja existe, pulando treino...
) ELSE (
    echo Executando treino do modelo de Pessoa Juridica...
    cd python-ia\ai_analyze\data\cnpj || (echo Erro ao acessar pasta cnpj & exit /b)
    python train_model_pj.py
    cd ..\..\..\..
)

echo.

REM Treino Pessoa Fisica (PF)
IF EXIST python-ia\ai_analyze\data\cpf\confidence_model.pkl (
    echo Modelo Pessoa Fisica ja existe, pulando treino...
) ELSE (
    echo Executando treino do modelo de Pessoa Fisica...
    cd python-ia\ai_analyze\data\cpf || (echo Erro ao acessar pasta cpf & exit /b)
    python train_model_pf.py
    cd ..\..\..\..
)

echo ====================================
echo Subindo containers Docker
echo ====================================

echo -> db-postgres
docker compose up db-postgres -d

echo -> todos os servicos
docker compose up -d

echo.
echo ====================================
echo Tudo concluido com sucesso!
echo ====================================

endlocal
pause
