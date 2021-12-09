import { Button, makeStyles } from '@material-ui/core';
import { Home } from '@material-ui/icons';
import React from 'react';
import { Helmet } from 'react-helmet';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { themeUtils } from '../theme';

const useStyles = makeStyles((theme) => ({
  centerContainer: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: '64vh',
  },
  container: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'white',
    boxShadow: themeUtils.shadows.containerShadow,
    padding: 30,
    maxWidth: 500,
    maxHeight: 400,
    width: '80vw',
    height: '60vh',
  },
  image: {
    width: '50%',
    height: '50%',
    objectFit: 'contain',
  },
  header: {
    fontSize: themeUtils.fontSizes.h1,
    fontWeight: 700,
    textAlign: 'center',
  },
  description: {
    fontSize: themeUtils.fontSizes.lg,
    fontWeight: 500,
    textAlign: 'center',
    marginBottom: 20,
  },
  button: {
    color: themeUtils.colors.electricBlue,
    textTransform: 'uppercase',
  },
}));

const Error = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div>
      <Helmet>
        <title>{t('title', { section: t('navigation.sections.error') })}</title>
      </Helmet>
      <div className={classes.centerContainer}>
        <div className={classes.container}>
          <img
            className={classes.image}
            src={process.env.PUBLIC_URL + '/img/broken-link.png'}
            alt=""
            loading="lazy"
          />
          <h1 className={classes.header}>{t('error.generic.header')}</h1>
          <p className={classes.description}>
            {t('error.generic.description')}
          </p>
          <Button
            startIcon={<Home />}
            className={classes.button}
            component={Link}
            to="/"
          >
            {t('error.generic.action')}
          </Button>
        </div>
      </div>
    </div>
  );
};

export default Error;
