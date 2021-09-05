import { render } from '@testing-library/react';
import App from '../App';
import { BrowserRouter } from 'react-router-dom';
import appTheme from '../theme';
import { ThemeProvider } from '@material-ui/core';
import '../i18n';
import { ConstantDataProvider, UserContextProvider } from '../context';
import React from 'react';
import { NavBarContextProvider } from '../context/navBarContext';

test('render without crashing', () => {
  render(
    <BrowserRouter basename={process.env.PUBLIC_URL}>
      <ThemeProvider theme={appTheme}>
        <UserContextProvider>
          <ConstantDataProvider>
            <NavBarContextProvider>
              <App />
            </NavBarContextProvider>
          </ConstantDataProvider>
        </UserContextProvider>
      </ThemeProvider>
    </BrowserRouter>
  );
});
