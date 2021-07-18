import React from 'react';
import NavBar from '../components/NavBar';
import styles from '../styles';
import {
  Grid,
  makeStyles,
  Card,
  CardMedia,
  Tab,
  Tabs,
  AppBar,
  Divider,
  LinearProgress,
} from '@material-ui/core';
import { useTranslation } from 'react-i18next';
import CircleIcon from '../components/CircleIcon';
import { Grade, Work } from '@material-ui/icons';
import { themeUtils } from '../theme';
import TabPanel from '../components/TabPanel';
import ServiceCard from '../components/ServiceCard';
import ReviewCard from '../components/ReviewCard';
import { Rating } from '@material-ui/lab';
import AverageRatingCard from '../components/AverageRatingCard';
import TimesHiredCard from '../components/TimesHiredCard';
import { useParams, useHistory } from 'react-router-dom';
import { Helmet } from 'react-helmet';

const professional = {
  'contractsCompleted': 3,
  'reviewAvg': 3.0,
  'reviewsQuantity': 2,
  'user': {
    'id': 5,
    'uri': 'http://localhost:8080/users/5',
  },
};

const user = {
  'email': 'manaaasd@gmail.com',
  'id': 5,
  'phone': '03034560',
  'roles': ['CLIENT', 'PROFESSIONAL'],
  'username': 'Manuel Rodriguez',
  'image':
    'https://i.pinimg.com/474x/ec/e4/89/ece489af93d39071a6e23bd6b93766a3.jpg',
};

const jobCard = {
  'avgRate': 4.666666666666667,
  'contractsCompleted': 4,
  'imageUrl': '/img/plumbing.jpeg',
  'jobPost': {
    'id': 1,
    'uri': 'http://localhost:8080/job-posts/1',
  },
  'jobType': {
    'description': 'PLUMBING',
    'id': 0,
  },
  'price': 160.0,
  'rateType': {
    'description': 'HOURLY',
    'id': 0,
  },
  'reviewsCount': 3,
  'title': 'Plomería de excelente calidad',
  'zones': [
    {
      'description': 'RETIRO',
      'id': 28,
    },
    {
      'description': 'PALERMO',
      'id': 21,
    },
    {
      'description': 'NUNIEZ',
      'id': 20,
    },
    {
      'description': 'COLEGIALES',
      'id': 9,
    },
    {
      'description': 'BELGRANO',
      'id': 4,
    },
  ],
};

const reviews = [
  {
    creationDate: '2021-05-02T18:22:21.684413',
    description: 'EL MEJOR NIÑERO',
    client: {
      username: 'El Beto',
      image: '/img/babysitting.jpeg',
    },
    jobPost: {
      title: 'Niñero turno mañana',
      id: 3,
    },
    jobContract: {
      id: 1,
      uri: 'http://localhost:8080/job-posts/8/packages/8/contracts/1',
    },
    rate: 5,
    title: 'No debes moverte de donde estas ⛹⛹⛹⛹⛹⛹',
  },
  {
    creationDate: '2021-10-02T18:22:21.684413',
    description: 'EL MEJOR NIÑERO',
    client: {
      username: 'El Beto',
      image: '/img/babysitting.jpeg',
    },
    jobPost: {
      title: 'Niñero turno mañana',
      id: 3,
    },
    jobContract: {
      id: 2,
      uri: 'http://localhost:8080/job-posts/3/packages/8/contracts/2',
    },
    rate: 3,
    title: 'No debes moverte de donde estas ⛹⛹⛹⛹⛹⛹',
  },
];

const services = [jobCard, jobCard];

//  TODO: levantar cantidad de servicios de un professional de algun dto
const postCount = 5;

const useStyles = makeStyles((theme) => ({
  card: {
    borderRadius: '10px',
  },
  avatarImage: {
    borderRadius: '10px',
    minHeight: '300px',
    minWidth: '100%',
    width: '100%',
    height: '200px',
    objectFit: 'cover',
  },
  tabs: {
    backgroundColor: 'white',
    color: 'black',
  },
}));

const useGlobalStyles = makeStyles(styles);

const Profile = ({ match }) => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <>
      <Helmet>
        <title>
          {t('title', {
            section: t('navigation.sections.profile', {
              username: user.username,
            }),
          })}
        </title>
      </Helmet>
      <NavBar currentSection={'/search'} />
      <div className={globalClasses.contentContainerTransparent}>
        <Grid container spacing={3}>
          <Grid item sm={3} xs={12}>
            <UserInfo />
          </Grid>
          <Grid item sm={9} xs={12}>
            <Card classes={{ root: classes.card }}>
              <ProfileTabs />
            </Card>
          </Grid>
        </Grid>
      </div>
    </>
  );
};

