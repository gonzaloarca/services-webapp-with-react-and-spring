import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { ThemeProvider } from '@material-ui/core';
import { BrowserRouter } from 'react-router-dom';
import appTheme from './theme';
import './i18n';
import { UserContextProvider,ConstantDataProvider } from './context';
import { NavBarContextProvider } from './context/navBarContext';

const AppContainer = () => {
  return (
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
};

ReactDOM.render(<AppContainer />, document.getElementById('root'));

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
