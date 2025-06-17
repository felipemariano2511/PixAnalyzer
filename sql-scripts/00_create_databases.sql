SELECT 'CREATE DATABASE dict_api'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'dict_api')
\gexec

SELECT 'CREATE DATABASE pix_analyzer'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'pix_analyzer')
\gexec

SELECT 'CREATE DATABASE receita_federal_cnpj'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'receita_federal_cnpj')
\gexec

SELECT 'CREATE DATABASE dados_gov'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'dados_gov')
\gexec

SELECT 'CREATE DATABASE keys_front'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'keys_front')
\gexec