const UserInfo = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <Grid container spacing={3}>
      <Grid item sm={12} className="w-full">
        <Card classes={{ root: classes.card }}>
          <CardMedia
            className={classes.avatarImage}
            image={user.image}
            title="Imagen del usuario"
          />
          <div className="p-3">
            <div className="font-bold text-2xl">{user.username}</div>
            <div className="font-extralight">
              {user.roles.includes('PROFESSIONAL')
                ? t('professional')
                : t('client')}
            </div>
          </div>
        </Card>
      </Grid>
      <Grid item sm={12} className="w-full">
        <AverageRatingCard
          reviewAvg={professional.reviewAvg}
          reviewsQuantity={professional.reviewsQuantity}
        />
      </Grid>
      <Grid item sm={12} className="w-full">
        <TimesHiredCard count={professional.contractsCompleted} />
      </Grid>
    </Grid>
  );
};

const ProfileTabs = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  const tabPaths = ['services', 'reviews'];

  const { id, activeTab } = useParams();

  const history = useHistory();

  let initialTab = 0;

  if (activeTab) {
    tabPaths.forEach((path, index) => {
      if (path === activeTab) initialTab = index;
    });
  }

  const [tabValue, setTabValue] = React.useState(initialTab);

  const handleChange = (event, newValue) => {
    setTabValue(newValue);
    history.push(`/profile/${id}/${tabPaths[newValue]}`);
  };

  return (
    <>
      <AppBar position="static">
        <Tabs
          variant="fullWidth"
          className={classes.tabs}
          value={tabValue}
          onChange={handleChange}
        >
          <Tab
            label={
              <div className="flex items-center justify-center">
                <CircleIcon
                  className="mr-2"
                  color={themeUtils.colors.lightBlue}
                  size="2rem"
                >
                  <Work className="text-white" />
                </CircleIcon>
                {t('profile.services', { count: postCount })}
              </div>
            }
          />
          <Tab
            label={
              <div className="flex items-center justify-center">
                <CircleIcon
                  className="mr-2"
                  color={themeUtils.colors.yellow}
                  size="2rem"
                >
                  <Grade className="text-white" />
                </CircleIcon>
                {t('profile.reviews', { count: professional.reviewsQuantity })}
              </div>
            }
          />
        </Tabs>
      </AppBar>

      <TabPanel value={tabValue} index={0}>
        {services.map((service, index) => {
          return <ServiceCard jobCard={service} key={index} />;
        })}
      </TabPanel>
      <TabPanel value={tabValue} index={1}>
        <Grid container spacing={5} className="my-1">
          <Grid item sm={6} xs={12} className="flex justify-center">
            <div className="flex flex-col justify-center items-end">
              <div className="font-bold text-7xl">
                {professional.reviewAvg.toFixed(2)}
              </div>
              <Rating readOnly value={professional.reviewAvg} />
              <div>
                {t('profile.ratecount', {
                  count: professional.reviewsQuantity,
                })}
              </div>
            </div>
          </Grid>
          <Grid
            item
            sm={6}
            xs={12}
            className="flex flex-col justify-center items-center"
          >
            <ReviewsDistribution />
          </Grid>
        </Grid>

        <Divider />
        {reviews.map((review) => (
          <div key={review.jobContract.id}>
            <ReviewCard review={review} />
          </div>
        ))}
      </TabPanel>
    </>
  );
};

const ReviewsDistribution = () => {
  let distribution = [0, 0, 0, 0, 0];

  reviews.forEach((review) => {
    distribution[review.rate - 1]++;
  });

  return (
    <>
      <ReviewCountComponent stars={5} count={distribution[5 - 1]} />
      <ReviewCountComponent stars={4} count={distribution[4 - 1]} />
      <ReviewCountComponent stars={3} count={distribution[3 - 1]} />
      <ReviewCountComponent stars={2} count={distribution[2 - 1]} />
      <ReviewCountComponent stars={1} count={distribution[1 - 1]} />
    </>
  );
};

const ReviewCountComponent = ({ stars, count }) => {
  const { t } = useTranslation();

  return (
    <Grid container spacing={1}>
      <Grid item sm={3} xs={3} className="flex justify-end">
        {stars === 1
          ? t('profile.starsingular')
          : t('profile.starsplural', { count: stars })}
      </Grid>
      <Grid item sm={6} xs={6} className="flex flex-col justify-center">
        <LinearProgress
          variant="determinate"
          value={(100 * count) / professional.reviewsQuantity}
          color="secondary"
          className="rounded"
        />
      </Grid>
      <Grid item sm={1} xs={1} className="flex justify-start">
        <div className="text-center w-full">{count}</div>
      </Grid>
    </Grid>
  );
};

export default Profile;
