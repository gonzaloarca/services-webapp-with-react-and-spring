import { faClock, faImage } from '@fortawesome/free-regular-svg-icons';
import {
  faBriefcase,
  faBusinessTime,
  faClipboardList,
  faCube,
  faCubes,
  faImages,
  faEdit,
  faMapMarkerAlt,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  Button,
  Chip,
  Divider,
  Fab,
  FormControl,
  FormHelperText,
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
import { Add } from '@material-ui/icons';
import clsx from 'clsx';
import { Form, Formik, useFormikContext, ErrorMessage } from 'formik';
import React from 'react';
import { useTranslation } from 'react-i18next';
import CircleIcon from '../components/CircleIcon';
import LocationList from '../components/LocationList';
import NavBar from '../components/NavBar';
import PackageFormItem from '../components/PackageFormItem';
import SectionHeader from '../components/SectionHeader';
import styles from '../styles';
import { themeUtils } from '../theme';
import createJobPostStyles from './CreateJobPostStyles';
import PackageAccordion from '../components/PackageAccordion';
import { Helmet } from 'react-helmet';
import FileInput, {
  checkTypeMultiple,
  checkSizeMultiple,
  checkQuantity,
} from '../components/FileInput';
import * as Yup from 'yup';

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
    3: faCube,
    4: faImage,
    5: faBusinessTime,
    6: faMapMarkerAlt,
    7: faClipboardList,
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
      label: 'createjobpost.steps.packages.label',
      title: 'createjobpost.steps.packages.header',
    },
    {
      label: 'createjobpost.steps.images.label',
      title: 'createjobpost.steps.images.header',
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
          color={themeUtils.colors.aqua}
          title={steps[step].title}
        >
          <PackagesStepBody
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
          color={themeUtils.colors.blue}
          title={steps[step].title}
        >
          <ImagesStepBody
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
    case 5:
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
    case 6:
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

// TODO: esto viene de la API?
const jobTypes = [
  {
    id: 0,
    description: 'Plumbing',
  },
  {
    id: 1,
    description: 'Carpentry',
  },
  {
    id: 2,
    description: 'Painting',
  },
  {
    id: 3,
    description: 'Babysitting',
  },
  {
    id: 4,
    description: 'Electricity',
  },
  {
    id: 5,
    description: 'Other',
  },
];

const rateTypes = [
  {
    id: 0,
    description: 'HOURLY',
  },
  {
    id: 1,
    description: 'ONE_TIME',
  },
  {
    id: 2,
    description: 'TBD',
  },
];

const CreateJobPost = () => {
  const classes = useStyles();
  const globalClasses = useGlobalStyles();
  const { t } = useTranslation();

  const [data, setData] = React.useState({
    category: '',
    title: '',
    packages: [{ title: '', description: '', rateType: '', price: '' }],
    images: '',
    hours: '',
    locations: '',
  });

  const [activeStep, setActiveStep] = React.useState(0);
  const steps = getSteps();

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

  const makeRequest = (newData) => {
    console.log(newData);
    //TODO: Registrar con la API
    //TODO: Redirigir a una vista de "Success"
  };

  const formRef = React.useRef();

  const handleSubmit = () => {
    if (formRef.current) {
      formRef.current.handleSubmit();
    }
  };

  // const handleReset = () => {
  //   setActiveStep(0);
  // };

  return (
    <>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.publish') })}
        </title>
      </Helmet>
      <NavBar currentSection={'/create-job-post'} />

      <div className={globalClasses.contentContainerTransparent}>
        <SectionHeader sectionName={t('createjobpost.header')} />
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
          {activeStep === steps.length ? (
            <div>Submitting form...</div>
          ) : (
            <div>
              {getStepContent(activeStep, formRef, handleNext, data)}

              <div className={classes.actionsContainer}>
                <Button
                  disabled={activeStep === 0}
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
                >
                  {activeStep === steps.length - 1
                    ? t('createjobpost.publish')
                    : t('createjobpost.next')}
                </Button>
              </div>
            </div>
          )}
        </div>
      </div>
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
                value={values.category}
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

const PackagesStepBody = ({ formRef, handleNext, data }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  const handleSubmit = (values) => {
    handleNext(values);
  };

  const validationSchema = Yup.object({
    packages: Yup.array().of(
      Yup.object().shape({
        title: Yup.string()
          .required(t('validationerror.required'))
          .max(100, t('validationerror.maxlength', { length: 100 })),
        description: Yup.string()
          .required(t('validationerror.required'))
          .max(100, t('validationerror.maxlength', { length: 100 })),
        rateType: Yup.number().required(t('validationerror.required')),
        price: Yup.number().when('rateType', {
          is: 2,
          otherwise: Yup.number().required(t('validationerror.required')),
        }),
      })
    ),
  });

  return (
    <div className={classes.packagesContainer}>
      <Formik
        innerRef={formRef}
        initialValues={data}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        enableReinitialize={true}
      >
        {(props) => (
          <Form>
            <PackagesForm />
          </Form>
        )}
      </Formik>
    </div>
  );
};

const PackagesForm = () => {
  const { values, setFieldValue } = useFormikContext();

  return (
    <>
      {values.packages.map((pack, index) => (
        <div className="mb-4" key={index}>
          <PackageFormItem
            withDelete={values.packages.length > 1}
            index={index}
          />
        </div>
      ))}
      <div className="flex justify-end pt-4">
        <Fab
          style={{ backgroundColor: themeUtils.colors.lightBlue }}
          onClick={() => {
            setFieldValue('packages', [
              ...values.packages,
              { title: '', description: '', rateType: '', price: '' },
            ]);
          }}
        >
          <Add />
        </Fab>
      </div>
    </>
  );
};

const ImagesStepBody = ({ formRef, handleNext, data }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  const handleSubmit = (values) => {
    handleNext(values);
  };

  const validationSchema = Yup.object({
    images: Yup.mixed()
      .test(
        'is-correct-type',
        t('validationerror.multipleimages.type'),
        checkTypeMultiple
      )
      .test(
        'is-correct-size',
        t('validationerror.multipleimages.size', { size: 2 }),
        checkSizeMultiple
      )
      .test(
        'is-correct-quantity',
        t('validationerror.multipleimages.quantity', { count: 5 }),
        checkQuantity
      ),
  });

  return (
    <div className={classes.stepContainer}>
      <Formik
        innerRef={formRef}
        initialValues={data}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        enableReinitialize={true}
      >
        {(props) => (
          <Form>
            <FileInput multiple fileName="images" />
            <div className="font-thin text-sm mt-3">
              {t('createjobpost.steps.images.disclaimer', {
                count: 5,
                size: 2,
              })}
            </div>
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

  const category = jobTypes[data.category]; //TODO obtener el objeto category aca
  const packages = [];
  const locations = [];

  data.packages.forEach((pack) => {
    // TODO: pack.rateType es solo el ID al salir del Form, ver cómo obtener el objeto de la API
    let rateType = rateTypes[pack.rateType];
    packages.push({
      title: pack.title,
      description: pack.description,
      price: pack.price,
      rateType: rateType,
    });
  });

  data.locations.forEach((location) => {
    // TODO location es solo el id de la zona, obtener el objeto de la API aca
    locations.push({
      id: 0,
      description: 'Recoleta',
    });
  });

  const handleSubmit = (values) => {
    // Reemplazo los valores por los convertidos antes de enviarlo TODO: esta bien?
    values.category = category;
    values.packages = packages;
    values.locations = locations;
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

      {/* Paquetes */}
      <div className={classes.summaryRow}>
        <div className={classes.summaryIcon}>
          <FontAwesomeIcon icon={faCubes} className="text-2xl" />
        </div>
        <div
          className={clsx(
            classes.summaryFieldContainer,
            classes.packagesSummary
          )}
        >
          <p>{t('createjobpost.steps.packages.label')}</p>
          <div className="p-3">
            {packages.map((pack, index) => (
              <PackageAccordion key={index} pack={pack} isHireable={false} />
            ))}
          </div>
        </div>
      </div>

      {/* Imagenes */}
      {data.images && Array.from(data.images).length > 0 && (
        <div className={classes.summaryRow}>
          <div className={classes.summaryIcon}>
            <FontAwesomeIcon icon={faImages} className="text-2xl" />
          </div>
          <div
            className={clsx(
              classes.summaryFieldContainer,
              classes.imagesSummary
            )}
          >
            <p>{t('createjobpost.steps.images.label')}</p>
            <div className={classes.imageSlideshow}>
              {Array.from(data.images).map((image, index) => (
                <img
                  className={classes.image}
                  src={
                    image === ''
                      ? `${process.env.PUBLIC_URL}/img/defaultavatar.svg`
                      : URL.createObjectURL(image)
                  }
                  key={index}
                  alt=""
                />
              ))}
            </div>
          </div>
        </div>
      )}

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

export default CreateJobPost;
