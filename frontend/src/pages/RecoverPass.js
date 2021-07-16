import React from 'react';
import NavBar from '../components/NavBar';
import { makeStyles } from '@material-ui/core/styles';
import { useTranslation } from 'react-i18next';
import clsx from 'clsx';
import {
  Button,
  FilledInput,
  InputLabel,
  FormControl,
  Card,
  FormHelperText,
} from '@material-ui/core';
import LoginAndRegisterStyles from '../components/LoginAndRegisterStyles';
import * as Yup from 'yup';
import { Formik, Form, Field, ErrorMessage } from 'formik';

const useStyles = makeStyles(LoginAndRegisterStyles);

const RecoverPass = () => {
  const classes = useStyles();
  const { t } = useTranslation();
  const initialValues = {
    email: '',
  };

  const validationSchema = Yup.object({
    email: Yup.string()
      .required(t('validationerror.required'))
      .email(t('validationerror.email'))
      .max(100, t('validationerror.maxlength', { length: 100 })),
  });

  const onSubmit = (values, props) => {
    console.log(values); //TODO: enviar mail de recuperacion de password
    //TODO: ver si hay una mejor forma de mostrar el mensaje de submitted que esta forma
    document.querySelector('#disclaimer').setAttribute('hidden', 'true');
    document.querySelector('#submitted-message').removeAttribute('hidden');
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
              alt={t('recover.title')}
              loading="lazy"
            />
            <p className={classes.title}>{t('recover.title')}</p>
          </div>
          <Card className={clsx(classes.customCard, 'max-w-lg')}>
            <Formik
              initialValues={initialValues}
              validationSchema={validationSchema}
              onSubmit={onSubmit}
            >
              {(props) => (
                <Form>
                  <div className="font-semibold mb-2">
                    {t('recover.instructions')}
                  </div>
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
                  <div className="text-sm mb-3" id="disclaimer">
                    {t('recover.disclaimer')}
                  </div>
                  <div
                    className="text-sm mb-3"
                    style={{ color: 'green' }}
                    hidden
                    id="submitted-message"
                  >
                    {t('recover.success')}
                  </div>
                  <Button
                    fullWidth
                    className={clsx(classes.submitButton, 'mb-4')}
                    type="submit"
                  >
                    {t('recover.submit')}
                  </Button>
                </Form>
              )}
            </Formik>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default RecoverPass;
