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
} from '@material-ui/core';
import { themeUtils } from '../theme';
import { ExitToApp, Lock, Person } from '@material-ui/icons';
import clsx from 'clsx';
import CircleIcon from '../components/CircleIcon';
import * as Yup from 'yup';
import { Formik, Form } from 'formik';
import { useTranslation } from 'react-i18next';
import FormControlPassword from '../components/FormControlPassword';

const currentUser = {
  email: 'manaaasd@gmail.com',
  id: 5,
  phone: '03034560',
  roles: ['CLIENT', 'PROFESSIONAL'],
  username: 'Manuel Rodriguez',
  image: '/img/plumbing.jpeg',
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
  const [tabValue, setTabValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setTabValue(newValue);
  };

  const accountSections = [
    {
      tabLabel: 'Datos personales',
      color: themeUtils.colors.orange,
      icon: <Person className="text-white" />,
      component: <PersonalData />,
    },
    {
      tabLabel: 'Inicio de sesión y contraseña',
      color: themeUtils.colors.blue,
      icon: <Lock className="text-white" />,
      component: <SecurityData />,
    },
    {
      tabLabel: 'Cerrar sesión',
      color: themeUtils.colors.red,
      icon: <ExitToApp className="text-white" />,
      component: <Logout />,
    },
  ];

  return (
    <>
      <NavBar />
      <div className={globalClasses.contentContainerTransparent}>
        <SectionHeader sectionName="Mi Cuenta" />
        <Grid container spacing={4}>
          <Grid item sm={12} md={3}>
            <div className={classes.accountTypeSection}>
              <h2 className={clsx(globalClasses.sectionTitle, 'p-3')}>
                Cuenta
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

const TabPanel = ({ children, value, index }) => {
  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
    >
      {value === index && children}
    </div>
  );
};

const PersonalData = () => {
  const classes = useStyles();
  const globalClasses = useGlobalStyles();

  return (
    <>
      <h1 className={clsx(globalClasses.sectionTitle, 'px-5')}>
        Datos Personales
      </h1>
      <Divider />
      <div className="p-5">
        <div>
          <Grid container className="mx-10 flex" spacing={3}>
            <Grid item sm={2}>
              <img
                loading="lazy"
                className={classes.profileImg}
                src={currentUser.image}
                alt="Imagen del usuario"
              />
            </Grid>
            <Grid item sm={10} className="flex flex-col justify-center">
              <div>{currentUser.username}</div>
              <div>Profesional</div>
            </Grid>
          </Grid>
        </div>
        <Divider />
        {/* TODO: Cambio de nombre y telefono */}
        <Divider />
        {/* TODO: CAmbio de imagen de perfil */}
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
        Inicio de sesión y contraseña
      </h1>
      <Divider />
      <div className="p-5">
        <div className="px-10">
          <h2 className="font-bold text-lg mb-5">Cambiar contraseña</h2>
          <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={onSubmit}
          >
            {(props) => (
              <Form>
                <FormControlPassword
                  placeholder="Contraseña actual"
                  variable="oldpassword"
                  fullWidth
                />
                <FormControlPassword
                  placeholder="Nueva contraseña"
                  variable="newpassword"
                  fullWidth
                />
                <FormControlPassword
                  placeholder="Repita nueva contraseña"
                  variable="repeatnewpassword"
                  fullWidth
                />
                <div className="flex justify-end">
                  <Button type="submit" className={classes.submitBtn}>
                    Guardar cambios
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

  return (
    <>
      <h1 className={clsx(globalClasses.sectionTitle, 'px-5')}>
        Cerrar Sesión
      </h1>
      <Divider />
      <div className="p-5 flex justify-center">
        {/* TODO: llamar a cerrar sesión */}
        <Button type="button" className={classes.logoutBtn}>
          CERRAR SESIÓN
        </Button>
      </div>
    </>
  );
};

export default Account;
