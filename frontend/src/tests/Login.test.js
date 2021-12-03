import { ThemeProvider } from '@material-ui/styles';
import { fireEvent, render, screen } from '@testing-library/react';
import { rest } from 'msw';
import { setupServer } from 'msw/node';
import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { ConstantDataProvider, UserContextProvider } from '../context';
import { NavBarContextProvider } from '../context/navBarContext';
import Login from '../pages/Login';
import appTheme from '../theme';
import { handlers } from './mocks/handlers';

const EMAIL = 'test@gmail.com';

const server = setupServer(
  ...handlers,
  rest.post('http://localhost:8080/api/login', (req, res, ctx) => {
    if (req.body.email === EMAIL) {
      return res(
        ctx.json({
          token: 'Bearer 1',
          refreshToken: 'Bearer 2',
        })
      );
    } else {
      return res(ctx.status(400), ctx.json({}));
    }
  })
);

beforeAll(() => {
  server.listen();
});

afterAll(() => {
  server.close();
});

test('login updates the navbar for non-professional user', async () => {
  render(
    <BrowserRouter basename={process.env.PUBLIC_URL}>
      <ThemeProvider theme={appTheme}>
        <UserContextProvider>
          <ConstantDataProvider>
            <NavBarContextProvider>
              <Login />
            </NavBarContextProvider>
          </ConstantDataProvider>
        </UserContextProvider>
      </ThemeProvider>
    </BrowserRouter>
  );

  fireEvent.change(screen.getByTestId('email-login-input'), {
    target: { value: EMAIL },
  });
  fireEvent.change(screen.getByTestId('password-login-input'), {
    target: { value: '12341234' },
  });

  fireEvent.click(screen.getByText('Log in'));

  expect(await screen.findByText('My Contracts')).toBeInTheDocument();
});
