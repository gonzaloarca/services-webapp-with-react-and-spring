import React, { useContext, useEffect, useState } from 'react';
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
import { Helmet } from 'react-helmet';
import { useUser } from '../hooks';
import { NavBarContext } from '../context';

const useStyles = makeStyles(LoginAndRegisterStyles);

const RecoverAccount = ({ history }) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const initialValues = {
    email: '',
  };
  const { recoverAccount } = useUser();
  const [statusCode, setStatusCode] = useState(-1);

  const { setNavBarProps } = useContext(NavBarContext);

  useEffect(() => {
    setNavBarProps({currentSection:'/login',isTransparent:true});
  },[])

  const validationSchema = Yup.object({
    email: Yup.string()
      .required(t('validationerror.required'))
      .email(t('validationerror.email'))
      .max(100, t('validationerror.maxlength', { length: 100 })),
  });

  const onSubmit = async ({ email }) => {
    try {
      await recoverAccount({
        email: email,
      });
      setStatusCode(200);
    } catch (e) {
      setStatusCode(e.statusCode);
      history.push(`/error`);
    }
  };

  return (
    <div>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.recoverpass') })}
        </title>
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
                  {statusCode === -1 ? (
                    <p className="text-sm mb-3">{t('recover.disclaimer')}</p>
                  ) : statusCode < 400 ? (
                    <p className="text-sm mb-3" style={{ color: 'green' }}>
                      {t('recover.success')}
                    </p>
                  ) : (
                    <p className="text-sm mb-3">{t('recover.error')}</p>
                  )}

                  <Button
                    fullWidth
                    className={clsx(classes.submitButton, 'mb-4')}
                    type="submit"
                    disabled={statusCode === 200 || props.isSubmitting}
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

export default RecoverAccount;
