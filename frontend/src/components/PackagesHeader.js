import { faCubes } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { makeStyles } from '@material-ui/core';
import clsx from 'clsx';
import React from 'react';
import { useTranslation } from 'react-i18next';
import { themeUtils } from '../theme';

const useStyles = makeStyles((theme) => ({
  header: {
    fontSize: themeUtils.fontSizes.h1,
    fontWeight: 700,
  },
}));

const PackagesHeader = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className="flex items-center">
      <FontAwesomeIcon
        className={clsx(classes.header, 'mr-2')}
        icon={faCubes}
      />
      <h1 className={classes.header}>{t('managepackages.header')}</h1>
    </div>
  );
};

export default PackagesHeader;
