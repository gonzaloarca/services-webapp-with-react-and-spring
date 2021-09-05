import React, { useContext, useEffect, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { useTranslation } from 'react-i18next';
import clsx from 'clsx';
import { Button, Card } from '@material-ui/core';
import LoginAndRegisterStyles from '../components/LoginAndRegisterStyles';
import * as Yup from 'yup';
import { Formik, Form } from 'formik';
import { useLocation, Link } from 'react-router-dom';
import FormControlPassword from '../components/FormControlPassword';
import { Helmet } from 'react-helmet';
import { useUser } from '../hooks';
import { NavBarContext } from '../context';

const useStyles = makeStyles(LoginAndRegisterStyles);

function CheckQueryParams(query) {
  if (query.get('user-id') && query.get('token')) return true;
  return false;
}

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

const RecoverPass = ({ history }) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const initialValues = {
    password: '',
    passwordrepeat: '',
  };
  let query = useQuery();
  const { recoverPass } = useUser();
  const [statusCode, setStatusCode] = useState(-1);

  const { setNavBarProps } = useContext(NavBarContext);

  useEffect(() => {
    setNavBarProps({currentSection:'/login',isTransparent:true});
  },[])

  const validationSchema = Yup.object({
    password: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 }))
      .min(8, t('validationerror.minlength', { length: 8 })),
    passwordrepeat: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 }))
      .min(8, t('validationerror.minlength', { length: 8 }))
      .oneOf([Yup.ref('password'), null], t('validationerror.passwordrepeat')),
  });

  const onSubmit = async ({ password }) => {
    try {
      await recoverPass({
        userId: query.get('user-id'),
        token: query.get('token'),
        password: password,
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
          {t('title', { section: t('navigation.sections.changepass') })}
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
              src={process.env.PUBLIC_URL + '/img/log-in.svg'}
              alt={t('recover.title')}
              loading="lazy"
            />
            <p className={classes.title}>{t('recover.title')}</p>
          </div>
          <Card className={clsx(classes.customCard, 'max-w-lg')}>
            <div>
              {statusCode !== 200 ? (
                CheckQueryParams(query) ? (
                  //Si el URL es valido, muestro el Form
                  <Formik
                    initialValues={initialValues}
                    validationSchema={validationSchema}
                    onSubmit={onSubmit}
                  >
                    {(props) => (
                      <Form>
                        <div className="font-semibold mb-2">
                          {t('recover.enternewpass')}
                        </div>
                        <FormControlPassword
                          placeholder={t('register.password')}
                          variable="password"
                          fullWidth
                        />
                        <FormControlPassword
                          placeholder={t('register.passwordrepeat')}
                          variable="passwordrepeat"
                          fullWidth
                        />
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
                ) : (
                  //Si no es valido el URL, muestro un error
                  <>
                    <div className="font-bold text-3xl mb-2">
                      {t('recover.wronglink')}
                    </div>
                    <div>{t('recover.wrongexplained')}</div>
                    <div className="flex justify-center mt-3">
                      <Button
                        className={clsx(classes.submitButton, 'w-32')}
                        component={Link}
                        to="/login"
                      >
                        {t('recover.login')}
                      </Button>
                    </div>
                  </>
                )
              ) : (
                <div>
                  <div
                    className="font-bold text-3xl mb-2"
                    style={{ color: 'green' }}
                  >
                    {t('recover.passwordChanged')}
                  </div>
                  <div>{t('recover.changedinstructions')}</div>
                  <div className="flex justify-center mt-3">
                    <Button
                      className={clsx(classes.submitButton, 'w-32')}
                      component={Link}
                      to="/login"
                    >
                      {t('recover.login')}
                    </Button>
                  </div>
                </div>
              )}
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default RecoverPass;
