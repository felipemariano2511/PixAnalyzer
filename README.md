# 📌 Projeto: Análise de Confiabilidade de Chaves Pix com IA

## 🚀 Descrição Geral

Este projeto tem como objetivo analisar a confiabilidade de chaves Pix (CPF, CNPJ, telefone, e-mail, etc) utilizando uma Inteligência Artificial. A aplicação recebe dados de transações Pix, processa essas informações e retorna ao usuário um score de confiabilidade, junto com os motivos para eventuais suspeitas de fraude.

---

## 🖥️ Apresentação

**Título:** Análise de Confiabilidade de Chaves Pix com IA  
**Desenvolvido por:**  
[Felipe Mariano](https://github.com/felipemariano2511),  
[Matheus Henrique](https://github.com/MatheusHAB),  
[Ramon Romano](https://github.com/ramon-romano) e  
[Vinícius Stencel](https://github.com/viniciusstencel).

---

## ⚠️ Problema

- Em janeiro, 324.752 fraudes via Pix foram confirmadas no Brasil, cerca de 8 vítimas por minuto.  
- Fraudes com empresas falsas usando Pix ocorrem diariamente, e as soluções atuais só reagem depois do prejuízo.

---

## 💡 Solução

- Desenvolvemos o PixAnalyzer, sistema inteligente que analisa a confiabilidade da chave Pix antes da transação.  
- Coleta dados públicos e comportamentais, envia para uma IA treinada com milhares de casos reais.  
- A IA gera um score de risco e motivos de alerta, permitindo que o usuário seja avisado antes de confirmar o pagamento.

---

## 🌟 Impactos e Benefícios

- Usuários ganham proteção e confiança nas transações Pix.  
- Instituições financeiras reduzem fraudes, prejuízos e fortalecem sua reputação e inovação.  
- Resultado: menos fraudes, clientes mais seguros e instituições mais competitivas.

---

## 🚀 Diferenciais

- Atua antes da fraude, emitindo alertas em tempo real e evitando prejuízos.  
- Aumenta a segurança e transparência, melhorando a experiência do usuário e fidelização.

---

## 📦 Estrutura de Pastas Principal

```
├── backend/               # Aplicação Spring Boot (Java 21)
├── frontend/              # Aplicação React
├── ai-engine/             # Serviço de IA em Python
├── nginx/                 # Configuração de proxy reverso
├── db/                    # Configurações de banco de dados
├── docker-compose.yml     # Orquestração dos containers
├── CONFIG.md              # Guia completo de instalação e configuração
└── README.md              # Este documento
```

---

## ⚙️ Guia de Instalação e Configuração

Para instruções completas de instalação e preparação do ambiente, consulte o [CONFIG.md](./CONFIG.md).

---

## 🎯 Funcionalidades Principais

- Análise preditiva de confiabilidade de chaves Pix.  
- Score de confiança com motivos detalhados para suspeitas.  
- Integração backend + frontend + IA via Docker.  
- Painel web para visualização e análise de transações.

---

## 🛠️ Como Contribuir

1. Fork o projeto.  
2. Crie sua branch: `git checkout -b minha-feature`  
3. Faça suas alterações.  
4. Commit suas alterações: `git commit -m 'feat: Minha nova feature'`  
5. Push para sua branch: `git push origin minha-feature`  
6. Abra um Pull Request.

---

**Desenvolvido por:**  
[Felipe Mariano](https://github.com/felipemariano2511),  
[Matheus Henrique](https://github.com/MatheusHAB),  
[Ramon Romano](https://github.com/ramon-romano) e  
[Vinícius Stencel](https://github.com/viniciusstencel).
