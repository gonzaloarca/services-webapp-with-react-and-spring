import { rest } from 'msw';
import { GENERIC_MOCKED_DATA, LOGIN_MOCKED_DATA } from '../mocked-models';

const loginHandlersMsw = [
  rest.post('/api/login', (req, res, ctx) => {
    if (req.body.email === LOGIN_MOCKED_DATA.EMAIL) {
      return res((res) => {
        res.status = 200;
        res.headers.set('Authorization', `Bearer ${LOGIN_MOCKED_DATA.TOKEN}`);
        return res;
      });
    } else {
      return res(ctx.status(400), ctx.json({}));
    }
  }),
  rest.get(`/api/users`, (req, res, ctx) => {
    return res(ctx.json(LOGIN_MOCKED_DATA.USER));
  }),
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

export default loginHandlersMsw;
