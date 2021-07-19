import React from 'react';
import NavBar from '../components/NavBar';
import { makeStyles } from '@material-ui/core/styles';
import { useTranslation } from 'react-i18next';
import clsx from 'clsx';
import { Button, Card, Grid, Link, TextField } from '@material-ui/core';
import LoginAndRegisterStyles from '../components/LoginAndRegisterStyles';
import FormControlPassword from '../components/FormControlPassword';
import * as Yup from 'yup';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { Link as RouterLink, useHistory } from 'react-router-dom';
import { useUser } from '../hooks';
import { Helmet } from 'react-helmet';

const useStyles = makeStyles(LoginAndRegisterStyles);

const Register = () => {
  const classes = useStyles();
  const { t } = useTranslation();
  const history = useHistory();

  const [data, setData] = React.useState({
    username: '',
    phone: '',
    email: '',
    password: '',
    passwordrepeat: '',
    image: '',
    badCredentials: false,
  });

  const { register } = useUser();

  const makeRequest = async (newData) => {
    try {
      await register({ ...newData });
      history.push('/register/success');
    } catch (error) {
      // console.log(error);
      if (error.response.status === 409) {
        handlePrevStep(newData);
        setData({ ...newData, badCredentials: true });
      }
      return;
    }
  };

  const handleNextStep = (newData, final = false) => {
    if (final) {
      makeRequest(newData);
      return;
    }
    setData((prev) => ({ ...prev, ...newData }));
    setCurrentStep((prev) => prev + 1);
  };

  const handlePrevStep = (newData) => {
    if (currentStep > 0) {
      setData((prev) => ({ ...prev, ...newData }));
      setCurrentStep((prev) => prev - 1);
    }
  };

  const [currentStep, setCurrentStep] = React.useState(0);
  const steps = [
    <StepOne next={handleNextStep} data={data} />,
    // <StepTwo next={handleNextStep} prev={handlePrevStep} data={data} />
  ];

  return (
    <div>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.register') })}
        </title>
      </Helmet>
      <NavBar currentSection={'/register'} isTransparent />
      <div
        className={classes.background}
        style={{
          backgroundImage:
            'url(' + process.env.PUBLIC_URL + '/img/background.jpg)',
        }}
      >
        <div className={classes.cardContainer}>
          <div className={classes.titleContainer}>
            <img
              src={process.env.PUBLIC_URL + '/img/adduser.svg'}
              alt={t('register.title')}
              className={classes.titleIcon}
              loading="lazy"
            />
            <p className={classes.title}>{t('register.into')}</p>
          </div>
          <Card className={clsx(classes.customCard, classes.registerCard)}>
            {steps[currentStep]}
            <span className={classes.bottomLabel}>
              <p className="text-sm font-medium">
                {t('register.alreadyhasaccount')}
              </p>
              <Link
                component={RouterLink}
                to="/login"
                className={classes.bottomLabelLink}
              >
                <p className="text-base font-semibold">{t('register.login')}</p>
              </Link>
            </span>
          </Card>
        </div>
      </div>
    </div>
  );
};

const StepOne = (props) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const handleSubmit = (values) => {
    props.next(values, true); // es final por que el segundo paso se encuentra comentado
  };

  const validationSchema = Yup.object({
    username: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 })),
    phone: Yup.string()
      .required(t('validationerror.required'))
      .matches(/^\+?[\d-]{7,100}$/, t('validationerror.phone', { length: 7 })),
    email: Yup.string()
      .required(t('validationerror.required'))
      .email(t('validationerror.email'))
      .max(100, t('validationerror.maxlength', { length: 100 })),
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

  return (
    <Formik
      initialValues={props.data}
      validationSchema={validationSchema}
      onSubmit={handleSubmit}
      enableReinitialize={true}
    >
      {({ values, isSubmitting }) => (
        <Form>
          <Grid container spacing={3}>
            <Grid item sm={7} xs={12}>
              <Field
                as={TextField}
                variant="filled"
                fullWidth
                label={<p className="text-sm">{t('register.username')}</p>}
                name="username"
                className={classes.FieldHeight}
                helperText={<ErrorMessage name="username"></ErrorMessage>}
                InputProps={{
                  classes: {
                    input: 'text-sm font-medium',
                  },
                }}
              />
            </Grid>
            <Grid item sm={5} xs={12}>
              <Field
                as={TextField}
                variant="filled"
                fullWidth
                label={<p className="text-sm">{t('register.phone')}</p>}
                name="phone"
                className={classes.FieldHeight}
                helperText={<ErrorMessage name="phone"></ErrorMessage>}
                InputProps={{
                  classes: {
                    input: 'text-sm font-medium',
                  },
                }}
              />
            </Grid>
            <Grid item xs={12}>
              <Field
                as={TextField}
                variant="filled"
                fullWidth
                label={<p className="text-sm">{t('register.email')}</p>}
                name="email"
                className={classes.FieldHeight}
                helperText={<ErrorMessage name="email"></ErrorMessage>}
                InputProps={{
                  classes: {
                    input: 'text-sm font-medium',
                  },
                }}
              />
            </Grid>
            <Grid item sm={6} xs={12}>
              <FormControlPassword
                placeholder={t('register.password')}
                variable="password"
                fullWidth
              />
            </Grid>
            <Grid item sm={6} xs={12}>
              <FormControlPassword
                placeholder={t('register.passwordrepeat')}
                variable="passwordrepeat"
                fullWidth
              />
            </Grid>
            <Grid item xs={12} className="h-28">
              <Button
                fullWidth
                className={clsx(classes.submitButton, 'mb-4')}
                type="submit"
                disabled={isSubmitting}
              >
                {t('register.submit')}
              </Button>
              {values.badCredentials ? (
                <p className={clsx(classes.badCredentials, 'mb-2')}>
                  {t('register.badcredentials')}
                </p>
              ) : (
                <></>
              )}
            </Grid>
          </Grid>
        </Form>
      )}
    </Formik>
  );
};

export default Register;
