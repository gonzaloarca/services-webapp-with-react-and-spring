import { Avatar, Card, CardActionArea, Grid } from '@material-ui/core';
import { ChevronRight } from '@material-ui/icons';
import { Rating } from '@material-ui/lab';
import { makeStyles } from '@material-ui/styles';
import clsx from 'clsx';
import React from 'react';
import { useTranslation } from 'react-i18next';
import Carousel from 'react-material-ui-carousel';
import { Link } from 'react-router-dom';
import NavBar from '../components/NavBar';
import styles from '../styles';
import { themeUtils } from '../theme';

const post = {
  active: true,
  availableHours: 'Lunes a viernes entre las 8am y 2pm',
  creationDate: '2021-05-02T18:22:13.338478',
  id: 8,
  jobType: {
    description: 'BABYSITTING',
    id: 7,
  },
  images: ['/img/plumbing.jpeg', '/img/carpentry.jpeg'],
  packages: [
    {
      id: 8,
      uri: 'http://localhost:8080/job-posts/8/packages/8',
    },
  ],
  professional: {
    id: 5,
    uri: 'http://localhost:8080/users/5',
  },
  title: 'Niñero turno mañana',
  zones: [
    {
      description: 'RETIRO',
      id: 28,
    },
    {
      description: 'NUNIEZ',
      id: 20,
    },
    {
      description: 'COLEGIALES',
      id: 9,
    },
  ],
};

const jobCard = {
  avgRate: 4.666666666666667,
  contractsCompleted: 4,
  imageUrl: 'http://localhost:8080/job-posts/1/images/1',
  jobPost: {
    id: 1,
    uri: 'http://localhost:8080/job-posts/1',
  },
  jobType: {
    description: 'PLUMBING',
    id: 0,
  },
  price: 160.0,
  rateType: {
    description: 'HOURLY',
    id: 0,
  },
  reviewsCount: 3,
  title: 'Plomería de excelente calidad',
  zones: [
    {
      description: 'RETIRO',
      id: 28,
    },
    {
      description: 'PALERMO',
      id: 21,
    },
    {
      description: 'NUNIEZ',
      id: 20,
    },
    {
      description: 'COLEGIALES',
      id: 9,
    },
    {
      description: 'BELGRANO',
      id: 4,
    },
  ],
};

const packages = [
  {
    active: true,
    description:
      'Atencion constante y juego para el desarrollo de musculos como brazos y piernas.',
    id: 8,
    jobPost: {
      id: 8,
      uri: 'http://localhost:8080/job-posts/8',
    },
    rateType: {
      description: 'TBD',
      id: 2,
    },
    title: '4 dias a la semana 4 horas',
  },
];

const professional = {
  contractsCompleted: 3,
  reviewAvg: 3.0,
  reviewsQuantity: 2,
  user: {
    id: 5,
    uri: 'http://localhost:8080/users/5',
  },
};

const proUser = {
  email: 'manaaasd@gmail.com',
  id: 5,
  phone: '03034560',
  roles: ['CLIENT', 'PROFESSIONAL'],
  username: 'Manuel Rodriguez',
  image: '/img/plumbing.jpeg',
};

const reviews = [
  {
    creationDate: '2021-05-02T18:22:21.684413',
    description: 'EL MEJOR NIÑERO',
    jobContract: {
      id: 1,
      uri: 'http://localhost:8080/job-posts/8/packages/8/contracts/1',
    },
    rate: 5,
    title: 'No debes moverte de donde estas ⛹⛹⛹⛹⛹⛹',
  },
  {
    creationDate: '2021-05-02T18:22:21.684413',
    description: 'EL MEJOR NIÑERO',
    jobContract: {
      id: 2,
      uri: 'http://localhost:8080/job-posts/8/packages/8/contracts/1',
    },
    rate: 5,
    title: 'No debes moverte de donde estas ⛹⛹⛹⛹⛹⛹',
  },
];

