import { makeStyles } from '@material-ui/core';
import { Rating } from '@material-ui/lab';
import clsx from 'clsx';
import React from 'react';
import { useTranslation } from 'react-i18next';
import { themeUtils } from '../theme';

const useStyles = makeStyles((theme) => ({
  reviewsCount: {
    color: themeUtils.colors.grey,
    fontWeight: 400,
  },
}));

const RatingDisplay = ({
  avgRate,
  reviewsCount,
  className = '',
  style = {},
}) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className={clsx('flex', className)} style={style}>
      <Rating precision={0.5} readOnly value={avgRate} />
      <p className={classes.reviewsCount}>
        {t('reviewcount', { count: reviewsCount })}
      </p>
    </div>
  );
};

export default RatingDisplay;
