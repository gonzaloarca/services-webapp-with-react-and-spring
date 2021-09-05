import React, { useContext, useState } from 'react';
import SectionHeader from '../components/SectionHeader';
import styles from '../styles';
import {
  Button,
  Divider,
  Grid,
  makeStyles,
  Tab,
  Tabs,
  withStyles,
  TextField,
} from '@material-ui/core';
import { themeUtils } from '../theme';
import { ExitToApp, Lock, Person } from '@material-ui/icons';
import clsx from 'clsx';
import CircleIcon from '../components/CircleIcon';
import * as Yup from 'yup';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { useTranslation } from 'react-i18next';
import FormControlPassword from '../components/FormControlPassword';
import FileInput, { checkSize, checkType } from '../components/FileInput';
import TabPanel from '../components/TabPanel';
import { useParams, useHistory } from 'react-router-dom';
import { Helmet } from 'react-helmet';
import { UserContext } from '../context';
import { useUser } from '../hooks';
import { isLoggedIn } from '../utils/userUtils';

const useStyles = makeStyles((theme) => ({
  tabs: {
    backgroundColor: 'white',
    color: 'black',
    '& .MuiTab-wrapper': {
      flexDirection: 'row',
      justifyContent: 'start',
    },
  },
  tabContent: {
    display: 'flex',
    alignItems: 'center',
  },
  accountSection: {
    padding: '10px 0',
    backgroundColor: 'white',
    boxShadow: themeUtils.shadows.containerShadow,
  },
  accountTypeSection: {
    padding: '10px 0',
    backgroundColor: 'white',
    boxShadow: themeUtils.shadows.containerShadow,
  },
  submitBtn: {
    fontWeight: 'bold',
    backgroundColor: '#485696',
    border: 'transparent',
    color: 'white',
    '&:hover': {
      backgroundColor: themeUtils.colors.darkBlue,
    },
  },
  logoutBtn: {
    fontWeight: 'bold',
    backgroundColor: 'red',
    border: 'transparent',
    color: 'white',
    width: '25%',
  },
  profileImg: {
    borderRadius: '50%',
    width: '100px',
    height: '100px',
    objectFit: 'cover',
  },
  answerMessage: {
    marginRight: '20px',
    alignSelf: 'center',
    fontSize: '0.9em',
  },
}));

const useGlobalStyles = makeStyles(styles);

const Account = () => {
  const classes = useStyles();
  const globalClasses = useGlobalStyles();
  const { t } = useTranslation();
  const { currentUser } = useContext(UserContext);
  const history = useHistory();

  if (!isLoggedIn()) {
    history.replace('/login');
  }

  const accountSections = [
    {
      tabLabel: t('account.data.title'),
      color: themeUtils.colors.orange,
      icon: <Person className="text-white" />,
      component: <PersonalData currentUser={currentUser} />,
      path: 'personal',
    },
    {
      tabLabel: t('account.security.title'),
      color: themeUtils.colors.blue,
      icon: <Lock className="text-white" />,
      component: (
        <SecurityData email={currentUser?.email} userId={currentUser?.id} />
      ),
      path: 'security',
    },
    {
      tabLabel: t('account.logout.title'),
      color: themeUtils.colors.red,
      icon: <ExitToApp className="text-white" />,
      component: <Logout />,
      path: 'logout',
    },
  ];

  const { activeTab } = useParams();

  let initialTab = 0;

  if (activeTab) {
    accountSections.forEach((section, index) => {
      if (section.path === activeTab) initialTab = index;
    });
  } else {
    history.replace(`/account/${accountSections[0].path}`);
  }

  const [tabValue, setTabValue] = React.useState(initialTab);

  const handleChange = (event, newValue) => {
    setTabValue(newValue);
    history.push(`/account/${accountSections[newValue].path}`);
  };

  return (
    <>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.account') })}
        </title>
      </Helmet>
      <div className={globalClasses.contentContainerTransparent}>
        <SectionHeader sectionName={t('account.header')} />
        <Grid container spacing={4}>
          <Grid item sm={12} md={3}>
            <div className={classes.accountTypeSection}>
              <h2 className={clsx(globalClasses.sectionTitle, 'p-3')}>
                {t('account.tabstitle')}
              </h2>
              <Divider />
              <Tabs
                orientation="vertical"
                value={tabValue}
                indicatorColor="primary"
                onChange={handleChange}
                className={classes.tabs}
              >
                {accountSections.map((section, index) => (
                  <HirenetTab
                    key={index}
                    className="items-start"
                    label={
                      <div
                        className={clsx(
                          classes.tabContent,
                          index === tabValue ? 'font-semibold' : 'font-medium'
                        )}
                      >
                        <CircleIcon
                          className="mr-2"
                          color={section.color}
                          size="2rem"
                        >
                          {section.icon}
                        </CircleIcon>
                        <div className="text-left">{section.tabLabel}</div>
                      </div>
                    }
                  />
                ))}
              </Tabs>
            </div>
          </Grid>
          <Grid item sm={12} md={9}>
            <div className={classes.accountSection}>
              {currentUser ? (
                accountSections.map((section, index) => (
                  <TabPanel value={tabValue} index={index} key={index}>
                    {section.component}
                  </TabPanel>
                ))
              ) : (
                <></>
              )}
            </div>
          </Grid>
        </Grid>
      </div>
    </>
  );
};

