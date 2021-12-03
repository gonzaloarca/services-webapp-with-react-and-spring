import { ThemeProvider } from '@material-ui/styles';
import { fireEvent, render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { rest } from 'msw';
import { setupServer } from 'msw/node';
import React from 'react';
import { BrowserRouter, MemoryRouter } from 'react-router-dom';
import App from '../App';
import { ConstantDataProvider, UserContextProvider } from '../context';
import { NavBarContextProvider } from '../context/navBarContext';
import Login from '../pages/Login';
import appTheme from '../theme';
import { handlers } from './mocks/handlers';

const EMAIL = 'fquesada@itba.edua.ar';

const TOKEN =
  'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmcXVlc2FkYUBpdGJhLmVkdWEuYXIiLCJpc3MiOiJoaXJlbmV0LmNvbSIsImlhdCI6MTYzODU2MDcwNywiZXhwIjoxNjM5MTY1NTA3fQ.Jwn8pIQDylfKPwHONwwdTptA2mLvJ9SJAjgBjqEJRzkZ5BkXWyU7b6WMPrcayk6IlFpwPeEnzCqJd7bdcDgCgQ';

const server = setupServer(
  ...handlers
  // rest.post('http://localhost:8080/api/login', null),
);

beforeAll(() => {
  server.listen();
});

afterAll(() => {
  server.close();
});

test('login updates the navbar for non-professional user', async () => {
  render(
    <MemoryRouter initialEntries={['/login']}>
      <ThemeProvider theme={appTheme}>
        <UserContextProvider>
          <ConstantDataProvider>
            <NavBarContextProvider>
              <App />
            </NavBarContextProvider>
          </ConstantDataProvider>
        </UserContextProvider>
      </ThemeProvider>
    </MemoryRouter>
  );

  fireEvent.change(screen.getByTestId('email-login-input'), {
    target: { value: EMAIL },
  });
  fireEvent.change(screen.getByTestId('password-login-input'), {
    target: { value: '12341234' },
  });

  userEvent.click(screen.getByText('Log in'));

  expect(await screen.findByText('My Contracts')).toBeInTheDocument();
});
