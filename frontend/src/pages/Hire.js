import { Button, Grid, makeStyles, TextField } from '@material-ui/core';
import React, { useState } from 'react';
import NavBar from '../components/NavBar';
import styles from '../styles';
import clsx from 'clsx';
import SectionHeader from '../components/SectionHeader';
import { useTranslation } from 'react-i18next';
import { AttachMoney, LocationOn, Schedule } from '@material-ui/icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBoxOpen, faBriefcase } from '@fortawesome/free-solid-svg-icons';
import {
  KeyboardDateTimePicker,
  MuiPickersUtilsProvider,
} from '@material-ui/pickers';
import DateFnsUtils from '@date-io/date-fns';

const pack = {
  active: true,
  description:
    'Atencion constante y juego para el desarrollo de musculos como brazos y piernas.',
  id: 8,
  jobPost: {
    id: 8,
    uri: 'http://localhost:8080/job-posts/8',
  },
  price: 160.0,
  rateType: {
    description: 'HOURLY',
    id: 2,
  },
  title: '4 dias a la semana 4 horas',
};

const post = {
  active: true,
  availableHours: 'Lunes a viernes entre las 8am y 2pm',
  creationDate: '2021-05-02T18:22:13.338478',
  id: 8,
  jobType: {
    description: 'BABYSITTING',
    id: 7,
  },
  images: ['/img/plumbing.jpeg', '/img/carpentry.jpeg'],
  packages: [
    {
      id: 8,
      uri: 'http://localhost:8080/job-posts/8/packages/8',
    },
  ],
  professional: {
    id: 5,
    uri: 'http://localhost:8080/users/5',
  },
  title: 'Niñero turno mañana',
  zones: [
    {
      description: 'RETIRO',
      id: 28,
    },
    {
      description: 'NUNIEZ',
      id: 20,
    },
    {
      description: 'COLEGIALES',
      id: 9,
    },
  ],
};

const proUser = {
  email: 'manaaasd@gmail.com',
  id: 5,
  phone: '03034560',
  roles: ['CLIENT', 'PROFESSIONAL'],
  username: 'Manuel Rodriguez',
  image: '/img/plumbing.jpeg',
};

const useStyles = makeStyles((theme) => ({
  hireBody: {
    paddingLeft: '0 !important',
    paddingRight: '0 !important',
  },
  hireForm: {
    padding: '50px 30px 10px 30px',
    backgroundColor: 'white',
    boxShadow: '0 3px 6px rgba(0, 0, 0, 0.16)',
  },
  packageInfo: {
    paddingTop: '20px',
    paddingBottom: '10px',
    backgroundColor: 'white',
    boxShadow: '0 3px 6px rgba(0, 0, 0, 0.16)',
  },
  headerFilter: {
    position: 'absolute',
    backdropFilter: 'blur(8px)',
    backgroundColor: 'rgba(128,128,128,0.7)',
    height: '100%',
    width: '100%',
  },
  detailTitle: {
    fontWeight: 'bold',
    margin: 'auto 20px 20px 20px',
    fontSize: '1.25rem',
  },
  detailImg: {
    width: '100%',
    height: '18vh',
    objectFit: 'cover',
  },
  detailRow: {
    padding: '0 20px 0 20px',
    marginTop: '1px',
    marginBottom: '1px',
  },
  detailDividerBar: {
    borderTop: '1px solid #a7a7a7',
    margin: '0 0 0 0',
  },
  avatarPic: {
    width: '40px !important',
    height: '40px !important',
    borderRadius: '40px !important',
    objectFit: 'cover',
  },
  priceTag: {
    backgroundColor: '#485696',
    color: 'white',
    margin: '5px 0 5px 0',
    fontSize: 'larger',
    fontWeight: 'bold',
    padding: '5px 10px 5px 10px',
    borderRadius: '15px',
  },
  formSectionTitle: {
    margin: '5px 0 10px 0',
    fontWeight: '600',
  },
  fillInput: {
    backgroundColor: 'rgba(0, 0, 0, 0.09)',
  },
  fileInputContainer: {
    display: 'flex',
    backgroundColor: '#e8e8e8',
    borderRadius: '5px',
    justifyContent: 'space-between',
  },
  cancelBtn: {
    paddingTop: '0',
    paddingBottom: '0',
    backgroundColor: 'white',
    color: '#6c757d',
    borderColor: '#6c757d',
  },
  submitButton: {
    color: '#fff',
    backgroundColor: '#007bff',
    borderColor: '#007bff',
  },
}));

const useGlobalStyles = makeStyles(styles);

const Hire = ({ match }) => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <>
      <NavBar />
      <div
        className={clsx(
          classes.hireBody,
          globalClasses.contentContainerTransparent
        )}
      >
        <SectionHeader
          sectionName={t('hirePage.title')}
          imageSrc={post.images[0]}
          filterClass={classes.headerFilter}
        />
        <Grid className="mt-4" container spacing={3}>
          <Grid item sm={7} xs={12}>
            <HireForm />
          </Grid>
          <Grid item sm={5} xs={12}>
            <PackageInfo />
          </Grid>
        </Grid>
      </div>
    </>
  );
};

