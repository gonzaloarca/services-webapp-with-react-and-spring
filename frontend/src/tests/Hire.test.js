import '../i18n';
import { hireHandlers } from './mocks/handlers';
import { setupTests } from './utils/setupTestUtils';
import { setupServer } from 'msw/node';
import renderFromRoute from './utils/renderFromRoute';
import { waitFor, screen, fireEvent } from '@testing-library/react';
import { HIRE_MOCKED_DATA, LOGIN_MOCKED_DATA } from './mocks/mocked-models';
import userEvent from '@testing-library/user-event';

const server = setupServer(...hireHandlers);

setupTests(server);

const JOB_PACKAGE_TITLE = HIRE_MOCKED_DATA.JOB_PACKAGE.title;
const JOB_POST_ID = HIRE_MOCKED_DATA.JOB_POST_ID;
const JOB_PACKAGE_ID = HIRE_MOCKED_DATA.JOB_PACKAGE_ID;
const PATH = `/hire/${JOB_POST_ID}/package/${JOB_PACKAGE_ID}`;

const waitForAppStateUpdatesHire = async () => {
  expect(await screen.findByText(JOB_PACKAGE_TITLE)).toBeTruthy();
};

const waitForAppStateUpdatesMyContracts = async () => {
  expect(
    await screen.findAllByText('Servicio de Limpieza 24hs/7 dias')
  ).toBeTruthy();
  expect(await screen.findAllByText('Francisco Quesada')).toBeTruthy();
};

test('hire page loaded metadata title', async () => {
  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute(PATH);

  await waitFor(() => expect(document.title).toBe('Hire service | HireNet'));

  await waitForAppStateUpdatesHire();
});

test('hire page loaded title properly', async () => {
  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute(PATH);

  expect(await screen.findByText('Hire Service')).toBeInTheDocument();

  await waitForAppStateUpdatesHire();
});

test('hire page loaded package data', async () => {
  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute(PATH);

  expect(await screen.findByText(JOB_PACKAGE_TITLE)).toBeInTheDocument();
});

test('empty form throws 2 validation errors', async () => {
  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute(PATH);

  userEvent.click(await screen.findByText('Hire'));

  expect(await screen.findAllByText('This field is required')).toHaveLength(2);

  await waitForAppStateUpdatesHire();
});

test('missing description throws validation error', async () => {
  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute(PATH);

  fireEvent.change(await screen.findByPlaceholderText('Date & time'), {
    target: { value: 'December 9, 2099 - 7:00 PM' },
  });

  userEvent.click(await screen.findByText('Hire'));
  expect(await screen.findAllByText('This field is required')).toHaveLength(1);

  await waitForAppStateUpdatesHire();
});

test('missing date throws validation error', async () => {
  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute(PATH);

  fireEvent.change(await screen.findByLabelText('Description'), {
    target: { value: 'Insert description here' },
  });

  userEvent.click(await screen.findByText('Hire'));
  expect(await screen.findAllByText('This field is required')).toHaveLength(1);

  await waitForAppStateUpdatesHire();
});

test('creates contract correctly', async () => {
  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute(PATH);

  fireEvent.change(await screen.findByPlaceholderText('Date & time'), {
    target: { value: 'December 9, 2099 - 7:00 PM' },
  });

  fireEvent.change(await screen.findByLabelText('Description'), {
    target: { value: 'Insert description here' },
  });

  userEvent.click(await screen.findByText('Hire'));

  expect(
    await screen.findByText('Pending approval contracts')
  ).toBeInTheDocument();

  await waitForAppStateUpdatesMyContracts();
});
