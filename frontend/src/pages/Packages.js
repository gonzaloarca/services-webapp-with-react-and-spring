import {
  Button,
  Divider,
  Grid,
  Link,
  makeStyles,
  withStyles,
} from '@material-ui/core';
import { Link as RouterLink } from 'react-router-dom';
import React, { useState } from 'react';
import { Trans, useTranslation } from 'react-i18next';
import NavBar from '../components/NavBar';
import styles from '../styles';
import { AddBox, Create, DeleteForever, Launch } from '@material-ui/icons';
import { themeUtils } from '../theme';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCubes } from '@fortawesome/free-solid-svg-icons';
import clsx from 'clsx';
import PackageAccordion from '../components/PackageAccordion';
import HirenetModal, { PlainTextBody } from '../components/HirenetModal';
import PackagesHeader from '../components/PackagesHeader';

const jobPost = {
  id: 3,
  title:
    'Aute commodo excepteur non esse irure consequat duis adipisicing ea adipisicing in cillum aliqua magna velit.',
};

const packages = [
  {
    description:
      'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
    id: 10,
    price: 1000,
    rateType: {
      id: 0,
      description: 'HOURLY',
    },
    title: 'Lorem ipsum dolor sit amet',
  },
  {
    description:
      'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
    id: 11,
    price: 1000,
    rateType: {
      id: 0,
      description: 'HOURLY',
    },
    title: 'Lorem ipsum dolor sit amet',
  },
];

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

const Packages = () => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();

  const [openDelete, setOpenDelete] = useState(false);

  return (
    <>
      <NavBar currentSection="/create-job-post" />
      <div className={globalClasses.contentContainerTransparent}>
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
                      body={
                        <PlainTextBody>
                          {t('managepackages.deletemodal.body')}
                        </PlainTextBody>
                      }
                      onNegative={() => setOpenDelete(false)}
                      onAffirmative={() => console.log('DELETE PACKAGE')}
                      affirmativeLabel={t(
                        'managepackages.deletemodal.affirmative'
                      )}
                      negativeLabel={t('managepackages.deletemodal.negative')}
                      affirmativeColor={themeUtils.colors.red}
                    />
                  </>
                )}
              </Grid>
            </Grid>
          ))}
        </div>
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
