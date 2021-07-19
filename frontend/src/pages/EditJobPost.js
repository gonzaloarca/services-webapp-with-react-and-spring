import { faClock } from '@fortawesome/free-regular-svg-icons';
import {
  faBriefcase,
  faBusinessTime,
  faClipboardList,
  faEdit,
  faMapMarkerAlt,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  Button,
  Chip,
  Divider,
  FormControl,
  FormHelperText,
  Grid,
  InputLabel,
  makeStyles,
  MenuItem,
  Select,
  Step,
  StepConnector,
  StepLabel,
  Stepper,
  TextField,
  withStyles,
} from '@material-ui/core';
import clsx from 'clsx';
import { Form, Formik, ErrorMessage } from 'formik';
import React, { useContext, useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import CircleIcon from '../components/CircleIcon';
import LocationList from '../components/LocationList';
import NavBar from '../components/NavBar';
import SectionHeader from '../components/SectionHeader';
import styles from '../styles';
import { themeUtils } from '../theme';
import createJobPostStyles from './CreateJobPostStyles';
import { Helmet } from 'react-helmet';
import * as Yup from 'yup';
import { useJobPosts, useUser } from '../hooks';
import { extractLastIdFromURL } from '../utils/urlUtils';
import { UserContext, ConstantDataContext } from '../context';
import { Skeleton } from '@material-ui/lab';

const HirenetConnector = withStyles({
  alternativeLabel: {
    top: 22,
  },
  active: {
    '& $line': {
      backgroundImage:
        'linear-gradient(to right, ' +
        themeUtils.colors.blue +
        ' 25%, ' +
        themeUtils.colors.yellow +
        ' 75%)',
      backgroundColor: themeUtils.colors.yellow,
    },
  },
  completed: {
    '& $line': {
      backgroundColor: themeUtils.colors.blue,
    },
  },
  line: {
    height: 3,
    border: 0,
    backgroundColor: '#ccc',
    borderRadius: 1,
  },
})(StepConnector);

const useHirenetStepIconStyles = makeStyles({
  root: {
    backgroundColor: '#ccc',
    zIndex: 1,
    color: '#fff',
    width: 50,
    height: 50,
    display: 'flex',
    borderRadius: '50%',
    justifyContent: 'center',
    alignItems: 'center',
  },
  active: {
    backgroundColor: themeUtils.colors.yellow,
    boxShadow: '0 4px 10px 0 rgba(0,0,0,.25)',
  },
  completed: {
    backgroundColor: themeUtils.colors.blue,
  },
});

const HirenetStepIcon = (props) => {
  const classes = useHirenetStepIconStyles();
  const { active, completed } = props;

  const icons = {
    1: faBriefcase,
    2: faEdit,
    3: faBusinessTime,
    4: faMapMarkerAlt,
    5: faClipboardList,
  };

  return (
    <div
      className={clsx(classes.root, {
        [classes.active]: active,
        [classes.completed]: completed,
      })}
    >
      <FontAwesomeIcon className="text-lg" icon={icons[String(props.icon)]} />
    </div>
  );
};

const useGlobalStyles = makeStyles(styles);
const useStyles = makeStyles(createJobPostStyles);

const getSteps = () => {
  return [
    {
      label: 'createjobpost.steps.category.label',
      title: 'createjobpost.steps.category.header',
    },
    {
      label: 'createjobpost.steps.jobtitle.label',
      title: 'createjobpost.steps.jobtitle.header',
    },
    {
      label: 'createjobpost.steps.hours.label',
      title: 'createjobpost.steps.hours.header',
    },
    {
      label: 'createjobpost.steps.locations.label',
      title: 'createjobpost.steps.locations.header',
    },
    {
      label: 'createjobpost.steps.summary.label',
      title: 'createjobpost.steps.summary.header',
    },
  ];
};

const getStepContent = (step, formRef, handleNext, data) => {
  const steps = getSteps();

  switch (step) {
    case 0:
      return (
        <PublishStep
          step={step + 1}
          color={themeUtils.colors.orange}
          title={steps[step].title}
        >
          <CategoryStepBody
            formRef={formRef}
            handleNext={handleNext}
            data={data}
          />
        </PublishStep>
      );
    case 1:
      return (
        <PublishStep
          step={step + 1}
          color={themeUtils.colors.lightBlue}
          title={steps[step].title}
        >
          <TitleStepBody
            formRef={formRef}
            handleNext={handleNext}
            data={data}
          />
        </PublishStep>
      );
    case 2:
      return (
        <PublishStep
          step={step + 1}
          color={themeUtils.colors.orange}
          title={steps[step].title}
        >
          <HoursStepBody
            formRef={formRef}
            handleNext={handleNext}
            data={data}
          />
        </PublishStep>
      );
    case 3:
      return (
        <PublishStep
          step={step + 1}
          color={themeUtils.colors.aqua}
          title={steps[step].title}
        >
          <LocationsStepBody
            formRef={formRef}
            handleNext={handleNext}
            data={data}
          />
        </PublishStep>
      );
    case 4:
      return (
        <PublishStep
          step={step + 1}
          color={themeUtils.colors.blue}
          title={steps[step].title}
        >
          <JobSummary formRef={formRef} handleNext={handleNext} data={data} />
        </PublishStep>
      );
    default:
      return <div></div>;
  }
};

const EditJobPost = ({ match, history }) => {
  const classes = useStyles();
  const globalClasses = useGlobalStyles();
  const { t } = useTranslation();

  const { getJobPostById, editJobPost } = useJobPosts();
  const { getUserById } = useUser();
  const { currentUser } = useContext(UserContext);

  const [loading, setLoading] = useState(true);
  const [postId, setPostId] = useState(match.params.id);
  const [jobPost, setJobPost] = useState(null);
  const [proUser, setProUser] = useState(null);

  const loadJobPost = async () => {
    try {
      const jobPost = await getJobPostById(postId);
      setJobPost(jobPost);
    } catch (e) {
      history.push('/error');
    }
  };

  const loadProUser = async () => {
    try {
      const proId = extractLastIdFromURL(jobPost.professional);
      const pro = await getUserById(proId);
      setProUser(pro);
    } catch (e) {
      history.push('/error');
    }
  };

  useEffect(() => {
    if (jobPost) {
      loadProUser();
    }
  }, [jobPost]);

  const [data, setData] = React.useState({
    category: '',
    title: '',
    hours: '',
    locations: '',
  });

  useEffect(() => {
    if (jobPost && proUser) {
      if (!currentUser || currentUser.id !== proUser.id)
        history.replace(`/job/${jobPost.id}`);

      setData({
        category: jobPost.jobType.id,
        title: jobPost.title,
        hours: jobPost.availableHours,
        locations: jobPost.zones.map((zone) => zone.id),
      });

      setLoading(false);
    }
  }, [jobPost, proUser]);

  useEffect(() => {
    loadJobPost();
  }, [postId]);

  const [activeStep, setActiveStep] = React.useState(0);
  const steps = getSteps();

  const [disableSubmit, setDisableSubmit] = React.useState(false);

  const handleNext = (newData, final = false) => {
    if (final) {
      makeRequest(newData);
      return;
    }
    setData((prev) => ({ ...prev, ...newData }));
    setActiveStep((prevActiveStep) => prevActiveStep + 1);
  };

  const handleBack = (newData) => {
    setData((prev) => ({ ...prev, ...newData }));
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };

  const makeRequest = async (newData) => {
    setDisableSubmit(true);
    try {
      await editJobPost(newData, jobPost.id);
      history.push(`/job/${jobPost.id}/success/edit`);
    } catch (e) {
      history.push('/error');
    }
  };

  const formRef = React.useRef();

  const handleSubmit = () => {
    if (formRef.current) {
      formRef.current.handleSubmit();
    }
  };

  return (
    <>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.publish') })}
        </title>
      </Helmet>
      <NavBar currentSection={'/create-job-post'} />
      {loading ? (
        <Grid container spacing={3} className="mt-10">
          <Grid item md={12} className="flex justify-center">
            <Skeleton item md={12} variant="rect" width={1000} height={200} />
          </Grid>
          <Grid item md={12} className="flex justify-center">
            <Skeleton item md={12} variant="rect" width={1000} height={200} />
          </Grid>
        </Grid>
      ) : (
        <div className={globalClasses.contentContainerTransparent}>
          <SectionHeader sectionName={t('editjobpost')} />
          <Stepper
            className={classes.stepperContainer}
            alternativeLabel
            activeStep={activeStep}
            connector={<HirenetConnector />}
          >
            {steps.map(({ label }) => (
              <Step key={label}>
                <StepLabel StepIconComponent={HirenetStepIcon}>
                  <p className="text-sm font-semibold">{t(label)}</p>
                </StepLabel>
              </Step>
            ))}
          </Stepper>
          <div>
            <div>
              {getStepContent(activeStep, formRef, handleNext, data)}

              <div className={classes.actionsContainer}>
                <Button
                  disabled={activeStep === 0 || disableSubmit}
                  onClick={() => handleBack(data)}
                  className={classes.button}
                >
                  {t('createjobpost.back')}
                </Button>
                <Button
                  variant="contained"
                  color="primary"
                  onClick={handleSubmit}
                  className={classes.button}
                  disabled={disableSubmit}
                >
                  {activeStep === steps.length - 1
                    ? t('editpackage.submit')
                    : t('createjobpost.next')}
                </Button>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

const PublishStep = ({ step, color, title, children }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className={classes.stepContainer}>
      <div className={classes.stepHeader}>
        <CircleIcon className="mr-2" size={40} color={color}>
          <p className={classes.stepCounter}>{step}</p>
        </CircleIcon>
        <p className={classes.stepTitle}>{t(title)}</p>
      </div>
      <Divider className="my-3 aa" />
      {children}
    </div>
  );
};

const CategoryStepBody = ({ formRef, handleNext, data }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  const { categories: jobTypes } = useContext(ConstantDataContext);

  const handleSubmit = (values) => {
    handleNext(values);
  };

  const validationSchema = Yup.object({
    category: Yup.number().required(t('validationerror.required')),
  });

  return (
    <div className="py-10">
      <Formik
        innerRef={formRef}
        initialValues={data}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        enableReinitialize={true}
      >
        {({ values, setFieldValue }) => (
          <Form>
            <FormControl className={classes.input} variant="filled">
              <InputLabel id="category-select-label">
                {t('createjobpost.steps.category.label')}
              </InputLabel>
              <Select
                labelId="category-select-label"
                name="category"
                value={`${values.category}`}
                onChange={(e) => setFieldValue('category', e.target.value)}
              >
                <MenuItem value="">
                  <em>None</em>
                </MenuItem>
                {jobTypes.map(({ id, description }) => (
                  <MenuItem key={id} value={id}>
                    {description}
                  </MenuItem>
                ))}
              </Select>
              <FormHelperText>
                <ErrorMessage name="category"></ErrorMessage>
              </FormHelperText>
            </FormControl>
          </Form>
        )}
      </Formik>
    </div>
  );
};

const TitleStepBody = ({ formRef, handleNext, data }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  const handleSubmit = (values) => {
    handleNext(values);
  };

  const validationSchema = Yup.object({
    title: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 })),
  });

  return (
    <div className="py-10">
      <Formik
        innerRef={formRef}
        initialValues={data}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        enableReinitialize={true}
      >
        {({ values, setFieldValue }) => (
          <Form>
            <TextField
              label={t('createjobpost.steps.jobtitle.label')}
              className={classes.input}
              variant="filled"
              value={values.title}
              onChange={(e) => setFieldValue('title', e.target.value)}
              name="title"
              helperText={<ErrorMessage name="title"></ErrorMessage>}
            />
          </Form>
        )}
      </Formik>
    </div>
  );
};

const HoursStepBody = ({ formRef, handleNext, data }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  const handleSubmit = (values) => {
    handleNext(values);
  };

  const validationSchema = Yup.object({
    hours: Yup.string()
      .required(t('validationerror.required'))
      .max(100, t('validationerror.maxlength', { length: 100 })),
  });

  return (
    <div className="py-10">
      <Formik
        innerRef={formRef}
        initialValues={data}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        enableReinitialize={true}
      >
        {({ values, setFieldValue }) => (
          <Form>
            <TextField
              multiline
              rows={3}
              variant="filled"
              placeholder={t('createjobpost.steps.hours.label')}
              className={classes.input}
              onChange={(e) => setFieldValue('hours', e.target.value)}
              name="hours"
              helperText={<ErrorMessage name="hours"></ErrorMessage>}
              value={values.hours}
            />
          </Form>
        )}
      </Formik>
    </div>
  );
};

const LocationsStepBody = ({ formRef, handleNext, data }) => {
  const { t } = useTranslation();

  const handleSubmit = (values) => {
    handleNext(values);
  };

  const validationSchema = Yup.object({
    locations: Yup.array()
      .min(1, t('validationerror.locations'))
      .of(Yup.number().required(t('validationerror.required'))),
  });

  return (
    <div className="py-10">
      <Formik
        innerRef={formRef}
        initialValues={data}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        enableReinitialize={true}
      >
        {({ values, setFieldValue }) => (
          <Form>
            <LocationList
              multiple
              name="locations"
              initial={values.locations}
              setFieldValue={setFieldValue}
            />
            <FormHelperText className="flex justify-center">
              <ErrorMessage name="locations"></ErrorMessage>
            </FormHelperText>
          </Form>
        )}
      </Formik>
    </div>
  );
};

const JobSummary = ({ formRef, handleNext, data }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  const { categories: jobTypes, zones } = useContext(ConstantDataContext);

  const category = jobTypes[data.category];
  const locations = [];

  data.locations.forEach((location) => {
    locations.push(zones.find((zone) => zone.id === location));
  });

  const handleSubmit = (values) => {
    handleNext(values, true);
  };

  return (
    <div key={0}>
      <Formik
        innerRef={formRef}
        initialValues={data}
        onSubmit={handleSubmit}
        enableReinitialize={true}
      />
      {/* Categoria */}
      <div className={classes.summaryRow}>
        <div className={classes.summaryIcon}>
          <FontAwesomeIcon icon={faBriefcase} className="text-2xl" />
        </div>
        <div
          className={clsx(
            classes.summaryFieldContainer,
            classes.categorySummary
          )}
        >
          <p>{t('createjobpost.steps.category.label')}</p>
          <div className={classes.summaryValue}>{category.description}</div>
        </div>
      </div>

      {/* Titulo */}
      <div className={classes.summaryRow}>
        <div className={classes.summaryIcon}>
          <FontAwesomeIcon icon={faEdit} className="text-2xl" />
        </div>
        <div
          className={clsx(classes.summaryFieldContainer, classes.titleSummary)}
        >
          <p>{t('createjobpost.steps.jobtitle.label')}</p>
          <div className={classes.summaryValue}>{data.title}</div>
        </div>
      </div>

      {/* Horarios */}
      <div className={classes.summaryRow}>
        <div className={classes.summaryIcon}>
          <FontAwesomeIcon icon={faClock} className="text-2xl" />
        </div>
        <div
          className={clsx(classes.summaryFieldContainer, classes.hoursSummary)}
        >
          <p>{t('createjobpost.steps.hours.label')}</p>
          <div className={clsx(classes.summaryValue, 'text-sm')}>
            {data.hours}
          </div>
        </div>
      </div>

      {/* Ubicaciones */}
      <div className={classes.summaryRow}>
        <div className={classes.summaryIcon}>
          <FontAwesomeIcon icon={faMapMarkerAlt} className="text-2xl" />
        </div>
        <div
          className={clsx(
            classes.summaryFieldContainer,
            classes.locationsSummary
          )}
        >
          <p>{t('createjobpost.steps.locations.label')}</p>
          <div className={classes.summaryValue}>
            {locations.map(({ description }, index) => (
              <Chip
                label={description}
                className="m-1 bg-white"
                icon={
                  <FontAwesomeIcon className="text-lg" icon={faMapMarkerAlt} />
                }
                key={index}
              />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default EditJobPost;