const HirenetTab = withStyles((theme) => ({
  root: {
    transition: '0.1s opacity',
    '&:hover': {
      opacity: 1,
      transition: '0.1s opacity',
    },
  },
}))(Tab);

const PersonalData = ({ currentUser }) => {
  const classes = useStyles();
  const globalClasses = useGlobalStyles();
  const { t } = useTranslation();
  const { changeAccountData, changeAccountImage } = useUser();
  const [answer, setAnswer] = useState('');
  const [image, setImage] = useState(currentUser.image);
  const [username, setUsername] = useState(currentUser.username);
  const [phone, setPhone] = useState(currentUser.phone);

  const history = useHistory();

  const initialValues = {
    username: currentUser.username,
    phone: currentUser.phone,
    image: '',
  };

  const validationSchema = Yup.object({
    username: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 })),
    phone: Yup.string()
      .required(t('validationerror.required'))
      .matches(/^\+?[\d-]{7,100}$/, t('validationerror.phone', { length: 7 })),
    image: Yup.mixed()
      .test('is-correct-type', t('validationerror.avatarfile.type'), checkType)
      .test(
        'is-correct-size',
        t('validationerror.avatarfile.size', { size: 2 }),
        checkSize
      ),
  });

  const onSubmit = async (values) => {
    try {
      setAnswer('');
      await changeAccountData({
        userId: currentUser.id,
        email: currentUser.email,
        phone: values.phone,
        username: values.username,
      });
      setUsername(values.username);
      setPhone(values.phone);
      if (values.image) {
        await changeAccountImage(currentUser.id, values.image);
        setImage(URL.createObjectURL(values.image));
      }
      setAnswer('ok');
    } catch (error) {
      setAnswer('error');
    }
  };

  return (
    <>
      <h1 className={clsx(globalClasses.sectionTitle, 'px-5')}>
        {t('account.data.title')}
      </h1>
      <Divider />
      <div className="p-5">
        <div className="mb-5">
          <Grid container className="mx-10 flex" spacing={3}>
            <Grid item sm={2} xs={12}>
              <img
                loading="lazy"
                className={classes.profileImg}
                src={image}
                alt={t('account.data.imagealt')}
              />
            </Grid>
            <Grid item sm={10} xs={12} className="flex flex-col justify-center">
              <div className="font-bold text-lg">{username}</div>
              <div>
                {currentUser.roles.includes('PROFESSIONAL')
                  ? t('professional')
                  : t('client')}
              </div>
              <div>
                {t('register.phone')}: {phone}
              </div>
            </Grid>
          </Grid>
        </div>
        <Divider className="mb-3" />
        <h1 className={globalClasses.sectionTitle}>{t('account.data.edit')}</h1>
        <Formik
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={onSubmit}
        >
          {({ values, isSubmitting }) => (
            <Form>
              <Grid container spacing={3} className="my-3">
                <Grid item sm={8} xs={12}>
                  <Field
                    as={TextField}
                    variant="filled"
                    fullWidth
                    label={t('register.username')}
                    name="username"
                    required
                    helperText={<ErrorMessage name="username"></ErrorMessage>}
                  />
                </Grid>
                <Grid item sm={4} xs={12}>
                  <Field
                    as={TextField}
                    variant="filled"
                    fullWidth
                    label={t('register.phone')}
                    name="phone"
                    required
                    helperText={<ErrorMessage name="phone"></ErrorMessage>}
                  />
                </Grid>
              </Grid>
              <div>
                <div className="text-base font-semibold mb-5">
                  {t('account.data.profilepic')}
                </div>
                <Grid container spacing={3} className="px-5">
                  <Grid item sm={3} xs={12}>
                    <div className={clsx('flex justify-center mb-3')}>
                      <img
                        className={'rounded-full h-24 w-24 object-cover'}
                        id="img-preview"
                        src={
                          values.image === ''
                            ? `${process.env.PUBLIC_URL}/img/defaultavatar.svg`
                            : URL.createObjectURL(values.image)
                        }
                        alt=""
                        loading="lazy"
                      />
                    </div>
                    <p className={'mb-3 text-center'}>
                      {t('register.imagepreview')}
                    </p>
                  </Grid>
                  <Grid
                    item
                    sm={9}
                    xs={12}
                    className="flex justify-center flex-col"
                  >
                    <FileInput fileName="image" />
                    <div className="font-thin text-sm">
                      {t('hirePage.form.imagedisclamer')}
                    </div>
                  </Grid>
                </Grid>
              </div>
              <div className="flex justify-end">
                {answer === '' ? (
                  <></>
                ) : answer === 'ok' ? (
                  <p
                    style={{ color: 'green' }}
                    className={classes.answerMessage}
                  >
                    {t('account.accepted')}
                  </p>
                ) : (
                  <p style={{ color: 'red' }} className={classes.answerMessage}>
                    {t('account.rejected')}{' '}
                  </p>
                )}
                <Button
                  type="submit"
                  className={classes.submitBtn}
                  disabled={isSubmitting}
                >
                  {t('account.savechanges')}
                </Button>
              </div>
            </Form>
          )}
        </Formik>
      </div>
    </>
  );
};

