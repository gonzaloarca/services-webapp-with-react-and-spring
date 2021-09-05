import { rest } from 'msw';

export const handlers = [
  rest.post('/login', (req, res, ctx) => {
    // Persist user's authentication in the session
    sessionStorage.setItem('is-authenticated', 'true');
    return res(
      // Respond with a 200 status code
      ctx.status(200)
    );
  }),
  rest.get('/api/zones', null),
  rest.get('/api/contracts/states', null),
  rest.get('/api/job-cards', null),
  rest.get('/api/categories', null),
  rest.get('/api/job-cards/order-params', null),
  rest.get('/api/rate-types', null),
];
