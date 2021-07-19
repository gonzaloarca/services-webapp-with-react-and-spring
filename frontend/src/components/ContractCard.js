import { faCalendarAlt } from '@fortawesome/free-regular-svg-icons';
import {
  faCube,
  faImage,
  faInfoCircle,
  faUserCircle,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  Avatar,
  Button,
  Card,
  CircularProgress,
  Divider,
  FormHelperText,
  Grid,
  makeStyles,
  TextField,
  withStyles,
} from '@material-ui/core';
import {
  Email,
  Error,
  LocalOffer,
  Person,
  Phone,
  Subject,
} from '@material-ui/icons';
import { Rating } from '@material-ui/lab';
import clsx from 'clsx';
import React, { useState, useRef, useEffect, useContext } from 'react';
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
import * as Yup from 'yup';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { filterPastTime, filterPastDate } from '../utils/filterPast';
import HirenetModal, { PlainTextBody } from './HirenetModal';
import { useContracts, useReviews, useUser } from '../hooks';
import { extractLastIdFromURL } from '../utils/urlUtils';
import { ConstantDataContext } from '../context';

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

const ContractCard = ({ contract, isOwner, refetch, setReload }) => {
  const { getUserById } = useUser();

  const [openCancel, setOpenCancel] = useState(false);
  const [openReject, setOpenReject] = useState(false);
  const [openReschedule, setOpenReschedule] = useState(false);
  const [openReviewReschedule, setOpenReviewReschedule] = useState(false);
  const [openRate, setOpenRate] = useState(false);
  const [openDetails, setOpenDetails] = useState(false);
  const [openContact, setOpenContact] = useState(false);
  const [loading, setLoading] = useState(true);

  const { changeContractStateClient, changeContractStatePro } = useContracts();
  const { states } = useContext(ConstantDataContext);
  const [professional, setProfessional] = useState(null);
  const [client, setClient] = useState(null);

  const loadProfessional = async () => {
    const user = await getUserById(extractLastIdFromURL(contract.professional));
    setProfessional(user);
  };

  const loadClient = async () => {
    const user = await getUserById(extractLastIdFromURL(contract.client));
    setClient(user);
  };

  useEffect(() => {
    loadClient();
    loadProfessional();
  }, []);

  useEffect(() => {
    if (contract && professional && client) {
      setLoading(false);
    }
  }, [professional, client]);

  const classes = useStyles();
  const { t } = useTranslation();

  const formRef = useRef();

  const handleSubmit = () => {
    if (formRef.current) {
      formRef.current.handleSubmit();
    }
  };

  const onFinalized = async () => {
    if (contract.state.description === 'APPROVED') {
      if (isOwner)
        await changeContractStatePro(
          contract.id,
          states.find((state) => state.description === 'COMPLETED').id
        );
      else
        await changeContractStateClient(
          contract.id,
          states.find((state) => state.description === 'COMPLETED').id
        );
      refetch('finalized');
      setOpenReviewReschedule(false);
    } else if (contract.state.description === 'PENDING_APPROVAL') {
      if (isOwner)
        await changeContractStatePro(
          contract.id,
          states.find((state) => state.description === 'APPROVED').id
        );
      else
        await changeContractStateClient(
          contract.id,
          states.find((state) => state.description === 'APPROVED').id
        );
      refetch('active');
      setOpenReviewReschedule(false);
    }
  };

  const modalDataMap = {
    'cancel': {
      open: openCancel,
      openModal: () => setOpenCancel(true),
      onNegative: () => setOpenCancel(false),
      onAffirmative: async () => {
        if (isOwner)
          await changeContractStatePro(
            contract.id,
            states.find((state) => state.description === 'PRO_CANCELLED').id
          );
        else
          await changeContractStateClient(
            contract.id,
            states.find((state) => state.description === 'CLIENT_CANCELLED').id
          );
        refetch('finalized');
        setOpenCancel(false);
      },
      title: t('mycontracts.modals.cancel.title'),
      body: (props) => (
        <>
          {!loading && (
            <PlainTextBody>
              {t('mycontracts.modals.cancel.message')}
            </PlainTextBody>
          )}
        </>
      ),
      negativeLabel: t('mycontracts.modals.cancel.negative'),
      affirmativeLabel: t('mycontracts.modals.cancel.affirmative'),
      affirmativeColor: themeUtils.colors.red,
    },
    'reject': {
      open: openReject,
      openModal: () => setOpenReject(true),
      onNegative: () => setOpenReject(false),
      onAffirmative: async () => {
        if (isOwner)
          await changeContractStatePro(
            contract.id,
            states.find((state) => state.description === 'PRO_REJECTED').id
          );
        else
          await changeContractStateClient(
            contract.id,
            states.find((state) => state.description === 'CLIENT_REJECTED').id
          );
        refetch('finalized');
        setOpenReject(false);
      },
      title: t('mycontracts.modals.reject.title'),
      body: (props) => (
        <>
          {!loading && (
            <PlainTextBody>
              {t('mycontracts.modals.reject.message')}
            </PlainTextBody>
          )}
        </>
      ),
      negativeLabel: t('mycontracts.modals.reject.negative'),
      affirmativeLabel: t('mycontracts.modals.reject.affirmative'),
      affirmativeColor: themeUtils.colors.red,
    },
    'reschedule': {
      open: openReschedule,
      openModal: () => setOpenReschedule(true),
      onNegative: () => setOpenReschedule(false),
      onAffirmative: async () => {
        handleSubmit();
      },
      title: t('mycontracts.modals.reschedule.title'),
      body: (props) => (
        <>
          {!loading && (
            <RescheduleBody
              formRef={formRef}
              setOpenReschedule={setOpenReschedule}
              refetch={refetch}
              contract={contract}
              isOwner={isOwner}
              {...props}
            />
          )}
        </>
      ),
      negativeLabel: t('mycontracts.modals.reschedule.negative'),
      affirmativeLabel: t('mycontracts.modals.reschedule.affirmative'),
      affirmativeColor: themeUtils.colors.yellow,
    },
    'reviewreschedule': {
      open: openReviewReschedule,
      openModal: () => setOpenReviewReschedule(true),
      onClose: () => setOpenReviewReschedule(false),
      onNegative: async () => {
        if (isOwner)
          await changeContractStatePro(
            contract.id,
            states.find((state) => state.description === 'PRO_REJECTED').id
          );
        else
          await changeContractStateClient(
            contract.id,
            states.find((state) => state.description === 'CLIENT_REJECTED').id
          );
        refetch('finalized');
        setOpenReviewReschedule(false);
      },
      onAffirmative: async () => {
        if (isOwner)
          await changeContractStatePro(
            contract.id,
            states.find((state) => state.description === 'APPROVED').id
          );
        else
          await changeContractStateClient(
            contract.id,
            states.find((state) => state.description === 'APPROVED').id
          );
        refetch('finalized');
        setOpenReviewReschedule(false);
      },
      title: t('mycontracts.modals.reviewreschedule.title'),
      body: (props) => (
        <>
          {!loading && (
            <ReviewRescheduleBody newDate={contract.scheduledDate} {...props} />
          )}{' '}
        </>
      ),
      negativeLabel: t('mycontracts.modals.reviewreschedule.negative'),
      affirmativeLabel: t('mycontracts.modals.reviewreschedule.affirmative'),
      affirmativeColor: themeUtils.colors.yellow,
      negativeColor: themeUtils.colors.red,
    },
    'rate': {
      open: openRate,
      openModal: () => setOpenRate(true),
      onNegative: () => setOpenRate(false),
      onAffirmative: async () => {
        handleSubmit();
      },
      body: (props) => (
        <>
          {!loading && (
            <RateBody
              formRef={formRef}
              setOpenRate={setOpenRate}
              contract={contract}
              refetch={refetch}
              setReload={setReload}
              {...props}
            />
          )}
        </>
      ),
      negativeLabel: t('mycontracts.modals.rate.negative'),
      affirmativeLabel: t('mycontracts.modals.rate.affirmative'),
      affirmativeColor: themeUtils.colors.yellow,
    },
    'details': {
      open: openDetails,
      openModal: () => setOpenDetails(true),
      onNegative: () => setOpenDetails(false),
      title: t('mycontracts.modals.details.title'),
      body: (props) => (
        <>
          {!loading && (
            <DetailsBody
              packageTitle={contract.packageTitle}
              price={contract.price}
              rateType={contract.rateType}
              creationDate={contract.creationDate}
              text={contract.description}
              image={contract.contractImage}
              {...props}
            />
          )}
        </>
      ),
      negativeLabel: t('mycontracts.modals.details.negative'),
    },
    'contact': {
      open: openContact,
      openModal: () => setOpenContact(true),
      onNegative: () => setOpenContact(false),
      title: t('mycontracts.modals.contact.title'),
      body: (props) => (
        <>
          {!loading && (
            <ContactBody
              username={isOwner ? client.username : professional.username}
              email={isOwner ? client.email : professional.email}
              phone={isOwner ? client.phone : professional.phone}
              {...props}
            />
          )}
        </>
      ),
      negativeLabel: t('mycontracts.modals.contact.negative'),
    },
  };

  return (
    <>
      {!loading ? (
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
                src={contract.postImage}
                alt=""
                loading="lazy"
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
                to={`/job/${extractLastIdFromURL(contract.jobPost)}`}
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
                    src={isOwner ? client.image : professional.image}
                  />
                  <p className={classes.username}>
                    {isOwner ? client.username : professional.username}
                  </p>
                </div>
              </div>
            </Grid>
          </Grid>
          <Divider />
          <div className={classes.contractActions}>
            <div className="flex flex-row">
              <div>
                <Button
                  className="ml-2"
                  onClick={modalDataMap['details'].openModal}
                  style={{ color: themeUtils.colors.blue }}
                  startIcon={
                    <FontAwesomeIcon
                      icon={faInfoCircle}
                      style={{ color: themeUtils.colors.blue }}
                    />
                  }
                >
                  {t('mycontracts.contractactions.details')}
                </Button>

                <HirenetModal
                  title={modalDataMap['details'].title}
                  body={modalDataMap['details'].body}
                  open={modalDataMap['details'].open}
                  onNegative={modalDataMap['details'].onNegative}
                  onClose={modalDataMap['details'].onClose}
                  onAffirmative={modalDataMap['details'].onAffirmative}
                  affirmativeLabel={modalDataMap['details'].affirmativeLabel}
                  negativeLabel={modalDataMap['details'].negativeLabel}
                  affirmativeColor={modalDataMap['details'].affirmativeColor}
                />
              </div>
              <div>
                <Button
                  className="ml-2"
                  onClick={modalDataMap['contact'].openModal}
                  style={{ color: themeUtils.colors.aqua }}
                  startIcon={
                    <FontAwesomeIcon
                      icon={faUserCircle}
                      style={{ color: themeUtils.colors.aqua }}
                    />
                  }
                >
                  {t('mycontracts.contractactions.contact')}
                </Button>

                <HirenetModal
                  title={modalDataMap['contact'].title}
                  body={modalDataMap['contact'].body}
                  open={modalDataMap['contact'].open}
                  onNegative={modalDataMap['contact'].onNegative}
                  onClose={modalDataMap['contact'].onClose}
                  onAffirmative={modalDataMap['contact'].onAffirmative}
                  affirmativeLabel={modalDataMap['contact'].affirmativeLabel}
                  negativeLabel={modalDataMap['contact'].negativeLabel}
                  affirmativeColor={modalDataMap['contact'].affirmativeColor}
                />
              </div>
            </div>
            <div>
              {contractActionsMap[
                contractStateDataMap[contract.state.description].state
              ].map(
                (
                  {
                    label,
                    onClick,
                    icon,
                    color,
                    roles,
                    action,
                    showActionButtons,
                    show,
                  },
                  index
                ) =>
                  ((isOwner && roles.includes('PROFESSIONAL')) ||
                    (!isOwner && roles.includes('CLIENT'))) &&
                  (!show || !contract[show]) ? (
                    <>
                      <Button
                        className="ml-2"
                        key={index}
                        onClick={
                          modalDataMap[action] && modalDataMap[action].openModal
                            ? modalDataMap[action].openModal
                            : onFinalized
                        }
                        style={{ color: color }}
                        startIcon={
                          <FontAwesomeIcon
                            icon={icon}
                            style={{ color: color }}
                          />
                        }
                      >
                        {t(label)}
                      </Button>
                      {modalDataMap[action] &&
                        modalDataMap[action].openModal && (
                          <HirenetModal
                            title={modalDataMap[action].title}
                            body={modalDataMap[action].body}
                            open={modalDataMap[action].open}
                            onNegative={modalDataMap[action].onNegative}
                            onClose={modalDataMap[action].onClose}
                            onAffirmative={modalDataMap[action].onAffirmative}
                            affirmativeLabel={
                              modalDataMap[action].affirmativeLabel
                            }
                            negativeLabel={modalDataMap[action].negativeLabel}
                            affirmativeColor={
                              modalDataMap[action].affirmativeColor
                            }
                            showActionButtons={
                              showActionButtons &&
                              ((showActionButtons.includes('PROFESSIONAL') &&
                                isOwner) ||
                                (showActionButtons.includes('CLIENT') &&
                                  !isOwner))
                            }
                          />
                        )}
                    </>
                  ) : (
                    <></>
                  )
              )}
            </div>
          </div>
        </Card>
      ) : (
        <div className="flex justify-center items-center w-full h-96">
          <CircularProgress />
        </div>
      )}
    </>
  );
};

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

