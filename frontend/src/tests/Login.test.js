import { fireEvent, screen, waitFor, act } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { setupServer } from 'msw/node';
import { loginHandlers } from './mocks/handlers';
import renderFromRoute from './utils/renderFromRoute';
import { setupTests } from './utils/setupTestUtils';

const EMAIL = 'fquesada@itba.edua.ar';

const server = setupServer(
  ...loginHandlers
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

test('login with wrong user', async () => {
  renderFromRoute('/login');

  fireEvent.change(screen.getByTestId('email-login-input'), {
    target: { value: 'hijitus@gmail.com' },
  });
  fireEvent.change(screen.getByTestId('password-login-input'), {
    target: { value: '12341234' },
  });

  userEvent.click(screen.getByText('Log in'));

  expect(await screen.findByText('User or password invalid')).toBeInTheDocument();
});

test('login with invalid email format', async () => {
  renderFromRoute('/login');

  fireEvent.change(screen.getByTestId('email-login-input'), {
    target: { value: 'abcdefg' },
  });

  userEvent.click(screen.getByText('Log in'));

  expect(await screen.findByText('Please enter a valid email address')).toBeInTheDocument();
});

test('login with no fields filled', async () => {
  renderFromRoute('/login');

  userEvent.click(screen.getByText('Log in'));

  expect(await screen.findAllByText('This field is required')).toHaveLength(2);
});

test('go to register', async () => {
  renderFromRoute('/login');

  userEvent.click(screen.getByText('Register now'));

  expect(await screen.findByText('Create an account in HireNet')).toBeInTheDocument();
});

test('go to recover password', async () => {
  renderFromRoute('/login');

  userEvent.click(screen.getByText('Recover it'));

  expect(await screen.findByText('Recover password')).toBeInTheDocument();
});
