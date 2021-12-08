import { fireEvent, screen, act, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { setupServer } from 'msw/node';
import { loginHandlers } from './mocks/handlers';
import renderFromRoute from './utils/renderFromRoute';
import { setupTests } from './utils/setupTestUtils';

const USERNAME = 'Hijitus';
const PHONE = '123456789';
const EMAIL = 'hijitus@itba.edu.ar';
const PASSWORD = 'asdfghjkl';

const server = setupServer(
    ...loginHandlers
);
  
setupTests(server);

test('register success', async () => {
    renderFromRoute('/register');
    
    await act(async () => {        
        fireEvent.change(screen.getByTestId('username-register-input'), {
            target: { value: USERNAME },
        });
        fireEvent.change(screen.getByTestId('phone-register-input'), {
            target: { value: PHONE },
        });
        fireEvent.change(screen.getByTestId('email-register-input'), {
            target: { value: EMAIL },
        });
        fireEvent.change(screen.getByTestId('password-register-input'), {
            target: { value: PASSWORD },
        });
        fireEvent.change(screen.getByTestId('passrepeat-register-input'), {
            target: { value: PASSWORD },
        });
    });

    userEvent.click(screen.getByTestId('submit-register-input'));
        
    expect(await screen.findByText('Registered successfully!')).toBeInTheDocument();
});

test('register with no fields filled', async () => {
    renderFromRoute('/register');
  
    userEvent.click(screen.getByTestId('submit-register-input'));
  
    expect(await screen.findAllByText('This field is required')).toHaveLength(5);
});

test('register with invalid data', async () => {
    renderFromRoute('/register');

    await act(async () => {
        fireEvent.change(screen.getByTestId('phone-register-input'), {
            target: { value: 'asdfghjkl' },
        });
        fireEvent.change(screen.getByTestId('email-register-input'), {
            target: { value: 'asdfghjkl' },
        });
        fireEvent.change(screen.getByTestId('password-register-input'), {
            target: { value: 'aaaa' },
        });
        fireEvent.change(screen.getByTestId('passrepeat-register-input'), {
            target: { value: 'bbbb' },
        });
    }); 

    userEvent.click(screen.getByTestId('submit-register-input'));

    expect(await screen.findByText('Please enter a valid phone number')).toBeInTheDocument();
    expect(await screen.findByText('Please enter a valid email address')).toBeInTheDocument();
    expect(await screen.findByText('Please enter at least 8 characters')).toBeInTheDocument();
    expect(await screen.findByText("Passwords doesn't match")).toBeInTheDocument();
});

