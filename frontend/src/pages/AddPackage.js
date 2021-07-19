import { Button, Grid, makeStyles } from '@material-ui/core';
import { AddCircle } from '@material-ui/icons';
import { Form, Formik } from 'formik';
import React, { useEffect, useState, useContext } from 'react';
import { Helmet } from 'react-helmet';
import { useTranslation } from 'react-i18next';
import NavBar from '../components/NavBar';
import PackageFormItem from '../components/PackageFormItem';
import PackagesHeader from '../components/PackagesHeader';
import styles from '../styles';
import { themeUtils } from '../theme';
import * as Yup from 'yup';
import { useJobPosts, useUser, useJobPackages } from '../hooks';
import { extractLastIdFromURL } from '../utils/urlUtils';
import { UserContext } from '../context';
import { Skeleton } from '@material-ui/lab';

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

const AddPackage = ({ match, history }) => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();
  const postId = match.params.id;

  const { getJobPostById } = useJobPosts();
  const { getUserById } = useUser();
  const { createJobPackage } = useJobPackages();
  const { currentUser } = useContext(UserContext);

  const [loading, setLoading] = useState(true);
  const [jobPost, setJobPost] = useState(null);
  const [proUser, setProUser] = useState(null);

  const loadJobPost = async () => {
    try {
      const jobPost = await getJobPostById(postId);
      setJobPost(jobPost);
    } catch (e) {
      history.replace(`/error`);
    }
  };

  const loadProUser = async () => {
    try {
      const proId = extractLastIdFromURL(jobPost.professional);
      const pro = await getUserById(proId);
      setProUser(pro);
    } catch (e) {
      history.replace(`/error`);
    }
  };

  useEffect(() => {
    if (jobPost) {
      loadProUser();
    }
  }, [jobPost]);

  useEffect(() => {
    if (jobPost && proUser) {
      if (!(currentUser && currentUser.id === proUser.id && jobPost.active))
        history.replace(`/job/${postId}`);
      else setLoading(false);
    }
  }, [jobPost, proUser]);

  useEffect(() => {
    loadJobPost();
  }, [postId]);

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

  const onSubmit = async (values, props) => {
    const pack = values.packages[0];
    try {
      await createJobPackage(postId, pack);
      history.push(`/job/${postId}/packages`);
    } catch (e) {
      console.log(e);
    }
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
        {loading ? (
          <Grid container spacing={3} className="mt-3">
            <Grid item md={12} className="flex justify-center">
              <Skeleton variant="text" width={1000} />
            </Grid>
            <Grid item md={12} className="flex justify-center">
              <Skeleton md={12} variant="rect" width={1000} height={700} />
            </Grid>
          </Grid>
        ) : (
          <>
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
          </>
        )}
      </div>
    </>
  );
};

export default AddPackage;
