import { Button, Grid, makeStyles, withStyles } from '@material-ui/core';
import clsx from 'clsx';
import React, { useContext, useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import CategoryCard from '../components/CategoryCard';
import Hero, { HeroSteps } from '../components/Hero';
import JobCard from '../components/JobCard';
import NavBar from '../components/NavBar';
import styles from '../styles';
import { LightenDarkenColor, themeUtils } from '../theme';
import homeStyles from './HomeStyles';
import { CategoriesAndZonesContext } from '../context';
import { useJobCards } from '../hooks';

const useStyles = makeStyles(homeStyles);
const useGlobalStyles = makeStyles(styles);

export const Home = (props) => {
  const classes = useStyles();
  const globalClasses = useGlobalStyles();
  const { t } = useTranslation();
  const { categories, zones } = useContext(CategoriesAndZonesContext);
  const [jobs, setJobs] = useState([]);
  const { getJobCards } = useJobCards();
  const loadJobCards = async () => {
    setJobs(await getJobCards());
  };

  useEffect(() => {
    loadJobCards();
  }, []);
  return (
    <div className="bg-white">
      <NavBar currentSection={'/'} isTransparent />
      <Hero zones={zones} />
      <HeroSteps />
      <div className={globalClasses.contentContainerTransparent}>
        <h3 className={clsx(classes.header, 'mb-5')}>{t('home.explore')}</h3>

        <Grid container justifyContent="space-evenly" spacing={3}>
          {categories.slice(0, 4).map((i) => (
            <Grid key={i.id} item xs={6} sm={4} md={3} lg={2}>
              <CategoryCard category={i} />
            </Grid>
          ))}
          <Grid item xs={6} sm={4} md={3} lg={2}>
            <CategoryCard showAll />
          </Grid>
        </Grid>
      </div>
      <div className={classes.sectionShadow}>
        <div className={globalClasses.contentContainerTransparent}>
          <h3 className={clsx(classes.header, 'mb-5')}>{t('home.newest')}</h3>

          <Grid container spacing={3}>
            {jobs.map((i) => (
              <Grid key={i.jobPost.id} item xs={12} sm={6} md={4} lg={3}>
                <JobCard job={i} />
              </Grid>
            ))}
          </Grid>
        </div>
      </div>
      <PublishBanner />
    </div>
  );
};

const PublishBanner = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className={classes.publishBannerContainer}>
      <p>{t('home.publishbanner.question')}</p>
      <p>{t('home.publishbanner.answer')}</p>
      <PublishButton className="mt-4">
        {t('home.publishbanner.button')}
      </PublishButton>
    </div>
  );
};

const PublishButton = withStyles((theme) => ({
  root: {
    fontSize: '1.1rem',
    color: themeUtils.colors.yellow,
    backgroundColor: themeUtils.colors.blue,
    '&:hover': {
      color: LightenDarkenColor(themeUtils.colors.yellow, 30),
      backgroundColor: LightenDarkenColor(themeUtils.colors.blue, 30),
    },
  },
}))(Button);
