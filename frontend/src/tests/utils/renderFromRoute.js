import { ThemeProvider } from '@material-ui/styles';
import { render } from '@testing-library/react';
import React from 'react';
import { MemoryRouter, Route } from 'react-router-dom';
import App from '../../App';
import { ConstantDataProvider, UserContextProvider } from '../../context';
import { NavBarContextProvider } from '../../context/navBarContext';
import appTheme from '../../theme';

const renderFromRoute = (path, locationAndHistory = {}) => {
  render(
    <MemoryRouter initialEntries={[path]}>
      <ThemeProvider theme={appTheme}>
        <UserContextProvider>
          <ConstantDataProvider>
            <NavBarContextProvider>
              <App />
              <Route
                path="*"
                render={({ history, location }) => {
                  locationAndHistory.location = location;
                  locationAndHistory.history = history;
                  return null;
                }}
              />
            </NavBarContextProvider>
          </ConstantDataProvider>
        </UserContextProvider>
      </ThemeProvider>
    </MemoryRouter>
  );
};

export default renderFromRoute;
