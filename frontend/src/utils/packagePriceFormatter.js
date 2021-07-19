const packagePriceFormatter = (t, rateType, price) => {
  switch (rateType.description) {
    case 'HOURLY':
      return t('ratetypeformat.hourly', {
        amount: price,
      });
    case 'TBD':
      return t('ratetypeformat.tbd');
    case 'ONE_TIME':
      return t('ratetypeformat.onetime', {
        amount: price,
      });
    default:
      return '';
  }
};

export default packagePriceFormatter;
