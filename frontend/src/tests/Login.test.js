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

  await act(async () => {
    fireEvent.change(screen.getByTestId('email-login-input'), {
      target: { value: EMAIL },
    });
    fireEvent.change(screen.getByTestId('password-login-input'), {
      target: { value: '12341234' },
    });
    userEvent.click(await screen.findByText('Log in'));
  });
  
  await screen.findByText('My Contracts');
});

test('login with wrong user', async () => {
  renderFromRoute('/login');

  await act(async () => {
    fireEvent.change(screen.getByTestId('email-login-input'), {
      target: { value: 'hijitus@gmail.com' },
    });
    fireEvent.change(screen.getByTestId('password-login-input'), {
      target: { value: '12341234' },
    });
    userEvent.click(await screen.findByText('Log in'));
  });
  
  await screen.findByText('User or password invalid');
});

test('login with invalid email format', async () => {
  renderFromRoute('/login');

  await act(async () => {
    fireEvent.change(screen.getByTestId('email-login-input'), {
      target: { value: 'abcdefg' },
    });
    userEvent.click(await screen.findByText('Log in'));
  });

  await screen.findByText('Please enter a valid email address');
});

test('login with no fields filled', async () => {
  renderFromRoute('/login');

  await act(async () => {
    userEvent.click(await screen.findByText('Log in'));
  });

  expect(await screen.findAllByText('This field is required')).toHaveLength(2);
});

test('go to register', async () => {
  renderFromRoute('/login');

  await act(async () => {
    userEvent.click(await screen.findByText('Register now'));
  });

  await screen.findByText('Create an account in HireNet');
});

test('go to recover password', async () => {
  renderFromRoute('/login');

  await act(async () => {
    userEvent.click(await screen.findByText('Recover it'));
  });

  await screen.findByText('Recover password');
});
