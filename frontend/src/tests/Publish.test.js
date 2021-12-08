import { act, fireEvent, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { setupServer } from 'msw/node';
import { publishHandlers } from './mocks/handlers';
import { LOGIN_MOCKED_DATA } from './mocks/mocked-models';
import renderFromRoute from './utils/renderFromRoute';
import { setupTests } from './utils/setupTestUtils';

const server = setupServer(...publishHandlers);

setupTests(server);

test('publish new service successfully', async () => {
  //Load Session storage to simulate logged in user
  await act(async () =>
    sessionStorage.setItem('token', LOGIN_MOCKED_DATA.TOKEN)
  );

  //Render page
  await act(async () => renderFromRoute('/create-job-post'));

  //Step: Category
  //Click on category select
  fireEvent.mouseDown(screen.getByLabelText('Service category'));

  //Select category
  userEvent.click(screen.getByText('Carpentry'));

  //Expect category to be selected
  expect(await screen.findByText('Carpentry')).toBeInTheDocument();

  //Next step
  userEvent.click(screen.getByTestId('submit-button'));

  //Step: Title
  //Expect to be on step 2
  expect(
    await screen.findByText('Enter a name for your service')
  ).toBeInTheDocument();

  //Enter title
  fireEvent.change(screen.getByTestId('title-input'), {
    target: { value: 'My Carpentry Job' },
  });

  //Next step
  userEvent.click(screen.getByTestId('submit-button'));

  //Step: Packages
  //Expect to be on step 3
  expect(
    await screen.findByText('Add at least one package to your service')
  ).toBeInTheDocument();

  //Add package name
  fireEvent.change(screen.getByPlaceholderText('Package name'), {
    target: { value: 'My Package' },
  });

  //Add package description
  fireEvent.change(screen.getByPlaceholderText('Package description'), {
    target: { value: 'My Package description' },
  });

  //Select rate type
  userEvent.click(screen.getByTestId('radio-0'));

  //Add package price

  fireEvent.change(screen.getByPlaceholderText('Price'), {
    target: { value: '12' },
  });

  //Next step
  userEvent.click(screen.getByTestId('submit-button'));

  //Step: Images
  //Expect to be on step 4
  expect(
    await screen.findByText('Add images to your post')
  ).toBeInTheDocument();

  //Next step
  userEvent.click(screen.getByTestId('submit-button'));

  //Expect to be on step 5
  expect(
    await screen.findByText('Enter the hours of availability for the service')
  ).toBeInTheDocument();

  //Set available hours
  fireEvent.change(screen.getByPlaceholderText('Available hours'), {
    target: { value: 'Monday - Friday' },
  });

  //Next step
  userEvent.click(screen.getByTestId('submit-button'));

  //Expect to be on step 6
  expect(
    await screen.findByText(
      'Select the locations where the service will be offered'
    )
  ).toBeInTheDocument();

  //Neighborhood should only appear on the select
  expect(await screen.findAllByText('Agronomia')).toHaveLength(1);

  //Click on neighborhood select
  userEvent.click(screen.getByText('Agronomia'));

  //Neighborhood should now appear in the tags and in the select
  expect(await screen.findAllByText('Agronomia')).toHaveLength(2);

  //Add another neighborhood
  expect(await screen.findAllByText('Almagro')).toHaveLength(1);

  userEvent.click(screen.getByText('Almagro'));

  expect(await screen.findAllByText('Almagro')).toHaveLength(2);

  expect(document.getElementsByClassName('MuiChip-deleteIcon')).toHaveLength(2);

  //Remove neighborhood
  userEvent.click(document.getElementsByClassName('MuiChip-deleteIcon')[1]);

  expect(document.getElementsByClassName('MuiChip-deleteIcon')).toHaveLength(1);

  expect(screen.getAllByText('Almagro')).toHaveLength(1);

  //Next step
  userEvent.click(screen.getByTestId('submit-button'));

  //Expect to be on step 7
  expect(await screen.findByText('Post summary')).toBeInTheDocument();

  //Submit
  userEvent.click(screen.getByTestId('submit-button'));

  expect(
    await screen.findByText('Post created successfully!')
  ).toBeInTheDocument();
});
