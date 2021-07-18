import {
  Divider,
  InputAdornment,
  makeStyles,
  TextField,
} from '@material-ui/core';
import { Search } from '@material-ui/icons';
import React from 'react';
import { useTranslation } from 'react-i18next';
import NavBar from '../components/NavBar';
import styles from '../styles';
import { themeUtils } from '../theme';

const useGlobalStyles = makeStyles(styles);
const useStyles = makeStyles((theme) => ({
  container: {
    backgroundColor: 'white',
    padding: 30,
    boxShadow: themeUtils.shadows.containerShadow,
    marginTop: 20,
  },
  header: {
    fontSize: themeUtils.fontSizes.h1,
    fontWeight: 700,
  },
  searchInput: {
    fontSize: themeUtils.fontSizes.sm,
    fontWeight: 500,
  },
}));

const Categories = () => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <div>
      <NavBar currentSection="/categories" />
      <div className={globalClasses.contentContainerTransparent}>
        <div className={classes.container}>
          <h1 className={classes.header}>{t('categories.header')}</h1>
          <Divider />
          <TextField
            hiddenLabel
            fullWidth
            size="small"
            variant="filled"
            placeholder={t('categories.filter')}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <Search />
                </InputAdornment>
              ),
              classes: {
                input: classes.searchInput,
              },
            }}
          />
        </div>
      </div>
    </div>
  );
};

export default Categories;
