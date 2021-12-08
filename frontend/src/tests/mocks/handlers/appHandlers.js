import { rest } from 'msw';
import { GENERIC_MOCKED_DATA } from '../mocked-models';

const appHandlersMsw = [
  rest.get('/api/zones', (_, res, ctx) =>
    res(ctx.json(GENERIC_MOCKED_DATA.ZONES))
  ),
  rest.get('/api/contracts/states', (_, res, ctx) =>
    res(ctx.json(GENERIC_MOCKED_DATA.STATES))
  ),
  rest.get('/api/job-cards', (_, res, ctx) =>
    res(ctx.json(GENERIC_MOCKED_DATA.JOB_CARDS))
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
];

export default appHandlersMsw;
