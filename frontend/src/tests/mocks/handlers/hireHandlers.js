import { rest } from 'msw';
import {
  GENERIC_MOCKED_DATA,
  HIRE_MOCKED_DATA,
  LOGIN_MOCKED_DATA,
  MY_CONTRACTS_MOCKED_DATA,
} from '../mocked-models';

const hireHandlersMsw = [
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
  rest.get('/api/job-posts/:jobId', (_, res, ctx) =>
    res(ctx.json(HIRE_MOCKED_DATA.JOB_POST))
  ),
  rest.get('/api/job-posts/:jobId/packages/:packageId', (_, res, ctx) =>
    res(ctx.json(HIRE_MOCKED_DATA.JOB_PACKAGE))
  ),
  rest.get('/api/users', (_, res, ctx) =>
    res(ctx.json(LOGIN_MOCKED_DATA.USER))
  ),
  rest.get('/api/users/:id', (_, res, ctx) =>
    res(ctx.json(HIRE_MOCKED_DATA.PRO_USER))
  ),
  rest.get('/api/contracts', (_, res, ctx) =>
    res(ctx.json(MY_CONTRACTS_MOCKED_DATA))
  ),
  rest.post('/api/contracts', (req, res, ctx) => {
    if (
      req.body.clientId &&
      req.body.description &&
      req.body.jobPackageId &&
      req.body.scheduledDate
    ) {
      return res((res) => {
        res.status = 201;
        res.headers.set('Location', '/api/contracts/1');
        return res;
      });
    } else {
      return res(ctx.status(400), ctx.json({}));
    }
  }),
];

export default hireHandlersMsw;
