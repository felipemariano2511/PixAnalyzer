FROM node:18-alpine AS builder

WORKDIR /app
COPY . .

RUN npm install
RUN npm install react-icons
RUN npm install react-router-dom
RUN npm install axios
RUN npm run build
