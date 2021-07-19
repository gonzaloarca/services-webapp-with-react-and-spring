import React, { useContext, useEffect, useState } from 'react';
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
import { Rating, Skeleton } from '@material-ui/lab';
import AverageRatingCard from '../components/AverageRatingCard';
import TimesHiredCard from '../components/TimesHiredCard';
import { useParams, useHistory } from 'react-router-dom';
import { Helmet } from 'react-helmet';
import { useReviews, useUser } from '../hooks';
import { isProfessional } from '../utils/userUtils.js';
import { UserContext } from '../context';
import { useJobCards } from '../hooks';
import BottomPagination from '../components/BottomPagination';

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
  noContentContainer: {
    height: 350,
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  noContentImage: {
    height: '40%',
    width: '40%',
    objectFit: 'contain',
    marginBottom: 30,
  },
  noContentMessage: {
    fontSize: themeUtils.fontSizes.lg,
    fontWeight: 500,
    textAlign: 'center',
  },
}));

const useGlobalStyles = makeStyles(styles);

const Profile = ({ match }) => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();
  const history = useHistory();
  const [loadingData, setLoadingData] = useState(true);
  const { getProfessionalInfo, getUserById } = useUser();
  const [values, setValues] = useState({
    userInfo: {},
    proInfo: {},
  });

  const loadData = async (id) => {
    try {
      setValues({
        userInfo: await getUserById(id),
        proInfo: await getProfessionalInfo(id),
      });
      setLoadingData(false);
    } catch (error) {
      console.log(error);
      history.push('/');
    }
  };

  useEffect(() => {
    loadData(match.params.id);
  }, []);

  return (
    <>
      <Helmet>
        <title>
          {t('title', {
            section: t('navigation.sections.profile', {
              username: values?.userInfo.username,
            }),
          })}
        </title>
      </Helmet>
      <NavBar />
      <div className={globalClasses.contentContainerTransparent}>
        <Grid container spacing={3}>
          <Grid item sm={3} xs={12}>
            <UserInfo values={values} loadingData={loadingData} />
          </Grid>
          <Grid item sm={9} xs={12}>
            {loadingData ? (
              <Skeleton height={600} />
            ) : (
              <Card classes={{ root: classes.card }}>
                <ProfileTabs
                  details={values.proInfo}
                  proId={values.userInfo.id}
                />
              </Card>
            )}
          </Grid>
        </Grid>
      </div>
    </>
  );
};

const UserInfo = ({ values, loadingData }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <Grid container spacing={3}>
      <Grid item sm={12} className="w-full">
        <Card classes={{ root: classes.card }}>
          {loadingData ? (
            <Skeleton variant="rect" height={300} />
          ) : (
            <>
              <CardMedia
                className={classes.avatarImage}
                image={values.userInfo.image}
                title="Imagen del usuario"
              />
              <div className="p-3">
                <div className="font-bold text-2xl">
                  {values.userInfo.username}
                </div>
                <div className="font-extralight">
                  {isProfessional(values.userInfo)
                    ? t('professional')
                    : t('client')}
                </div>
              </div>
            </>
          )}
        </Card>
      </Grid>
      <Grid item sm={12} className="w-full">
        {loadingData ? (
          <Skeleton variant="rect" height={100} />
        ) : (
          <AverageRatingCard
            reviewAvg={values.proInfo.reviewAvg}
            reviewsQuantity={values.proInfo.reviewsQuantity}
          />
        )}
      </Grid>
      <Grid item sm={12} className="w-full">
        {loadingData ? (
          <Skeleton variant="rect" height={100} />
        ) : (
          <TimesHiredCard count={values.proInfo.contractsCompleted} />
        )}
      </Grid>
    </Grid>
  );
};

