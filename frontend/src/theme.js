import { createTheme } from '@material-ui/core/styles';

const appTheme = createTheme({
  palette: {
    primary: {
      main: '#485696',
    },
    secondary: {
      main: '#FCB839',
    },
    background: {
      default: '#fff',
    },
  },
  typography: {
    fontSize: 14,
    fontWeightLight: 300,
    fontWeightRegular: 400,
    fontWeightMedium: 500,
    fontWeightBold: 700,
    fontFamily: "'Montserrat', 'Roboto', 'Helvetica', 'Arial', sans-serif",
    button: {
      textTransform: 'none',
      fontWeight: 600,
    },
  },
});

export default appTheme;
