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
} from '@material-ui/core';
import LoginAndRegisterStyles from '../components/LoginAndRegisterStyles';
import FormControlPassword from '../components/FormControlPassword';

const useStyles = makeStyles(LoginAndRegisterStyles);

const Register = () => {
  const classes = useStyles();
  const { t } = useTranslation();
  const [values, setValues] = React.useState({
    username: '',
    phone: '',
    email: '',
    password: '',
    passwordRepeat: '',
    avatar: null,
    currentStep: 0,
  });

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event.target.value });
  };

  const changeToStep = (newStep) => {
    setValues({ ...values, currentStep: newStep });
  };

  const handleAvatarChange = (event) => {
    if (event.target.files[0] !== undefined) {
      setValues({
        ...values,
        avatar: URL.createObjectURL(event.target.files[0]),
      });
    }
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
            <img src="/img/adduser.svg" alt={t('register.title')} />
            <p className={classes.title}>{t('register.into')}</p>
          </div>
          <Card className={clsx(classes.customCard, classes.registerCard)}>
            <form>
              {values.currentStep <= 0 ? (
                <Grid container spacing={3}>
                  <Grid item sm={8} xs={12}>
                    <FormControl fullWidth variant="filled">
                      <InputLabel>{t('register.username')}</InputLabel>
                      <FilledInput
                        variant="outlined"
                        id="username"
                        value={values.username}
                        onChange={handleChange('username')}
                        required
                      />
                    </FormControl>
                  </Grid>
                  <Grid item sm={4} xs={12}>
                    <FormControl fullWidth variant="filled">
                      <InputLabel>{t('register.phone')}</InputLabel>
                      <FilledInput
                        variant="outlined"
                        id="phone"
                        value={values.phone}
                        onChange={handleChange('phone')}
                        required
                      />
                    </FormControl>
                  </Grid>
                  <Grid item xs={12}>
                    <FormControl fullWidth variant="filled">
                      <InputLabel>{t('register.email')}</InputLabel>
                      <FilledInput
                        variant="outlined"
                        id="email"
                        type="email"
                        value={values.email}
                        onChange={handleChange('email')}
                        required
                      />
                    </FormControl>
                  </Grid>
                  <Grid item sm={6} xs={12}>
                    <FormControlPassword
                      placeholder={t('register.password')}
                      variable="password"
                      handleChange={handleChange('password')}
                      value={values.password}
                      className={'mr-4'}
                      fullWidth
                    />
                  </Grid>
                  <Grid item sm={6} xs={12}>
                    <FormControlPassword
                      placeholder={t('register.passwordRepeat')}
                      variable="passwordRepeat"
                      handleChange={handleChange('passwordRepeat')}
                      value={values.passwordRepeat}
                      fullWidth
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <SubmitButton
                      fullWidth
                      className={'mb-4'}
                      onClick={() => changeToStep(1)}
                    >
                      {t('register.continue')}
                    </SubmitButton>
                  </Grid>
                </Grid>
              ) : (
                <div>
                  <p className={clsx(classes.subtitle, 'mb-3')}>
                    {t('register.selectImage')}
                  </p>

                  <div className={clsx('flex justify-center mb-3')}>
                    <img
                      className={'rounded-full h-48 w-48 object-cover'}
                      id="img-preview"
                      src={
                        values.avatar === null
                          ? './img/defaultavatar.svg'
                          : values.avatar
                      }
                      alt=""
                    />
                  </div>
                  <p className={'mb-3 text-center'}>
                    {t('register.imagePreview')}
                  </p>
                  <Grid container className={'mb-3 justify-center'}>
                    <Grid item sm={4}>
                      <Button variant="contained" component="label" fullWidth>
                        {t('image.pick')}
                        <input
                          onChange={(event) => handleAvatarChange(event)}
                          type="file"
                          id="file"
                          hidden
                        />
                      </Button>
                    </Grid>
                  </Grid>
                  <p className={'mb-5 text-gray-500'}>
                    {t('register.fileDisclaimer')}
                  </p>
                  <div className="flex justify-center">
                    <GoBackButton
                      className={'mb-4 align-center'}
                      onClick={() => changeToStep(0)}
                    >
                      {t('register.goBack')}
                    </GoBackButton>
                  </div>
                  <div className="flex justify-center">
                    <SubmitButton
                      fullWidth
                      className={'mb-4'}
                      //   onClick={() => ()} TODO: REGISTRAR
                    >
                      {t('register.submit')}
                    </SubmitButton>
                  </div>
                </div>
              )}
            </form>
            <span className={classes.bottomLabel}>
              <p>{t('register.alreadyHasAccount')}</p>
              <a href="/login" className={classes.bottomLabelLink}>
                {/* TODO: FIX HREF? */}
                {t('register.login')}
              </a>
            </span>
          </Card>
        </div>
      </div>
    </div>
  );
};

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

const GoBackButton = withStyles({
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

class Upload extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      file: null,
    };
    this.handleChange = this.handleChange.bind(this);
  }
  handleChange(event) {
    this.setState({
      file: URL.createObjectURL(event.target.files[0]),
    });
  }
  render() {
    return (
      <div>
        <input type="file" onChange={this.handleChange} />
        <img src={this.state.file} />
      </div>
    );
  }
}

export default Register;
