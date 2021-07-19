import {
  Avatar,
  Button,
  Card,
  CardActionArea,
  Chip,
  Grid,
} from '@material-ui/core';
import {
  ChevronRight,
  Delete,
  Edit,
  ListAlt,
  LocationOn,
  RateReview,
  Schedule,
  Work,
} from '@material-ui/icons';
import { makeStyles } from '@material-ui/styles';
import clsx from 'clsx';
import React, { useEffect, useState, useContext } from 'react';
import { useTranslation } from 'react-i18next';
import Carousel from 'react-material-ui-carousel';
import { Link as RouterLink } from 'react-router-dom';
import NavBar from '../components/NavBar';
import styles from '../styles';
import { themeUtils } from '../theme';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCube } from '@fortawesome/free-solid-svg-icons';
import PackageAccordion from '../components/PackageAccordion';
import ReviewCard from '../components/ReviewCard';
import RatingDisplay from '../components/RatingDisplay';
import {
  useJobPosts,
  useJobCards,
  useUser,
  useJobPackages,
  useReviews,
} from '../hooks';
import { Helmet } from 'react-helmet';
import { extractLastIdFromURL } from '../utils/urlUtils';
import BottomPagination from '../components/BottomPagination';
import { Skeleton } from '@material-ui/lab';
import { Link } from 'react-router-dom';
import { UserContext } from '../context';
import HirenetModal, { PlainTextBody } from '../components/HirenetModal';

const useStyles = makeStyles((theme) => ({
  carouselImage: {
    width: '100%',
    height: 400,
    objectFit: 'cover',
    boxShadow: themeUtils.shadows.containerShadow,
  },
  cardContainer: {
    boxShadow: themeUtils.shadows.containerShadow,
  },
  cardLabel: {
    display: 'flex',
    padding: '5px 10px',
    fontWeight: 600,
    fontSize: '1.2rem',
  },
  cardLabelIcon: {
    display: 'flex',
    alignItems: 'center',
    marginRight: '0.5rem',
  },
  cardContentContainer: {
    backgroundColor: 'white',
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
  usernameContainer: {
    WebkitLineClamp: 3,
    display: '-webkit-box',
    WebkitBoxOrient: 'vertical',
    overflow: 'hidden',
    overflowWrap: 'break-word',
    textOverflow: 'ellipsis',
    width: '60%',
    paddingLeft: '0.5rem',
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
  contractsCompleted: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    fontSize: '2rem',
    fontWeight: 500,
    color: 'white',
    backgroundColor: themeUtils.colors.blue,
    width: '3rem',
    height: '3rem',
    borderRadius: '3rem',
    border: '2px solid ' + themeUtils.colors.yellow,
  },
  workingHoursContainer: {
    display: 'flex',
    alignItems: 'start',
    justifyContent: 'center',
    backgroundColor: themeUtils.colors.lightGrey,
    borderRadius: 20,
    fontWeight: 500,
    fontSize: '0.9rem',
    padding: 20,
    height: 120,
    overflow: 'auto',
  },
  locationsContainer: {
    display: 'flex',
    alignItems: 'start',
    alignContent: 'start',
    justifyContent: 'start',
    flexWrap: 'wrap',
    backgroundColor: themeUtils.colors.lightGrey,
    borderRadius: 20,
    padding: 20,
    height: 120,
    overflow: 'auto',
  },
  locationChip: {
    backgroundColor: themeUtils.colors.grey,
    color: 'white',
    fontWeight: 500,
    marginRight: 5,
    marginBottom: 5,
  },
  noReviewsContainer: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
    maxHeight: 300,
  },
  noReviewsImage: {
    height: '20%',
    width: '20%',
    objectFit: 'contain',
  },
  noReviewsMessage: {
    fontSize: themeUtils.fontSizes.lg,
    fontWeight: 500,
    textAlign: 'center',
  },
}));

const useGlobalStyles = makeStyles(styles);

