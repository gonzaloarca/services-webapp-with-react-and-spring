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
  summaryContainer: {
    backgroundColor: 'white',
    padding: 15,
    boxShadow: themeUtils.shadows.containerShadow,
    borderRadius: 5,
  },
  summaryFieldContainer: {
    display: 'flex',
    color: 'white',
    width: '100%',
    minHeight: 70,
    fontWeight: 500,
    fontSize: themeUtils.fontSizes.sm,
    flexDirection: 'column',
    justifyContent: 'space-between',
    alignItems: 'space-between',
    boxShadow: themeUtils.shadows.containerShadow,
    padding: 10,
    marginBottom: 10,
    borderRadius: 5,
    maxWidth: '90%',
  },
  summaryRow: {
    display: 'flex',
    alignItems: 'flex-start',
    justifyContent: 'center',
  },
  summaryIcon: {
    height: 70,
    width: 70,
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
  summaryValue: {
    display: 'flex',
    flexWrap: 'wrap',
    width: '100%',
    justifyContent: 'flex-end',
    fontWeight: 600,
    fontSize: themeUtils.fontSizes.base,
    textAlign: 'right',
    marginTop: 20,
  },
  titleSummary: {
    backgroundColor: themeUtils.colors.aqua,
  },
  categorySummary: {
    backgroundColor: themeUtils.colors.orange,
  },
  imagesSummary: {
    backgroundColor: themeUtils.colors.blue,
  },
  packagesSummary: {
    backgroundColor: themeUtils.colors.lightBlue,
  },
  hoursSummary: {
    backgroundColor: themeUtils.colors.orange,
  },
  locationsSummary: {
    backgroundColor: themeUtils.colors.aqua,
  },
  imageSlideshow: {
    width: '100%',
    padding: 10,
    display: 'flex',
    justifyContent: 'space-evenly',
    flexWrap: 'wrap',
  },
  image: {
    height: 170,
    width: 170,
    objectFit: 'cover',
    borderRadius: 10,
    margin: 7,
  },
});

export default createJobPostStyles;
