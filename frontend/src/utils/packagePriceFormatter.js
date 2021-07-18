const packagePriceFormatter = (t, rateType, price) => {
  switch (rateType.description) {
    case 'HOURLY':
      return t('ratetypeformat.hourly', {
        amount: t('price', { value: price }),
      });
    case 'TBD':
      return t('ratetypeformat.tbd');
    case 'ONE_TIME':
      return t('price', { value: price });
    default:
      return '';
  }
};

export default packagePriceFormatter;
