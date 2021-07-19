import { Button, Fade, Grid, makeStyles, withStyles } from '@material-ui/core';
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
import { ConstantDataContext } from '../context';
import { useJobCards } from '../hooks';
import { Helmet } from 'react-helmet';
import { Link } from 'react-router-dom';
import { ChevronRight } from '@material-ui/icons';

const useStyles = makeStyles(homeStyles);
const useGlobalStyles = makeStyles(styles);

export const Home = (props) => {
  const classes = useStyles();
  const globalClasses = useGlobalStyles();
  const { t } = useTranslation();
  const { categories, zones } = useContext(ConstantDataContext);
  const [jobs, setJobs] = useState([]);
  const [loadingJobs, setLoadingJobs] = useState(true);
  const [loadingCategories, setLoadingCategories] = useState(true);
  const [showChevron, setShowChevron] = useState(false);
  const { getJobCards } = useJobCards();
  const loadJobCards = async () => {
    setJobs(await getJobCards());
    setLoadingJobs(false);
  };

  useEffect(() => {
    if (categories && categories.length > 0) setLoadingCategories(false);
  }, [categories]);

  useEffect(() => {
    loadJobCards();
  }, []);
  return (
    <div className="bg-white">
      <Helmet>
        <title>{t('title', { section: t('navigation.sections.home') })}</title>
      </Helmet>
      <NavBar currentSection={'/'} isTransparent />
      <Hero zones={zones} />
      <HeroSteps />
      <div className={globalClasses.contentContainerTransparent}>
        <h3 className={clsx(classes.header, 'mb-5')}>{t('home.explore')}</h3>

        <Grid container justifyContent="space-evenly" spacing={3}>
          {loadingCategories ? (
            [1, 2, 3, 4, 5].map((i) => (
              <Grid key={i} item xs={6} sm={4} md={3} lg={2}>
                <CategoryCard category={{}} isLoading />
              </Grid>
            ))
          ) : (
            <>
              {categories
                .sort(() => 0.5 - Math.random())
                .slice(0, 4)
                .map((i) => (
                  <Grid key={i.id} item xs={6} sm={4} md={3} lg={2}>
                    <CategoryCard category={i} />
                  </Grid>
                ))}
              <Grid item xs={6} sm={4} md={3} lg={2}>
                <CategoryCard showAll />
              </Grid>
            </>
          )}
        </Grid>
      </div>
      <div className={classes.sectionShadow}>
        <div className={globalClasses.contentContainerTransparent}>
          <div className="flex items-start justify-between">
            <h3 className={clsx(classes.header, 'mb-5')}>{t('home.newest')}</h3>
            <div className="flex items-center h-full pt-2">
              <Link
                className={classes.viewAll}
                onMouseEnter={() => setShowChevron(true)}
                onMouseLeave={() => setShowChevron(false)}
                to="/search"
              >
                {t('home.viewall')}
              </Link>
              <Fade in={showChevron}>
                <ChevronRight className={clsx(classes.viewAll, 'text-2xl')} />
              </Fade>
            </div>
          </div>

          <Grid container spacing={3}>
            {loadingJobs ? (
              [1, 2, 3, 4].map((i) => (
                <Grid key={i} item xs={12} sm={6} md={4} lg={3}>
                  <JobCard job={{}} isLoading />
                </Grid>
              ))
            ) : jobs.length > 0 ? (
              jobs.map((i) => (
                <Grid key={i.id} item xs={12} sm={6} md={4} lg={3}>
                  <JobCard job={i} />
                </Grid>
              ))
            ) : (
              <div className={classes.noJobsContainer}>
                <img
                  src={process.env.PUBLIC_URL + 'img/unavailable-1.svg'}
                  alt=""
                  className={classes.noJobsImage}
                  loading="lazy"
                />
                <h3 className={classes.noJobsMessage}>{t('home.nojobs')}</h3>
              </div>
            )}
            {}
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
      <PublishButton component={Link} to="/create-job-post" className="mt-4">
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
