import axios from 'axios';

const api = axios.create({
  baseURL: 'https://felipemariano.com.br/api/pix-analyzer/transferencia/pix',
});

export const buscarDadosDaChavePix = (destinationKeyValue, originClientId) => {
  return api.post('/info-chave-pix', { destinationKeyValue, originClientId });
};

export const consultarAvaliacaoIA = (destinationKeyValue, originClientId, amount, description) => {
  return api.post('/analisar', { destinationKeyValue, originClientId, amount, description });
};

export const realizarTransferenciaPix = (destinationKeyValue, originClientId, amount, description) => {
  return api.post('/transferir', { destinationKeyValue, originClientId, amount, description });
};

export default api;

