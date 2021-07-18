import { Button, makeStyles } from '@material-ui/core';
import { AddCircle, Create } from '@material-ui/icons';
import { Form, Formik, useFormikContext } from 'formik';
import React from 'react';
import { Helmet } from 'react-helmet';
import { useTranslation } from 'react-i18next';
import NavBar from '../components/NavBar';
import PackageFormItem from '../components/PackageFormItem';
import PackagesHeader from '../components/PackagesHeader';
import styles from '../styles';
import { themeUtils } from '../theme';

const useGlobalStyles = makeStyles(styles);
const useStyles = makeStyles((theme) => ({
  formContainer: {
    boxShadow: themeUtils.shadows.containerShadow,
    backgroundColor: 'white',
    padding: '40px 50px',
    marginTop: 30,
  },
  subHeader: {
    fontSize: themeUtils.fontSizes.h2,
    fontWeight: 600,
    marginBottom: 10,
  },
  editButton: {
    color: themeUtils.colors.electricBlue,
    textTransform: 'uppercase',
    fontSize: themeUtils.fontSizes.base,
    marginTop: 20,
    backgroundColor: themeUtils.colors.lightGrey,
  },
}));

const packageForm = {
  description:
    'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
  id: 10,
  price: 1000,
  rateType: 0,
  title: 'Lorem ipsum dolor sit amet',
};

const EditPackage = () => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.editpackage') })}
        </title>
      </Helmet>
      <NavBar currentSection="/create-job-post" />
      <div className={globalClasses.contentContainerTransparent}>
        <PackagesHeader />
        <div className={classes.formContainer}>
          <h2 className={classes.subHeader}>{t('editpackage.header')}</h2>
          <Formik
            initialValues={{
              packages: [{ ...packageForm }],
            }}
            onSubmit={() => {}}
          >
            <Form>
              <PackageFormItem index={0} withDelete={false} />
              <Button
                fullWidth
                startIcon={<Create />}
                className={classes.editButton}
              >
                {t('editpackage.submit')}
              </Button>
            </Form>
          </Formik>
        </div>
      </div>
    </>
  );
};

export default EditPackage;
