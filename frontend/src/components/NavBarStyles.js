import { alpha } from '@material-ui/core/styles';

const navBarStyles = (theme) => ({
  root: {
    // position: 'fixed',
    // top: 0,
    // width: '100vw',
    flexGrow: 1,
  },
  offset: theme.mixins.toolbar,
  transparentBar: {
    backgroundColor: 'transparent',
    boxShadow: 'none',
  },
  solidBar: {
    backgroundColor: theme.palette.primary.main,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
    display: 'none',
    [theme.breakpoints.up('sm')]: {
      display: 'block',
    },
  },
  search: {
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: alpha(theme.palette.common.white, 0.15),
    '&:hover': {
      backgroundColor: alpha(theme.palette.common.white, 0.25),
    },
    marginLeft: 0,
    width: '100%',
    [theme.breakpoints.up('sm')]: {
      marginLeft: theme.spacing(1),
      width: 'auto',
    },
  },
  searchIcon: {
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  inputRoot: {
    color: 'inherit',
  },
  inputInput: {
    padding: theme.spacing(1, 1, 1, 0),
    // vertical padding + font size from searchIcon
    paddingLeft: `calc(1em + ${theme.spacing(4)}px)`,
    transition: theme.transitions.create('width'),
    width: '100%',
    [theme.breakpoints.up('sm')]: {
      width: '12ch',
      '&:focus': {
        width: '20ch',
      },
    },
  },
  hirenetIcon: {
    height: 50,
    [theme.breakpoints.down('sm')]: {
      marginRight: 20,
    },
  },
  sectionsContainer: {
    margin: '0 10px',
    [theme.breakpoints.down('sm')]: {
      display: 'none',
    },
  },

  selectedSection: {
    color: '#fff !important',
  },
  selectedRegisterSection: {
    color: 'black !important',
  },
  drawerButton: {
    [theme.breakpoints.up('md')]: {
      display: 'none',
    },
  },
  drawerContainer: {
    width: 300,
  },
  selectedDrawerSection: {
    fontWeight: 600,
  },
});

export default navBarStyles;
