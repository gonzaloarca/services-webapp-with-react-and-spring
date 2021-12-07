import { fireEvent, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { setupServer } from 'msw/node';
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
