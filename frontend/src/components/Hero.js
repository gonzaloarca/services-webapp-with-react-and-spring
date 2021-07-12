import {
  FormControl,
  IconButton,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from '@material-ui/core';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import { LocationOn, Search } from '@material-ui/icons';
import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { themeUtils } from '../theme';
import HeroStyles from './HeroStyles';

const useStyles = makeStyles(HeroStyles);

const steps = [
  {
    iconSrc: '/img/location-1.svg',
    instruction: 'home.steps.location',
    number: 1,
    numberColor: themeUtils.colors.orange,
    backgroundColor: themeUtils.colors.darkBlue,
  },
  {
    iconSrc: '/img/search1.svg',
    instruction: 'home.steps.search',
    number: 2,
    numberColor: themeUtils.colors.blue,
    backgroundColor: themeUtils.colors.yellow,
  },
  {
    iconSrc: '/img/hire-1.svg',
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
        src="/img/landingbg1.svg"
        alt=""
      />
    </div>
  );
};

const HeroSearchBar = () => {
  const [location, setLocation] = useState('');
  const classes = useStyles();
  const { t } = useTranslation();

  const handleLocationChange = (newLocation) => setLocation(newLocation);

  return (
    <div className={classes.searchBarContainer}>
      <div className={classes.locationContent}>
        <LocationOn className={classes.locationIcon} />
        <CustomFormControl className={classes.locationForm} variant="filled">
          <InputLabel
            id="location-select-label"
            className={classes.selectLabel}
          >
            {t('home.location')}
          </InputLabel>
          <CustomSelect
            className={classes.locationSelect}
            inputProps={{
              classes: {
                icon: classes.locationSelectIcon,
              },
            }}
            labelId="location-select-label"
            id="location-select"
            value={location}
            onChange={(e) => handleLocationChange(e.target.value)}
            disableUnderline={true}
          >
            <MenuItem value="">
              <em>None</em>
            </MenuItem>
            <MenuItem value={0}>La Boca</MenuItem>
            <MenuItem value={1}>Palermo</MenuItem>
            <MenuItem value={2}>Recoleta</MenuItem>
          </CustomSelect>
        </CustomFormControl>
      </div>

      <CustomSearchBar
        InputProps={{
          disableUnderline: true,
        }}
        hiddenLabel
        placeholder={t('home.searchJob')}
        variant="filled"
        margin="none"
      />
      <IconButton type="submit" className={classes.searchButton}>
        <Search />
      </IconButton>
    </div>
  );
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
