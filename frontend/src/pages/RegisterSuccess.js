import React, { useContext, useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { useTranslation } from 'react-i18next';
import clsx from 'clsx';
import { Button, Card } from '@material-ui/core';
import LoginAndRegisterStyles from '../components/LoginAndRegisterStyles';
import { Link } from 'react-router-dom';
import { Helmet } from 'react-helmet';
import { NavBarContext } from '../context';

const useStyles = makeStyles(LoginAndRegisterStyles);

const RegisterSuccess = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  const { setNavBarProps } = useContext(NavBarContext);

  useEffect(() => {
    setNavBarProps({currentSection:'/',isTransparent:true});
  },[])

  return (
    <div>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.registersuccess') })}
        </title>
      </Helmet>
      <div
        className={classes.background}
        style={{
          backgroundImage: `url(${process.env.PUBLIC_URL}/img/background.jpg)`,
        }}
      >
        <div className={classes.cardContainer}>
          <div className={classes.titleContainer}>
            <img
              src={`${process.env.PUBLIC_URL}/img/log-in.svg`}
              alt={t('register.success.title')}
              loading="lazy"
            />
            <p className={classes.title}>{t('verifyemail.title')}</p>
          </div>
          <Card className={clsx(classes.customCard, 'max-w-lg text-center')}>
            <div
              className="font-semibold text-3xl mb-2"
              style={{ color: 'green' }}
            >
              {t('register.success.title')}
            </div>
            <div>{t('register.success.verify')}</div>
            <div className="flex justify-center mt-4">
              <Button
                className={clsx(classes.submitButton)}
                component={Link}
                to="/"
              >
                {t('register.success.return')}
              </Button>
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default RegisterSuccess;
