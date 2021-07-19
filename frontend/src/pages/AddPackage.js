import { Button, makeStyles } from '@material-ui/core';
import { AddCircle } from '@material-ui/icons';
import { Form, Formik } from 'formik';
import React from 'react';
import { Helmet } from 'react-helmet';
import { useTranslation } from 'react-i18next';
import NavBar from '../components/NavBar';
import PackageFormItem from '../components/PackageFormItem';
import PackagesHeader from '../components/PackagesHeader';
import styles from '../styles';
import { themeUtils } from '../theme';
import * as Yup from 'yup';

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

  const initialValues = {
    packages: [
      {
        title: '',
        description: '',
        rateType: '',
        price: '',
      },
    ],
  };

  const validationSchema = Yup.object({
    packages: Yup.array().of(
      Yup.object().shape({
        title: Yup.string()
          .required(t('validationerror.required'))
          .max(100, t('validationerror.maxlength', { length: 100 })),
        description: Yup.string()
          .required(t('validationerror.required'))
          .max(100, t('validationerror.maxlength', { length: 100 })),
        rateType: Yup.number().required(t('validationerror.required')),
        price: Yup.number().when('rateType', {
          is: 2,
          otherwise: Yup.number().required(t('validationerror.required')),
        }),
      })
    ),
  });

  const onSubmit = (values, props) => {
    const pack = values.packages[0];
    console.log(pack); //TODO: SUBMITEAR
    //TODO: Redirect a /job/id/packages
  };

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
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={onSubmit}
          >
            {(props) => (
              <Form>
                <PackageFormItem index={0} withDelete={false} />
                <Button
                  fullWidth
                  startIcon={<AddCircle />}
                  className={classes.addButton}
                  type="submit"
                  disabled={props.isSubmitting}
                >
                  {t('addpackage.submit')}
                </Button>
              </Form>
            )}
          </Formik>
        </div>
      </div>
    </>
  );
};

export default AddPackage;
