import React from 'react';
import NavBar from '../components/NavBar';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import { useTranslation } from 'react-i18next';
import clsx from 'clsx';
import { themeUtils } from '../theme';
import {
  Button,
  FilledInput,
  InputLabel,
  FormControl,
  Card,
  Grid,
  FormHelperText,
  Link,
  Input,
} from '@material-ui/core';
import LoginAndRegisterStyles from '../components/LoginAndRegisterStyles';
import FormControlPassword from '../components/FormControlPassword';
import * as Yup from 'yup';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { Link as RouterLink } from 'react-router-dom';

const useStyles = makeStyles(LoginAndRegisterStyles);

const Register = () => {
  const classes = useStyles();
  const { t } = useTranslation();
  const [values, setValues] = React.useState({
    currentStep: 0,
  });

  const initialValues = {
    username: '',
    phone: '',
    email: '',
    password: '',
    passwordrepeat: '',
    image: '',
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
    image: Yup.mixed()
      .test(
        'is-correct-type',
        t('validationerror.avatarfile.type'),
        (file) =>
          file === '' ||
          (file && ['image/png', 'image/jpeg'].includes(file.type))
      )
      .test(
        'is-correct-size',
        t('validationerror.avatarfile.size', { size: 2 }),
        (file) => file === '' || (file && file.size <= 2 * 1024 * 1024)
      ),
  });

  const onSubmit = (values, props) => {
    console.log(values); //TODO: REGISTRAR
  };

  const changeToStep = (newStep) => {
    setValues({ ...values, currentStep: newStep });
  };

  return (
    <div>
      <NavBar currentSection={'/register'} isTransparent />
      <div
        className={classes.background}
        style={{ backgroundImage: `url(./img/background.jpg)` }}
      >
        <div className={classes.cardContainer}>
          <div className={classes.titleContainer}>
            <img
              src="/img/adduser.svg"
              alt={t('register.title')}
              loading="lazy"
            />
            <p className={classes.title}>{t('register.into')}</p>
          </div>
          <Card className={clsx(classes.customCard, classes.registerCard)}>
            <Formik
              initialValues={initialValues}
              validationSchema={validationSchema}
              onSubmit={onSubmit}
            >
              {(props) => (
                <Form>
                  {values.currentStep <= 0 ? (
                    <Grid container spacing={3}>
                      <Grid item sm={7} xs={12}>
                        <Field
                          as={FormControl}
                          name="username"
                          required
                          fullWidth
                          variant="filled"
                          className={classes.FieldHeight}
                        >
                          <InputLabel>{t('register.username')}</InputLabel>
                          <FilledInput id="username" required />
                          <FormHelperText>
                            <ErrorMessage name="username" />
                          </FormHelperText>
                        </Field>
                      </Grid>
                      <Grid item sm={5} xs={12}>
                        <Field
                          as={FormControl}
                          name="phone"
                          required
                          fullWidth
                          variant="filled"
                          className={classes.FieldHeight}
                        >
                          <InputLabel>{t('register.phone')}</InputLabel>
                          <FilledInput id="phone" required />
                          <FormHelperText>
                            <ErrorMessage name="phone" />
                          </FormHelperText>
                        </Field>
                      </Grid>
                      <Grid item xs={12}>
                        <Field
                          as={FormControl}
                          name="email"
                          required
                          fullWidth
                          variant="filled"
                          className={classes.FieldHeight}
                        >
                          <InputLabel>{t('register.email')}</InputLabel>
                          <FilledInput id="email" type="email" required />
                          <FormHelperText>
                            <ErrorMessage name="email" />
                          </FormHelperText>
                        </Field>
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
                      <Grid item xs={12}>
                        <Button
                          fullWidth
                          className={clsx(classes.submitButton, 'mb-4')}
                          onClick={() => changeToStep(1)}
                        >
                          {t('register.continue')}
                        </Button>
                      </Grid>
                    </Grid>
                  ) : (
                    <div>
                      <p className={clsx(classes.subtitle, 'mb-3')}>
                        {t('register.selectimage')}
                      </p>

                      <div className={clsx('flex justify-center mb-3')}>
                        {console.log(props.values.image)}
                        <img
                          className={'rounded-full h-48 w-48 object-cover'}
                          id="img-preview"
                          src={
                            props.values.image === ''
                              ? './img/defaultavatar.svg'
                              : URL.createObjectURL(props.values.image)
                          }
                          alt=""
                        />
                      </div>
                      <p className={'mb-3 text-center'}>
                        {t('register.imagepreview')}
                      </p>
                      <Grid container className={'mb-3 justify-center'}>
                        <Grid item>
                          <Input
                            name="image"
                            onChange={(event) =>
                              event.target.files[0] !== undefined &&
                              props.setFieldValue(
                                'image',
                                event.target.files[0]
                              )
                            }
                            type="file"
                          />
                          <GreyButton
                            disabled={props.values.image === ''}
                            onClick={() => props.setFieldValue('image', '')}
                          >
                            {t('register.discardimage')}
                          </GreyButton>
                          <FormHelperText>
                            <ErrorMessage name="image" />
                          </FormHelperText>
                        </Grid>
                      </Grid>
                      <p className={'mb-5 text-gray-500'}>
                        {t('register.filedisclaimer', { size: 2 })}
                      </p>
                      <div className="flex justify-center">
                        <GreyButton
                          className={'mb-4 align-center'}
                          onClick={() => changeToStep(0)}
                        >
                          {t('register.goback')}
                        </GreyButton>
                      </div>
                      <div className="flex justify-center">
                        <Button
                          fullWidth
                          className={clsx(classes.submitButton, 'mb-4')}
                          type="submit"
                        >
                          {t('register.submit')}
                        </Button>
                      </div>
                    </div>
                  )}
                </Form>
              )}
            </Formik>
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
