export const setupTests = (server) => {
  beforeAll(() => {
    const noop = () => {};
    Object.defineProperty(window, 'scrollTo', { value: noop, writable: true });
    server.listen();
  });

  // Reset any request handlers that we may add during the tests,
  // so they don't affect other tests.
  afterEach(() => server.resetHandlers());
  // Clean up after the tests are finished.
  afterAll(() => server.close());
};
