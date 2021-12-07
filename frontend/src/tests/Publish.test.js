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
  await act(async () =>
    fireEvent.mouseDown(screen.getByLabelText('Service category'))
  );
  //Select category
  await act(async () => userEvent.click(screen.getByText('Carpentry')));
  //Expect category to be selected
  await waitFor(async () => {
    expect(screen.getByText('Carpentry')).toBeInTheDocument();
  });
  //Next step
  await act(async () => userEvent.click(screen.getByTestId('submit-button')));
  //Step: Title
  //Expect to be on step 2
  await waitFor(async () => {
    expect(
      screen.getByText('Enter a name for your service')
    ).toBeInTheDocument();
  });
  //Enter title
  await act(async () =>
    fireEvent.change(screen.getByTestId('title-input'), {
      target: { value: 'My Carpentry Job' },
    })
  );
  //Next step
  await act(async () => userEvent.click(screen.getByTestId('submit-button')));
  //Step: Packages
  //Expect to be on step 3
  await waitFor(async () => {
    expect(
      screen.getByText('Add at least one package to your service')
    ).toBeInTheDocument();
  });
  //Add package name
  await act(async () =>
    fireEvent.change(screen.getByPlaceholderText('Package name'), {
      target: { value: 'My Package' },
    })
  );
  //Add package description
  await act(async () =>
    fireEvent.change(screen.getByPlaceholderText('Package description'), {
      target: { value: 'My Package description' },
    })
  );
  //Select rate type
  await act(async () =>
    userEvent.click(screen.getByTestId('radio-0'))
  );
  //Add package price
  await act(async () =>
    fireEvent.change(screen.getByPlaceholderText('Price'), {
      target: { value: '12'},
    })
  );
  //Next step
  await act(async () => userEvent.click(screen.getByTestId('submit-button')));
  //Step: Images
  //Expect to be on step 4
  await waitFor(async () => {
    expect(
      screen.getByText('Add images to your post')
    ).toBeInTheDocument();
  });
  //Next step
  await act(async () => userEvent.click(screen.getByTestId('submit-button')));
  //Expect to be on step 5
  await waitFor(async () => {
    expect(
      screen.getByText('Enter the hours of availability for the service')
    ).toBeInTheDocument();
  });
  //Set available hours
  await act(async () =>
    fireEvent.change(screen.getByPlaceholderText('Available hours'), {
      target: { value: 'Monday - Friday' },
    })
  );
  //Next step
  await act(async () => userEvent.click(screen.getByTestId('submit-button')));
  //Expect to be on step 6
  await waitFor(async () => {
    expect(
      screen.getByText('Select the locations where the service will be offered')
    ).toBeInTheDocument();
  });
  //Neighborhood should only appear on the select
  await waitFor(async () => {
    expect(screen.getAllByText('Agronomia')).toHaveLength(1);
  });
  //Click on neighborhood select
  await act(async () =>
    userEvent.click(screen.getByText('Agronomia'))
  );
  //Neighborhood should now appear in the tags and in the select
  await waitFor(async () => {
    expect(screen.getAllByText('Agronomia')).toHaveLength(2);
  });
  //Add another neighborhood
  await waitFor(async () => {
    expect(screen.getAllByText('Almagro')).toHaveLength(1);
  });
  await act(async () =>
    userEvent.click(screen.getByText('Almagro'))
  );
  await waitFor(async () => {
    expect(screen.getAllByText('Almagro')).toHaveLength(2);
  });
  await waitFor(async () => {
    expect(document.getElementsByClassName('MuiChip-deleteIcon')).toHaveLength(2);
  });
  //Remove neighborhood
  await act(async () =>
    userEvent.click(document.getElementsByClassName('MuiChip-deleteIcon')[1])
  );
  await waitFor(async () => {
    expect(document.getElementsByClassName('MuiChip-deleteIcon')).toHaveLength(1);
  });
  await waitFor(async () => {
    expect(screen.getAllByText('Almagro')).toHaveLength(1);
  });
  //Next step
  await act(async () => userEvent.click(screen.getByTestId('submit-button')));
  //Expect to be on step 7
  await waitFor(async () => {
    expect(
      screen.getByText('Post summary')
    ).toBeInTheDocument();
  });
  //Submit
  await act(async () => userEvent.click(screen.getByTestId('submit-button')));
  await waitFor(async () => {
    expect(
      screen.getByText('Post created successfully!')
    ).toBeInTheDocument();
  });
});
