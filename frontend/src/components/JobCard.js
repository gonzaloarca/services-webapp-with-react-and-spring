import {
  Card,
  CardActionArea,
  CardContent,
  CardMedia,
  Chip,
  makeStyles,
  Tooltip,
} from '@material-ui/core';
import React from 'react';
import { useTranslation } from 'react-i18next';
import { themeUtils } from '../theme';
import { Rating } from '@material-ui/lab';
import { Check, LocalOffer, LocationOn, Star } from '@material-ui/icons';
import { Link } from 'react-router-dom';
import packagePriceFormatter from '../utils/packagePriceFormatter';
import RatingDisplay from './RatingDisplay';

const useStyles = makeStyles({
  image: {
    height: 140,
  },
  title: {
    fontWeight: 600,
    fontSize: '1.2rem',
    lineHeight: '1.5rem',
    height: '4.5rem',
    WebkitLineClamp: 3,
    display: '-webkit-box',
    WebkitBoxOrient: 'vertical',
    overflowWrap: 'break-word',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
  },
  jobType: {
    marginTop: 5,
    fontWeight: 500,
    color: themeUtils.colors.grey,
  },
  priceContainer: {
    color: themeUtils.colors.blue,
    fontSize: '1.1rem',
    fontWeight: 600,
    width: '100%',
    height: '4rem',
    overflowWrap: 'break-word',
    textOverflow: 'ellipsis',
  },
  ratingContainer: {
    display: 'flex',
    alignItems: 'center',
    height: 50,
  },
  reviewsCount: {
    color: themeUtils.colors.grey,
    marginLeft: 5,
  },
  noReviewsChip: {
    backgroundColor: themeUtils.colors.grey,
    color: 'white',
  },
  noReviewsStar: {
    color: 'white',
  },
  zoneChip: {
    fontWeight: 500,
    width: '100%',
    fontSize: '0.85rem',
  },
  completedContracts: {
    fontWeight: 500,
    fontSize: '0.85rem',
  },
});

const JobCard = ({ job }) => {
  const classes = useStyles();
  const { t } = useTranslation();
  return (
    <div>
      <Card>
        <CardActionArea component={Link} to={`/job/${job.id}`}>
          {/* Card image */}
          <CardMedia
            className={classes.image}
            image={job.imageUrl}
            title={job.title}
          />
          <CardContent>
            <p className={classes.title}>{job.title}</p>
            <p className={classes.jobType}>{job.jobType.description}</p>
            {/* Rating */}
            <CardRating avgRate={job.avgRate} reviewsCount={job.reviewsCount} />
            <div className={classes.priceContainer}>
              <LocalOffer className="mr-2" />
              {packagePriceFormatter(t, job.rateType, job.price)}
            </div>
            <div className="mt-4">
              <ZonesChip zones={job.zones} />
            </div>
            <hr className="my-4" />
            <div className="font-semibold text-sm flex items-center">
              <Check className="mr-2" />
              {job.contractsCompleted !== 1
                ? t('completedcontracts', { count: job.contractsCompleted })
                : t('completedcontractsonce')}
            </div>
          </CardContent>
        </CardActionArea>
      </Card>
    </div>
  );
};

const CardRating = ({ avgRate, reviewsCount }) => {
  const { t } = useTranslation();
  const classes = useStyles();

  return (
    <div className={classes.ratingContainer}>
      {reviewsCount > 0 ? (
        <RatingDisplay avgRate={avgRate} reviewsCount={reviewsCount} />
      ) : (
        <Chip
          className={classes.noReviewsChip}
          icon={<Star className={classes.noReviewsStar} />}
          label={t('jobcard.noreviews')}
        />
      )}
    </div>
  );
};

const ZonesChip = ({ zones }) => {
  const { t } = useTranslation();
  const classes = useStyles();
  const firstZone = zones[0].description;
  const zoneCount = zones.length - 1;

  return (
    <Tooltip
      title={
        <React.Fragment>
          <div className="text-xs">
            {zones.map((i) => i.description).join(', ')}
          </div>
        </React.Fragment>
      }
    >
      <Chip
        className={classes.zoneChip}
        icon={<LocationOn />}
        label={
          zoneCount === 0
            ? t('jobcard.onezone', {firstZone: firstZone})
            : t('jobcard.morezones', {
                firstZone: firstZone,
                zoneCount: zoneCount,
              })
        }
      />
    </Tooltip>
  );
};

export default JobCard;
