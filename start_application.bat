@echo off

REM Verifica se confidence_model.pkl existe para PJ
if exist ".\python-ia\ai_analyze\data\cnpj\confidence_model.pkl" (
    echo Modelo Pessoa Juridica ja existe, pulando treino...
) else (
    echo Executando treino do modelo de Pessoa Juridica...
    pushd ".\python-ia\ai_analyze\data\cnpj"
    python train_model_pj.py
    if errorlevel 1 (
        echo Erro no treino do modelo Pessoa Juridica. Abortando.
        pause
        popd
        goto :EOF
    )
    popd
)

REM Verifica se confidence_model.pkl existe para PF
if exist ".\python-ia\ai_analyze\data\cpf\confidence_model.pkl" (
    echo Modelo Pessoa Fisica ja existe, pulando treino...
) else (
    echo Executando treino do modelo de Pessoa Fisica...
    pushd ".\python-ia\ai_analyze\data\cpf"
    python train_model_pf.py
    if errorlevel 1 (
        echo Erro no treino do modelo Pessoa Fisica. Abortando.
        pause
        popd
        goto :EOF
    )
    popd
)

echo Subindo container db-postgres...
docker compose up db-postgres -d
if errorlevel 1 (
    echo Erro ao subir db-postgres. Abortando.
    pause
    goto :EOF
)

echo Subindo todos containers em modo detach...
docker compose up -d
if errorlevel 1 (
    echo Erro ao subir containers em modo detach. Abortando.
    pause
    goto :EOF
)

echo Tudo concluido com sucesso.
pause
