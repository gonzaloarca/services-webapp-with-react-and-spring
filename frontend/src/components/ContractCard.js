import { faCalendarAlt } from '@fortawesome/free-regular-svg-icons';
import { faCube, faImage } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  Avatar,
  Button,
  Card,
  Dialog,
  DialogActions,
  DialogContent,
  Divider,
  Grid,
  IconButton,
  makeStyles,
  TextField,
  withStyles,
} from '@material-ui/core';
import {
  Close,
  Email,
  Error,
  LocalOffer,
  Person,
  Phone,
  Subject,
} from '@material-ui/icons';
import { Rating } from '@material-ui/lab';
import clsx from 'clsx';
import React, { useState } from 'react';
import { Trans, useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { themeUtils } from '../theme';
import {
  contractActionsMap,
  contractStateDataMap,
} from '../utils/contractState';
import createDate from '../utils/createDate';
import packagePriceFormatter from '../utils/packagePriceFormatter';
import contractCardStyles from './ContractCardStyles';
import RatingDisplay from './RatingDisplay';
import DatePicker from 'react-datepicker';

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
      body: (
        <PlainTextBody>{t('mycontracts.modals.cancel.message')}</PlainTextBody>
      ),
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
      body: (
        <PlainTextBody>{t('mycontracts.modals.reject.message')}</PlainTextBody>
      ),
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
      body: <RescheduleBody />,
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
      body: <ReviewRescheduleBody newDate={contract.scheduledDate} />,
      negativeLabel: t('mycontracts.modals.reviewreschedule.negative'),
      affirmativeLabel: t('mycontracts.modals.reviewreschedule.affirmative'),
      affirmativeColor: themeUtils.colors.yellow,
      negativeColor: themeUtils.colors.red,
    },
    'rate': {
      open: openRate,
      openModal: () => setOpenRate(true),
      onNegative: () => setOpenRate(false),
      onAffirmative: () => setOpenRate(false),
      body: <RateBody />,
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
          packageTitle={contract.packageTitle}
          price={contract.price}
          rateType={contract.rateType}
          creationDate={contract.creationDate}
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
  negativeColor = 'black',
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
      <Divider />

      <DialogContent className="p-0">{body}</DialogContent>
      <Divider />
      <DialogActions>
        <Button style={{ color: negativeColor }} onClick={onNegative}>
          {negativeLabel}
        </Button>
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

const PlainTextBody = ({ children }) => (
  <div className="p-6 text-sm font-medium">{children}</div>
);

const DetailsBody = ({
  packageTitle,
  rateType,
  price,
  creationDate,
  text,
  image,
}) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className={classes.detailsModalBody}>
      {/* Package */}
      <div className={classes.detailsHeaderContainer}>
        <FontAwesomeIcon className="text-lg" icon={faCube} />
        <h3 className={classes.detailsHeader}>{t('mycontracts.package')}</h3>
      </div>
      <div className={classes.packageContainer}>
        <p className={classes.packageTitle}>{packageTitle}</p>
        <div className="flex justify-end w-full">
          <div className={classes.priceContainer}>
            <LocalOffer />
            <p className="text-sm ml-2">
              {packagePriceFormatter(t, rateType, price)}
            </p>
          </div>
        </div>
      </div>
      {/* Fecha de creación */}
      <div className={classes.detailsHeaderContainer}>
        <FontAwesomeIcon className="text-lg" icon={faCalendarAlt} />
        <h3 className={classes.detailsHeader}>
          {t('mycontracts.creationdate')}
        </h3>
      </div>
      <div
        className={clsx(
          classes.detailsDescription,
          'flex',
          'justify-center',
          'font-semibold',
          'text-base'
        )}
      >
        {t('datetime', { date: createDate(creationDate) })}
      </div>
      {/* Imagen */}
      {image && (
        <>
          <div className={classes.detailsHeaderContainer}>
            <FontAwesomeIcon className="text-lg" icon={faImage} />
            <h3 className={classes.detailsHeader}>
              {t('mycontracts.contractimage')}
            </h3>
          </div>
          <img className={classes.detailsImage} src={image} alt="" />
        </>
      )}
      {/* Descripcion */}
      <div className={classes.detailsHeaderContainer}>
        <Subject />
        <h3 className={classes.detailsHeader}>
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
    <div className="p-4">
      {/* Nombre */}
      <Card
        className="mb-2"
        style={{ backgroundColor: themeUtils.colors.aqua }}
      >
        <div className={classes.contactField}>
          <Person className={classes.contactIcon} />
          <div className={classes.contactFieldValue}>{username}</div>
        </div>
      </Card>
      {/* Telefono */}
      <Card
        className="mb-2"
        style={{ backgroundColor: themeUtils.colors.lightBlue }}
      >
        <div className={classes.contactField}>
          <Phone className={classes.contactIcon} />
          <div className={classes.contactFieldValue}>{phone}</div>
        </div>
      </Card>
      {/* Email */}
      <Card style={{ backgroundColor: themeUtils.colors.orange }}>
        <div className={classes.contactField}>
          <Email className={classes.contactIcon} />
          <div className={classes.contactFieldValue}>{email}</div>
        </div>
      </Card>
    </div>
  );
};

