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
import { Add, Adjust, LocationOn } from '@material-ui/icons';
import clsx from 'clsx';
import { Form, Formik, useFormikContext } from 'formik';
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

const getStepContent = (step) => {
  const steps = getSteps();

  switch (step) {
    case 0:
      return (
        <PublishStep
          step={step + 1}
          color={themeUtils.colors.orange}
          title={steps[step].title}
        >
          <CategoryStepBody />
        </PublishStep>
      );
    case 1:
      return (
        <PublishStep
          step={step + 1}
          color={themeUtils.colors.lightBlue}
          title={steps[step].title}
        >
          <TitleStepBody />
        </PublishStep>
      );
    case 2:
      return (
        <PublishStep
          step={step + 1}
          color={themeUtils.colors.aqua}
          title={steps[step].title}
        >
          <PackagesStepBody />
        </PublishStep>
      );
    case 3:
      return (
        <PublishStep
          step={step + 1}
          color={themeUtils.colors.blue}
          title={steps[step].title}
        >
          <ImagesStepBody />
        </PublishStep>
      );
    case 4:
      return (
        <PublishStep
          step={step + 1}
          color={themeUtils.colors.orange}
          title={steps[step].title}
        >
          <HoursStepBody />
        </PublishStep>
      );
    case 5:
      return (
        <PublishStep
          step={step + 1}
          color={themeUtils.colors.aqua}
          title={steps[step].title}
        >
          <LocationsStepBody />
        </PublishStep>
      );
    case 6:
      return (
        <PublishStep
          step={step + 1}
          color={themeUtils.colors.blue}
          title={steps[step].title}
        >
          <JobSummary form={mockedForm} />
        </PublishStep>
      );
    default:
      return <div></div>;
  }
};

const mockedForm = {
  title: 'Duis est eiusmod est ea nostrud consequat.',
  category: {
    id: 0,
    description: 'Plumbing',
  },
  packages: [
    {
      title: 'Lorem ipsum',
      description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      price: 1000,
      rateType: {
        id: 0,
        description: 'HOURLY',
      },
    },
  ],
  images: [
    process.env.PUBLIC_URL + '/img/plumbing.jpeg',
    process.env.PUBLIC_URL + '/img/babysitting.jpeg',
    process.env.PUBLIC_URL + '/img/carpentry.jpeg',
  ],
  hours:
    'Excepteur esse in labore anim irure velit magna sit id qui. Culpa do magna officia proident.',
  locations: [
    {
      id: 0,
      description: 'Recoleta',
    },
    {
      id: 1,
      description: 'Recoleta',
    },
    {
      id: 2,
      description: 'Recoleta',
    },
    {
      id: 3,
      description: 'Recoleta',
    },
    {
      id: 4,
      description: 'Recoleta',
    },
    {
      id: 5,
      description: 'Recoleta',
    },
  ],
};

const CreateJobPost = () => {
  const classes = useStyles();
  const globalClasses = useGlobalStyles();
  const { t } = useTranslation();
  const [activeStep, setActiveStep] = React.useState(0);
  const steps = getSteps();

  const handleNext = () => {
    setActiveStep((prevActiveStep) => prevActiveStep + 1);
  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };

  const handleReset = () => {
    setActiveStep(0);
  };

  return (
    <>
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
              {getStepContent(activeStep)}

              <div className={classes.actionsContainer}>
                <Button
                  disabled={activeStep === 0}
                  onClick={handleBack}
                  className={classes.button}
                >
                  {t('createjobpost.back')}
                </Button>
                <Button
                  variant="contained"
                  color="primary"
                  onClick={handleNext}
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

const CategoryStepBody = () => {
  const classes = useStyles();
  const { t } = useTranslation();
  const [category, setCategory] = React.useState('');
  const handleChange = (newValue) => {
    setCategory(newValue);
  };
  return (
    <div className="py-10">
      <FormControl className={classes.input} variant="filled">
        <InputLabel id="category-select-label">
          {t('createjobpost.steps.category.label')}
        </InputLabel>
        <Select
          labelId="category-select-label"
          value={category}
          onChange={(e) => handleChange(e.target.value)}
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
      </FormControl>
    </div>
  );
};

const TitleStepBody = () => {
  const classes = useStyles();
  const { t } = useTranslation();
  const [title, setTitle] = React.useState('');
  const handleChange = (newValue) => {
    setTitle(newValue);
  };

  return (
    <div className="py-10">
      <TextField
        label={t('createjobpost.steps.jobtitle.label')}
        className={classes.input}
        variant="filled"
        value={title}
        onChange={(e) => handleChange(e.target.value)}
      />
    </div>
  );
};

const PackagesStepBody = () => {
  const classes = useStyles();

  return (
    <div className={classes.packagesContainer}>
      <Formik
        initialValues={{
          packages: [{ title: '', description: '', rateType: '', price: '' }],
        }}
        onSubmit={() => {}}
      >
        <Form>
          <PackagesForm />
        </Form>
      </Formik>
    </div>
  );
};

const PackagesForm = () => {
  const { values, setFieldValue } = useFormikContext();

  return (
    <>
      {values.packages.map((pack, index) => (
        <div className="mb-4">
          <PackageFormItem
            withDelete={values.packages.length > 1}
            index={index}
            key={index}
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

const ImagesStepBody = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  return <div className={classes.stepContainer}></div>;
};

const HoursStepBody = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className="py-10">
      <TextField
        multiline
        rows={3}
        variant="filled"
        placeholder={t('createjobpost.steps.hours.label')}
        className={classes.input}
      />
    </div>
  );
};

const LocationsStepBody = () => {
  return (
    <div className="py-10">
      <LocationList multiple />
    </div>
  );
};

const JobSummary = ({ form }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <>
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
          <div className={classes.summaryValue}>
            {form.category.description}
          </div>
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
          <div className={classes.summaryValue}>{form.title}</div>
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
            {form.packages.map((pack, index) => (
              <PackageAccordion key={index} pack={pack} isHireable={false} />
            ))}
          </div>
        </div>
      </div>

      {/* Imagenes */}
      {form.images && form.images.length > 0 && (
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
              {form.images.map((image, index) => (
                <img className={classes.image} src={image} key={index} alt="" />
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
            {form.hours}
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
            {form.locations.map(({ description }) => (
              <Chip
                label={description}
                className="m-1 bg-white"
                icon={
                  <FontAwesomeIcon className="text-lg" icon={faMapMarkerAlt} />
                }
              />
            ))}
          </div>
        </div>
      </div>
    </>
  );
};

export default CreateJobPost;
