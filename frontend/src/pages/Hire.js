import {
  Button,
  FormHelperText,
  Grid,
  makeStyles,
  TextField,
} from '@material-ui/core';
import NavBar from '../components/NavBar';
import FileInput, { checkType, checkSize } from '../components/FileInput';
import styles from '../styles';
import clsx from 'clsx';
import SectionHeader from '../components/SectionHeader';
import { useTranslation } from 'react-i18next';
import { LocalOffer, LocationOn, WatchLater, Work } from '@material-ui/icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBoxOpen } from '@fortawesome/free-solid-svg-icons';
import * as Yup from 'yup';
import {
  Formik,
  Form,
  Field,
  ErrorMessage,
  useField,
  useFormikContext,
} from 'formik';
import DatePicker, { registerLocale } from 'react-datepicker';
import { es } from 'date-fns/locale';
import { themeUtils } from '../theme';
import PriceTag from '../components/PriceTag';
import { filterPastTime, filterPastDate } from '../utils/filterPast';
import { UserContext } from '../context';
import { useJobPosts, useUser, useJobPackages, useContracts } from '../hooks';
import React, { useEffect, useState, useContext } from 'react';
import { extractLastIdFromURL } from '../utils/urlUtils';
import 'react-datepicker/dist/react-datepicker.css';
import { Helmet } from 'react-helmet';
import { isLoggedIn } from '../utils/userUtils';

registerLocale('es', es);

// const pack = {
//   'active': true,
//   'description':
//     'Atencion constante y juego para el desarrollo de musculos como brazos y piernas.',
//   'id': 8,
//   'jobPost': {
//     'id': 8,
//     'uri': 'http://localhost:8080/job-posts/8',
//   },
//   'rateType': {
//     'description': 'TBD',
//     'id': 2,
//   },
//   'price': 50,
//   'title': '4 dias a la semana 4 horas',
// };

// //TODO: como se levanta esto?
// const jobpostimages = [
//   `${process.env.PUBLIC_URL}/img/plumbing.jpeg`,
//   `${process.env.PUBLIC_URL}/img/carpentry.jpeg`,
// ];

// const post = {
//   'active': true,
//   'availableHours': 'Lunes a viernes entre las 8am y 2pm',
//   'creationDate': '2021-05-02T18:22:13.338478',
//   'id': 8,
//   'jobType': {
//     'description': 'BABYSITTING',
//     'id': 7,
//   },
//   'packages': [
//     {
//       'id': 8,
//       'uri': 'http://localhost:8080/job-posts/8/packages/8',
//     },
//   ],
//   'professional': {
//     'id': 5,
//     'uri': 'http://localhost:8080/users/5',
//   },
//   'title': 'Niñero turno mañana',
//   'zones': [
//     {
//       'description': 'RETIRO',
//       'id': 28,
//     },
//     {
//       'description': 'NUNIEZ',
//       'id': 20,
//     },
//     {
//       'description': 'COLEGIALES',
//       'id': 9,
//     },
//   ],
// };

// const proUser = {
//   email: 'manaaasd@gmail.com',
//   id: 5,
//   phone: '03034560',
//   roles: ['CLIENT', 'PROFESSIONAL'],
//   username: 'Manuel Rodriguez',
//   image: `${process.env.PUBLIC_URL}/img/plumbing.jpeg`,
// };

