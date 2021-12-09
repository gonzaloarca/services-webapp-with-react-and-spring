import React, { useContext, useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { useTranslation, Trans } from 'react-i18next';
import clsx from 'clsx';
import { Button, Card } from '@material-ui/core';
import LoginAndRegisterStyles from '../components/LoginAndRegisterStyles';
import { useLocation, Link } from 'react-router-dom';
import { Helmet } from 'react-helmet';
import { useUser } from '../hooks';
import { Skeleton } from '@material-ui/lab';
import { NavBarContext, UserContext } from '../context';
import jwt from 'jwt-decode';

const useStyles = makeStyles(LoginAndRegisterStyles);

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

const VerifyEmail = ({ history }) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const { verifyEmail, getUserByEmail } = useUser();
  let query = useQuery();
  const [statusCode, setStatusCode] = React.useState(-1);
  const [redirectTimer, setRedirectTimer] = React.useState(null);

  const { token, setCurrentUser, setToken } = useContext(UserContext);

  const { setNavBarProps } = useContext(NavBarContext);

  useEffect(() => {
    setNavBarProps({ currentSection: '/search', isTransparent: true });
  }, []);

  const setUserData = async (email) => {
    try {
      const user = await getUserByEmail(email);
      setCurrentUser(user);
    } catch (error) {
      history.replace('/error');
    }
  };

  useEffect(() => {
    try {
      if (token) {
        sessionStorage.setItem('token', token);
        setToken(token);
        setUserData(jwt(token).sub);
        setRedirectTimer(3);
      }
    } catch (error) {
      history.replace('/error');
    }
  }, [token]);

  const tryVerification = async () => {
    try {
      setToken(
        await verifyEmail({
          id: query.get('user-id'),
          token: query.get('token'),
        })
      );
      setStatusCode(200);
    } catch (e) {
      setStatusCode(e.statusCode);
      // history.push(`/error`);
    }
  };
  useEffect(() => {
    if (statusCode === 200) {
      if (redirectTimer <= 3 && redirectTimer > 0) {
        setTimeout(() => {
          setRedirectTimer(redirectTimer - 1);
        }, 1000);
      } else if (redirectTimer === 0) {
        history.push('/');
      }
    }
  }, [redirectTimer]);

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
                <Trans
                  i18nKey={'verifyemail.redirecting'}
                  values={{ time: redirectTimer }}
                />
              </>
            ) : (
              <>
                <div className="font-bold text-3xl mb-2">
                  {t('verifyemail.invalid')}
                </div>
                <div>{t('verifyemail.instructions')}</div>
                {!token && (
                  <div className="flex justify-center mt-3">
                    <Button
                      className={clsx(classes.submitButton, 'w-32')}
                      component={Link}
                      to="/register"
                    >
                      {t('verifyemail.register')}
                    </Button>
                  </div>
                )}
              </>
            )}
          </Card>
        </div>
      </div>
    </div>
  );
};

export default VerifyEmail;
