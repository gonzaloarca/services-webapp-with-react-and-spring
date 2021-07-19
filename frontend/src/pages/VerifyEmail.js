import React, { useEffect } from 'react';
import NavBar from '../components/NavBar';
import { makeStyles } from '@material-ui/core/styles';
import { useTranslation } from 'react-i18next';
import clsx from 'clsx';
import { Button, Card } from '@material-ui/core';
import LoginAndRegisterStyles from '../components/LoginAndRegisterStyles';
import { useLocation, Link } from 'react-router-dom';
import { Helmet } from 'react-helmet';
import { useUser } from '../hooks';
import { Skeleton } from '@material-ui/lab';

const useStyles = makeStyles(LoginAndRegisterStyles);

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

const VerifyEmail = ({ history }) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const { verifyEmail } = useUser();
  let query = useQuery();
  const [statusCode, setStatusCode] = React.useState(-1);

  const tryVerification = async () => {
    try {
      await verifyEmail({
        id: query.get('user-id'),
        token: query.get('token'),
      });
      setStatusCode(200);
    } catch (e) {
      setStatusCode(e.statusCode);
      history.push(`/error`);
    }
  };

  useEffect(() => {
    tryVerification();
  }, []);

  return (
    <div>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.verify') })}
        </title>
      </Helmet>
      <NavBar isTransparent />
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
              alt={t('verifyemail.title')}
              loading="lazy"
            />
            <p className={classes.title}>{t('verifyemail.title')}</p>
          </div>
          <Card className={clsx(classes.customCard, 'max-w-lg ')}>
            {statusCode === -1 ? (
              <Skeleton variant="rect" className="w-96 h-40" />
            ) : statusCode === 200 ? (
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
