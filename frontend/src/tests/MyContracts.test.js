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
  await act(async () => {
    sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
    renderFromRoute('/my-contracts/pro/pending');
  });

  await screen.findByText('Pending approval contracts');
});

test('on click details button opens modal', async () => {
  await act(async () => {
    sessionStorage.setItem('token', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2RyaWd1ZXptYW51ZWxqb2FxdWluQGdtYWlsLmNvbSIsImlzcyI6ImhpcmVuZXQuY29tIiwiaWF0IjoxNjM4OTA3MjYwLCJleHAiOjE2Mzk1MTIwNjB9.5Y63h98K00L-bdE0VEif80OBvD1mpYpyf5OHF0FRO9E0U_yWPBB4mIAULV466Jai6EmQAfJMPShBXhydC887qw');
    renderFromRoute('/my-contracts/pro/pending');
    userEvent.click(await screen.findByText('Details'));
  });

  await screen.findByText('Job details');
});

test('on click contact button opens modal', async () => {
  await act(async () => {
    sessionStorage.setItem('token', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2RyaWd1ZXptYW51ZWxqb2FxdWluQGdtYWlsLmNvbSIsImlzcyI6ImhpcmVuZXQuY29tIiwiaWF0IjoxNjM4OTA3MjYwLCJleHAiOjE2Mzk1MTIwNjB9.5Y63h98K00L-bdE0VEif80OBvD1mpYpyf5OHF0FRO9E0U_yWPBB4mIAULV466Jai6EmQAfJMPShBXhydC887qw');
    renderFromRoute('/my-contracts/pro/pending');
    userEvent.click(await screen.findByText('Contact'));
  });

  await screen.findByText('Contact information');
});

test('on click reschedule button opens modal', async () => {
  await act(async () => {
    sessionStorage.setItem('token', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2RyaWd1ZXptYW51ZWxqb2FxdWluQGdtYWlsLmNvbSIsImlzcyI6ImhpcmVuZXQuY29tIiwiaWF0IjoxNjM4OTA3MjYwLCJleHAiOjE2Mzk1MTIwNjB9.5Y63h98K00L-bdE0VEif80OBvD1mpYpyf5OHF0FRO9E0U_yWPBB4mIAULV466Jai6EmQAfJMPShBXhydC887qw');
    renderFromRoute('/my-contracts/pro/pending');
    userEvent.click(await screen.findByText('Reschedule'));
  });

  await screen.findByText('Are you sure that you want to reschedule this contract?');
});

test('on click cancel button opens modal', async () => {
  await act(async () => {
    sessionStorage.setItem('token', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2RyaWd1ZXptYW51ZWxqb2FxdWluQGdtYWlsLmNvbSIsImlzcyI6ImhpcmVuZXQuY29tIiwiaWF0IjoxNjM4OTA3MjYwLCJleHAiOjE2Mzk1MTIwNjB9.5Y63h98K00L-bdE0VEif80OBvD1mpYpyf5OHF0FRO9E0U_yWPBB4mIAULV466Jai6EmQAfJMPShBXhydC887qw');
    renderFromRoute('/my-contracts/pro/pending');
    userEvent.click(await screen.findByText('Cancel'));
  });

  await screen.findByText('Are you sure that you want to cancel this contract?');
});

test('contract without reschedules shows reschedule button', async () => {

  await act(async () => {
    sessionStorage.setItem('token', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2RyaWd1ZXptYW51ZWxqb2FxdWluQGdtYWlsLmNvbSIsImlzcyI6ImhpcmVuZXQuY29tIiwiaWF0IjoxNjM4OTA3MjYwLCJleHAiOjE2Mzk1MTIwNjB9.5Y63h98K00L-bdE0VEif80OBvD1mpYpyf5OHF0FRO9E0U_yWPBB4mIAULV466Jai6EmQAfJMPShBXhydC887qw');
    renderFromRoute('/my-contracts/pro/pending');
  });

  await waitFor(async () => {
    expect(screen.queryByTestId('contract-card-60')).toBeTruthy();
  });

  const card = screen.getByTestId('contract-card-60');
  expect(await within(card).findByText('Reschedule')).toBeInTheDocument();
});

test('contract already rescheduled doesnt show reschedule button', async () => {

  await act(async () => {
    sessionStorage.setItem('token', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2RyaWd1ZXptYW51ZWxqb2FxdWluQGdtYWlsLmNvbSIsImlzcyI6ImhpcmVuZXQuY29tIiwiaWF0IjoxNjM4OTA3MjYwLCJleHAiOjE2Mzk1MTIwNjB9.5Y63h98K00L-bdE0VEif80OBvD1mpYpyf5OHF0FRO9E0U_yWPBB4mIAULV466Jai6EmQAfJMPShBXhydC887qw');
    renderFromRoute('/my-contracts/pro/pending');
  });

  await waitFor(async () => {
    expect(screen.queryByTestId('contract-card-36')).toBeTruthy();
  });

  const card = screen.getByTestId('contract-card-36');
  expect(within(card).queryByText('Reschedule')).not.toBeInTheDocument()
});
