import React, { useContext, useEffect, useState } from 'react';
import NavBar from '../components/NavBar';
import SectionHeader from '../components/SectionHeader';
import styles from '../styles';
import { Card, Grid, makeStyles } from '@material-ui/core';
import { useTranslation } from 'react-i18next';
import AverageRatingCard from '../components/AverageRatingCard';
import TimesHiredCard from '../components/TimesHiredCard';
import clsx from 'clsx';
import JobCard from '../components/JobCard';
import { Helmet } from 'react-helmet';
import { UserContext } from '../context';
import { useUser } from '../hooks';
import { useJobCards } from '../hooks';
import BottomPagination from '../components/BottomPagination';
import { useLocation } from 'react-router-dom';
import { parse } from 'query-string';

const useStyles = makeStyles((theme) => ({
  analyticsCard: {
    borderRadius: '10px',
    height: '100%',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '20px',
  },
  card: {
    borderRadius: '10px',
  },
  profileImg: {
    borderRadius: '50%',
    width: '150px',
    height: '150px',
    objectFit: 'cover',
  },
  cardHeader: {
    'backgroundColor': '#485696',
    'color': 'white',
    'fontSize': '20px',
  },
}));

const useGlobalStyles = makeStyles(styles);

const Analytics = () => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();
  const { getRankings, getProfessionalInfo } = useUser();
  const { currentUser } = useContext(UserContext);
  const [details, setDetails] = React.useState({ rankings: '', info: '' });

  const loadData = async (id) => {
    try {
      setDetails({
        rankings: await getRankings(id),
        info: await getProfessionalInfo(id),
      });
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    loadData(currentUser.id);
  }, []);

  return (
    <div>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.analytics') })}
        </title>
      </Helmet>
      <NavBar currentSection={'/analytics'} />
      <div className={globalClasses.contentContainerTransparent}>
        <SectionHeader sectionName={t('analytics.title')} />
        <Grid container spacing={3}>
          <Grid item sm={3} xs={6}>
            <Card classes={{ root: classes.analyticsCard }}>
              <img
                loading="lazy"
                className={classes.profileImg}
                src={currentUser.image}
                alt={t('account.data.imagealt')}
              />
              <div className="font-bold mt-2 text-center">
                {currentUser.username}
              </div>
            </Card>
          </Grid>
          {details?.info && (
            <Grid
              item
              sm={3}
              xs={6}
              className="h-64 flex flex-col justify-between"
            >
              <AverageRatingCard
                reviewAvg={details.info.reviewAvg || 0}
                reviewsQuantity={details.info.reviewsQuantity || 0}
              />
              <TimesHiredCard count={details.info.contractsCompleted || 0} />
            </Grid>
          )}
          {details?.rankings &&
            details.rankings.map((rank, index) => {
              return (
                <Grid item key={index} sm={3} xs={6} className="font-bold h-64">
                  <Card classes={{ root: classes.analyticsCard }}>
                    <div className="text-5xl">
                      {t('analytics.number', { rank: rank.ranking })}
                    </div>
                    <div className="mt-2 text-center">
                      {t('analytics.mosthired', {
                        jobType: rank.jobType.description,
                      })}
                    </div>
                  </Card>
                </Grid>
              );
            })}
        </Grid>
        <div className="mt-5">
          <ClientsRecommendation userId={currentUser.id} />
        </div>
      </div>
    </div>
  );
};

function useQuery() {
  return parse(useLocation().search);
}

const ClientsRecommendation = ({ userId }) => {
  const classes = useStyles();
  const { t } = useTranslation();
  let queryParameters = useQuery();
  const [queryParams, setQueryParams] = React.useState({
    page: queryParameters.page || 1,
  });
  const { relatedJobCards, links } = useJobCards();
  const [jobCards, setJobCards] = React.useState([]);
  const [maxPage, setMaxPage] = useState(1);

  const loadData = async (userId) => {
    try {
      setJobCards(await relatedJobCards(userId));
      setMaxPage(parseInt(links.last?.page) || parseInt(links.prev?.page));
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    loadData(userId);
  }, []);

  return (
    <>
      {jobCards?.length == 0 ? (
        <></>
      ) : (
        <Card classes={{ root: classes.card }}>
          <div className={clsx(classes.cardHeader, 'py-2 px-5 w-full')}>
            {t('analytics.similar')}
          </div>
          <div className="flex flex-wrap justify-evenly content-center p-3">
            {jobCards.map((job) => {
              return (
                <div className="m-3 w-60">
                  <JobCard key={job.id} job={job} />
                </div>
              );
            })}
          </div>
          <BottomPagination
            maxPage={maxPage}
            setQueryParams={setQueryParams}
            queryParams={queryParams}
          />
        </Card>
      )}
    </>
  );
};

export default Analytics;
