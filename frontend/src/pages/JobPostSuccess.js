import React, { useContext, useEffect } from 'react';
import { Helmet } from 'react-helmet';
import { useTranslation } from 'react-i18next';
import { Button, Card, Grid, makeStyles } from '@material-ui/core';
import styles from '../styles';
import clsx from 'clsx';
import { Link } from 'react-router-dom';
import { themeUtils } from '../theme';
import { NavBarContext } from '../context';

const useGlobalStyles = makeStyles(styles);

const useStyles = makeStyles((theme) => ({
  submitButton: {
    color: 'white',
    backgroundColor: themeUtils.colors.blue,
    transition: 'color 0.1s',
    '&:hover': {
      backgroundColor: themeUtils.colors.darkBlue,
      transition: 'color 0.1s',
    },
    fontSize: '1em',
  },
}));

const JobPostSuccess = ({ match }) => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();

  const { setNavBarProps } = useContext(NavBarContext);

  useEffect(() => {
    setNavBarProps({currentSection:'/create-job-post',isTransparent:false});
  },[])

  return (
    <>
      <Helmet>
        <title>{t('title', { section: 'Servicio creado' })}</title>
      </Helmet>
      <Grid
        container
        className={clsx(
          globalClasses.contentContainerTransparent,
          'flex justify-center content-center my-40'
        )}
      >
        <Card className="p-20 flex flex-col justify-center content-center">
          <div className="font-bold text-4xl mb-2" style={{ color: 'green' }}>
            {match.params.edit
              ? t('jobpostsuccess.edit')
              : t('jobpostsuccess.title')}
          </div>
          <div>{t('jobpostsuccess.instruction')}</div>
          <div className="flex justify-center mt-3">
            <Button
              className={clsx(classes.submitButton, 'w-full mt-5')}
              component={Link}
              to={`/job/${match.params.id}`}
            >
              {t('jobpostsuccess.btn')}
            </Button>
          </div>
        </Card>
      </Grid>
    </>
  );
};

export default JobPostSuccess;
