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
  FormHelperText,
  Link,
} from '@material-ui/core';
import LoginAndRegisterStyles from '../components/LoginAndRegisterStyles';
import FormControlPassword from '../components/FormControlPassword';
import * as Yup from 'yup';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { Link as RouterLink } from 'react-router-dom';

const useStyles = makeStyles(LoginAndRegisterStyles);

const Login = () => {
  const classes = useStyles();
  const { t } = useTranslation();
  const initialValues = {
    email: '',
    password: '',
    rememberme: false,
  };

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

  const onSubmit = (values, props) => {
    console.log(props); //TODO: INICIAR SESION
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
            <img src="/img/log-in.svg" alt={t('login.title')} loading="lazy" />
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
                    variant="filled"
                    name="email"
                    className={classes.FieldHeight}
                  >
                    <InputLabel>{t('login.email')}</InputLabel>
                    <FilledInput id="email" />
                    <FormHelperText>
                      <ErrorMessage name="email" />
                    </FormHelperText>
                  </Field>
                  <FormControlPassword
                    placeholder={t('register.password')}
                    variable="password"
                    fullWidth
                  />
                  <Field
                    as={FormControlLabel}
                    name="rememberme"
                    className={'mb-2'}
                    control={
                      <BlueCheckBox
                        id="remember-me"
                        className={classes.rememberme}
                      />
                    }
                    label={t('login.rememberme')}
                  />
                  <Button
                    fullWidth
                    className={clsx(classes.submitButton, 'mb-4')}
                    type="submit"
                  >
                    {t('login.submit')}
                  </Button>
                </Form>
              )}
            </Formik>
            <div className={'flex justify-around'}>
              <span className={classes.bottomLabel}>
                <p>{t('login.hasaccountquestion')}</p>
                <Link
                  component={RouterLink}
                  to="/register"
                  className={classes.bottomLabelLink}
                >
                  {t('login.getaccount')}
                </Link>
              </span>
              <div className={classes.separator} />
              <span className={classes.bottomLabel}>
                <p>{t('login.recoverquestion')}</p>
                <Link
                  component={RouterLink}
                  to="/recover"
                  className={classes.bottomLabelLink}
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