const HireForm = () => {
  const classes = useStyles();
  const globalClasses = useGlobalStyles();
  const { t } = useTranslation();

  const [selectedDate, handleDateChange] = useState(new Date());

  return (
    <div className={classes.hireForm}>
      <div className="text-3xl font-bold mb-3">{t('hirePage.form.title')}</div>
      <div>{t('hirePage.form.required')}</div>
      <hr className={classes.detailDividerBar} />
      <form noValidate autoComplete="off" className="p-5">
        <Grid container>
          <Grid item sm={1}>
            <div className={globalClasses.blueCircle}>
              <p className={globalClasses.circleText}>1</p>
            </div>
          </Grid>
          <Grid item sm={11}>
            <div className={classes.formSectionTitle}>
              {t('hirePage.form.descTitle')}
            </div>
          </Grid>
          <Grid item sm={12} className="mx-10 mb-5">
            <TextField
              id="filled-multiline-static"
              multiline
              rows={8}
              defaultValue={t('hirePage.form.descDefault')}
              variant="filled"
              fullWidth
              label={t('hirePage.form.description')}
            />
          </Grid>

          <Grid item sm={1}>
            <div className={globalClasses.orangeCircle}>
              <p className={globalClasses.circleText}>2</p>
            </div>
          </Grid>
          <Grid item sm={11}>
            <div className={classes.formSectionTitle}>
              {t('hirePage.form.dateTitle')}
            </div>
          </Grid>
          <Grid item sm={12} className="mx-10 mb-5">
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
              <KeyboardDateTimePicker
                variant="inline"
                inputVariant="outlined"
                ampm={false}
                value={selectedDate}
                onChange={handleDateChange}
                disablePast
                format={t('dateTimeFormat')}
                fullWidth
                className={classes.fillInput}
              />
            </MuiPickersUtilsProvider>
            <div className="font-thin text-sm">
              {t('hirePage.form.dateDisclamer')}
            </div>
          </Grid>

          <Grid item sm={1}>
            <div className={globalClasses.yellowCircle}>
              <p className={globalClasses.circleText}>3</p>
            </div>
          </Grid>
          <Grid item sm={11}>
            <div className={classes.formSectionTitle}>
              {t('hirePage.form.imageTitle')}
            </div>
          </Grid>
          <Grid item sm={12} className="mx-10 mb-5">
            <div className={classes.fileInputContainer}>
              <input type="file" path="image" id="imageInput" />
              {/* TODO: boton de descartar imagen subida
              <Button className={classes.cancelBtn} variant="outlined" type="button">
                Descartar
              </Button> */}
            </div>
            <div className="font-thin text-sm">
              {t('hirePage.form.imageDisclamer')}
            </div>
          </Grid>

          <Grid item sm={12} className="flex justify-end">
            <Button
              type="submit"
              variant="contained"
              disableElevation
              className={classes.submitButton}
            >
              {t('hirePage.form.submit')}
            </Button>
          </Grid>
        </Grid>
      </form>
    </div>
  );
};

const PackageInfo = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className={classes.packageInfo}>
      <div className={classes.detailTitle}>{t('hirePage.detail.title')}</div>
      <img
        loading="lazy"
        className={classes.detailImg}
        alt=""
        src={post.images[0]}
      />
      <div className="flex flex-col px-5">
        <DetailRow
          text={post.title}
          icon={<FontAwesomeIcon icon={faBriefcase} size="2x" />}
          divider
        />
        <DetailRow
          text={pack.title}
          icon={<FontAwesomeIcon icon={faBoxOpen} size="2x" />}
          divider
        />
        <DetailRow
          text={ZonesText()}
          icon={<LocationOn fontSize="large" />}
          divider
        />
        <DetailRow
          text={proUser.username}
          icon={
            <img
              loading="lazy"
              className={classes.avatarPic}
              src={proUser.image}
              alt="avatar"
            />
          }
          divider
        />
        <DetailRow
          text={post.availableHours}
          icon={<Schedule fontSize="large" />}
          divider
        />
        <DetailRow
          text={<PriceTag />}
          icon={<AttachMoney fontSize="large" />}
        />
      </div>
    </div>
  );
};

const DetailRow = ({ icon, text, divider }) => {
  const classes = useStyles();

  const renderDivider = () => {
    if (divider)
      return <hr className={clsx(classes.detailDividerBar, 'mx-5')} />;
  };

  return (
    <div>
      <Grid
        container
        className={clsx(classes.detailRow, 'flex items-center')}
        spacing={3}
      >
        <Grid item xs={2} className="flex justify-center">
          {icon}
        </Grid>
        <Grid item xs={10}>
          {text}
        </Grid>
      </Grid>
      {renderDivider()}
    </div>
  );
};

const ZonesText = () => {
  let result = '',
    size = 0;

  post.zones.forEach((zone) => {
    result += zone.description;
    size++;
    if (size !== post.zones.length) result += ', ';
  });

  return result;
};

const PriceTag = () => {
  const classes = useStyles();
  const { t } = useTranslation();
  let priceStr;

  //TODO: verificar si esta logica todavia se cumple
  switch (pack.rateType.description) {
    case 'HOURLY':
      priceStr = t('ratetype.hourly', { amount: pack.price });
      break;
    case 'TBD':
      priceStr = t('ratetype.tbd');
      break;
    case 'ONE_TIME':
      priceStr = t('ratetype.oneTime', { amount: pack.price });
      break;
    default:
      break;
  }

  return (
    <div className="flex">
      <div className={classes.priceTag}>{priceStr}</div>
    </div>
  );
};

export default Hire;
