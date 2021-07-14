import React from 'react';
import NavBar from '../components/NavBar';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import { useTranslation } from 'react-i18next';
import clsx from 'clsx';
import { themeUtils } from '../theme';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';
import {
  Checkbox,
  FormControlLabel,
  Button,
  FilledInput,
  InputLabel,
  FormControl,
  InputAdornment,
  IconButton,
  Card,
} from '@material-ui/core';

const useStyles = makeStyles({
  loginBackground: {
    width: '100%',
    height: '100vh',
    paddingTop: '50px',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
  loginContainer: {},
  loginTitleContainer: {
    backgroundColor: themeUtils.colors.darkBlue,
    width: 'max-content',
    padding: '10px',
    borderRadius: '20px 20px 0 0',
    display: 'flex',
    alignItems: 'center',
    color: 'white',
    fontWeight: 'bold',
  },
  loginTitle: {
    marginLeft: '15px',
    fontSize: '1.7em',
  },
  loginIcon: {},
  loginCard: {
    justifyContent: 'center',
    maxWidth: '500px',
  },
  separator: {
    borderLeft: '1px solid #c8c8c8',
    marginTop: '10px',
  },
  bottomLabel: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    textAlign: 'center',
  },
  bottomLabeLink: {
    color: themeUtils.colors.darkBlue,
  },
});

const Login = () => {
  const classes = useStyles();
  const { t } = useTranslation();
  const [values, setValues] = React.useState({
    email: '',
    password: '',
    rememberMe: false,
    showPassword: false,
  });

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
  };

  const handleCheckboxChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.checked });
  };

  const handleClickShowPassword = () => {
    setValues({ ...values, showPassword: !values.showPassword });
  };

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  return (
    <div>
      <NavBar currentSection={'/login'} isTransparent />
      <div
        className={classes.loginBackground}
        style={{ backgroundImage: `url(./img/background.jpg)` }}
      >
        <div className={classes.loginContainer}>
          <div className={classes.loginTitleContainer}>
            <img
              className={classes.loginIcon}
              src="/img/log-in.svg"
              alt={t('login.title')}
            />
            <p className={classes.loginTitle}>{t('login.into')}</p>
          </div>
          <Card className={clsx(classes.loginCard, 'p-9')}>
            <form>
              <FormControl fullWidth className={clsx(classes.textField)} variant="filled">
                <InputLabel>
                  {t('login.emailPlaceholder')}
                </InputLabel>
                <FilledInput
				variant="outlined"
                  className={'mb-5'}
                  id="email"
                  type="email"
                  value={values.email}
                  onChange={handleChange('email')}
                  required
                />
              </FormControl>
              <FormControl fullWidth className={clsx(classes.textField)} variant="filled">
                <InputLabel>
                  {t('login.passwordPlaceholder')}
                </InputLabel>
                <FilledInput
                  className={'mb-3'}
                  id="password"
                  type={values.showPassword ? 'text' : 'password'}
                  value={values.password}
                  onChange={handleChange('password')}
                  required
                  endAdornment={
                    <InputAdornment position="end">
                      <IconButton
                        aria-label="toggle password visibility"
                        onClick={handleClickShowPassword}
                        onMouseDown={handleMouseDownPassword}
                      >
                        {values.showPassword ? (
                          <Visibility />
                        ) : (
                          <VisibilityOff />
                        )}
                      </IconButton>
                    </InputAdornment>
                  }
                />
              </FormControl>
              <FormControlLabel
                className={'mb-2'}
                control={
                  <BlueCheckBox
                    id="remember-me"
                    className={classes.rememberMe}
                    checked={values.rememberMe}
                    onChange={handleCheckboxChange('rememberMe')}
                  />
                }
                label={t('login.rememberMe')}
              />
              <SubmitButton fullWidth className={'mb-4'}>
                {t('login.submit')}
              </SubmitButton>
            </form>
            <div className={'flex justify-around'}>
              <span className={classes.bottomLabel}>
                <p>{t('login.hasAccountQuestion')}</p>
                <a href="/register" className={classes.bottomLabeLink}>
                  {/* TODO: FIX HREF? */}
                  {t('login.getAccount')}
                </a>
              </span>
              <div className={classes.separator} />
              <span className={classes.bottomLabel}>
                <p>{t('login.recoverQuestion')}</p>
                <a href="/recover" className={classes.bottomLabeLink}>
                  {/* TODO: FIX HREF? */}
                  {t('login.recover')}
                </a>
              </span>
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
};

const BlueCheckBox = withStyles({
  root: {
    color: themeUtils.colors.blue,
  },
})((props) => <Checkbox color="default" {...props} />);

const SubmitButton = withStyles({
  root: {
    color: 'white',
    backgroundColor: themeUtils.colors.blue,
    transition: 'color 0.1s',
    '&:hover': {
      backgroundColor: themeUtils.colors.darkBlue,
      transition: 'color 0.1s',
    },
    fontSize: '1em',
  },
})(Button);

export default Login;
