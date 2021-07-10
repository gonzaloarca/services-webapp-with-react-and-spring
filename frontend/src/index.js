import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { ThemeProvider } from '@material-ui/core';
import { Router } from 'react-router-dom';
import history from './history';
import appTheme from './theme';
import './i18n';

ReactDOM.render(
  <Router history={history}>
    <ThemeProvider theme={appTheme}>
      <App />
    </ThemeProvider>
  </Router>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
