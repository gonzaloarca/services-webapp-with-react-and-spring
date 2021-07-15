import { themeUtils } from '../theme';

const LoginAndRegisterStyles = (theme) => ({
  background: {
    width: '100%',
    height: '100vh',
    paddingTop: '50px',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
  titleContainer: {
    backgroundColor: themeUtils.colors.darkBlue,
    width: 'max-content',
    padding: '10px',
    borderRadius: '20px 20px 0 0',
    display: 'flex',
    alignItems: 'center',
    color: 'white',
    fontWeight: 'bold',
  },
  title: {
    marginLeft: '15px',
    fontSize: '1.7em',
  },
  subtitle: {
    fontSize: '1.4em',
  },
  customCard: {
    justifyContent: 'center',
    padding: '3em 4em',
  },
  registerCard: {
    width: '800px',
  },
  separator: {
    borderLeft: '1px solid #c8c8c8',
    marginTop: '10px',
  },
  bottomLabel: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    textAlign: 'center',
  },
  bottomLabelLink: {
    color: themeUtils.colors.darkBlue,
  },
  submitButton: {
    color: 'white',
    backgroundColor: themeUtils.colors.blue,
    transition: 'color 0.1s',
    '&:hover': {
      backgroundColor: themeUtils.colors.darkBlue,
      transition: 'color 0.1s',
    },
    fontSize: '1em',
  },
});

export default LoginAndRegisterStyles;
