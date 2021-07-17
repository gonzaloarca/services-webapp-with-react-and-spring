import {
  FormControl,
  IconButton,
  InputLabel,
  MenuItem,
  Select,
  TextField,
  FormHelperText,
} from '@material-ui/core';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import { LocationOn, Search } from '@material-ui/icons';
import React from 'react';
import { useTranslation } from 'react-i18next';
import { themeUtils } from '../theme';
import HeroStyles from './HeroStyles';
import * as Yup from 'yup';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import clsx from 'clsx';
import { Link as RouterLink } from 'react-router-dom';

const useStyles = makeStyles(HeroStyles);

const steps = [
  {
    iconSrc: `${process.env.PUBLIC_URL}/img/location-1.svg`,
    instruction: 'home.steps.location',
    number: 1,
    numberColor: themeUtils.colors.orange,
    backgroundColor: themeUtils.colors.darkBlue,
  },
  {
    iconSrc: `${process.env.PUBLIC_URL}/img/search1.svg`,
    instruction: 'home.steps.search',
    number: 2,
    numberColor: themeUtils.colors.blue,
    backgroundColor: themeUtils.colors.yellow,
  },
  {
    iconSrc: `${process.env.PUBLIC_URL}/img/hire-1.svg`,
    instruction: 'home.steps.hire',
    number: 3,
    numberColor: themeUtils.colors.yellow,
    backgroundColor: themeUtils.colors.orange,
  },
];

const Hero = () => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div className={classes.heroContainer}>
      <div className={classes.heroContent}>
        <h3 className={classes.heroText}>{t('home.herotext')}</h3>
        <HeroSearchBar />
      </div>
      <img
        className={classes.heroBackground}
        src={`${process.env.PUBLIC_URL}/img/landingbg1.svg`}
        alt=""
      />
    </div>
  );
};

const HeroSearchBar = () => {
  const classes = useStyles();
  const { t } = useTranslation();
  const initialValues = {
    zone: '',
    query: '',
  };

  const validationSchema = Yup.object({
    zone: Yup.string()
      .required(t('validationerror.zone'))
      .matches(/^[0-9]+$/, t('validationerror.zone')),
    query: Yup.string(),
  });

  const onSubmit = (values, props) => {
    console.log('SUBMITTING');
    console.log(values); //TODO: REDIRIGIR A SEARCH CON QUERY
  };

  return (
    <Formik
      initialValues={initialValues}
      validationSchema={validationSchema}
      onSubmit={onSubmit}
    >
      {({ values }) => (
        <Form
		className="flex flex-col items-center w-full h-32">
          <div className={clsx("h-16", classes.searchBarContainer)}>
            <div className={classes.locationContent}>
              <LocationOn className={classes.locationIcon} />
              <CustomFormControl
                className={classes.locationForm}
                variant="filled"
              >
                <InputLabel
                  id="location-select-label"
                  className={classes.selectLabel}
                >
                  {t('home.location')}
                </InputLabel>
                <Field
                  as={CustomSelect}
                  name="zone"
                  className={classes.locationSelect}
                  inputProps={{
                    classes: {
                      icon: classes.locationSelectIcon,
                    },
                  }}
                  labelId="location-select-label"
                  id="location-select"
                  disableUnderline={true}
                  value={values.zone !== undefined ? values.zone : ''}
                >
                  <MenuItem value="">
                    <em>None</em>
                  </MenuItem>
                  <MenuItem value="0">La Boca</MenuItem>
                  <MenuItem value="1">Palermo</MenuItem>
                  <MenuItem value="2">Recoleta</MenuItem>
                </Field>
              </CustomFormControl>
            </div>

            <Field
              as={CustomSearchBar}
              InputProps={{
                disableUnderline: true,
              }}
              hiddenLabel
              placeholder={t('home.searchJob')}
              variant="filled"
              margin="none"
              name="query"
              helperText={<ErrorMessage name="query" />}
              onSubmit={(e) => {
                onSubmit({ values });
              }}
            />
            <IconButton type="submit" className={classes.searchButton}>
              <Search />
            </IconButton>
          </div>
          <FormHelperText className={classes.zoneErrorMessage}>
            <ErrorMessage name="zone"/>
          </FormHelperText>
        </Form>
      )}
    </Formik>
  );
};

const CustomErrorMessage = () => {
  const classes = useStyles();

  return <p className={classes.zoneErrorMessage}></p>;
};

export const HeroSteps = () => {
  const classes = useStyles();

  return (
    <div className={classes.stepsContainer}>
      {steps.map((i) => (
        <HeroStep
          key={i.number}
          numberColor={i.numberColor}
          backgroundColor={i.backgroundColor}
          iconSrc={i.iconSrc}
          instruction={i.instruction}
          number={i.number}
        />
      ))}
    </div>
  );
};

const HeroStep = ({
  iconSrc,
  instruction,
  number,
  numberColor,
  backgroundColor,
}) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div
      className={classes.stepContainer}
      style={{ backgroundColor: backgroundColor }}
    >
      <div
        className={classes.stepNumber}
        style={{ backgroundColor: numberColor }}
      >
        {number}
      </div>
      <img className={'m-2'} src={iconSrc} alt="" />
      <p>{t(instruction)}</p>
    </div>
  );
};

const CustomSelect = withStyles((theme) => ({
  root: {
    color: 'white',
    backgroundColor: '#2C345B',
    borderRadius: '50px',
    border: 'none',
    '&:focus': {
      color: 'white',
      backgroundColor: '#2C345B',
      borderRadius: 50,
    },
    input: {},
  },
}))(Select);

const CustomSearchBar = withStyles((theme) => ({
  root: {
    flex: 3,
    height: '100%',
    '& .MuiFilledInput-root': {
      paddingRight: 30,
      fontWeight: 500,
      backgroundColor: 'white',
      borderRadius: '0 100px 100px 0',
      height: '100%',
    },
  },
}))(TextField);

const CustomFormControl = withStyles((theme) => ({
  root: {
    '& .MuiFilledInput-root': {
      height: '100%',
      color: 'white',
      borderRadius: '50px',
    },
  },
}))(FormControl);

export default Hero;
