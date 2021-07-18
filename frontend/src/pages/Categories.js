import {
  Divider,
  Grid,
  InputAdornment,
  makeStyles,
  TextField,
} from '@material-ui/core';
import { Search } from '@material-ui/icons';
import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import CategoryCard from '../components/CategoryCard';
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
  categoriesContainer: {
    marginTop: 20,
    minHeight: 500,
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
  noResults: {
    fontWeight: 600,
    opacity: 0.5,
    WebkitLineClamp: 4,
    display: '-webkit-box',
    WebkitBoxOrient: 'vertical',
    overflowWrap: 'break-word',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    width: '50%',
    margin: '0 auto',
    textAlign: 'center',
  },
}));

const categories = [
  {
    'description': 'Plumbing',
    'id': 0,
  },
  {
    'description': 'Electricity',
    'id': 1,
  },
  {
    'description': 'Carpentry',
    'id': 2,
  },
  {
    'description': 'Catering',
    'id': 3,
  },
  {
    'description': 'Painting',
    'id': 4,
  },
  {
    'description': 'Teaching',
    'id': 5,
  },
  {
    'description': 'Cleaning',
    'id': 6,
  },
  {
    'description': 'Babysitting',
    'id': 7,
  },
];

const Categories = () => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();
  const [filter, setFilter] = useState('');

  const renderList = (list) => {
    const renderedList = list
      .filter(
        ({ description }) =>
          description
            .toLowerCase()
            .startsWith(filter.trimStart().trimEnd().toLowerCase()) ||
          filter === ''
      )
      .map((category) => (
        <Grid item key={category.id} xs={12} sm={6} md={4} lg={3}>
          <CategoryCard category={category} />
        </Grid>
      ));
    if (renderedList.length === 0) {
      return (
        <Grid item xs={12}>
          <p className={classes.noResults}>
            {t('noresults', { search: filter })}
          </p>
        </Grid>
      );
    } else {
      return renderedList;
    }
  };

  return (
    <div>
      <NavBar currentSection="/categories" />
      <div className={globalClasses.contentContainerTransparent}>
        <div className={classes.container}>
          <h1 className={classes.header}>{t('categories.header')}</h1>
          <Divider className="mb-8 mt-2" />
          <TextField
            hiddenLabel
            fullWidth
            size="small"
            variant="filled"
            placeholder={t('categories.filter')}
            value={filter}
            onChange={(e) => setFilter(e.target.value)}
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
          <div className={classes.categoriesContainer}>
            <Grid container spacing={3}>
              {renderList(categories)}
            </Grid>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Categories;
