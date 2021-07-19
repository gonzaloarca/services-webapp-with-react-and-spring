import React from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles, Card, CardContent } from '@material-ui/core';
import RatingDisplay from '../components/RatingDisplay';

const useStyles = makeStyles((theme) => ({
  card: {
    borderRadius: '10px',
  },
}));

const AverageRatingCard = ({ reviewAvg, reviewsQuantity }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <Card classes={{ root: classes.card }}>
      <CardContent>
        <div className="font-semibold flex justify-center">
          {t('profile.rating')}
        </div>
        <div className="flex justify-evenly items-center">
          <div className="text-4xl font-semibold">{reviewAvg.toFixed(1)}</div>
          <RatingDisplay avgRate={reviewAvg} reviewsCount={reviewsQuantity} />
        </div>
      </CardContent>
    </Card>
  );
};

export default AverageRatingCard;
