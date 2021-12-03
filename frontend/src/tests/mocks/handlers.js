import { rest } from 'msw';

const EMAIL = 'fquesada@itba.edua.ar';

const TOKEN =
  'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmcXVlc2FkYUBpdGJhLmVkdWEuYXIiLCJpc3MiOiJoaXJlbmV0LmNvbSIsImlhdCI6MTYzODU2MDcwNywiZXhwIjoxNjM5MTY1NTA3fQ.Jwn8pIQDylfKPwHONwwdTptA2mLvJ9SJAjgBjqEJRzkZ5BkXWyU7b6WMPrcayk6IlFpwPeEnzCqJd7bdcDgCgQ';

export const handlers = [
  rest.post('/api/login', (req, res, ctx) => {
    if (req.body.email === EMAIL) {
      return res((res) => {
        res.status = 200;
        res.headers.set('Authorization', TOKEN);
        return res;
      });
    } else {
      return res(ctx.status(400), ctx.json({}));
    }
  }),
  rest.get(`/api/users`, (req, res, ctx) => {
    return res(
      ctx.json({
        'contracts':
          'http://localhost:8080/paw-2021a-03/api/contracts?userId=1',
        'email': EMAIL,
        'id': 1,
        'image': 'http://localhost:8080/paw-2021a-03/api/users/1/image',
        'phone': '12345678',
        'roles': ['CLIENT'],
        'username': 'Francisco Quesada',
      })
    );
  }),
  rest.get('/api/zones', null),
  rest.get('/api/contracts/states', null),
  rest.get('/api/job-cards', null),
  rest.get('/api/categories', null),
  rest.get('/api/job-cards/order-params', null),
  rest.get('/api/rate-types', null),
];
