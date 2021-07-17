import React from 'react';
import { makeStyles } from '@material-ui/core';
import { useTranslation } from 'react-i18next';
import { LocalOffer } from '@material-ui/icons';
import clsx from 'clsx';

const useStyles = makeStyles((theme) => ({
  priceTag: {
    backgroundColor: '#485696',
    color: 'white',
    margin: '5px 0 5px 0',
    fontSize: 'larger',
    fontWeight: 'bold',
    padding: '5px 10px 5px 10px',
    borderRadius: '15px',
    width: 'max-content',
  },
}));

const PriceTag = ({ rateType, price, icon = false }) => {
  const classes = useStyles();
  const { t } = useTranslation();
  let priceStr;

  //TODO: verificar si esta logica todavia se cumple
  switch (rateType.description) {
    case 'HOURLY':
      priceStr = t('ratetype.hourly', { amount: price });
      break;
    case 'TBD':
      priceStr = t('ratetype.tbd');
      break;
    case 'ONE_TIME':
      priceStr = t('ratetype.oneTime', { amount: price });
      break;
    default:
      break;
  }

  return (
    <div className={clsx(classes.priceTag, 'flex items-center')}>
      {icon ? <LocalOffer className="mr-2" /> : <></>}
      {priceStr}
    </div>
  );
};

export default PriceTag;
