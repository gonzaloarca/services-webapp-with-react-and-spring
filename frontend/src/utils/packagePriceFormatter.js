const packagePriceFormatter = (t, rateType, price) => {
  switch (rateType.description) {
    case 'HOURLY':
      return t('ratetype.hourly', { amount: t('price', { value: price }) });
    case 'TBD':
      return t('ratetype.tbd');
    case 'ONE_TIME':
      return t('price', { value: price });
    default:
      return '';
  }
};

export default packagePriceFormatter;
