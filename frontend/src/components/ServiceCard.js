import React from 'react';
import { useTranslation } from 'react-i18next';
import {
  Card,
  CardActionArea,
  Divider,
  Grid,
  makeStyles,
} from '@material-ui/core';
import contractCardStyles from './ContractCardStyles';
import RatingDisplay from './RatingDisplay';
import { Link } from 'react-router-dom';
import PriceTag from './PriceTag';
import { Check } from '@material-ui/icons';

const useStyles = makeStyles(contractCardStyles);

const ServiceCard = ({ jobCard }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <Card className={classes.contractCard}>
      <Link to={`/job/${jobCard.jobPost.id}`} className={classes.contractTitle}>
        <CardActionArea>
          <Grid className="p-4 pr-4" container spacing={3}>
            <Grid
              className={classes.jobImageContainer}
              item
              xs={12}
              md={4}
              lg={3}
            >
              <img
                className={classes.jobImage}
                src={jobCard.imageUrl}
                alt="Service"
                loading="lazy"
              />
            </Grid>
            <Grid
              className="flex flex-col justify-center"
              item
              xs={12}
              md={8}
              lg={9}
            >
              {/* Titulo */}
              <div className="font-bold">{jobCard.title}</div>
              {/* Categoria y calificacion */}
              <Grid className="mb-1" container spacing={3}>
                <Grid item xs={12} sm={6}>
                  <p className={classes.jobType}>
                    {jobCard.jobType.description}
                  </p>
                </Grid>
                <Grid item xs={12} sm={6}>
                  <RatingDisplay
                    className="justify-end"
                    avgRate={jobCard.avgRate}
                    reviewsCount={jobCard.reviewsCount}
                  />
                </Grid>
              </Grid>
              <div className="flex items-center justify-between text-base">
                <div className="flex items-center">
                  <PriceTag
                    rateType={jobCard.rateType}
                    price={jobCard.price}
                    icon
                  />
                </div>
                <div className={classes.timesHired}>
                  <Check className="mr-2" />
                  {jobCard.contractsCompleted === 1
                    ? t('timesHiredSingular')
                    : t('timesHiredPlural', {
                        times: jobCard.contractsCompleted,
                      })}
                </div>
              </div>
            </Grid>
          </Grid>
          <Divider />
        </CardActionArea>
      </Link>
    </Card>
  );
};

export default ServiceCard;
