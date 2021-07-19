import React, { useState } from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { ThemeProvider } from '@material-ui/core';
import { BrowserRouter } from 'react-router-dom';
import appTheme from './theme';
import './i18n';
import { UserContext } from './context';

const AppContainer = () => {
  const [currentUser, setCurrentUser] = useState(null);
  const [token, setToken] = useState(null);
  return (
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
};

ReactDOM.render(<AppContainer />, document.getElementById('root'));

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
