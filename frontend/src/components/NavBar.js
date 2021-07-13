import React, { useEffect, useState } from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import InputBase from '@material-ui/core/InputBase';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import SearchIcon from '@material-ui/icons/Search';
import { Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import {
  Button,
  IconButton,
  Drawer,
  List,
  ListItem,
  ListItemText,
  Typography,
} from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import navBarStyles from './NavBarStyles';

const useStyles = makeStyles(navBarStyles);

const sections = [
  { name: 'navigation.sections.home', path: '/' },
  { name: 'navigation.sections.explore', path: '/search' },
  { name: 'navigation.sections.publish', path: '/create-job-post' },
  { name: 'navigation.sections.categories', path: '/categories' },
  {
    name: 'navigation.sections.mycontracts',
    path: '/my-contracts',
  },
  { name: 'navigation.sections.analytics', path: '/analytics' },
];

const NavBar = ({ currentSection, isTransparent = false }) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const [showDrawer, setShowDrawer] = useState(false);
  const [scrolled, setScrolled] = useState(false);

  const changeBarBackground = () => {
    if (window.scrollY >= 200) {
      setScrolled(true);
    } else {
      setScrolled(false);
    }
  };

  useEffect(() => {
    if (isTransparent) {
      window.addEventListener('scroll', changeBarBackground);
    }

    return () => window.removeEventListener('scroll', changeBarBackground);
  });

  return (
    <div className={classes.root}>
      <AppBar
        className={
          !isTransparent
            ? classes.solidBar
            : scrolled
            ? classes.solidBar
            : classes.transparentBar
        }
        position="fixed"
      >
        <Toolbar>
          <div className={classes.drawerButton}>
            <IconButton
              edge="start"
              className={classes.menuButton}
              onClick={() => setShowDrawer(true)}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Drawer
              anchor="left"
              open={showDrawer}
              onClose={() => setShowDrawer(false)}
            >
              <List className={classes.drawerContainer} component="nav">
                {sections.map((i) => (
                  <ListItem key={i.path} button component={Link} to={i.path}>
                    <ListItemText
                      disableTypography
                      primary={
                        <Typography
                          type="body2"
                          className={
                            i.path === currentSection
                              ? classes.selectedDrawerSection
                              : ''
                          }
                        >
                          {t(i.name)}
                        </Typography>
                      }
                    />
                  </ListItem>
                ))}
              </List>
            </Drawer>
          </div>
          <Link to="/">
            <img
              className={classes.hirenetIcon}
              src="/img/hirenet-logo-nav-1.svg"
              alt=""
            />
          </Link>
          <div className={classes.sectionsContainer}>
            {sections.map((i) => (
              <LinkButton
                className={
                  currentSection === i.path ? classes.selectedSection : ''
                }
                key={i.path}
                component={Link}
                to={i.path}
              >
                {t(i.name)}
              </LinkButton>
            ))}
          </div>
          <div className={classes.search}>
            <div className={classes.searchIcon}>
              <SearchIcon />
            </div>
            <InputBase
              placeholder={t('navigation.search')}
              classes={{
                root: classes.inputRoot,
                input: classes.inputInput,
              }}
              inputProps={{ 'aria-label': 'search' }}
            />
          </div>
        </Toolbar>
      </AppBar>
      {isTransparent ? <></> : <div className={classes.offset} />}
    </div>
  );
};

const LinkButton = withStyles((theme) => ({
  root: {
    color: 'rgba(255, 255, 255, 0.5)',
    transition: 'color 0.1s',
    '&:hover': {
      backgroundColor: 'rgba(255, 255, 255, 0.15)',
      color: '#fff',
      transition: 'color 0.1s',
    },
    marginLeft: 5,
  },
}))(Button);

export default NavBar;
