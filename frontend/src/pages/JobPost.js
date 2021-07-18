import {
  Avatar,
  Card,
  CardActionArea,
  Chip,
  Grid,
  Link,
} from '@material-ui/core';
import {
  ChevronRight,
  LocationOn,
  RateReview,
  Schedule,
  Work,
} from '@material-ui/icons';
import { Rating } from '@material-ui/lab';
import { makeStyles } from '@material-ui/styles';
import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import Carousel from 'react-material-ui-carousel';
import { Link as RouterLink, useParams } from 'react-router-dom';
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
import axios from 'axios';
import { Helmet } from 'react-helmet';
import { extractLastIdFromURL } from '../utils/urlUtils';
import BottomPagination from '../components/BottomPagination';

// const post = {
//   active: true,
//   availableHours: 'Lunes a viernes entre las 8am y 2pm',
//   creationDate: '2021-05-02T18:22:13.338478',
//   id: 8,
//   jobType: {
//     description: 'BABYSITTING',
//     id: 7,
//   },
//   images: [
//     `${process.env.PUBLIC_URL}/img/plumbing.jpeg`,
//     `${process.env.PUBLIC_URL}/img/carpentry.jpeg`,
//   ],

//   packages: [
//     {
//       id: 8,
//       uri: 'http://localhost:8080/job-posts/8/packages/8',
//     },
//   ],
//   professional: {
//     id: 5,
//     uri: 'http://localhost:8080/users/5',
//   },
//   title: 'Niñero turno mañana',
//   zones: [
//     {
//       description: 'RETIRO',
//       id: 28,
//     },
//     {
//       description: 'NUNIEZ',
//       id: 20,
//     },
//     {
//       description: 'COLEGIALES',
//       id: 9,
//     },
//   ],
// };

// const jobCard = {
//   avgRate: 4.666666666666667,
//   contractsCompleted: 4,
//   imageUrl: 'http://localhost:8080/job-posts/1/images/1',
//   jobPost: {
//     id: 1,
//     uri: 'http://localhost:8080/job-posts/1',
//   },
//   jobType: {
//     description: 'PLUMBING',
//     id: 0,
//   },
//   price: 160.0,
//   rateType: {
//     description: 'HOURLY',
//     id: 0,
//   },
//   reviewsCount: 3,
//   title: 'Plomería de excelente calidad',
//   zones: [
//     {
//       description: 'RETIRO',
//       id: 28,
//     },
//     {
//       description: 'PALERMO',
//       id: 21,
//     },
//     {
//       description: 'NUNIEZ',
//       id: 20,
//     },
//     {
//       description: 'COLEGIALES',
//       id: 9,
//     },
//     {
//       description: 'BELGRANO',
//       id: 4,
//     },
//   ],
// };

// const packages = [
//   {
//     active: true,
//     description:
//       'Atencion constante y juego para el desarrollo de musculos como brazos y piernas.',
//     id: 8,
//     jobPost: {
//       id: 8,
//       uri: 'http://localhost:8080/job-posts/8',
//     },
//     rateType: {
//       description: 'TBD',
//       id: 2,
//     },
//     title: '4 dias a la semana 4 horas',
//   },
//   {
//     active: true,
//     description:
//       'Atencion constante y juego para el desarrollo de musculos como brazos y piernas.',
//     id: 8,
//     jobPost: {
//       id: 8,
//       uri: 'http://localhost:8080/job-posts/8',
//     },
//     rateType: {
//       description: 'TBD',
//       id: 2,
//     },
//     title: '4 dias a la semana 4 horas',
//   },
// ];

// const proUser = {
//   email: 'manaaasd@gmail.com',
//   id: 5,
//   phone: '03034560',
//   roles: ['CLIENT', 'PROFESSIONAL'],
//   username: 'Manuel Rodriguez',
//   image: `${process.env.PUBLIC_URL}/img/plumbing.jpeg`,
// };

// const reviews = [
//   {
//     creationDate: '2021-05-02T18:22:21.684413',
//     description: 'EL MEJOR NIÑERO',
//     client: {
//       username: 'El Beto',
//       image: `${process.env.PUBLIC_URL}/img/babysitting.jpeg`,
//     },
//     jobContract: {
//       id: 1,
//       uri: 'http://localhost:8080/job-posts/8/packages/8/contracts/1',
//     },
//     rate: 5,
//     title: 'No debes moverte de donde estas ⛹⛹⛹⛹⛹⛹',
//   },
//   {
//     creationDate: '2021-10-02T18:22:21.684413',
//     description: 'EL MEJOR NIÑERO',
//     client: {
//       username: 'El Beto',
//       image: `${process.env.PUBLIC_URL}/img/babysitting.jpeg`,
//     },
//     jobPost: {
//       title: 'Niñero turno mañana',
//       id: 3,
//     },
//     jobContract: {
//       id: 2,
//       uri: 'http://localhost:8080/job-posts/3/packages/8/contracts/2',
//     },
//     rate: 5,
//     title: 'No debes moverte de donde estas ⛹⛹⛹⛹⛹⛹',
//   },
// ];

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
}));

const useGlobalStyles = makeStyles(styles);

const JobPost = ({ match }) => {
  const [postId, setPostId] = useState(match.params.id);
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();
  const { getJobPostById } = useJobPosts();
  const { getJobCardById } = useJobCards();
  const { getJobPackagesByPostId } = useJobPackages();
  const { getUserById } = useUser();
  const [loading, setLoading] = useState(true);
  const [post, setJobPost] = useState(null);
  const [jobCard, setJobCard] = useState(null);
  const [packages, setPackages] = useState([]);
  const [proUser, setProUser] = useState(null);

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
    if (post && jobCard && packages && packages.length > 0 && proUser)
      setLoading(false);
  }, [post, jobCard, packages, proUser]);

  useEffect(() => {
    loadJobPost();
    loadJobCard();
    loadPackages();
  }, [postId]);

  return (
    <>
      <NavBar currentSection={'/search'} />
      {loading ? (
        <div>loading</div>
      ) : (
        <>
          <Helmet>
            <title>{t('title', { section: post.title })}</title>
          </Helmet>
          <div className={globalClasses.contentContainerTransparent}>
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
              <PackageListCard packages={packages} />
            </div>
            {/* Opiniones */}
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
              <Link href="#reviews">{t('jobpost.seereviews')}</Link>
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
      {reviews.map((review) => (
        <div
          key={`review_${extractLastIdFromURL(review.jobContract)}`}
          className="mb-4"
        >
          <ReviewCard review={review} />
        </div>
      ))}

      <BottomPagination
        maxPage={links.last.page}
        setQueryParams={setQueryParams}
        queryParams={queryParams}
      />
    </SectionCard>
  );
};
export default JobPost;
