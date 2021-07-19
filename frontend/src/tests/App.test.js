import { render } from '@testing-library/react';
import App from '../App';
import { BrowserRouter } from 'react-router-dom';
import appTheme from '../theme';
import { ThemeProvider } from '@material-ui/core';
import '../i18n';
import { useState } from 'react';
import { UserContext } from '../context';
import React from 'react';
import { renderHook } from '@testing-library/react-hooks';

test('render without crashing', () => {
  const mockSetState = jest.spyOn(React, 'useState');

  const [currentUser, setCurrentUser] = useState(null);
  const [token, setToken] = useState(null);
  render(
    <BrowserRouter basename={process.env.PUBLIC_URL}>
      <ThemeProvider theme={appTheme}>
        <UserContext.Provider
          value={{ currentUser, setCurrentUser, token, setToken }}
        >
          <App />
        </UserContext.Provider>
      </ThemeProvider>
    </BrowserRouter>
  );
});
