import { render, screen } from '@testing-library/react';
import App from './App';
import { Router } from 'react-router-dom';
import history from './history';
import appTheme from './theme';
import { ThemeProvider } from '@material-ui/core';
import './i18n';

test('renders learn react link', () => {
  render(
      <Router history={history}>
        <ThemeProvider theme={appTheme}>
          <App />
        </ThemeProvider>
      </Router>,);
  const linkElement = screen.getByText("Home");
  expect(linkElement).toBeInTheDocument();
});
