import { rest } from 'msw';

export const handlers = [
  rest.post('/login', null),
  rest.get('/api/zones', null),
  rest.get('/api/contracts/states', null),
  rest.get('/api/job-cards', null),
  rest.get('/api/categories', null),
  rest.get('/api/job-cards/order-params', null),
  rest.get('/api/rate-types', null),
];
