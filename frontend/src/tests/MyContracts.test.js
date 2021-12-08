import { fireEvent, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { setupServer } from 'msw/node';
import { myContractsHandlers } from './mocks/handlers';
import renderFromRoute from './utils/renderFromRoute';
import { setupTests } from './utils/setupTestUtils';
import { LOGIN_MOCKED_DATA } from './mocks/mocked-models';
import { within } from '@testing-library/react';

const server = setupServer(
  ...myContractsHandlers
);

async function waitMyContractsSetup() {
  expect(await screen.findAllByText('Servicio de Limpieza 24hs/7 dias')).toBeTruthy();
  expect(await screen.findAllByText('Francisco Quesada')).toBeTruthy();
}

setupTests(server);

test('pending tab displays title', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/pro/pending');

	expect(await screen.findByText('Pending approval contracts')).toBeInTheDocument();

	await waitMyContractsSetup();
});

test('states tabs work', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/pro/active');
	expect(await screen.findByText('Active contracts')).toBeInTheDocument();

	userEvent.click(await screen.findByText('Pending approval'));
	expect(await screen.findByText('Pending approval contracts')).toBeInTheDocument();

	userEvent.click(await screen.findByText('Finalized'));
	expect(await screen.findByText('Finalized contracts')).toBeInTheDocument();

	userEvent.click(await screen.findByText('Active'));
	expect(await screen.findByText('Active contracts')).toBeInTheDocument();

	await waitMyContractsSetup();
});

test('hired contracts showing rate button on non rated contracts', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/hired/finalized');

	const card = await screen.findByTestId('contract-card-77');
	expect(await within(card).findByText('Rate')).toBeInTheDocument();

	await waitMyContractsSetup();
});

test('offered contracts not showing rate button on non rated contracts', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/pro/finalized');

	const card = await screen.findByTestId('contract-card-77');
	expect(within(card).queryByText('Rate')).not.toBeInTheDocument();

	await waitMyContractsSetup();
});

test('hired contracts not showing rate button on non rated cancelled contracts', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/pro/finalized');

	const card = await screen.findByTestId('contract-card-75');
	expect(within(card).queryByText('Rate')).not.toBeInTheDocument();

	await waitMyContractsSetup();
});

test('on click details button opens modal', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/pro/pending');

	userEvent.click(await screen.findByText('Details'));

	expect(await screen.findByText('Job details')).toBeInTheDocument();

	await waitMyContractsSetup();
});

test('on click contact button opens modal', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/pro/pending');

	userEvent.click(await screen.findByText('Contact'));

	expect(await screen.findByText('Contact information')).toBeInTheDocument();

	await waitMyContractsSetup();
});

test('on click reschedule button opens modal', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/pro/pending');

	userEvent.click(await screen.findByText('Reschedule'));

	expect(await screen.findByText('Are you sure that you want to reschedule this contract?')).toBeInTheDocument();

	await waitMyContractsSetup();
});

test('on click cancel button opens modal', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/pro/pending');

	userEvent.click(await screen.findByText('Cancel'));

	expect(await screen.findByText('Are you sure that you want to cancel this contract?')).toBeInTheDocument();

	await waitMyContractsSetup();
});

test('contract without reschedules shows reschedule button', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/pro/pending');

	const card = await screen.findByTestId('contract-card-60');

	expect(await within(card).findByText('Reschedule')).toBeInTheDocument();

	await waitMyContractsSetup();
});

test('contract pro_modified shows waiting for reschedule button and text', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/hired/pending');

	const card = await screen.findByTestId('contract-card-36');

	expect(await within(card).findByText('Review rescheduling')).toBeInTheDocument();

	await waitMyContractsSetup();
});

test('contract pro_modified opens modal on review rescheduling click', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/hired/pending');

	userEvent.click(await screen.findByText('Review rescheduling'));

	expect(await screen.findByText('New date proposal')).toBeInTheDocument();

	await waitMyContractsSetup();
});

test('opens modal on rate click', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/hired/finalized');

	const card = await screen.findByTestId('contract-card-77');
	userEvent.click(await within(card).findByText('Rate'));

	expect(await screen.findByText('How would you rate your experience with this service?')).toBeInTheDocument();

	await waitMyContractsSetup();
});

test('rate modal shows error when nothing completed', async () => {
  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute('/my-contracts/hired/finalized');

  const card = await screen.findByTestId('contract-card-77');
  userEvent.click(await within(card).findByText('Rate'));
  userEvent.click(await screen.findByText('Submit'));

  expect(await screen.findByText('Please enter your rating')).toBeInTheDocument();
  expect(await screen.findByText('This field is required')).toBeInTheDocument();

  await waitMyContractsSetup();
});

test('rate modal shows error when no rate picked', async () => {
  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute('/my-contracts/hired/finalized');

  const card = await screen.findByTestId('contract-card-77');
  userEvent.click(await within(card).findByText('Rate'));

  //Enter opinion
  fireEvent.change(screen.getByTestId('opinion-input'), {
    target: { value: 'Very friendly' },
  });
  userEvent.click(await screen.findByText('Submit'));

  expect(await screen.findByText('Please enter your rating')).toBeInTheDocument();
  expect(screen.queryByText('This field is required')).not.toBeInTheDocument();

  await waitMyContractsSetup();
});

test('rate modal shows error when no description', async () => {
  sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
  renderFromRoute('/my-contracts/hired/finalized');

  const card = await screen.findByTestId('contract-card-77');
  userEvent.click(await within(card).findByText('Rate'));

  //Rate
  userEvent.click(document.getElementById('rating-2'));
  userEvent.click(await screen.findByText('Submit'));

  expect(screen.queryByText('Please enter your rating')).not.toBeInTheDocument();
  expect(await screen.findByText('This field is required')).toBeInTheDocument();

  await waitMyContractsSetup();
});

test('contract already rescheduled doesnt show reschedule button', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/pro/pending');

	const card = await screen.findByTestId('contract-card-36');
	expect(within(card).queryByText('Reschedule')).not.toBeInTheDocument();

	await waitMyContractsSetup();
});

test('contract finalized doesnt show updates buttons', async () => {
	sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN);
	renderFromRoute('/my-contracts/pro/finalized');

	await waitMyContractsSetup();

	expect(screen.queryByText('Reschedule')).not.toBeInTheDocument();
	expect(screen.queryByText('Cancel')).not.toBeInTheDocument();
	expect(screen.queryByText('Approve')).not.toBeInTheDocument();
	expect(screen.queryByText('Review rescheduling')).not.toBeInTheDocument();
});