const useStyles = makeStyles((theme) => ({
  hireBody: {
    paddingLeft: '0 !important',
    paddingRight: '0 !important',
  },
  hireForm: {
    padding: '50px 30px 10px 30px',
    backgroundColor: 'white',
    boxShadow: themeUtils.shadows.containerShadow,
  },
  packageInfo: {
    paddingTop: '20px',
    paddingBottom: '10px',
    backgroundColor: 'white',
    boxShadow: themeUtils.shadows.containerShadow,
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
  formSectionTitle: {
    margin: '5px 0 10px 0',
    fontWeight: 600,
  },
  fillInput: {
    backgroundColor: 'rgba(0, 0, 0, 0.09)',
    width: '100%',
    padding: '5px',
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

const Hire = ({ match, history }) => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();

  const packId = match.params.id;
  const postId = match.params.postId;

  const { getJobPostById } = useJobPosts();
  const { getUserById } = useUser();
  const { getJobPackageByPostIdAndPackageId } = useJobPackages();
  const { createContract } = useContracts();
  const { currentUser } = useContext(UserContext);

  const [loading, setLoading] = useState(true);
  const [jobPost, setJobPost] = useState(null);
  const [proUser, setProUser] = useState(null);
  const [pack, setPackage] = useState(null);

  const loadJobPost = async () => {
    try {
      const jobPost = await getJobPostById(postId);
      setJobPost(jobPost);
    } catch (e) {
      history.replace(`/error`);
    }
  };

  const loadProUser = async () => {
    try {
      const proId = extractLastIdFromURL(jobPost.professional);
      const pro = await getUserById(proId);
      setProUser(pro);
    } catch (e) {
      history.replace(`/error`);
    }
  };

  const loadPackage = async () => {
    try {
      const pack = await getJobPackageByPostIdAndPackageId(postId, packId);
      setPackage(pack);
    } catch (e) {
      history.replace(`/error`);
    }
  };

  useEffect(() => {
    if (jobPost) {
      loadProUser();
    }
  }, [jobPost]);

  useEffect(() => {
    if (jobPost && proUser && pack) {
      if (!isLoggedIn()) history.replace(`/login`);
      else if (
        !(currentUser && currentUser.id !== proUser.id && jobPost.active)
      )
        history.replace(`/error`);
      else setLoading(false);
    }
  }, [jobPost, proUser, pack]);

  useEffect(() => {
    loadJobPost();
    loadPackage();
  }, [postId]);

  const tryCreate = async (values) => {
    try {
      await createContract(currentUser.id, pack.id, values);
      history.push(`/my-contracts`);
    } catch (e) {
      history.push(`/error`);
    }
  };

  return (
    <>
      <Helmet>
        <title>{t('title', { section: t('navigation.sections.hire') })}</title>
      </Helmet>
      <NavBar currentSection="/search" />
      {loading ? (
        <></>
      ) : (
        <div
          className={clsx(
            classes.hireBody,
            globalClasses.contentContainerTransparent
          )}
        >
          <SectionHeader
            sectionName={t('hirePage.title')}
            imageSrc={jobPost.images[0]}
            filterClass={classes.headerFilter}
          />
          <Grid className="mt-4" container spacing={3}>
            <Grid item sm={7} xs={12}>
              <HireForm tryCreate={tryCreate} />
            </Grid>
            <Grid item sm={5} xs={12}>
              <PackageInfo jobPost={jobPost} pack={pack} proUser={proUser} />
            </Grid>
          </Grid>
        </div>
      )}
    </>
  );
};

const HireForm = ({ tryCreate }) => {
  const classes = useStyles();
  const globalClasses = useGlobalStyles();
  const { t } = useTranslation();

  const initialValues = {
    description: '',
    date: '',
    image: '',
  };

  const validationSchema = Yup.object({
    description: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 })),
    date: Yup.date().required(t('validationerror.required')).nullable(),
    image: Yup.mixed()
      .test('is-correct-type', t('validationerror.avatarfile.type'), checkType)
      .test(
        'is-correct-size',
        t('validationerror.avatarfile.size', { size: 2 }),
        checkSize
      ),
  });

  const onSubmit = async (values, props) => {
    tryCreate(values);
  };

  return (
    <div className={classes.hireForm}>
      <div className="text-3xl font-bold mb-3">{t('hirePage.form.title')}</div>
      <div>{t('hirePage.form.required')}</div>
      <hr className={classes.detailDividerBar} />
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={onSubmit}
      >
        {(props) => (
          <Form className="p-5">
            <Grid container>
              <Grid item sm={1}>
                <div className={globalClasses.blueCircle}>
                  <p className={globalClasses.circleText}>1</p>
                </div>
              </Grid>
              <Grid item sm={11}>
                <div className={classes.formSectionTitle}>
                  {t('hirePage.form.desctitle')}
                </div>
              </Grid>
              <Grid item sm={12} className="mx-10 mb-5 h-56">
                <Field
                  as={TextField}
                  id="filled-multiline-static"
                  multiline
                  rows={8}
                  placeholder={t('hirePage.form.descdefault')}
                  variant="filled"
                  fullWidth
                  label={t('hirePage.form.description')}
                  name="description"
                  helperText={<ErrorMessage name="description"></ErrorMessage>}
                />
              </Grid>

              <Grid item sm={1}>
                <div className={globalClasses.orangeCircle}>
                  <p className={globalClasses.circleText}>2</p>
                </div>
              </Grid>
              <Grid item sm={11}>
                <div className={classes.formSectionTitle}>
                  {t('hirePage.form.datetitle')}
                </div>
              </Grid>
              <Grid item sm={12} className="mx-10 mb-5 h-20">
                <DatePickerField name="date" />
                <FormHelperText>
                  <ErrorMessage name="date"></ErrorMessage>
                </FormHelperText>
                <div className="font-thin text-sm">
                  {t('hirePage.form.datedisclamer')}
                </div>
              </Grid>

              <Grid item sm={1}>
                <div className={globalClasses.yellowCircle}>
                  <p className={globalClasses.circleText}>3</p>
                </div>
              </Grid>
              <Grid item sm={11}>
                <div className={classes.formSectionTitle}>
                  {t('hirePage.form.imagetitle')}
                </div>
              </Grid>
              <Grid item sm={12} className="mx-10 mb-5">
                <FileInput fileName="image" />
                <div className="font-thin text-sm">
                  {t('hirePage.form.imagedisclamer')}
                </div>
              </Grid>

              <Grid item sm={12} className="flex justify-end">
                <Button
                  type="submit"
                  variant="contained"
                  disableElevation
                  className={classes.submitButton}
                  disabled={props.isSubmitting}
                >
                  {t('hirePage.form.submit')}
                </Button>
              </Grid>
            </Grid>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export const DatePickerField = (props) => {
  const classes = useStyles();
  const { t } = useTranslation();

  const { setFieldValue } = useFormikContext();
  const [field] = useField(props);

  return (
    <DatePicker
      selected={(field.value && new Date(field.value)) || null}
      onChange={(val) => {
        setFieldValue(field.name, val);
      }}
      showTimeSelect
      dateFormat={t('datetimeformat')}
      name="date"
      wrapperClassName="w-full"
      className={classes.fillInput}
      filterDate={filterPastDate}
      filterTime={filterPastTime}
      placeholderText={t('hirePage.form.dateplaceholder')}
      locale={t('locale')}
    />
  );
};

const PackageInfo = ({ jobPost, pack, proUser }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className={classes.packageInfo}>
      <div className={classes.detailTitle}>{t('hirePage.detail.title')}</div>
      <img
        loading="lazy"
        className={classes.detailImg}
        alt=""
        src={jobPost.images[0]}
      />
      <div className="flex flex-col px-5">
        <DetailRow
          text={jobPost.title}
          icon={<Work fontSize="large" />}
          divider
        />
        <DetailRow
          text={pack.title}
          icon={<FontAwesomeIcon icon={faBoxOpen} size="2x" />}
          divider
        />
        <DetailRow
          text={ZonesText(jobPost)}
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
          text={jobPost.availableHours}
          icon={<WatchLater fontSize="large" />}
          divider
        />
        <DetailRow
          text={<PriceTag rateType={pack.rateType} price={pack.price} />}
          icon={<LocalOffer fontSize="large" />}
        />
      </div>
    </div>
  );
};

const DetailRow = ({ icon, text, divider = false }) => {
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

const ZonesText = (post) => {
  let result = '',
    size = 0;

  post.zones.forEach((zone) => {
    result += zone.description;
    size++;
    if (size !== post.zones.length) result += ', ';
  });

  return result;
};

export default Hire;
