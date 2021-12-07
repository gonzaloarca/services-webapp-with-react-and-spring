import '../i18n';
import { appHandlers } from './mocks/handlers';
import { setupTests } from './utils/setupTestUtils';
import { setupServer } from 'msw/node';
import renderFromRoute from './utils/renderFromRoute';
import { act, waitFor } from '@testing-library/react';
import { screen } from '@testing-library/react';

const server = setupServer(...appHandlers);

setupTests(server);

test('render without crashing and helmet sets metadata title correctly', async () => {
  await act(async () => renderFromRoute('/'));

  await waitFor(async () => {
    expect(document.title).toBe('Home | HireNet');
  });
});
