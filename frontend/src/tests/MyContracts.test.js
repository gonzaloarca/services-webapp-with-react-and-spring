import { fireEvent, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { setupServer } from 'msw/node';
import { myContractsHandlers } from './mocks/handlers';
import renderFromRoute from './utils/renderFromRoute';
import { setupTests } from './utils/setupTestUtils';
import { LOGIN_MOCKED_DATA } from './mocks/mocked-models';
import { act, waitFor, within } from '@testing-library/react';

const server = setupServer(
  ...myContractsHandlers
);

setupTests(server);

test('pending tab displays title', async () => {
    sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);

  renderFromRoute('/my-contracts/pro/pending');

  expect(await screen.findByText('Pending approval contracts')).toBeInTheDocument();

  expect(await screen.findByText('Servicio de Limpieza 24hs/7 dias')).toBeInTheDocument();
});

test('on click details button opens modal', async () => {
  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute('/my-contracts/pro/pending');
  userEvent.click(await screen.findByText('Details'));

  expect(await screen.findByText('Job details')).toBeInTheDocument();
});

test('on click contact button opens modal', async () => {
  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute('/my-contracts/pro/pending');
  userEvent.click(await screen.findByText('Contact'));

  expect(await screen.findByText('Contact information')).toBeInTheDocument();
});

test('on click reschedule button opens modal', async () => {
  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute('/my-contracts/pro/pending');
  userEvent.click(await screen.findByText('Reschedule'));

  expect(await screen.findByText('Are you sure that you want to reschedule this contract?')).toBeInTheDocument();
});

test('on click cancel button opens modal', async () => {
  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute('/my-contracts/pro/pending');
  userEvent.click(await screen.findByText('Cancel'));

  expect(await screen.findByText('Are you sure that you want to cancel this contract?')).toBeInTheDocument();
});

test('contract without reschedules shows reschedule button', async () => {

  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute('/my-contracts/pro/pending');

  await waitFor(async () => {
    expect(screen.queryByTestId('contract-card-60')).toBeTruthy();
  });

  const card = screen.getByTestId('contract-card-60');
  expect(await within(card).findByText('Reschedule')).toBeInTheDocument();
});

test('contract already rescheduled doesnt show reschedule button', async () => {

  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute('/my-contracts/pro/pending');

  await waitFor(async () => {
    expect(screen.queryByTestId('contract-card-36')).toBeTruthy();
  });

  const card = screen.getByTestId('contract-card-36');
  expect(within(card).queryByText('Reschedule')).not.toBeInTheDocument()
});
