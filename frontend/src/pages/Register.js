import React from 'react';
import NavBar from '../components/NavBar';
import FileInput, { checkSize, checkType } from '../components/FileInput';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import { useTranslation } from 'react-i18next';
import clsx from 'clsx';
import { themeUtils } from '../theme';
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
      await register(newData);
      history.push('/register/success');
    } catch (error) {
      console.log(error);
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
          backgroundImage: `url(${process.env.PUBLIC_URL}/img/background.jpg)`,
        }}
      >
        <div className={classes.cardContainer}>
          <div className={classes.titleContainer}>
            <img
              src={`${process.env.PUBLIC_URL}/img/adduser.svg`}
              alt={t('register.title')}
              loading="lazy"
            />
            <p className={classes.title}>{t('register.into')}</p>
          </div>
          <Card className={clsx(classes.customCard, classes.registerCard)}>
            {steps[currentStep]}
            <span className={classes.bottomLabel}>
              <p>{t('register.alreadyhasaccount')}</p>
              <Link
                component={RouterLink}
                to="/login"
                className={classes.bottomLabelLink}
              >
                {t('register.login')}
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
      {({ values }) => (
        <Form>
          <Grid container spacing={3}>
            <Grid item sm={7} xs={12}>
              <Field
                as={TextField}
                variant="filled"
                fullWidth
                label={t('register.username')}
                name="username"
                required
                className={classes.FieldHeight}
                helperText={<ErrorMessage name="username"></ErrorMessage>}
              />
            </Grid>
            <Grid item sm={5} xs={12}>
              <Field
                as={TextField}
                variant="filled"
                fullWidth
                label={t('register.phone')}
                name="phone"
                required
                className={classes.FieldHeight}
                helperText={<ErrorMessage name="phone"></ErrorMessage>}
              />
            </Grid>
            <Grid item xs={12}>
              <Field
                as={TextField}
                variant="filled"
                fullWidth
                label={t('register.email')}
                name="email"
                required
                className={classes.FieldHeight}
                helperText={<ErrorMessage name="email"></ErrorMessage>}
              />
            </Grid>
            <Grid item sm={6} xs={12}>
              <FormControlPassword
                required
                placeholder={t('register.password')}
                variable="password"
                fullWidth
              />
            </Grid>
            <Grid item sm={6} xs={12}>
              <FormControlPassword
                required
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

const StepTwo = (props) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const handleSubmit = (values) => {
    props.next(values, true);
  };

  const validationSchema = Yup.object({
    image: Yup.mixed()
      .test('is-correct-type', t('validationerror.avatarfile.type'), checkType)
      .test(
        'is-correct-size',
        t('validationerror.avatarfile.size', { size: 2 }),
        checkSize
      ),
  });

  return (
    <Formik
      initialValues={props.data}
      validationSchema={validationSchema}
      onSubmit={handleSubmit}
    >
      {({ values, isSubmitting }) => (
        <Form>
          <div>
            <p className={clsx(classes.subtitle, 'mb-3')}>
              {t('register.selectimage')}
            </p>

            <div className={clsx('flex justify-center mb-3')}>
              <img
                className={'rounded-full h-48 w-48 object-cover'}
                id="img-preview"
                src={
                  values.image === ''
                    ? `${process.env.PUBLIC_URL}/img/defaultavatar.svg`
                    : URL.createObjectURL(values.image)
                }
                alt=""
              />
            </div>
            <p className={'mb-3 text-center'}>{t('register.imagepreview')}</p>
            <Grid container className={'mb-3 justify-center'}>
              <Grid item>
                <FileInput fileName="image" />
              </Grid>
            </Grid>
            <p className={'mb-5 text-gray-500'}>
              {t('register.filedisclaimer', { size: 2 })}
            </p>
            <div className="flex justify-center">
              <GreyButton
                className={'mb-4 align-center'}
                onClick={() => props.prev(values)}
              >
                {t('register.goback')}
              </GreyButton>
            </div>
            <div className="flex justify-center">
              <Button
                fullWidth
                className={clsx(classes.submitButton, 'mb-4')}
                type="submit"
                disabled={isSubmitting}
              >
                {t('register.submit')}
              </Button>
            </div>
          </div>
        </Form>
      )}
    </Formik>
  );
};

const GreyButton = withStyles({
  root: {
    color: themeUtils.colors.grey,
    backgroundColor: themeUtils.colors.lightGrey,
    transition: 'color 0.1s',
    '&:hover': {
      color: 'white',
      backgroundColor: themeUtils.colors.grey,
      transition: 'color 0.1s',
    },
    fontSize: '1em',
  },
})(Button);

export default Register;
