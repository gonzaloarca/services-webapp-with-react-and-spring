import { ThemeProvider } from '@material-ui/styles';
import { render } from '@testing-library/react';
import React from 'react';
import { MemoryRouter } from 'react-router-dom';
import App from '../../App';
import { ConstantDataProvider, UserContextProvider } from '../../context';
import { NavBarContextProvider } from '../../context/navBarContext';
import appTheme from '../../theme';

const renderFromRoute = (path) => {
  render(
    <MemoryRouter initialEntries={[path]}>
      <ThemeProvider theme={appTheme}>
        <UserContextProvider>
          <ConstantDataProvider>
            <NavBarContextProvider>
              <App />
            </NavBarContextProvider>
          </ConstantDataProvider>
        </UserContextProvider>
      </ThemeProvider>
    </MemoryRouter>
  );
};

export default renderFromRoute;
