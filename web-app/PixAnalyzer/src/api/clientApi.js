import axios from 'axios';

const api = axios.create({
  baseURL: 'https://felipemariano.com.br/api/pix-analyzer/transferencia/pix',
});

export const postBuscarDadosChavePix = (data) => api.post('/info-chave-pix', data);
export const postConsultaAvaliacaoIA = (data) => api.post('/analisar', data);
export const postRealizarTransferenciaPix = (data) => api.post('/trasferir', data);