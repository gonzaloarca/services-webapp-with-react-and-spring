const HeroStyles = (theme) => ({
  heroBackground: {
    width: '100%',
    height: '100%',
    objectFit: 'cover',
  },
  heroContainer: {
    overflowY: 'hidden',
    width: '100%',
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
    fontWeight: 600,
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
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  locationContent: {
    flex: 2,
    display: 'flex',
    alignItems: 'center',
  },
  locationForm: {
    width: '80%',
    borderRadius: '50px',
  },
  selectLabel: {
    fontWeight: 500,
    color: 'white',
    '&.Mui-focused': {
      color: 'white',
    },
  },
  locationSelect: {
    fontWeight: 600,
    color: 'white',
    '&:before': {
      borderColor: 'white',
    },
    '&:after': {
      borderColor: 'white',
    },
  },
  locationSelectIcon: {
    fill: 'white',
  },
  locationIcon: {
    color: 'white',
    width: '20%',
  },
  searchButton: {
    position: 'absolute',
    right: 0,
  },
  stepsContainer: {
    width: '100%',
    display: 'flex',
    flexWrap: 'wrap',
  },
  stepContainer: {
    position: 'relative',
    color: 'white',
    fontWeight: 600,
    height: '150px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    [theme.breakpoints.down('sm')]: {
      width: '100%',
    },
    [theme.breakpoints.up('sm')]: {
      flex: 1,
    },
    padding: 10,
  },
  stepNumber: {
    position: 'absolute',
    margin: 20,
    top: 0,
    left: 0,
    borderRadius: 100,
    height: 40,
    width: 40,
    fontSize: '1.5rem',
    fontWeight: 'bold',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
  yellowStep: {
    backgroundColor: theme.palette.secondary.main,
  },
});

export default HeroStyles;