const JobPost = ({ match, history }) => {
  const [postId, setPostId] = useState(match.params.id);
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();
  const { getJobPostById, deleteJobPost } = useJobPosts();
  const { getJobCardById } = useJobCards();
  const { getJobPackagesByPostId } = useJobPackages();
  const { getUserById } = useUser();
  const { currentUser } = useContext(UserContext);
  const [loading, setLoading] = useState(true);
  const [post, setJobPost] = useState(null);
  const [jobCard, setJobCard] = useState(null);
  const [packages, setPackages] = useState([]);
  const [proUser, setProUser] = useState(null);
  const [isOwner, setIsOwner] = useState(false);
  const [openDelete, setOpenDelete] = useState(false);

  const loadJobPost = async () => {
    try {
      const post = await getJobPostById(postId);
      setJobPost(post);
    } catch (e) {
      console.log(e);
    }
  };

  const loadJobCard = async () => {
    try {
      const jobCard = await getJobCardById(postId);
      setJobCard(jobCard);
    } catch (e) {
      console.log(e);
    }
  };

  const loadPackages = async () => {
    try {
      const jobPackages = await getJobPackagesByPostId(postId);
      setPackages(jobPackages);
    } catch (e) {
      console.log(e);
    }
  };

  const loadProUser = async () => {
    try {
      const proId = extractLastIdFromURL(post.professional);
      const pro = await getUserById(proId);
      setProUser(pro);
    } catch (e) {
      console.log(e);
    }
  };

  useEffect(() => {
    if (post) {
      loadProUser();
    }
  }, [post]);

  useEffect(() => {
    if (post && jobCard && packages && packages.length > 0 && proUser) {
      if (currentUser && currentUser.id === proUser.id && post.active)
        setIsOwner(true);
      setLoading(false);
    }
  }, [post, jobCard, packages, proUser]);

  useEffect(() => {
    loadJobPost();
    loadJobCard();
    loadPackages();
  }, [postId]);

  const deletePost = async () => {
    try {
      await deleteJobPost(post);
      history.push(`/`);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <>
      <NavBar currentSection={'/search'} />
      {loading ? (
        <Grid container spacing={3} className="mt-10">
          <Grid item md={12} className="flex justify-center">
            <Skeleton md={12} variant="rect" width={1000} height={500} />
          </Grid>
          <Grid item md={12} className="flex justify-center">
            <Skeleton md={12} variant="rect" width={1000} height={200} />
          </Grid>
          <Grid item md={12} className="flex justify-center">
            <Skeleton md={12} variant="rect" width={1000} height={200} />
          </Grid>
        </Grid>
      ) : (
        <>
          <Helmet>
            <title>{t('title', { section: post.title })}</title>
          </Helmet>
          <div className={globalClasses.contentContainerTransparent}>
            {isOwner ? (
              <div className="flex justify-end">
                <Button
                  className="text-lg"
                  style={{ color: themeUtils.colors.blue }}
                  component={Link}
                  to={`/job/${match.params.id}/edit`}
                >
                  <Edit fontSize="large" className="mr-1" />
                  {t('jobpost.edit')}
                </Button>
                <div className="my-2 mx-3 border-l-2 border-solid border-gray-400" />
                <Button
                  fontSize="large"
                  className="text-lg"
                  style={{ color: themeUtils.colors.red }}
                  onClick={() => setOpenDelete(true)}
                >
                  <Delete fontSize="large" className="mr-1" />
                  {t('jobpost.delete')}
                </Button>
                <HirenetModal
                  open={openDelete}
                  title={t('jobpost.deletetitle')}
                  body={
                    <PlainTextBody>
                      {t('managepackages.deletemodal.body')}
                    </PlainTextBody>
                  }
                  onNegative={() => setOpenDelete(false)}
                  onAffirmative={deletePost}
                  affirmativeLabel={t('managepackages.deletemodal.affirmative')}
                  negativeLabel={t('managepackages.deletemodal.negative')}
                  affirmativeColor={themeUtils.colors.red}
                />
              </div>
            ) : (
              <></>
            )}
            <Carousel navButtonsAlwaysVisible>
              {post.images.map((item, i) => (
                <img
                  key={`image_${i}`}
                  src={item}
                  className={classes.carouselImage}
                  alt=""
                  loading="eager"
                />
              ))}
            </Carousel>
            {/* Datos profesional / estadisticas del servicio */}
            <div className="mt-4">
              <StatsCard
                title={jobCard.title}
                user={proUser}
                avgRate={jobCard.avgRate}
                reviewsCount={jobCard.reviewsCount}
                contractsCompleted={jobCard.contractsCompleted}
              />
            </div>
            {/* Horarios y zonas */}
            <Grid className="mt-4" container spacing={3}>
              <Grid item sm={6} xs={12}>
                {/* Horarios */}
                <SectionCard
                  icon={<Schedule />}
                  title={t('workinghours')}
                  labelBackgroundColor={themeUtils.colors.aqua}
                >
                  <div className={classes.workingHoursContainer}>
                    {post.availableHours}
                  </div>
                </SectionCard>
              </Grid>
              {/* Zonas */}
              <Grid item sm={6} xs={12}>
                <SectionCard
                  icon={<LocationOn />}
                  title={t('availablezones')}
                  labelBackgroundColor={themeUtils.colors.orange}
                >
                  <div className={classes.locationsContainer}>
                    {post.zones.map((zone) => (
                      <Chip
                        className={classes.locationChip}
                        key={`zone_${zone.id}`}
                        label={zone.description}
                      />
                    ))}
                  </div>
                </SectionCard>
              </Grid>
            </Grid>
            {/* Paquetes */}
            <div className="mt-7">
              {isOwner ? (
                <div className="flex justify-end">
                  <Button
                    className="text-lg"
                    style={{ color: themeUtils.colors.blue }}
                    component={Link}
                    to={`/job/${match.params.id}/packages`}
                  >
                    <ListAlt fontSize="large" className="mr-1" />
                    {t('jobpost.managepacks')}
                  </Button>
                </div>
              ) : (
                <></>
              )}
              <PackageListCard packages={packages} />
            </div>
            {/* Reseñas */}
            <div id="reviews" className="mt-7">
              <ReviewListCard postId={post.id} />
            </div>
          </div>
        </>
      )}
    </>
  );
};

const SectionCard = ({
  icon,
  title,
  labelBackgroundColor = themeUtils.colors.blue,
  labelTextColor = 'white',
  children,
}) => {
  const classes = useStyles();

  return (
    <>
      <div className={classes.cardContainer}>
        <div
          className={classes.cardLabel}
          style={{
            backgroundColor: labelBackgroundColor,
            color: labelTextColor,
          }}
        >
          {
            <>
              <div className={classes.cardLabelIcon}>{icon}</div>
              {title}
            </>
          }
        </div>
        <div className={classes.cardContentContainer}>{children}</div>
      </div>
    </>
  );
};

const DataDisplayCard = ({ title, color, routePath = '', children }) => {
  const classes = useStyles();

  return (
    <Card className={classes.roundedCorners}>
      <div className="p-1" style={{ backgroundColor: color }}>
        {routePath === '' ? (
          children
        ) : (
          <CardActionArea component={RouterLink} to={routePath}>
            {children}
          </CardActionArea>
        )}
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

const StatsCard = ({
  title,
  user,
  avgRate,
  reviewsCount,
  contractsCompleted,
}) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <SectionCard
      icon={<Work />}
      title={title}
      labelBackgroundColor={themeUtils.colors.blue}
    >
      <Grid justifyContent="center" container spacing={4}>
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
              <p className={classes.usernameContainer}>{user.username}</p>
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
                classes.roundedCorners,
                'flex-col'
              )}
            >
              <div className="flex flex-row items-center">
                <p className={classes.ratingValue}>{avgRate.toFixed(2)}</p>
                <RatingDisplay avgRate={avgRate} reviewsCount={reviewsCount} />
              </div>
              <a href="#reviews">{t('jobpost.seereviews')}</a>
            </div>
          </DataDisplayCard>
        </Grid>
        <Grid item md={4} sm={6} xs={12}>
          <DataDisplayCard
            title={t('timeshired')}
            color={themeUtils.colors.lightBlue}
          >
            <div
              className={clsx(
                classes.dataDisplayContainer,
                classes.roundedCorners
              )}
            >
              <div className="flex justify-center items-center">
                <div className={classes.contractsCompleted}>
                  {contractsCompleted}
                </div>
              </div>
            </div>
          </DataDisplayCard>
        </Grid>
      </Grid>
    </SectionCard>
  );
};

