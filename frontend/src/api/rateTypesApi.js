import hireNetApi from './hirenetApi';

export const getRateTypesRequest = () => {
  return hireNetApi.get('/rate-types');
};

export const getRateTypeByIdRequest = (id) => {
  return hireNetApi.get(`/rate-types/${id}`);
};
