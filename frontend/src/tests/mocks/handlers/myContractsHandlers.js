import { rest } from 'msw';
import { GENERIC_MOCKED_DATA, LOGIN_MOCKED_DATA, MY_CONTRACTS_MOCKED_DATA } from '../mocked-models';

const myContractsHandlersMsw = [
  rest.get(`/api/users`, (req, res, ctx) => {
    return res(ctx.json(LOGIN_MOCKED_DATA.USER));
  }),
  rest.get(`/api/users/:id`, (req, res, ctx) => {
    return res(ctx.json(LOGIN_MOCKED_DATA.USER));
  }),
  rest.get('/api/zones', (_, res, ctx) =>
    res(ctx.json(GENERIC_MOCKED_DATA.ZONES))
  ),
  rest.get('/api/contracts/states', (_, res, ctx) =>
    res(ctx.json(GENERIC_MOCKED_DATA.STATES))
  ),
  rest.get('/api/categories', (_, res, ctx) =>
    res(ctx.json(GENERIC_MOCKED_DATA.CATEGORIES))
  ),
  rest.get('/api/job-cards/order-params', (_, res, ctx) =>
    res(ctx.json(GENERIC_MOCKED_DATA.ORDER_PARAMS))
  ),
  rest.get('/api/rate-types', (_, res, ctx) =>
    res(ctx.json(GENERIC_MOCKED_DATA.RATE_TYPES))
  ),
  rest.get('/api/contracts', (req, res, ctx) => {
    switch (req.url.searchParams.get('state')) {
      case 'pending':
        return res(ctx.json(MY_CONTRACTS_MOCKED_DATA.PENDING));
      case 'active':
        return res(ctx.json(MY_CONTRACTS_MOCKED_DATA.ACTIVE));
      case 'finalized':
      default:
        return res(ctx.json(MY_CONTRACTS_MOCKED_DATA.FINALIZED));
    }
  }),
  rest.put('/api/contracts/:id', (req, res, ctx) => {
    return res(ctx.json(MY_CONTRACTS_MOCKED_DATA.UPDATE_DATA));
  }),
];

export default myContractsHandlersMsw;