const SecurityData = ({ email, userId }) => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();
  const { changePassword } = useUser();
  const [answer, setAnswer] = useState('');

  const initialValues = {
    oldPassword: '',
    newPassword: '',
    repeatnewpassword: '',
  };

  const validationSchema = Yup.object({
    oldPassword: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 }))
      .min(8, t('validationerror.minlength', { length: 8 })),
    newPassword: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 }))
      .min(8, t('validationerror.minlength', { length: 8 })),
    repeatnewpassword: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 }))
      .min(8, t('validationerror.minlength', { length: 8 }))
      .oneOf(
        [Yup.ref('newPassword'), null],
        t('validationerror.passwordrepeat')
      ),
  });

  const onSubmit = async (values) => {
    try {
      await changePassword({
        userId: userId,
        email: email,
        newPassword: values.newPassword,
        oldPassword: values.oldPassword,
      });
      setAnswer('ok');
    } catch (error) {
      setAnswer('error');
    }
  };

  return (
    <>
      <h1 className={clsx(globalClasses.sectionTitle, 'px-5')}>
        {t('account.security.title')}
      </h1>
      <Divider />
      <div className="p-5">
        <div className="px-10">
          <h2 className="font-bold text-lg mb-5">
            {t('account.security.changepass')}
          </h2>
          <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={onSubmit}
          >
            {({ isSubmitting, values }) => (
              <Form>
                <FormControlPassword
                  placeholder={t('account.security.oldpassword')}
                  variable="oldPassword"
                  fullWidth
                />
                <FormControlPassword
                  placeholder={t('account.security.newpassword')}
                  variable="newPassword"
                  fullWidth
                />
                <FormControlPassword
                  placeholder={t('account.security.repeatpassword')}
                  variable="repeatnewpassword"
                  fullWidth
                />
                <div className="flex justify-end">
                  {answer === '' ? (
                    <></>
                  ) : answer === 'ok' ? (
                    <p
                      style={{ color: 'green' }}
                      className={classes.answerMessage}
                    >
                      {t('account.accepted')}
                    </p>
                  ) : (
                    <p
                      style={{ color: 'red' }}
                      className={classes.answerMessage}
                    >
                      {t('account.rejected')}{' '}
                    </p>
                  )}
                  <Button
                    type="submit"
                    className={classes.submitBtn}
                    disabled={isSubmitting}
                  >
                    {t('account.savechanges')}
                  </Button>
                </div>
              </Form>
            )}
          </Formik>
        </div>
      </div>
    </>
  );
};

const Logout = () => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();
  const { setCurrentUser, setToken } = useContext(UserContext);
  const history = useHistory();

  const logout = () => {
    localStorage.removeItem('token');
    sessionStorage.removeItem('token');
    setCurrentUser(null);
    setToken(null);
    history.push('/');
  };

  return (
    <>
      <h1 className={clsx(globalClasses.sectionTitle, 'px-5')}>
        {t('account.logout.title')}
      </h1>
      <Divider />
      <div className="p-5 flex justify-center">
        <Button type="button" className={classes.logoutBtn} onClick={logout}>
          {t('account.logout.btn')}
        </Button>
      </div>
    </>
  );
};

export default Account;
