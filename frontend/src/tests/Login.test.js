import { ThemeProvider } from '@material-ui/styles';
import { fireEvent, render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { setupServer } from 'msw/node';
import React from 'react';
import { MemoryRouter } from 'react-router-dom';
import App from '../App';
import { ConstantDataProvider, UserContextProvider } from '../context';
import { NavBarContextProvider } from '../context/navBarContext';
import appTheme from '../theme';
import { loginHandlers } from './mocks/handlers';
import renderFromRoute from './utils/renderFromRoute';
import { setupTests } from './utils/setupTestUtils';

const EMAIL = 'fquesada@itba.edua.ar';

const server = setupServer(
  ...loginHandlers
  // rest.post('http://localhost:8080/api/login', null),
);

setupTests(server);

test('login updates the navbar for non-professional user', async () => {
  renderFromRoute('/login');

  fireEvent.change(screen.getByTestId('email-login-input'), {
    target: { value: EMAIL },
  });
  fireEvent.change(screen.getByTestId('password-login-input'), {
    target: { value: '12341234' },
  });

  userEvent.click(screen.getByText('Log in'));

  expect(await screen.findByText('My Contracts')).toBeInTheDocument();
});
