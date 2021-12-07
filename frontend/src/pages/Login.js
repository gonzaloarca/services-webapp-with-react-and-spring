import jwt from 'jwt-decode';
import React, { useContext, useEffect, useState } from 'react';
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
  FormHelperText,
  Link,
} from '@material-ui/core';
import LoginAndRegisterStyles from '../components/LoginAndRegisterStyles';
import FormControlPassword from '../components/FormControlPassword';
import * as Yup from 'yup';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { Link as RouterLink, useHistory } from 'react-router-dom';
import { useUser } from '../hooks';
import { NavBarContext, UserContext } from '../context';
import { Helmet } from 'react-helmet';

const useStyles = makeStyles(LoginAndRegisterStyles);

const Login = () => {
  const classes = useStyles();
  const { t } = useTranslation();
  const history = useHistory();
  const initialValues = {
    email: '',
    password: '',
  };

  const { token, setCurrentUser, setToken } = useContext(UserContext);
  const [rememberMe, setRememberMe] = useState(false);
  const [badCredentials, setBadCredentials] = useState(false);

  const { getUserByEmail, login } = useUser();

  const { setNavBarProps } = useContext(NavBarContext);

  useEffect(() => {
    setNavBarProps({ currentSection: '/login', isTransparent: true });
  }, []);

  const setUserData = async (email) => {
    try {
      const user = await getUserByEmail(email);
      setCurrentUser(user);
    } catch (error) {
      history.replace('/error');
    }
  };

  useEffect(() => {
    try {
      if (token) {
        if (rememberMe) {
          localStorage.setItem('token', token);
        } else sessionStorage.setItem('token', token);
        setToken(token);
        setUserData(jwt(token).sub);
      }
    } catch (error) {
      history.replace('/error');
    }
  }, [token]);

  const validationSchema = Yup.object({
    email: Yup.string()
      .required(t('validationerror.required'))
      .email(t('validationerror.email'))
      .max(100, t('validationerror.maxlength', { length: 100 })),
    password: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 }))
      .min(8, t('validationerror.minlength', { length: 8 })),
  });

  const onSubmit = async (values, props) => {
    try {
      setToken(await login(values));
      history.push('/');
    } catch (error) {
      if (error.response.status === 401) {
        setBadCredentials(true);
      } else {
        history.replace('/error');
      }
      return;
    }
  };

  return (
    <div>
      <Helmet>
        <title>{t('title', { section: t('navigation.sections.login') })}</title>
      </Helmet>
      <div
        className={classes.background}
        style={{
          backgroundImage: `url(${process.env.PUBLIC_URL}/img/background.jpg)`,
        }}
      >
        <div className={classes.cardContainer}>
          <div className={classes.titleContainer}>
            <img
              src={`${process.env.PUBLIC_URL}/img/log-in.svg`}
              alt={t('login.title')}
              loading="lazy"
              className={classes.titleIcon}
            />
            <p className={classes.title}>{t('login.into')}</p>
          </div>
          <Card className={clsx(classes.customCard, 'max-w-lg')}>
            <Formik
              initialValues={initialValues}
              validationSchema={validationSchema}
              onSubmit={onSubmit}
            >
              {(props) => (
                <Form>
                  <Field
                    as={FormControl}
                    fullWidth
                    size="small"
                    variant="filled"
                    name="email"
                    className={classes.FieldHeight}
                  >
                    <InputLabel
                      aria-label={t('login.email')}
                      className="text-sm"
                    >
                      {t('login.email')}
                    </InputLabel>
                    <FilledInput
                      inputProps={{ 'data-testid': 'email-login-input' }}
                      className="text-sm font-medium"
                      id="email"
                    />
                    <FormHelperText>
                      <ErrorMessage name="email" />
                    </FormHelperText>
                  </Field>
                  <FormControlPassword
                    placeholder={t('register.password')}
                    variable="password"
                    fullWidth
                    onSubmit={(e) => {
                      props.onSubmit(e.values, props);
                    }}
                    inputProps={{ 'data-testid': 'password-login-input' }}
                  />
                  <div className="flex justify-between items-center mb-3">
                    <FormControlLabel
                      onChange={(e) => setRememberMe(e.target.checked)}
                      control={<BlueCheckBox id="remember-me" />}
                      label={
                        <p className="text-sm font-medium">
                          {t('login.rememberme')}
                        </p>
                      }
                    />
                    {badCredentials ? (
                      <p className={classes.badCredentials}>
                        {t('login.badcredentials')}
                      </p>
                    ) : (
                      <></>
                    )}
                  </div>
                  <Button
                    fullWidth
                    className={clsx(classes.submitButton, 'mb-4')}
                    type="submit"
                    disabled={props.isSubmitting}
                  >
                    {t('login.submit')}
                  </Button>
                </Form>
              )}
            </Formik>
            <div className={'flex justify-around'}>
              <span className={classes.bottomLabel}>
                <p className="text-sm font-medium">
                  {t('login.hasaccountquestion')}
                </p>
                <Link
                  component={RouterLink}
                  to="/register"
                  className={clsx(
                    classes.bottomLabelLink,
                    'text-base font-semibold'
                  )}
                >
                  {t('login.getaccount')}
                </Link>
              </span>
              <div className={classes.separator} />
              <span className={classes.bottomLabel}>
                <p className="text-sm font-medium">
                  {t('login.recoverquestion')}
                </p>
                <Link
                  component={RouterLink}
                  to="/recover"
                  className={clsx(
                    classes.bottomLabelLink,
                    'text-base font-semibold'
                  )}
                >
                  {t('login.recover')}
                </Link>
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

export default Login;
