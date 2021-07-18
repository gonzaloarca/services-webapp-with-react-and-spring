import { Button, Grid, makeStyles, withStyles } from '@material-ui/core';
import clsx from 'clsx';
import React from 'react';
import { useTranslation } from 'react-i18next';
import CategoryCard from '../components/CategoryCard';
import Hero, { HeroSteps } from '../components/Hero';
import JobCard from '../components/JobCard';
import NavBar from '../components/NavBar';
import styles from '../styles';
import { LightenDarkenColor, themeUtils } from '../theme';
import homeStyles from './HomeStyles';
import * as Yup from 'yup';
import { Formik, Form, Field, ErrorMessage } from 'formik';

const useStyles = makeStyles(homeStyles);
const useGlobalStyles = makeStyles(styles);

const categories = [
  { name: 'PLUMBING', image: `${process.env.PUBLIC_URL}/img/plumbing.jpeg`, id: 0 },
  { name: 'PLUMBING', image: `${process.env.PUBLIC_URL}/img/plumbing.jpeg`, id: 1 },
  { name: 'PLUMBING', image: `${process.env.PUBLIC_URL}/img/plumbing.jpeg`, id: 2 },
  { name: 'PLUMBING', image: `${process.env.PUBLIC_URL}/img/plumbing.jpeg`, id: 3 },
];

const jobs = [
  {
    avgRate: 3,
    contractsCompleted: 3,
    price: 2000.1,
    imageUrl: `${process.env.PUBLIC_URL}/img/babysitting.jpeg`,
    jobPost: {
      id: 3,
      uri: 'http://localhost:8080/job-posts/8',
    },
    jobType: {
      description: 'Babysitting',
      id: 7,
    },
    rateType: {
      description: 'ONE_TIME',
      id: 2,
    },
    reviewsCount: 2,
    title: 'Niñero turno mañana',
    zones: [
      {
        description: 'Retiro',
        id: 28,
      },
      {
        description: 'Nuñez',
        id: 20,
      },
      {
        description: 'Colegiales',
        id: 9,
      },
    ],
  },
  {
    avgRate: 3,
    contractsCompleted: 3,
    price: 1500,
    imageUrl: `${process.env.PUBLIC_URL}/img/babysitting.jpeg`,
    jobPost: {
      id: 7,
      uri: 'http://localhost:8080/job-posts/8',
    },
    jobType: {
      description: 'Babysitting',
      id: 7,
    },
    rateType: {
      description: 'HOURLY',
      id: 2,
    },
    reviewsCount: 2,
    title:
      'Niñero turno mañanaaaa ajofejo jaofjaeo aehfeah ofgeafg aoeifgaeof goafg oaeg efeoia',
    zones: [
      {
        description: 'Retiro',
        id: 28,
      },
      {
        description: 'Nuñez',
        id: 20,
      },
      {
        description: 'Colegiales',
        id: 9,
      },
    ],
  },
  {
    avgRate: 3,
    contractsCompleted: 3,
    imageUrl: `${process.env.PUBLIC_URL}/img/babysitting.jpeg`,
    jobPost: {
      id: 8,
      uri: 'http://localhost:8080/job-posts/8',
    },
    jobType: {
      description: 'Babysitting',
      id: 7,
    },
    rateType: {
      description: 'TBD',
      id: 2,
    },
    reviewsCount: 2,
    title: 'Niñero turno mañana',
    zones: [
      {
        description: 'Retiro',
        id: 28,
      },
      {
        description: 'Nuñez',
        id: 20,
      },
      {
        description: 'Colegiales',
        id: 9,
      },
    ],
  },
  {
    avgRate: 3,
    contractsCompleted: 3,
    price: 1500,
    imageUrl: `${process.env.PUBLIC_URL}/img/babysitting.jpeg`,
    jobPost: {
      id: 2,
      uri: 'http://localhost:8080/job-posts/8',
    },
    jobType: {
      description: 'Babysitting',
      id: 7,
    },
    rateType: {
      description: 'TBD',
      id: 2,
    },
    reviewsCount: 0,
    title: 'Niñero turno mañana',
    zones: [
      {
        description: 'Retiro',
        id: 28,
      },
      {
        description: 'Nuñez',
        id: 20,
      },
      {
        description: 'Colegiales',
        id: 9,
      },
    ],
  },
];

export const Home = () => {
  const classes = useStyles();
  const globalClasses = useGlobalStyles();
  const { t } = useTranslation();
  return (
    <div className="bg-white">
      <NavBar currentSection={'/'} isTransparent />
      <Hero />
      <HeroSteps />
      <div className={globalClasses.contentContainerTransparent}>
        <h3 className={clsx(classes.header, 'mb-5')}>{t('home.explore')}</h3>

        <Grid container justifyContent="space-evenly" spacing={3}>
          {categories.map((i) => (
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
