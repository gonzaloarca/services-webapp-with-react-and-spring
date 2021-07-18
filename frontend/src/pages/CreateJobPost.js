import { faImage } from '@fortawesome/free-regular-svg-icons';
import {
  faBriefcase,
  faBusinessTime,
  faCube,
  faEdit,
  faMapMarkerAlt,
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  Button,
  Divider,
  Fab,
  FormControl,
  FormControlLabel,
  IconButton,
  InputLabel,
  makeStyles,
  MenuItem,
  Radio,
  RadioGroup,
  Select,
  Step,
  StepConnector,
  StepLabel,
  Stepper,
  TextField,
  withStyles,
} from '@material-ui/core';
import { Add, Close } from '@material-ui/icons';
import clsx from 'clsx';
import { Form, Formik, useFormikContext } from 'formik';
import React from 'react';
import { useTranslation } from 'react-i18next';
import CircleIcon from '../components/CircleIcon';
import NavBar from '../components/NavBar';
import SectionHeader from '../components/SectionHeader';
import styles from '../styles';
import { themeUtils } from '../theme';
import rateTypeI18nMapper from '../i18n/mappers/rateType';
import createJobPostStyles from './CreateJobPostStyles';

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
    default:
      return <div></div>;
  }
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
            <div>
              All steps completed - you&apos;re finished
              <Button onClick={handleReset} className={classes.button}>
                Reset
              </Button>
            </div>
          ) : (
            <div>
              {getStepContent(activeStep)}

              <div className={classes.actionsContainer}>
                <Button
                  disabled={activeStep === 0}
                  onClick={handleBack}
                  className={classes.button}
                >
                  Back
                </Button>
                <Button
                  variant="contained"
                  color="primary"
                  onClick={handleNext}
                  className={classes.button}
                >
                  {activeStep === steps.length - 1 ? 'Finish' : 'Next'}
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
  const classes = useStyles();
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

const PackageFormItem = ({ index, withDelete }) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const { values, setFieldValue } = useFormikContext();

  const handleDelete = () => {
    setFieldValue('packages', [
      ...values.packages.filter((_, i) => i !== index),
    ]);
  };

  return (
    <div className={classes.packageForm}>
      <div className="flex items-center justify-between">
        <div>
          <FontAwesomeIcon className="text-lg" icon={faCube} />
          <span className={classes.packageHeader}>
            {t('createjobpost.steps.packages.package')}
          </span>
        </div>
        {withDelete && (
          <IconButton onClick={() => handleDelete()}>
            <Close className="text-white" />
          </IconButton>
        )}
      </div>

      <p className="text-sm font-medium opacity-50">
        {t('createjobpost.required')}
      </p>
      <Divider className="bg-white opacity-50 mb-4" />
      <p className={classes.packageLabel}>
        {t('createjobpost.steps.packages.title.label')}
      </p>
      <TextField
        fullWidth
        hiddenLabel
        InputProps={{
          classes: {
            input: classes.packageInput,
          },
        }}
        name={`packages[${index}].title`}
        value={values.packages[index].title}
        placeholder={t('createjobpost.steps.packages.title.placeholder')}
        variant="filled"
        onChange={(e) => {
          console.log(values);
          setFieldValue(`packages[${index}].title`, e.target.value);
        }}
        className="mb-6"
      />
      <p className={classes.packageLabel}>
        {t('createjobpost.steps.packages.description.label')}
      </p>
      <TextField
        fullWidth
        hiddenLabel
        multiline
        rows={3}
        InputProps={{
          classes: {
            input: classes.packageInput,
          },
        }}
        name={`packages[${index}].description`}
        value={values.packages[index].description}
        placeholder={t('createjobpost.steps.packages.description.placeholder')}
        variant="filled"
        onChange={(e) =>
          setFieldValue(`packages[${index}].description`, e.target.value)
        }
        className={classes.packageDescription}
      />
      {/* RateType */}
      <p className={classes.packageLabel}>
        {t('createjobpost.steps.packages.ratetype.label')}
      </p>

      <FormControl className="w-full" component="fieldset">
        <RadioGroup
          row
          name={`packages[${index}].rateType`}
          value={values.packages[index].rateType}
          onChange={(e) =>
            setFieldValue(`packages[${index}].rateType`, e.target.value)
          }
          className={classes.packagesRadioContainer}
        >
          {rateTypes.map(({ id }) => (
            <FormControlLabel
              value={`${id}`}
              key={id}
              label={
                <p className="text-sm font-medium">
                  {t(rateTypeI18nMapper[id])}
                </p>
              }
              control={<Radio />}
            />
          ))}
        </RadioGroup>
      </FormControl>

      <p className={classes.packageLabel}>
        {t('createjobpost.steps.packages.price.label')}
      </p>
      <TextField
        fullWidth
        hiddenLabel
        InputProps={{
          classes: {
            input: classes.packageInput,
          },
        }}
        name={`packages[${index}].price`}
        value={values.packages[index].price}
        placeholder={t('createjobpost.steps.packages.price.placeholder')}
        variant="filled"
        onChange={(e) =>
          setFieldValue(`packages[${index}].price`, e.target.value)
        }
      />
    </div>
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

  return <div className={classes.stepContainer}></div>;
};

const LocationsStepBody = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  return <div className={classes.stepContainer}></div>;
};

export default CreateJobPost;
