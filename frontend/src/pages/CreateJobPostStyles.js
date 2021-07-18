import { themeUtils } from '../theme.js';

const createJobPostStyles = (theme) => ({
  stepperContainer: {
    // boxShadow: themeUtils.shadows.containerShadow,
    // borderRadius: 20,
    backgroundColor: 'transparent',
    padding: '20px 15px',
    width: '80%',
    margin: '0 auto',
  },
  button: {
    marginRight: theme.spacing(1),
  },
  actionsContainer: {
    display: 'flex',
    justifyContent: 'flex-end',
    width: '70%',
    margin: '0 auto',
  },
  instructions: {
    marginTop: theme.spacing(1),
    marginBottom: theme.spacing(1),
  },
  stepContainer: {
    boxShadow: themeUtils.shadows.containerShadow,
    borderRadius: 5,
    backgroundColor: 'white',
    padding: 20,
    maxWidth: '70%',
    margin: '20px auto',
  },
  stepCounter: {
    color: 'white',
    fontWeight: 700,
    fontSize: themeUtils.fontSizes.h2,
  },
  stepHeader: {
    display: 'flex',
    alignItems: 'center',
  },
  stepTitle: {
    fontSize: themeUtils.fontSizes.h2,
    fontWeight: 600,
  },
  input: {
    margin: '0 auto',
    display: 'flex',
    width: '80%',
  },
  packagesContainer: {
    padding: 10,
    width: '100%',
  },
  packageForm: {
    position: 'relative',
    padding: 30,
    backgroundColor: themeUtils.colors.blue,
    color: 'white',
    borderRadius: 10,
    width: '100%',
    boxShadow: themeUtils.shadows.containerShadow,
  },
  packageLabel: {
    color: 'white',
    fontWeight: 600,
    marginBottom: 10,
    fontSize: themeUtils.fontSizes.base,
  },
  packageInput: {
    color: 'white',
    backgroundColor: themeUtils.colors.darkBlue,
    fontSize: themeUtils.fontSizes.sm,
    fontWeight: 500,
  },
  packagesRadioContainer: {
    backgroundColor: 'white',
    color: 'black',
    borderRadius: 10,
    boxShadow: themeUtils.shadows.containerShadow,
    width: '100%',
    display: 'flex',
    justifyContent: 'center',
    padding: 10,
    marginBottom: 25,
  },
  packageHeader: {
    fontSize: themeUtils.fontSizes.h2,
    fontWeight: 600,
    marginLeft: 10,
  },
  packageDescription: {
    marginBottom: 25,
    backgroundColor: themeUtils.colors.darkBlue,
    '& .MuiFilledInput-root': {
      backgroundColor: 'transparent',
    },
  },
});

export default createJobPostStyles;
