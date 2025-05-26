FROM nginx:alpine

# Criação do diretório para armazenar os certificados
RUN mkdir -p /etc/nginx/ssl

# Copia o arquivo de configuração do NGINX
COPY default.conf /etc/nginx/conf.d/default.conf

# Copia os certificados para o diretório de SSL do NGINX
COPY ssl/fullchain.pem /etc/nginx/ssl/fullchain.pem
COPY ssl/privkey.pem /etc/nginx/ssl/privkey.pem
