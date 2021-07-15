import React from 'react';
import NavBar from '../components/NavBar';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import { useTranslation } from 'react-i18next';
import clsx from 'clsx';
import { themeUtils } from '../theme';
import {
  Checkbox,
  FormControlLabel,
  Button,
  FilledInput,
  InputLabel,
  FormControl,
  Card,
} from '@material-ui/core';
import LoginAndRegisterStyles from '../components/LoginAndRegisterStyles';
import FormControlPassword from '../components/FormControlPassword';

const useStyles = makeStyles(LoginAndRegisterStyles);

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

  return (
    <div>
      <NavBar currentSection={'/login'} isTransparent />
      <div
        className={classes.background}
        style={{ backgroundImage: `url(./img/background.jpg)` }}
      >
        <div className={classes.cardContainer}>
          <div className={classes.titleContainer}>
            <img
              src="/img/log-in.svg"
              alt={t('login.title')}
            />
            <p className={classes.title}>{t('login.into')}</p>
          </div>
          <Card className={clsx(classes.customCard, 'max-w-lg')}>
            <form>
              <FormControl fullWidth variant="filled">
                <InputLabel>
                  {t('login.email')}
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
              <FormControlPassword
                placeholder={t('register.password')}
                variable="password"
                handleChange={handleChange('password')}
                value={values.password}
				fullWidth
				className={'mb-3'}
              />
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
                <a href="/register" className={classes.bottomLabelLink}>
                  {/* TODO: FIX HREF? */}
                  {t('login.getAccount')}
                </a>
              </span>
              <div className={classes.separator} />
              <span className={classes.bottomLabel}>
                <p>{t('login.recoverQuestion')}</p>
                <a href="/recover" className={classes.bottomLabelLink}>
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
