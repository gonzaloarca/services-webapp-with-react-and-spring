import { rest } from 'msw';
import { GENERIC_MOCKED_DATA, LOGIN_MOCKED_DATA } from '../mocked-models';

const publishHandlersMsw = [
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
  rest.post('/api/job-posts', (req, res, ctx) => {
    if (
      req.body.availableHours &&
      req.body.jobType &&
      req.body.title &&
      req.body.zones &&
      req.body.zones.length > 0
    ) {
      return res((res) => {
        res.status = 201;
        res.headers.set('Location', '/api/job-posts/1');
        return res;
      });
    } else {
      return res(ctx.status(400), ctx.json({}));
    }
  }),

  rest.post('/api/job-posts/1/packages', (req, res, ctx) => {
    if (
      req.body.description &&
      req.body.price &&
      req.body.rateType &&
      req.body.title
    ) {
      return res((res) => {
        res.status = 201;
        res.headers.set('Location', '/api/packages/1');
        return res;
      });
    } else {
      return res(ctx.status(400), ctx.json({}));
    }
  }),
];

export default publishHandlersMsw;
