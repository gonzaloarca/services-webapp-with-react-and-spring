import { faImage } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  Avatar,
  Button,
  Card,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  Divider,
  Grid,
  IconButton,
  makeStyles,
} from '@material-ui/core';
import { Close, Email, Person, Phone, Subject } from '@material-ui/icons';
import React, { useState } from 'react';
import { Trans, useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { themeUtils } from '../theme';
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
  const [openCancel, setOpenCancel] = useState(false);
  const [openReject, setOpenReject] = useState(false);
  const [openReschedule, setOpenReschedule] = useState(false);
  const [openReviewReschedule, setOpenReviewReschedule] = useState(false);
  const [openRate, setOpenRate] = useState(false);
  const [openDetails, setOpenDetails] = useState(false);
  const [openContact, setOpenContact] = useState(false);
  const classes = useStyles();
  const { t } = useTranslation();

  const modalDataMap = {
    'cancel': {
      open: openCancel,
      openModal: () => setOpenCancel(true),
      onNegative: () => setOpenCancel(false),
      onAffirmative: () => setOpenCancel(false),
      title: t('mycontracts.modals.cancel.title'),
      body: t('mycontracts.modals.cancel.message'),
      negativeLabel: t('mycontracts.modals.cancel.negative'),
      affirmativeLabel: t('mycontracts.modals.cancel.affirmative'),
      affirmativeColor: themeUtils.colors.red,
    },
    'reject': {
      open: openReject,
      openModal: () => setOpenReject(true),
      onNegative: () => setOpenReject(false),
      onAffirmative: () => setOpenReject(false),
      title: t('mycontracts.modals.reject.title'),
      body: t('mycontracts.modals.reject.message'),
      negativeLabel: t('mycontracts.modals.reject.negative'),
      affirmativeLabel: t('mycontracts.modals.reject.affirmative'),
      affirmativeColor: themeUtils.colors.red,
    },
    'reschedule': {
      open: openReschedule,
      openModal: () => setOpenReschedule(true),
      onNegative: () => setOpenReschedule(false),
      onAffirmative: () => setOpenReschedule(false),
      title: t('mycontracts.modals.reschedule.title'),
      body: t('mycontracts.modals.reschedule.message'),
      negativeLabel: t('mycontracts.modals.reschedule.negative'),
      affirmativeLabel: t('mycontracts.modals.reschedule.affirmative'),
      affirmativeColor: themeUtils.colors.yellow,
    },
    'reviewreschedule': {
      open: openReviewReschedule,
      openModal: () => setOpenReviewReschedule(true),
      onNegative: () => setOpenReviewReschedule(false),
      onAffirmative: () => setOpenReviewReschedule(false),
      title: t('mycontracts.modals.reviewreschedule.title'),
      body: t('mycontracts.modals.reviewreschedule.message'),
      negativeLabel: t('mycontracts.modals.reviewreschedule.negative'),
      affirmativeLabel: t('mycontracts.modals.reviewreschedule.affirmative'),
      affirmativeColor: themeUtils.colors.yellow,
    },
    'rate': {
      open: openRate,
      openModal: () => setOpenRate(true),
      onNegative: () => setOpenRate(false),
      onAffirmative: () => setOpenRate(false),
      title: t('mycontracts.modals.rate.title'),
      negativeLabel: t('mycontracts.modals.rate.negative'),
      affirmativeLabel: t('mycontracts.modals.rate.affirmative'),
      affirmativeColor: themeUtils.colors.yellow,
    },
    'details': {
      open: openDetails,
      openModal: () => setOpenDetails(true),
      onNegative: () => setOpenDetails(false),
      title: t('mycontracts.modals.details.title'),
      body: (
        <DetailsBody
          text={contract.jobContract.description}
          image={contract.jobContract.image}
        />
      ),
      negativeLabel: t('mycontracts.modals.details.negative'),
    },
    'contact': {
      open: openContact,
      openModal: () => setOpenContact(true),
      onNegative: () => setOpenContact(false),
      title: t('mycontracts.modals.contact.title'),
      body: (
        <ContactBody
          username={
            isOwner ? contract.client.username : contract.professional.username
          }
          email={isOwner ? contract.client.email : contract.professional.email}
          phone={isOwner ? contract.client.phone : contract.professional.phone}
        />
      ),
      negativeLabel: t('mycontracts.modals.contact.negative'),
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
            <img className={classes.jobImage} src={contract.image} alt="" />
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
                    modalDataMap[action]
                      ? modalDataMap[action].openModal
                      : onClick
                  }
                  style={{ color: color }}
                  startIcon={
                    <FontAwesomeIcon icon={icon} style={{ color: color }} />
                  }
                >
                  {t(label)}
                </Button>
                {modalDataMap[action] && (
                  <HirenetModal
                    title={modalDataMap[action].title}
                    body={modalDataMap[action].body}
                    open={modalDataMap[action].open}
                    onNegative={modalDataMap[action].onNegative}
                    onAffirmative={modalDataMap[action].onAffirmative}
                    affirmativeLabel={modalDataMap[action].affirmativeLabel}
                    negativeLabel={modalDataMap[action].negativeLabel}
                    affirmativeColor={modalDataMap[action].affirmativeColor}
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

const HirenetModal = ({
  open,
  title,
  body,
  onClose = null,
  onNegative,
  onAffirmative,
  affirmativeLabel,
  negativeLabel,
  affirmativeColor,
}) => {
  return (
    <Dialog open={open} onClose={onClose ? onClose : onNegative}>
      <div className="p-2">
        <div className="flex justify-end">
          <IconButton
            onClick={onClose ? onClose : onNegative}
            style={{ width: 30, height: 30 }}
          >
            <Close />
          </IconButton>
        </div>
        <h2 className="font-semibold px-4 text-lg">{title}</h2>
      </div>
      <DialogContent>{body}</DialogContent>
      <DialogActions>
        <Button onClick={onNegative}>{negativeLabel}</Button>
        {onAffirmative && (
          <Button
            style={{ color: affirmativeColor }}
            onClick={onAffirmative}
            autoFocus
          >
            {affirmativeLabel}
          </Button>
        )}
      </DialogActions>
    </Dialog>
  );
};

const DetailsBody = ({ text, image }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className={classes.detailsModalBody}>
      {image && (
        <>
          <div className="flex w-full mb-2 items-center">
            <FontAwesomeIcon className="text-lg" icon={faImage} />
            <h3 className="ml-2 font-semibold text-base">
              {t('mycontracts.contractimage')}
            </h3>
          </div>
          <img className={classes.detailsImage} src={image} alt="" />
        </>
      )}
      <div className="flex w-full mt-6 mb-2 items-center">
        <Subject />
        <h3 className="ml-2 font-semibold text-base">
          {t('mycontracts.contractdescription')}
        </h3>
      </div>
      <p className={classes.detailsDescription}>{text}</p>
    </div>
  );
};

const ContactBody = ({ username, email, phone }) => {
  const classes = useStyles();

  return (
    <div className={classes.contactModalBody}>
      <Card
        className="mb-2"
        style={{ backgroundColor: themeUtils.colors.aqua }}
      >
        <div className={classes.contactField}>
          <Person className={classes.contactIcon} />
          <div className={classes.contactFieldValue}>{username}</div>
        </div>
      </Card>
      <Card
        className="mb-2"
        style={{ backgroundColor: themeUtils.colors.lightBlue }}
      >
        <div className={classes.contactField}>
          <Phone className={classes.contactIcon} />
          <div className={classes.contactFieldValue}>{phone}</div>
        </div>
      </Card>
      <Card style={{ backgroundColor: themeUtils.colors.orange }}>
        <div className={classes.contactField}>
          <Email className={classes.contactIcon} />
          <div className={classes.contactFieldValue}>{email}</div>
        </div>
      </Card>
    </div>
  );
};

export default ContractCard;
