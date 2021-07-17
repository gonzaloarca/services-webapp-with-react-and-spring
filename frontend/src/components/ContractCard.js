import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  Avatar,
  Button,
  Card,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Divider,
  Grid,
  makeStyles,
} from '@material-ui/core';
import React from 'react';
import { Trans, useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import {
  contractActionsMap,
  contractStateDataMap,
} from '../utils/contractState';
import createDate from '../utils/createDate';
import contractCardStyles from './ContractCardStyles';
import RatingDisplay from './RatingDisplay';

const useStyles = makeStyles(contractCardStyles);

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

const ContractCard = ({ contract, isOwner }) => {
  const [openCancel, setOpenCancel] = React.useState(false);
  const [openReject, setOpenReject] = React.useState(false);
  const [openReschedule, setOpenReschedule] = React.useState(false);
  const [openReviewReschedule, setOpenReviewReschedule] = React.useState(false);
  const [openRate, setOpenRate] = React.useState(false);
  const [openDetails, setOpenDetails] = React.useState(false);
  const [openContact, setOpenContact] = React.useState(false);
  const classes = useStyles();
  const { t } = useTranslation();

  const modalStateMap = {
    'cancel': {
      open: openCancel,
      openModal: () => setOpenCancel(true),
      onNegative: () => setOpenCancel(false),
      onAffirmative: () => setOpenCancel(false),
    },
    'reject': {
      open: openReject,
      openModal: () => setOpenReject(true),
      onNegative: () => setOpenReject(false),
      onAffirmative: () => setOpenReject(false),
    },
    'reschedule': {
      open: openReschedule,
      openModal: () => setOpenReschedule(true),
      onNegative: () => setOpenReschedule(false),
      onAffirmative: () => setOpenReschedule(false),
    },
    'reviewreschedule': {
      open: openReviewReschedule,
      openModal: () => setOpenReviewReschedule(true),
      onNegative: () => setOpenReviewReschedule(false),
      onAffirmative: () => setOpenReviewReschedule(false),
    },
    'rate': {
      open: openRate,
      openModal: () => setOpenRate(true),
      onNegative: () => setOpenRate(false),
      onAffirmative: () => setOpenRate(false),
    },
    'details': {
      open: openDetails,
      openModal: () => setOpenDetails(true),
      onNegative: () => setOpenDetails(false),
    },
    'contact': {
      open: openContact,
      openModal: () => setOpenContact(true),
      onNegative: () => setOpenContact(false),
    },
  };

  console.log('STATE', contract.state);
  console.log(
    'contractActions',
    contractActionsMap[contractStateDataMap[contract.state.description].state]
  );

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
          ].map(({ label, onClick, icon, color, roles, action }, index) =>
            (isOwner && roles.includes('PROFESSIONAL')) ||
            (!isOwner && roles.includes('CLIENT')) ? (
              <>
                <Button
                  className="ml-2"
                  key={index}
                  onClick={
                    modalStateMap[action]
                      ? modalStateMap[action].openModal
                      : onClick
                  }
                  style={{ color: color }}
                  startIcon={
                    <FontAwesomeIcon icon={icon} style={{ color: color }} />
                  }
                >
                  {t(label)}
                </Button>
                {modalStateMap[action] && (
                  <WarningModal
                    title={t('mycontracts.modals.' + action + '.title')}
                    body={t('mycontracts.modals.' + action + '.message')}
                    open={modalStateMap[action].open}
                    onNegative={modalStateMap[action].onNegative}
                    onAffirmative={modalStateMap[action].onAffirmative}
                    affirmativeLabel={t(
                      'mycontracts.modals.' + action + '.affirmative'
                    )}
                    negativeLabel={t(
                      'mycontracts.modals.' + action + '.negative'
                    )}
                  />
                )}
              </>
            ) : (
              <></>
            )
          )}
        </div>
      </Card>
    </>
  );
};

const WarningModal = ({
  open,
  title,
  body,
  onNegative,
  onAffirmative,
  affirmativeLabel,
  negativeLabel,
}) => {
  return (
    <Dialog open={open} onClose={onNegative}>
      <DialogTitle>{title}</DialogTitle>
      <DialogContent>
        <DialogContentText>{body}</DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={onNegative}>{negativeLabel}</Button>
        {onAffirmative && (
          <Button onClick={onAffirmative} autoFocus>
            {affirmativeLabel}
          </Button>
        )}
      </DialogActions>
    </Dialog>
  );
};

export default ContractCard;