const RateBody = ({ formRef, setOpenRate, contract, refetch, setReload }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  const { createReview } = useReviews();

  const initialValues = {
    rating: '',
    opinion: '',
    summary: '',
  };

  const [rating, setRating] = useState(0);

  const validationSchema = Yup.object({
    rating: Yup.number().required(t('validationerror.rating')),
    opinion: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 })),
    summary: Yup.string().max(
      100,
      t('validationerror.maxlength', { length: 100 })
    ),
  });

  const onSubmit = async (values, props) => {
    await createReview({
      title: values.summary,
      description: values.opinion,
      jobContractId: contract.id,
      rate: values.rating,
    });
    refetch('finalized');
    setOpenRate(false);
    setReload(true);
  };

  return (
    <div className="p-4">
      <Formik
        innerRef={formRef}
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={onSubmit}
      >
        {(props) => (
          <Form>
            <div className={classes.ratingContainer}>
              <h2 className="font-bold text-lg">
                {t('mycontracts.rate.header')}
              </h2>
              <WhiteRating
                name="rating"
                precision={1}
                value={rating}
                onChange={(event, newValue) => {
                  setRating(newValue);
                  props.setFieldValue('rating', newValue);
                }}
              />
              <FormHelperText>
                <ErrorMessage name="rating"></ErrorMessage>
              </FormHelperText>
            </div>
            <p className={classes.ratingLabel}>
              {t('mycontracts.rate.tellus')}
            </p>
            <Field
              as={TextField}
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
              name="opinion"
              helperText={<ErrorMessage name="opinion"></ErrorMessage>}
            />
            <p className={classes.ratingLabel}>
              {t('mycontracts.rate.summarize')}
            </p>
            <Field
              as={TextField}
              fullWidth
              hiddenLabel
              variant="filled"
              InputProps={{ classes: { input: classes.ratingInput } }}
              placeholder={t('mycontracts.rate.summarizeplaceholder')}
              name="summary"
              helperText={<ErrorMessage name="summary"></ErrorMessage>}
            />
          </Form>
        )}
      </Formik>
    </div>
  );
};

