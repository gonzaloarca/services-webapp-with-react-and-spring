import { faCalendarAlt, faStar } from '@fortawesome/free-regular-svg-icons';
import {
  faTimesCircle,
  faCheckCircle,
  faUserCircle,
  faInfoCircle,
  faTimes,
  faExclamationCircle,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  Avatar,
  Button,
  Card,
  Divider,
  Grid,
  makeStyles,
} from '@material-ui/core';
import React from 'react';
import { Trans, useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { themeUtils } from '../theme';
import createDate from '../utils/createDate';
import RatingDisplay from './RatingDisplay';

const useStyles = makeStyles((theme) => ({
  contractCard: {
    boxShadow: themeUtils.shadows.containerShadow,
  },
  contractTitle: {
    fontSize: '1.2rem',
    lineHeight: '1.5rem',
    height: '3rem',
    fontWeight: 700,
    width: '100%',
    WebkitLineClamp: 2,
    display: '-webkit-box',
    WebkitBoxOrient: 'vertical',
    overflowWrap: 'break-word',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
  },
  stateContainer: {
    display: 'flex',
    justifyContent: 'start',
    alignItems: 'center',
    width: '100%',
    color: 'white',
    fontSize: '0.9rem',
    fontWeight: 500,
    padding: '10px 10px',
  },
  avatarSize: {
    height: 40,
    width: 40,
  },
  jobType: {
    color: themeUtils.colors.blue,
    fontSize: '0.9rem',
    fontWeight: 600,
  },
  fieldLabel: {
    color: themeUtils.colors.grey,
    fontSize: '0.8rem',
    fontWeight: 500,
    marginBottom: 5,
  },
  jobImageContainer: {
    position: 'relative',
    marginBottom: 20,
    [theme.breakpoints.up('sm')]: {
      marginBottom: 30,
    },
    display: 'flex',
    justifyContent: 'center',
  },
  scheduledDateContainer: {
    position: 'absolute',
    bottom: -20,
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    fontSize: '0.8rem',
    textAlign: 'center',
    padding: '5px 15px',
    backgroundColor: themeUtils.colors.lightBlue,
    boxShadow: themeUtils.shadows.containerShadow,
    borderRadius: 25,
    color: 'white',
  },
  jobImage: {
    borderRadius: 10,
    height: 170,
    width: 170,
    [theme.breakpoints.down('sm')]: {
      width: '100%',
    },
    objectFit: 'cover',
  },
  scheduledDate: {
    fontSize: '1.3rem',
    fontWeight: 600,
    lineHeight: '1.2rem',
  },
  scheduledTime: {
    fontSize: '1.1rem',
    fontWeight: 600,
    lineHeight: '1.2rem',
  },
  userContainer: {
    display: 'flex',
    alignItems: 'center',
    width: '40%',
  },
  username: {
    width: '80%',
    lineHeight: '1.2rem',
    fontWeight: 500,
    WebkitLineClamp: 2,
    display: '-webkit-box',
    WebkitBoxOrient: 'vertical',
    overflowWrap: 'break-word',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    marginLeft: 10,
  },
  contractActions: {
    display: 'flex',
    width: '100%',
    justifyContent: 'flex-end',
    padding: 7,
  },
  // contractActionsCard: {
  //   borderRadius: '0 0 20px 20px',
  //   backgroundColor: '#F1F1F1',
  //   boxShadow: themeUtils.shadows.containerShadow,
  // },
}));

const contractStateDataMap = {
  'PENDING_APPROVAL': {
    state: 'PENDING',
  },
  'APPROVED': {
    state: 'ACTIVE',
  },
  'CLIENT_REJECTED': {
    clientMessage: 'mycontracts.contractstate.yourejected',
    proMessage: 'mycontracts.contractstate.clientrejected',
    state: 'FINALIZED',
    color: themeUtils.colors.red,
    icon: faTimesCircle,
  },
  'PRO_REJECTED': {
    clientMessage: 'mycontracts.contractstate.prorejected',
    proMessage: 'mycontracts.contractstate.yourejected',
    state: 'FINALIZED',
    color: themeUtils.colors.red,
    icon: faTimesCircle,
  },
  'CLIENT_CANCELLED': {
    clientMessage: 'mycontracts.contractstate.youcancelled',
    proMessage: 'mycontracts.contractstate.clientcancelled',
    state: 'FINALIZED',
    color: themeUtils.colors.red,
    icon: faTimesCircle,
  },
  'PRO_CANCELLED': {
    clientMessage: 'mycontracts.contractstate.procancelled',
    proMessage: 'mycontracts.contractstate.youcancelled',
    state: 'FINALIZED',
    color: themeUtils.colors.red,
    icon: faTimesCircle,
  },
  'COMPLETED': {
    clientMessage: 'mycontracts.contractstate.finalized',
    proMessage: 'mycontracts.contractstate.finalized',
    state: 'FINALIZED',
    color: themeUtils.colors.darkGreen,
    icon: faCheckCircle,
  },
  'CLIENT_MODIFIED': {
    clientMessage: 'mycontracts.contractstate.yourescheduled',
    proMessage: 'mycontracts.contractstate.prorescheduled',
    state: 'PENDING',
    color: themeUtils.colors.yellow,
    icon: faExclamationCircle,
  },
  'PRO_MODIFIED': {
    clientMessage: 'mycontracts.contractstate.clientrescheduled',
    proMessage: 'mycontracts.contractstate.',
    state: 'PENDING',
    color: themeUtils.colors.yellow,
    icon: faExclamationCircle,
  },
};

const ContractStateHeader = ({ contract, isOwner }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  const stateData = contractStateDataMap[contract.state.description];
  console.log(stateData);
  return stateData.clientMessage ? (
    <div
      className={classes.stateContainer}
      style={{ backgroundColor: stateData.color }}
    >
      <FontAwesomeIcon className="text-lg mr-2" icon={stateData.icon} />
      {t(isOwner ? stateData.proMessage : stateData.clientMessage)}
    </div>
  ) : (
    <></>
  );
};

const contractActionsMap = {
  'ACTIVE': [
    {
      label: 'mycontracts.contractactions.finalize',
      onClick: () => {
        console.log('FINALIZING CONTRACT');
      },
      icon: faCheckCircle,
      color: themeUtils.colors.green,
      roles: ['CLIENT', 'PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.cancel',
      onClick: () => {
        console.log('CANCELLING CONTRACT');
      },
      icon: faTimes,
      color: themeUtils.colors.red,
      roles: ['CLIENT', 'PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.reschedule',
      onClick: () => {
        console.log('RESCHEDULING CONTRACT');
      },
      icon: faCalendarAlt,
      color: themeUtils.colors.yellow,
      roles: ['CLIENT', 'PROFESSIONAL'],
    },
  ],
  'PENDING': [
    {
      label: 'mycontracts.contractactions.approve',
      onClick: () => {
        console.log('APPROVING CONTRACT');
      },
      icon: faCheckCircle,
      color: themeUtils.colors.green,
      roles: ['PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.reject',
      onClick: () => {
        console.log('REJECTING CONTRACT');
      },
      icon: faTimes,
      color: themeUtils.colors.red,
      roles: ['PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.reschedule',
      onClick: () => {
        console.log('RESCHEDULING CONTRACT');
      },
      icon: faCalendarAlt,
      color: themeUtils.colors.yellow,
      roles: ['CLIENT', 'PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.reviewreschedule',
      onClick: () => {
        console.log('REVIEWING CONTRACT RESCHEDULING');
      },
      icon: faCalendarAlt,
      color: themeUtils.colors.yellow,
      roles: ['CLIENT'],
    },
  ],
  'FINALIZED': [
    {
      label: 'mycontracts.contractactions.details',
      onClick: () => {
        console.log('OPENING CONTRACT DETAILS');
      },
      icon: faInfoCircle,
      color: themeUtils.colors.blue,
      roles: ['CLIENT', 'PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.contact',
      onClick: () => {
        console.log('OPENING USER DETAILS');
      },
      icon: faUserCircle,
      color: themeUtils.colors.aqua,
      roles: ['CLIENT', 'PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.rate',
      onClick: () => {
        console.log('RATING CONTRACT');
      },
      icon: faStar,
      color: themeUtils.colors.yellow,
      roles: ['CLIENT'],
    },
  ],
};

const ContractCard = ({ contract, isOwner }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <>
      <Card className={classes.contractCard}>
        <ContractStateHeader contract={contract} isOwner={isOwner} />
        <Grid className="p-4 pr-4" container spacing={3}>
          <Grid
            className={classes.jobImageContainer}
            item
            xs={12}
            md={4}
            lg={3}
          >
            {/* Fecha programada */}
            <div className={classes.scheduledDateContainer}>
              {t('mycontracts.scheduleddate')}
              <Trans
                i18nKey="mycontracts.scheduleddateformat"
                components={{
                  date: <div className={classes.scheduledDate} />,
                  time: <div className={classes.scheduledTime} />,
                }}
                values={{
                  date: t('date', {
                    date: createDate(contract.scheduledDate),
                  }),
                  time: t('time', {
                    date: createDate(contract.scheduledDate),
                  }),
                }}
              />
            </div>
            <img
              className={classes.jobImage}
              src={contract.jobPost.image}
              alt=""
            />
          </Grid>
          <Grid
            className="flex flex-col justify-center"
            item
            xs={12}
            md={8}
            lg={9}
          >
            {/* Titulo */}
            <Link
              to={`/job/${contract.jobPost.id}`}
              className={classes.contractTitle}
            >
              {contract.jobTitle}
            </Link>
            {/* Categoria y calificacion */}
            <Grid className="mb-1" container spacing={3}>
              <Grid item xs={12} sm={6}>
                <p className={classes.jobType}>
                  {contract.jobType.description}
                </p>
              </Grid>
              <Grid item xs={12} sm={6}>
                <RatingDisplay
                  className="justify-end"
                  avgRate={contract.avgRate}
                  reviewsCount={contract.reviewsCount}
                />
              </Grid>
            </Grid>
            <div>
              <p className={classes.fieldLabel}>
                {t(
                  isOwner ? 'mycontracts.hiredby' : 'mycontracts.professional'
                )}
              </p>
              <div className={classes.userContainer}>
                <Avatar
                  className={classes.avatarSize}
                  src={
                    isOwner
                      ? contract.client.image
                      : contract.professional.image
                  }
                />
                <p className={classes.username}>
                  {isOwner
                    ? contract.client.username
                    : contract.professional.username}
                </p>
              </div>
            </div>
          </Grid>
        </Grid>
        <Divider />
        <div className={classes.contractActions}>
          {contractActionsMap[
            contractStateDataMap[contract.state.description].state
          ].map(({ label, onClick, icon, color, roles }, index) =>
            (isOwner && roles.includes('PROFESSIONAL')) ||
            (!isOwner && roles.includes('CLIENT')) ? (
              <Button
                className="ml-2"
                key={index}
                onClick={onClick}
                style={{ color: color }}
                startIcon={
                  <FontAwesomeIcon icon={icon} style={{ color: color }} />
                }
              >
                {t(label)}
              </Button>
            ) : (
              <></>
            )
          )}
        </div>
      </Card>
    </>
  );
};

export default ContractCard;
