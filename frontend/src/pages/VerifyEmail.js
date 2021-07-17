import React from 'react';
import NavBar from '../components/NavBar';
import { makeStyles } from '@material-ui/core/styles';
import { useTranslation } from 'react-i18next';
import clsx from 'clsx';
import { Button, Card } from '@material-ui/core';
import LoginAndRegisterStyles from '../components/LoginAndRegisterStyles';
import { useLocation, Link } from 'react-router-dom';

const useStyles = makeStyles(LoginAndRegisterStyles);

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

function CheckQueryParams(query) {
  //TODO: checkear si el user_id existe y si el token es valido
  if (query.get('user_id') && query.get('token')) return true;

  return false;
}

const VerifyEmail = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  let query = useQuery();

  return (
    <div>
      <NavBar isTransparent />
      <div
        className={classes.background}
        style={{ backgroundImage: `url(${process.env.PUBLIC_URL}/img/background.jpg)` }}
      >
        <div className={classes.cardContainer}>
          <div className={classes.titleContainer}>
            <img
              src={`${process.env.PUBLIC_URL}/img/log-in.svg`}
              alt={t('verifyemail.title')}
              loading="lazy"
            />
            <p className={classes.title}>{t('verifyemail.title')}</p>
          </div>
          <Card className={clsx(classes.customCard, 'max-w-lg')}>
            {CheckQueryParams(query) ? (
              //Si el URL es valido
              <>
                <div
                  className="font-bold text-3xl mb-2"
                  style={{ color: 'green' }}
                >
                  {t('verifyemail.success')}
                </div>
                <div>{t('verifyemail.pleaselogin')}</div>
                <div className="flex justify-center mt-3">
                  <Button
                    className={clsx(classes.submitButton, 'w-32')}
                    component={Link}
                    to="/login"
                  >
                    {t('verifyemail.login')}
                  </Button>
                </div>
              </>
            ) : (
              //Si no es valido el URL, muestro un error
              <>
                <div className="font-bold text-3xl mb-2">
                  {t('verifyemail.invalid')}
                </div>
                <div>{t('verifyemail.instructions')}</div>
                <div className="flex justify-center mt-3">
                  <Button
                    className={clsx(classes.submitButton, 'w-32')}
                    component={Link}
                    to="/register"
                  >
                    {t('verifyemail.register')}
                  </Button>
                </div>
              </>
            )}
          </Card>
        </div>
      </div>
    </div>
  );
};

export default VerifyEmail;
