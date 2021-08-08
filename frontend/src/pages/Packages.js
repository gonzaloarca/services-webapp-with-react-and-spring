import {
  Button,
  Divider,
  Grid,
  Link,
  makeStyles,
  withStyles,
} from '@material-ui/core';
import { Link as RouterLink } from 'react-router-dom';
import React, { useEffect, useState, useContext } from 'react';
import { Trans, useTranslation } from 'react-i18next';
import NavBar from '../components/NavBar';
import styles from '../styles';
import { AddBox, Create, DeleteForever, Launch } from '@material-ui/icons';
import { themeUtils } from '../theme';
import PackageAccordion from '../components/PackageAccordion';
import HirenetModal, { PlainTextBody } from '../components/HirenetModal';
import PackagesHeader from '../components/PackagesHeader';
import { Helmet } from 'react-helmet';
import { useJobPosts, useUser, useJobPackages } from '../hooks';
import { extractLastIdFromURL } from '../utils/urlUtils';
import { UserContext } from '../context';
import { Skeleton } from '@material-ui/lab';

const useGlobalStyles = makeStyles(styles);
const useStyles = makeStyles((theme) => ({
  subHeader: {
    fontSize: themeUtils.fontSizes.h2,
    fontWeight: 600,
    marginBottom: 10,
  },
  packagesContainer: {
    margin: '20px auto',
    width: '70%',
    backgroundColor: 'white',
    padding: 30,
    boxShadow: themeUtils.shadows.containerShadow,
  },
  addIcon: {
    color: themeUtils.colors.electricBlue,
  },
}));

const Packages = ({ match, history }) => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();
  const postId = match.params.id;

  const { getJobPostById } = useJobPosts();
  const { getJobPackagesByPostId, deleteJobPackage } = useJobPackages();
  const { getUserById } = useUser();
  const { currentUser } = useContext(UserContext);

  const [openDelete, setOpenDelete] = useState(false);
  const [loading, setLoading] = useState(true);
  const [jobPost, setJobPost] = useState(null);
  const [packages, setPackages] = useState([]);
  const [proUser, setProUser] = useState(null);

  const loadJobPost = async () => {
    try {
      const jobPost = await getJobPostById(postId);
      setJobPost(jobPost);
    } catch (e) {
      history.replace(`/error`);
    }
  };

  const loadPackages = async () => {
    try {
      const jobPackages = await getJobPackagesByPostId(postId);
      setPackages(jobPackages);
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
    if (jobPost && packages && packages.length > 0 && proUser) {
      if (!(currentUser && currentUser.id === proUser.id && jobPost.active))
        history.replace(`/job/${postId}`);
      else setLoading(false);
    }
  }, [jobPost, packages, proUser]);

  useEffect(() => {
    loadJobPost();
    loadPackages();
  }, [postId]);

  const deletePackage = async (pack) => {
    try {
      await deleteJobPackage(pack, postId);
      setLoading(true);
      await loadPackages();
      setLoading(false);
    } catch (e) {
      history.push(`/error`);
    }
  };

  return (
    <>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.packages') })}
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
              <Skeleton md={12} variant="rect" width={800} height={300} />
            </Grid>
          </Grid>
        ) : (
          <>
            <PackagesHeader />
            <div className={classes.packagesContainer}>
              <h2 className={classes.subHeader}>
                {t('managepackages.servicepackages')}
              </h2>
              <Link
                className="text-sm flex px-5"
                component={RouterLink}
                to={`/job/${jobPost.id}`}
              >
                <Launch className="text-lg mr-1" />
                <Trans
                  i18nKey={'jobreference'}
                  values={{ title: jobPost.title }}
                  components={{
                    semibold: <span className="ml-1 font-semibold" />,
                  }}
                />
                {/* {t('review.jobreference', { title: review.jobPost.title })} */}
              </Link>
              <AddPackageButton
                className="mt-6"
                startIcon={<AddBox className={classes.addIcon} />}
                fullWidth
                component={RouterLink}
                to={`/job/${jobPost.id}/packages/add`}
              >
                {t('managepackages.add')}
              </AddPackageButton>
              <Divider className="my-8" />
              {packages.map((pack) => (
                <Grid container spacing={3} key={pack.id}>
                  <Grid item xs={10}>
                    <PackageAccordion pack={pack} isHireable={false} />
                  </Grid>
                  <Grid item xs={2}>
                    <Button
                      fullWidth
                      startIcon={<Create />}
                      style={{ color: themeUtils.colors.electricBlue }}
                      component={RouterLink}
                      to={`/job/${jobPost.id}/packages/${pack.id}/edit`}
                    >
                      {t('managepackages.edit')}
                    </Button>
                    {packages.length > 1 && (
                      <>
                        <Button
                          fullWidth
                          startIcon={<DeleteForever />}
                          style={{ color: themeUtils.colors.red }}
                          onClick={() => setOpenDelete(true)}
                        >
                          {' '}
                          {t('managepackages.delete')}{' '}
                        </Button>
                        <HirenetModal
                          open={openDelete}
                          title={t('managepackages.deletemodal.title')}
                          body={() => (
                            <PlainTextBody>
                              {t('managepackages.deletemodal.body')}
                            </PlainTextBody>
                          )}
                          onNegative={() => setOpenDelete(false)}
                          onAffirmative={() => deletePackage(pack)}
                          affirmativeLabel={t(
                            'managepackages.deletemodal.affirmative'
                          )}
                          negativeLabel={t(
                            'managepackages.deletemodal.negative'
                          )}
                          affirmativeColor={themeUtils.colors.red}
                        />
                      </>
                    )}
                  </Grid>
                </Grid>
              ))}
            </div>
          </>
        )}
      </div>
    </>
  );
};

const AddPackageButton = withStyles((theme) => ({
  root: {
    backgroundColor: themeUtils.colors.lightGrey,
    textTransform: 'uppercase',
  },
}))(Button);

export default Packages;