const ProfileTabs = ({ details, proId }) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const { getReviewsByProId, reviewsLinks } = useReviews();
  const [reviews, setReviews] = useState();
  const [queryParams, setQueryParams] = useState({ page: '1' });
  const [reviewsMaxPage, setReviewsMaxPage] = useState(1);
  const { getJobCardsByProId, jobCardsLinks } = useJobCards();
  const [jobCards, setJobCards] = useState([]);
  const [jobCardsMaxPage, setJobCardsMaxPage] = useState(1);
  const [loadingReviews, setLoadingReviews] = useState(true);
  const [loadingJobCards, setLoadingJobcards] = useState(true);

  const tabPaths = ['services', 'reviews'];

  const { id, activeTab } = useParams();

  const history = useHistory();

  let initialTab = 0;

  if (activeTab) {
    tabPaths.forEach((path, index) => {
      if (path === activeTab) initialTab = index;
    });
  } else {
    history.replace(`/profile/${id}/${tabPaths[0]}`);
  }

  const [tabValue, setTabValue] = useState(initialTab);

  const handleChange = (event, newValue) => {
    // event es necesario para que funcione las tabs
    setTabValue(newValue);
    history.push(`/profile/${id}/${tabPaths[newValue]}`);
  };

  const loadJobCards = async () => {
    try {
      setJobCards(
        await getJobCardsByProId({ proId: proId, page: queryParams.page })
      );
      setJobCardsMaxPage(
        parseInt(jobCardsLinks?.last?.page) ||
          parseInt(jobCardsLinks?.prev?.page)
      );
      setLoadingJobcards(false);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    loadJobCards();
  }, [queryParams]);

  const loadReviews = async () => {
    if (details.reviewsQuantity > 0) {
      try {
        setReviews(await getReviewsByProId(proId, queryParams.page));
        setReviewsMaxPage(
          parseInt(reviewsLinks?.last?.page) ||
            parseInt(reviewsLinks?.prev?.page)
        );
      } catch (e) {
        // console.log(e);
        return;
      }
    }
    setLoadingReviews(false);
  };

  useEffect(() => {
    loadReviews();
  }, [queryParams]);

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
              loadingJobCards ? (
                <Skeleton height={100} width={300} />
              ) : (
                <div className="flex items-center justify-center">
                  <CircleIcon
                    className="mr-2"
                    color={themeUtils.colors.lightBlue}
                    size="2rem"
                  >
                    <Work className="text-white" />
                  </CircleIcon>
                  {t('profile.services', { count: jobCards?.length })}
                </div>
              )
            }
          />
          <Tab
            label={
              loadingReviews ? (
                <Skeleton height={100} width={300} />
              ) : (
                <div className="flex items-center justify-center">
                  <CircleIcon
                    className="mr-2"
                    color={themeUtils.colors.yellow}
                    size="2rem"
                  >
                    <Grade className="text-white" />
                  </CircleIcon>
                  {t('profile.reviews', {
                    count: details.reviewsQuantity || 0,
                  })}
                </div>
              )
            }
          />
        </Tabs>
      </AppBar>

      <TabPanel value={tabValue} index={0}>
        {loadingJobCards ? (
          <Grid container spacing={1}>
            <Grid item sm={12} className="flex justify-center">
              <Skeleton height={300} width={700} />
            </Grid>
            <Grid item sm={12} className="flex justify-center">
              <Skeleton height={300} width={700} />
            </Grid>
            <Grid item sm={12} className="flex justify-center">
              <Skeleton height={300} width={700} />
            </Grid>
          </Grid>
        ) : jobCards?.length === 0 ? (
          <div className={classes.noContentContainer}>
            <img
              src={process.env.PUBLIC_URL + '/img/job-1.svg'}
              alt=""
              className={classes.noContentImage}
              loading="lazy"
            />
            <h3 className={classes.noContentMessage}>
              {t('profile.noservices')}
            </h3>
          </div>
        ) : (
          <>
            {jobCards?.map((jobCard, index) => {
              return <ServiceCard jobCard={jobCard} key={index} />;
            })}
            <BottomPagination
              maxPage={jobCardsMaxPage}
              setQueryParams={setQueryParams}
              queryParams={queryParams}
            />
          </>
        )}
      </TabPanel>
      <TabPanel value={tabValue} index={1}>
        {details.reviewsQuantity === 0 ? (
          <div className={classes.noContentContainer}>
            <img
              src={process.env.PUBLIC_URL + '/img/star-1.svg'}
              alt=""
              className={classes.noContentImage}
              loading="lazy"
            />
            <h3 className={classes.noContentMessage}>
              {t('profile.noreviews')}
            </h3>
          </div>
        ) : (
          <>
            <Grid container spacing={5} className="my-1">
              <Grid item sm={6} xs={12} className="flex justify-center">
                <div className="flex flex-col justify-center items-end">
                  <div className="font-medium text-7xl">
                    {details.reviewAvg?.toFixed(2) || 0}
                  </div>
                  <Rating readOnly value={details.reviewAvg || 0} />
                  <div>
                    {t('profile.ratecount', {
                      count: details.reviewsQuantity || 0,
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
                <ReviewsDistribution
                  reviewsQuantity={details.reviewsQuantity || 0}
                  userId={proId}
                />
              </Grid>
            </Grid>

            <Divider />
            {loadingReviews ? (
              <Grid container spacing={1}>
                <Grid sm={12} className="flex justify-center">
                  <Skeleton height={300} width={700} />
                </Grid>
                <Grid sm={12} className="flex justify-center">
                  <Skeleton height={300} width={700} />
                </Grid>
                <Grid sm={12} className="flex justify-center">
                  <Skeleton height={300} width={700} />
                </Grid>
              </Grid>
            ) : (
              reviews?.map((review) => (
                <div key={review.jobContract.id}>
                  <ReviewCard review={review} />
                </div>
              ))
            )}
            <BottomPagination
              maxPage={reviewsMaxPage}
              setQueryParams={setQueryParams}
              queryParams={queryParams}
            />
          </>
        )}
      </TabPanel>
    </>
  );
};

const ReviewsDistribution = ({ reviewsQuantity, userId }) => {
  const { getRates } = useUser();
  const [distribution, setDistribution] = useState([]);
  const [loadingData, setLoadingData] = useState(true);

  const loadData = async (userId) => {
    try {
      const rates = await getRates(userId);
      setDistribution([
        rates['one'],
        rates['two'],
        rates['three'],
        rates['four'],
        rates['five'],
      ]);
      setLoadingData(false);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    loadData(userId);
  }, []);

  return (
    <>
      {loadingData ? (
        <Skeleton width={280} height={200} />
      ) : (
        [1, 2, 3, 4, 5].map((rate) => {
          return (
            <ReviewCountComponent
              key={rate}
              stars={5 - rate + 1}
              count={distribution[5 - rate]}
              reviewsQuantity={reviewsQuantity}
            />
          );
        })
      )}
    </>
  );
};

const ReviewCountComponent = ({ stars, count, reviewsQuantity }) => {
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
          value={(100 * count) / reviewsQuantity}
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
