import axios from 'axios';

const api = axios.create({
  baseURL: 'https://felipemariano.com.br/api/pix-analyzer/transferencia/pix',
});

export const buscarDadosDaChavePix = (destinationKeyValue, originClientId) => {
  return api.post('/info-chave-pix', { destinationKeyValue, originClientId });
};

export const consultarAvaliacaoIA = (destinationKeyValue, originClientId, amout, description) => {
  return api.post('/analisar', { destinationKeyValue, originClientId, amout, description });
};

export const realizarTransferenciaPix = (chavePix, valor, usuario) => {
  return api.post('/transferir', { chavePix, valor, usuario });
};

export default api;
