import React from 'react';
import { makeStyles } from '@material-ui/core';
import { useTranslation } from 'react-i18next';
import { LocalOffer } from '@material-ui/icons';
import packagePriceFormatter from '../utils/packagePriceFormatter';
import clsx from 'clsx';

const useStyles = makeStyles((theme) => ({
  priceTag: {
    backgroundColor: '#485696',
    color: 'white',
    margin: '5px 0 5px 0',
    fontSize: '1rem',
    fontWeight: 500,
    padding: '5px 10px 5px 10px',
    borderRadius: '15px',
    width: 'max-content',
  },
}));

const PriceTag = ({ rateType, price, icon = false }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className={clsx(classes.priceTag, 'flex items-center')}>
      {icon ? <LocalOffer className="mr-2" /> : <></>}
      {packagePriceFormatter(t, rateType, price)}
    </div>
  );
};

export default PriceTag;