const useStyles = makeStyles((theme) => ({
  carouselImage: {
    width: '100%',
    height: 400,
    objectFit: 'cover',
    boxShadow: '0 3px 6px rgba(0, 0, 0, 0.16)',
  },
  cardContainer: {
    boxShadow: '0 3px 6px rgba(0, 0, 0, 0.16)',
    padding: 30,
  },
  roundedCorners: {
    borderRadius: 10,
  },
  dataDisplayContainer: {
    backgroundColor: 'white',
    display: 'flex',
    justifyContent: 'space-evenly',
    alignItems: 'center',
    padding: '5px',
    height: 80,
    fontWeight: 600,
  },
  avatar: {
    height: '3rem',
    width: '3rem',
  },
  dataDisplayLabel: {
    width: '100%',
    color: 'white',
    display: 'flex',
    justifyContent: 'center',
    fontWeight: 600,
    fontSize: '0.9rem',
  },
  ratingContainer: {
    backgroundColor: 'white',
    display: 'flex',
    justifyContent: 'space-evenly',
    alignItems: 'center',
  },
  ratingValue: {
    fontSize: '2rem',
    fontWeight: 600,
  },
  reviewsCount: {
    color: themeUtils.colors.grey,
    fontWeight: 400,
  },
  hiredContainer: {},
}));

const useGlobalStyles = makeStyles(styles);

const JobPost = ({ match }) => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();

  return (
    <>
      <NavBar currentSection={'/search'} />
      <div className={globalClasses.contentContainerTransparent}>
        <Carousel navButtonsAlwaysVisible>
          {post.images.map((item, i) => (
            <img key={i} src={item} className={classes.carouselImage} alt="" />
          ))}
        </Carousel>
        <ProfessionalCard
          professional={professional}
          user={proUser}
          avgRate={jobCard.avgRate}
          reviewsCount={jobCard.reviewsCount}
          contractsCompleted={jobCard.contractsCompleted}
        />
      </div>
      Viendo jobpost con id {match.params.id}
    </>
  );
};

const ProfessionalCard = ({
  professional,
  user,
  avgRate,
  reviewsCount,
  contractsCompleted,
}) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className={classes.cardContainer}>
      <Grid container spacing={4}>
        <Grid item md={4} sm={6} xs={12}>
          {/* Avatar y nombre */}
          <DataDisplayCard
            title={t('professional')}
            color={themeUtils.colors.blue}
            routePath={`/profile/${user.id}`}
          >
            <div
              className={clsx(
                classes.dataDisplayContainer,
                classes.roundedCorners
              )}
            >
              <Avatar src={user.image} className={classes.avatar} />
              {user.username}
              <ChevronRight />
            </div>
          </DataDisplayCard>
        </Grid>
        <Grid item md={4} sm={6} xs={12}>
          <DataDisplayCard
            title={t('avgrate')}
            color={themeUtils.colors.yellow}
          >
            <div
              className={clsx(
                classes.dataDisplayContainer,
                classes.roundedCorners
              )}
            >
              <p className={classes.ratingValue}>{avgRate.toFixed(2)}</p>
              <div className="flex">
                <Rating precision={0.5} readOnly value={avgRate} />
                <p className={classes.reviewsCount}>
                  {t('reviewcount', { count: reviewsCount })}
                </p>
              </div>
            </div>
          </DataDisplayCard>
        </Grid>
        <Grid item md={4} sm={6} xs={12}>
          <div className={classes.hiredContainer}></div>
        </Grid>
      </Grid>
    </div>
  );
};

const DataDisplayCard = ({ title, color, routePath = null, children }) => {
  const classes = useStyles();

  return (
    <Card className={classes.roundedCorners}>
      <div className="p-1" style={{ backgroundColor: color }}>
        <CardActionArea
          disabled={routePath == null}
          component={Link}
          to={routePath}
        >
          {children}
        </CardActionArea>
      </div>
      <div
        className={classes.dataDisplayLabel}
        style={{ backgroundColor: color }}
      >
        {title.toUpperCase()}
      </div>
    </Card>
  );
};

export default JobPost;
