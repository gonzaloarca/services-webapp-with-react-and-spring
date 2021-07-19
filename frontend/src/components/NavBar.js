import React, { useContext, useEffect, useState } from 'react';
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
  Avatar,
  Popover,
  ListItemIcon,
} from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import navBarStyles from './NavBarStyles';
import clsx from 'clsx';
import { themeUtils } from '../theme';
import { UserContext } from '../context';
import { useHistory } from 'react-router-dom';
import { isProfessional, isLoggedIn } from '../utils/userUtils';
import { ExitToApp, Person, Settings } from '@material-ui/icons';

const useStyles = makeStyles(navBarStyles);

const initialSections = [
  { name: 'navigation.sections.home', path: '/' },
  { name: 'navigation.sections.explore', path: '/search' },
  { name: 'navigation.sections.publish', path: '/create-job-post' },
  { name: 'navigation.sections.categories', path: '/categories' },
];

const rightSections = [
  { name: 'navigation.sections.login', path: '/login' },
  { name: 'navigation.sections.register', path: '/register' },
];

const NavBar = ({
  currentSection = '',
  isTransparent = false,
  searchBarQueryParams,
  searchBarSetQueryParams,
}) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const [showDrawer, setShowDrawer] = useState(false);
  const [scrolled, setScrolled] = useState(false);
  const { currentUser, setCurrentUser, setToken } = useContext(UserContext);
  const history = useHistory();
  const [sections, setSections] = useState(initialSections);
  const [anchorEl, setAnchorEl] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  const handleAvatarClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const open = Boolean(anchorEl);
  const id = open ? 'simple-popover' : undefined;

  useEffect(() => {
    const newSections = [...initialSections];
    if (currentUser) {
      newSections.push({
        name: 'navigation.sections.mycontracts',
        path: '/my-contracts',
      });
      if (isProfessional(currentUser)) {
        newSections.push({
          name: 'navigation.sections.analytics',
          path: '/analytics',
        });
      }
      setIsLoading(false);
    } else if (!isLoggedIn()) {
      setIsLoading(false);
    }

    setSections(newSections);
  }, [currentUser]);

  const logout = () => {
    localStorage.removeItem('token');
    sessionStorage.removeItem('token');
    setCurrentUser(null);
    setToken(null);
  };

  const changeBarBackground = () => {
    if (window.scrollY >= 200) {
      setScrolled(true);
    } else {
      setScrolled(false);
    }
  };
  const [values, setValues] = useState({
    query: !searchBarQueryParams ? '' : searchBarQueryParams.query,
  });

  useEffect(() => {
    if (isTransparent) {
      window.addEventListener('scroll', changeBarBackground);
    }

    return () => window.removeEventListener('scroll', changeBarBackground);
  });

  return (
    <div className={classes.root}>
      {!isLoading && (
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
          <Toolbar className="flex justify-between">
            <div className="flex items-center">
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
                      <ListItem
                        key={i.path}
                        button
                        component={Link}
                        to={i.path}
                      >
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
                  src={process.env.PUBLIC_URL + '/img/hirenet-logo-nav-1.svg'}
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
              {currentSection !== '/' && (
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
                    value={values.query}
                    onChange={(e) => setValues({ query: e.target.value })}
                    onKeyDown={(event) => {
                      if (event.key === 'Enter') {
                        if (!searchBarQueryParams)
                          history.push('/search?query=' + event.target.value);
                        else
                          searchBarSetQueryParams({
                            ...searchBarQueryParams,
                            query: event.target.value,
                          });
                      }
                    }}
                  />
                </div>
              )}
            </div>
            {!currentUser ? (
              <div
                className={clsx(classes.sectionsContainer, 'items-end', 'flex')}
              >
                {rightSections.map((i) =>
                  i.path === '/login' ? (
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
                  ) : (
                    <LinkButtonRegister
                      className={
                        currentSection === i.path
                          ? classes.selectedRegisterSection
                          : ''
                      }
                      key={i.path}
                      component={Link}
                      to={i.path}
                    >
                      {t(i.name)}
                    </LinkButtonRegister>
                  )
                )}
              </div>
            ) : (
              <>
                <IconButton onClick={handleAvatarClick}>
                  <Avatar src={currentUser.image} />
                </IconButton>
                <Popover
                  id={id}
                  open={open}
                  anchorEl={anchorEl}
                  onClose={handleMenuClose}
                  anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'center',
                  }}
                  transformOrigin={{
                    vertical: 'top',
                    horizontal: 'center',
                  }}
                >
                  <div className={classes.userMenu}>
                    <div className="flex items-center p-2">
                      <Avatar src={currentUser.image} className="mr-2" />
                      <div>
                        <p className={classes.menuName}>
                          {currentUser.username}
                        </p>
                        <p className={classes.menuEmail}>{currentUser.email}</p>
                      </div>
                    </div>
                    <List>
                      <ListItem
                        dense
                        button
                        component={Link}
                        to={`/profile/${currentUser.id}/services`}
                      >
                        <ListItemIcon>
                          <Person />
                        </ListItemIcon>
                        <p className="text-xs font-medium">
                          {t('navigation.profile')}
                        </p>
                      </ListItem>
                      <ListItem
                        dense
                        button
                        component={Link}
                        to="/account/personal"
                      >
                        <ListItemIcon>
                          <Settings />
                        </ListItemIcon>
                        <p className="text-xs font-medium">
                          {t('navigation.account')}
                        </p>
                      </ListItem>
                      <ListItem dense button onClick={logout}>
                        <ListItemIcon>
                          <ExitToApp />
                        </ListItemIcon>
                        <p className="text-xs font-medium">
                          {t('navigation.logout')}
                        </p>
                      </ListItem>
                    </List>
                  </div>
                </Popover>
                {/* <Button >LOGOUT</Button> */}
              </>
            )}
          </Toolbar>
        </AppBar>
      )}
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

const LinkButtonRegister = withStyles((theme) => ({
  root: {
    color: themeUtils.colors.blue,
    transition: 'color 0.1s',
    backgroundColor: 'rgba(255, 255, 255, 1)',
    '&:hover': {
      backgroundColor: 'rgba(255, 255, 255, 0.9)',
      transition: 'color 0.1s',
    },
    marginLeft: 5,
  },
}))(Button);

export default NavBar;
