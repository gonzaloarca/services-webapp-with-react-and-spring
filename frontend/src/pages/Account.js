import React from 'react';
import NavBar from '../components/NavBar';
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

// TODO: integracion con API
const currentUser = {
  email: 'manaaasd@gmail.com',
  id: 5,
  phone: '03034560',
  roles: ['CLIENT', 'PROFESSIONAL'],
  username: 'Manuel Rodriguez',
  image: `${process.env.PUBLIC_URL}/img/plumbing.jpeg`,
};

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
}));

const useGlobalStyles = makeStyles(styles);

const Account = () => {
  const classes = useStyles();
  const globalClasses = useGlobalStyles();
  const { t } = useTranslation();

  const accountSections = [
    {
      tabLabel: t('account.data.title'),
      color: themeUtils.colors.orange,
      icon: <Person className="text-white" />,
      component: <PersonalData />,
      path: 'personal',
    },
    {
      tabLabel: t('account.security.title'),
      color: themeUtils.colors.blue,
      icon: <Lock className="text-white" />,
      component: <SecurityData />,
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

  const history = useHistory();

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
      <NavBar />
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
              {accountSections.map((section, index) => (
                <TabPanel value={tabValue} index={index} key={index}>
                  {section.component}
                </TabPanel>
              ))}
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

const PersonalData = () => {
  const classes = useStyles();
  const globalClasses = useGlobalStyles();
  const { t } = useTranslation();

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

  const onSubmit = (values, props) => {
    console.log(values); //TODO: GUARDAR CAMBIOS
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
                src={currentUser.image}
                alt={t('account.data.imagealt')}
              />
            </Grid>
            <Grid item sm={10} xs={12} className="flex flex-col justify-center">
              <div className="font-bold text-lg">{currentUser.username}</div>
              <div>
                {currentUser.roles.includes('PROFESSIONAL')
                  ? t('professional')
                  : t('client')}
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

const SecurityData = () => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();

  const initialValues = {
    oldpassword: '',
    newpassword: '',
    repeatnewpassword: '',
  };

  //TODO: encontrar si hay una forma de verificar que oldpassword !== newpassword (?)
  const validationSchema = Yup.object({
    oldpassword: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 }))
      .min(8, t('validationerror.minlength', { length: 8 })),
    newpassword: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 }))
      .min(8, t('validationerror.minlength', { length: 8 })),
    repeatnewpassword: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 }))
      .min(8, t('validationerror.minlength', { length: 8 }))
      .oneOf(
        [Yup.ref('newpassword'), null],
        t('validationerror.passwordrepeat')
      ),
  });

  const onSubmit = (values, props) => {
    console.log(values); //TODO: CAMBIAR CONTRASEÑA
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
            {({ isSubmitting }) => (
              <Form>
                <FormControlPassword
                  placeholder={t('account.security.oldpassword')}
                  variable="oldpassword"
                  fullWidth
                />
                <FormControlPassword
                  placeholder={t('account.security.newpassword')}
                  variable="newpassword"
                  fullWidth
                />
                <FormControlPassword
                  placeholder={t('account.security.repeatpassword')}
                  variable="repeatnewpassword"
                  fullWidth
                />
                <div className="flex justify-end">
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

  return (
    <>
      <h1 className={clsx(globalClasses.sectionTitle, 'px-5')}>
        {t('account.logout.title')}
      </h1>
      <Divider />
      <div className="p-5 flex justify-center">
        {/* TODO: llamar a cerrar sesión */}
        <Button type="button" className={classes.logoutBtn}>
          {t('account.logout.btn')}
        </Button>
      </div>
    </>
  );
};

export default Account;
