import { createTheme } from '@material-ui/core/styles';

export const themeUtils = {
  colors: {
    blue: '#485696',
    yellow: '#FCB839',
    orange: '#FC7A27',
    darkBlue: '#2F3965',
    lightBlue: '#89AAE6',
    grey: 'grey',
    lightGrey: '#EAEAEA',
    aqua: '#5FB0B7',
    green: '#7BC778',
    darkGreen: '#54AA51',
    red: '#E26767',
    electricBlue: '#007bff',
    background: '#EFEFEF',
  },
  shadows: {
    containerShadow: '0 3px 6px rgba(0, 0, 0, 0.16)',
    cardHoverShadow: '0 3px 10px rgba(0, 0, 0, 0.4)',
  },
  fontSizes: {
    xs: '0.75rem',
    sm: '0.875rem',
    base: '1rem',
    lg: '1.125rem',
    h2: '1.25rem',
    h1: '1.5rem',
  },
};

export const LightenDarkenColor = (color, amt) => {
  let usePound = false;

  if (color[0] === '#') {
    color = color.slice(1);
    usePound = true;
  }

  const num = parseInt(color, 16);

  let r = (num >> 16) + amt;

  if (r > 255) r = 255;
  else if (r < 0) r = 0;

  let b = ((num >> 8) & 0x00ff) + amt;

  if (b > 255) b = 255;
  else if (b < 0) b = 0;

  let g = (num & 0x0000ff) + amt;

  if (g > 255) g = 255;
  else if (g < 0) g = 0;

  return (usePound ? '#' : '') + (g | (b << 8) | (r << 16)).toString(16);
};

const appTheme = createTheme({
  palette: {
    primary: {
      main: '#485696',
    },
    secondary: {
      main: '#FCB839',
    },
    background: {
      default: '#efefef',
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
