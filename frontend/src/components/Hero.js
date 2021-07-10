import { FormControl, InputLabel, MenuItem, Select } from '@material-ui/core';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';

const useStyles = makeStyles((theme) => ({
  heroBackground: {
    width: '100%',
    height: '100%',
    objectFit: 'cover',
  },
  heroContainer: {
    width: '100vw',
    height: '100vh',
    position: 'relative',
  },
  heroContent: {
    position: 'absolute',
    width: '100%',
    height: '100%',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    zIndex: 2,
  },
  heroText: {
    margin: '0 30px 20px',
    color: 'white',
    fontSize: '2rem',
    textAlign: 'center',
  },
  searchBarContainer: {
    height: 70,
    borderRadius: 100,
    backgroundColor: 'transparent',
    backdropFilter: 'blur(10px) brightness(60%)',
    width: '80%',
    maxWidth: 950,
  },
  locationSelect: {
    width: '30%',
  },
}));

const Hero = () => {
  const classes = useStyles();
  const { t } = useTranslation();
  const [offsetY, setOffsetY] = useState(0);
  const handleScroll = () => setOffsetY(window.pageYOffset);

  useEffect(() => {
    window.addEventListener('scroll', handleScroll);

    return () => window.removeEventListener('scroll', handleScroll);
  });

  return (
    <div className={classes.heroContainer}>
      <div className={classes.heroContent}>
        <h3 className={classes.heroText}>{t('home.herotext')}</h3>
        <HeroSearchBar />
      </div>
      <img
        className={classes.heroBackground}
        style={{ transform: `translateY(${offsetY * 0.5}px)` }}
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
      <div>
        <FormControl className={classes.locationSelect} variant="filled">
          <InputLabel id="location-select-label">
            {t('home.location')}
          </InputLabel>
          <CustomSelect
            labelId="location-select-label"
            id="location-select"
            value={location}
            onChange={handleLocationChange}
          >
            <MenuItem value="">
              <em>None</em>
            </MenuItem>
            <MenuItem value={'La Boca'}>La Boca</MenuItem>
            <MenuItem value={'Palermo'}>Palermo</MenuItem>
            <MenuItem value={'Recoleta'}>Recoleta</MenuItem>
          </CustomSelect>
        </FormControl>
      </div>
    </div>
  );
};

const CustomSelect = withStyles((theme) => ({
  root: {
    '& .MuiInputBase-root': {
      backgroundColor: 'white',
      backdropFilter: 'blur(10px)',
      borderRadius: '50px !important',
      border: 'none',
      '&:focus': {
        backgroundColor: 'white',
        backdropFilter: 'blur(10px)',
        borderRadius: 50,
      },
    },
  },
}))(Select);

export default Hero;