const RateBody = () => {
  const [rating, setRating] = useState(0);
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className="p-4">
      <div className={classes.ratingContainer}>
        <h2 className="font-bold text-lg">{t('mycontracts.rate.header')}</h2>
        <WhiteRating
          precision={0.5}
          value={rating}
          onChange={(event, newValue) => setRating(newValue)}
        />
      </div>
      <p className={classes.ratingLabel}>{t('mycontracts.rate.tellus')}</p>
      <TextField
        variant="filled"
        InputProps={{
          classes: {
            input: classes.ratingInput,
          },
        }}
        placeholder={t('mycontracts.rate.tellusplaceholder')}
        multiline
        rows={3}
        hiddenLabel
        fullWidth
      />
      <p className={classes.ratingLabel}>{t('mycontracts.rate.summarize')}</p>
      <TextField
        fullWidth
        hiddenLabel
        variant="filled"
        InputProps={{ classes: { input: classes.ratingInput } }}
        placeholder={t('mycontracts.rate.summarizeplaceholder')}
      />
    </div>
  );
};

const RescheduleBody = () => {
  // const { setFieldValue } = useFormikContext();
  // const [field] = useField(props);
  const classes = useStyles();
  const { t } = useTranslation();

  const filterPast = (date) => {
    const currentDate = new Date();
    const selectedDate = new Date(date);

    return currentDate.getTime() < selectedDate.getTime();
  };

  return (
    <div className="p-4">
      <div className="flex text-base font-semibold items-center mb-2">
        <FontAwesomeIcon className="text-lg mr-2" icon={faCalendarAlt} />
        <h2>{t('mycontracts.pickdate')}</h2>
      </div>

      <div className={classes.centerDatePicker}>
        <DatePicker
          showTimeSelect
          onChange={() => {}}
          dateFormat={t('datetimeformat')}
          name="date"
          inline
          filterDate={filterPast}
          filterTime={filterPast}
          placeholderText={t('hirePage.form.dateplaceholder')}
          locale={t('locale')}
        />
      </div>
      <div className={classes.rescheduleDisclaimer}>
        <Error className={classes.disclaimerIcon} />
        <p className="font-medium text-sm ml-2">
          {t('mycontracts.modals.reschedule.message')}
        </p>
      </div>
    </div>
  );
};

const ReviewRescheduleBody = ({ newDate }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className={classes.reviewRescheduleContainer}>
      <CalendarDisplay date={newDate} size={200} />
      <div className={classes.rescheduleDisclaimer}>
        <Error className={classes.disclaimerIcon} />
        <p className="font-medium text-sm ml-2">
          {t('mycontracts.reviewreschedule.disclaimer')}
        </p>
      </div>
    </div>
  );
};

const CalendarDisplay = ({ date = null, size }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div>
      <div
        className={classes.calendarTop}
        style={{ height: size / 4, width: size }}
      />
      <div
        className={classes.calendarBody}
        style={{ height: size, width: size }}
      >
        <p>{t('mycontracts.reviewreschedule.newdate')}</p>
        {date && (
          <Trans
            i18nKey="mycontracts.scheduleddateformat"
            components={{
              date: <div className={classes.calendarDate} />,
              time: <div className={classes.calendarTime} />,
            }}
            values={{
              date: t('date', {
                date: createDate(date),
              }),
              time: t('time', {
                date: createDate(date),
              }),
            }}
          />
        )}
      </div>
    </div>
  );
};

const WhiteRating = withStyles((theme) => ({
  icon: {
    fontSize: '3rem',
  },
  iconFilled: {
    color: 'white',
  },
  iconHover: {
    color: 'white',
  },
}))(Rating);

export default ContractCard;