const PackageListCard = ({ packages }) => {
  const { t } = useTranslation();

  return (
    <SectionCard
      labelBackgroundColor={themeUtils.colors.lightBlue}
      icon={<FontAwesomeIcon icon={faCube} />}
      title={t('availablepackages')}
    >
      {packages.map((pack) => (
        <PackageAccordion key={`package_${pack.id}`} pack={pack} isHireable />
      ))}
    </SectionCard>
  );
};

const ReviewListCard = ({ postId }) => {
  const { t } = useTranslation();
  const { getReviewsByPostId, links } = useReviews();
  const [reviews, setReviews] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [queryParams, setQueryParams] = useState({ page: '1' });
  const classes = useStyles();

  const loadReviews = async () => {
    try {
      const reviewsData = await getReviewsByPostId(postId, queryParams.page);
      setReviews(reviewsData);
    } catch (e) {
      console.log(e);
    }
  };

  useEffect(() => {
    loadReviews();
  }, [queryParams]);

  const renderReviews = (reviews) => {
    if (reviews.length === 0) {
      return (
        <div className={classes.noReviewsContainer}>
          <img
            className={classes.noReviewsImage}
            src={process.env.PUBLIC_URL + '/img/star-1.svg'}
            alt=""
          />
          <p className={classes.noReviewsMessage}>{t('jobpost.noreviews')}</p>
        </div>
      );
    } else {
      return reviews.map((review) => (
        <div
          key={`review_${extractLastIdFromURL(review.jobContract)}`}
          className="mb-4"
        >
          <ReviewCard review={review} />
        </div>
      ));
    }
  };

  useEffect(() => {
    if (reviews) setIsLoading(false);
  }, [reviews]);
  return isLoading ? (
    <div>loading</div>
  ) : (
    <SectionCard
      labelBackgroundColor={themeUtils.colors.yellow}
      icon={<RateReview />}
      title={t('reviews')}
    >
      {renderReviews(reviews)}

      <BottomPagination
        maxPage={links.last.page}
        setQueryParams={setQueryParams}
        queryParams={queryParams}
      />
    </SectionCard>
  );
};
export default JobPost;
