import {
  Avatar,
  Card,
  Link,
  makeStyles,
  CircularProgress,
} from '@material-ui/core';
import { Launch } from '@material-ui/icons';
import { Rating } from '@material-ui/lab';
import React, { useEffect, useState } from 'react';
import { Trans, useTranslation } from 'react-i18next';
import { Link as RouterLink } from 'react-router-dom';
import { useUser } from '../hooks';
import createDate from '../utils/createDate';
import { extractLastIdFromURL } from '../utils/urlUtils';

const useStyles = makeStyles((theme) => ({
  reviewHeader: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    padding: 5,
  },
  headerLeft: {
    display: 'flex',
    flex: 5,
    alignItems: 'center',
    justifyContent: 'start',
  },
  nameAndDateContainer: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'start',
    height: '100%',
    marginLeft: '0.5rem',
  },
  reviewBody: {},
}));

const ReviewCard = ({ review }) => {
  const { getUserById } = useUser();
  const classes = useStyles();
  const headerHeight = 65;
  const { t } = useTranslation();

  const [client, setClient] = useState(null);
  const [loading, setLoading] = useState(true);
  const loadUser = async () => {
    const userId = extractLastIdFromURL(review.client);
    const userData = await getUserById(userId);
    setClient(userData);
  };

  useEffect(() => {
    loadUser();
  }, [review]);

  useEffect(() => {
    if (client) {
      setLoading(false);
    }
  }, [client]);

  return loading ? (
    <div className="flex justify-center items-center w-full h-52">
      <CircularProgress />
    </div>
  ) : (
    <Card className="p-4">
      <div className={classes.reviewHeader} style={{ height: headerHeight }}>
        <div className={classes.headerLeft}>
          <Avatar
            src={client.image}
            style={{ height: headerHeight * 0.8, width: headerHeight * 0.8 }}
          />
          <div className={classes.nameAndDateContainer}>
            {/* Nombre del usuario */}
            <p className="text-sm font-semibold">{client.username}</p>
            {/* Fecha de review */}
            <p className="text-gray-400 font-medium text-sm">
              {t('date', { date: createDate(review.creationDate) })}
            </p>
            {/* Referencia al jobPost */}
            {review.jobPost && (
              <Link
                className="text-sm flex items-center"
                component={RouterLink}
                to={`/job/${review.jobPost.id}`}
              >
                <Launch className="text-lg mr-1" />
                <Trans
                  i18nKey={'jobreference'}
                  values={{ title: review.jobPost.title }}
                  components={{
                    semibold: <span className="ml-1 font-semibold" />,
                  }}
                />
                {/* {t('review.jobreference', { title: review.jobPost.title })} */}
              </Link>
            )}
          </div>
        </div>
        <Rating readOnly precision={0.5} value={review.rate} />
      </div>
      <div>
        <p className="font-bold">{review.title}</p>
        <p className="text-sm font-medium">{review.description}</p>
      </div>
    </Card>
  );
};

export default ReviewCard;
