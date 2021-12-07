import '../i18n';
import { appHandlers } from './mocks/handlers';
import { setupTests } from './utils/setupTestUtils';
import { setupServer } from 'msw/node';
import renderFromRoute from './utils/renderFromRoute';

const server = setupServer(...appHandlers);

setupTests(server);

test('render without crashing', () => {
  renderFromRoute('/');
});
