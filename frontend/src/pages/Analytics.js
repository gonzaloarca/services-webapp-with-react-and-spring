import React from 'react';
import NavBar from '../components/NavBar';
import SectionHeader from '../components/SectionHeader';
import styles from '../styles';
import { Card, Grid, makeStyles } from '@material-ui/core';
import { useTranslation } from 'react-i18next';
import AverageRatingCard from '../components/AverageRatingCard';
import TimesHiredCard from '../components/TimesHiredCard';
import clsx from 'clsx';
import JobCard from '../components/JobCard';
import { Helmet } from 'react-helmet';

// TODO: integracion con API
const currentUser = {
  email: 'manaaasd@gmail.com',
  id: 5,
  phone: '03034560',
  roles: ['CLIENT', 'PROFESSIONAL'],
  username: 'Manuel Rodriguez',
  image: `${process.env.PUBLIC_URL}/img/plumbing.jpeg`,
};

const professional = {
  'contractsCompleted': 3,
  'reviewAvg': 3.0,
  'reviewsQuantity': 2,
  'user': {
    'id': 5,
    'uri': 'http://localhost:8080/users/5',
  },
};

const rankings = [
  {
    'jobType': {
      'description': 'Babysitting',
      'id': 7,
    },
    'ranking': 1,
  },
  {
    'jobType': {
      'description': 'Plumbing',
      'id': 7,
    },
    'ranking': 3,
  },
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
];

const useStyles = makeStyles((theme) => ({
  analyticsCard: {
    borderRadius: '10px',
    height: '100%',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '20px',
  },
  card: {
    borderRadius: '10px',
  },
  profileImg: {
    borderRadius: '50%',
    width: '150px',
    height: '150px',
    objectFit: 'cover',
  },
  cardHeader: {
    'backgroundColor': '#485696',
    'color': 'white',
    'fontSize': '20px',
  },
}));

const useGlobalStyles = makeStyles(styles);

const Analytics = () => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.analytics') })}
        </title>
      </Helmet>
      <NavBar currentSection={'/analytics'} />
      <div className={globalClasses.contentContainerTransparent}>
        <SectionHeader sectionName={t('analytics.title')} />
        <Grid container spacing={3}>
          <Grid item sm={3} xs={6}>
            <Card classes={{ root: classes.analyticsCard }}>
              <img
                loading="lazy"
                className={classes.profileImg}
                src={currentUser.image}
                alt={t('account.data.imagealt')}
              />
              <div className="font-bold mt-2 text-center">
                {currentUser.username}
              </div>
            </Card>
          </Grid>
          <Grid
            item
            sm={3}
            xs={6}
            className="h-64 flex flex-col justify-between"
          >
            <AverageRatingCard
              reviewAvg={professional.reviewAvg}
              reviewsQuantity={professional.reviewsQuantity}
            />
            <TimesHiredCard count={professional.contractsCompleted} />
          </Grid>
          {rankings.map((rank) => {
            return (
              <Grid item sm={3} xs={6} className="font-bold h-64">
                <Card classes={{ root: classes.analyticsCard }}>
                  <div className="text-5xl">
                    {t('analytics.number', { rank: rank.ranking })}
                  </div>
                  <div className="mt-2 text-center">
                    {t('analytics.mosthired', {
                      jobType: rank.jobType.description,
                    })}
                  </div>
                </Card>
              </Grid>
            );
          })}
        </Grid>
        <div className="mt-5">
          <ClientsRecommendation />
        </div>
      </div>
    </div>
  );
};

const ClientsRecommendation = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <Card classes={{ root: classes.card }}>
      <div className={clsx(classes.cardHeader, 'py-2 px-5 w-full')}>
        {t('analytics.similar')}
      </div>
      <div className="flex flex-wrap justify-evenly content-center p-3">
        {jobs.map((job) => {
          return (
            <div className="m-3 w-60">
              <JobCard job={job} />
            </div>
          );
        })}
      </div>
    </Card>
  );
};

export default Analytics;