const RescheduleBody = ({
  formRef,
  setOpenReschedule,
  refetch,
  contract,
  isOwner,
}) => {
  const classes = useStyles();
  const { t } = useTranslation();

  const { changeContractStateClient, changeContractStatePro } = useContracts();
  const { states } = useContext(ConstantDataContext);

  const initialValues = {
    date: '',
  };

  const validationSchema = Yup.object({
    date: Yup.date().required(t('validationerror.required')).nullable(),
  });

  const onSubmit = async (values, props) => {
    // console.log(formRef.current.values);
    if (isOwner)
      await changeContractStatePro(
        contract.id,
        states.find((state) => state.description === 'PRO_MODIFIED').id,
        values.date.toISOString()
      );
    else
      await changeContractStateClient(
        contract.id,
        states.find((state) => state.description === 'CLIENT_MODIFIED').id,
        values.date.toISOString()
      );
    refetch('pending');
    setOpenReschedule(false);
  };

  return (
    <div className="p-4">
      <div className="flex text-base font-semibold items-center mb-2">
        <FontAwesomeIcon className="text-lg mr-2" icon={faCalendarAlt} />
        <h2>{t('mycontracts.pickdate')}</h2>
      </div>

      <Formik
        innerRef={formRef}
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={onSubmit}
      >
        {(props) => (
          <Form>
            <div className={classes.centerDatePicker}>
              <DatePicker
                selected={
                  (props.values.date && new Date(props.values.date)) || null
                }
                onChange={(val) => {
                  props.setFieldValue('date', val);
                }}
                showTimeSelect
                dateFormat={t('datetimeformat')}
                name="date"
                inline
                filterDate={filterPastDate}
                filterTime={filterPastTime}
                placeholderText={t('hirePage.form.dateplaceholder')}
                locale={t('locale')}
              />
            </div>
            <FormHelperText className="flex justify-center">
              <ErrorMessage name="date"></ErrorMessage>
            </FormHelperText>
          </Form>
        )}
      </Formik>
      <div className={classes.rescheduleDisclaimer}>
        <Error className={classes.disclaimerIcon} />
        <p className="font-medium text-sm ml-2">
          {t('mycontracts.modals.reschedule.message')}
        </p>
      </div>
    </div>
  );
};

const ReviewRescheduleBody = ({ newDate, showDisclaimer }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className={classes.reviewRescheduleContainer}>
      <CalendarDisplay date={newDate} size={200} />
      <>
        {showDisclaimer && (
          <div className={classes.rescheduleDisclaimer}>
            <Error className={classes.disclaimerIcon} />
            <p className="font-medium text-sm ml-2">
              {t('mycontracts.reviewreschedule.disclaimer')}
            </p>
          </div>
        )}
      </>
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
