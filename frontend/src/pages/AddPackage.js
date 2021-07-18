import { Button, makeStyles } from '@material-ui/core';
import { AddCircle } from '@material-ui/icons';
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
  addButton: {
    color: themeUtils.colors.electricBlue,
    textTransform: 'uppercase',
    fontSize: themeUtils.fontSizes.base,
    marginTop: 20,
    backgroundColor: themeUtils.colors.lightGrey,
  },
}));

const AddPackage = () => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.addpackage') })}
        </title>
      </Helmet>
      <NavBar currentSection="/create-job-post" />
      <div className={globalClasses.contentContainerTransparent}>
        <PackagesHeader />
        <div className={classes.formContainer}>
          <h2 className={classes.subHeader}>{t('addpackage.header')}</h2>
          <Formik
            initialValues={{
              packages: [
                { title: '', description: '', rateType: '', price: '' },
              ],
            }}
            onSubmit={() => {}}
          >
            <Form>
              <PackageFormItem index={0} withDelete={false} />
              <Button
                fullWidth
                startIcon={<AddCircle />}
                className={classes.addButton}
              >
                {t('addpackage.submit')}
              </Button>
            </Form>
          </Formik>
        </div>
      </div>
    </>
  );
};

export default AddPackage;
