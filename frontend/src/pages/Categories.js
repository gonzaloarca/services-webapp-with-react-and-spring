import {
  Divider,
  Grid,
  InputAdornment,
  makeStyles,
  TextField,
} from '@material-ui/core';
import { Search } from '@material-ui/icons';
import React, { useContext, useState, useEffect } from 'react';
import { Helmet } from 'react-helmet';
import { useTranslation } from 'react-i18next';
import CategoryCard from '../components/CategoryCard';
import NavBar from '../components/NavBar';
import { ConstantDataContext } from '../context';
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
    fontWeight: 600,
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

const Categories = () => {
  const globalClasses = useGlobalStyles();
  const classes = useStyles();
  const { t } = useTranslation();
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState('');

  const { categories } = useContext(ConstantDataContext);

  useEffect(() => {
    if (categories) {
      setLoading(false);
    }
  }, [categories]);

  const renderList = (isLoading, list) => {
    if (isLoading) {
      list = [1, 2, 3, 4, 5, 6, 7, 8];
    } else {
      list = list.filter(
        ({ description }) =>
          description
            .toLowerCase()
            .startsWith(filter.trimStart().trimEnd().toLowerCase()) ||
          filter === ''
      );
    }

    const renderedList = list.map((category) => (
      <Grid
        item
        key={isLoading ? category : category.id}
        xs={12}
        sm={6}
        md={4}
        lg={3}
      >
        <CategoryCard isLoading={isLoading} category={category} />
      </Grid>
    ));

    if (renderedList.length > 0) return renderedList;
    else
      return (
        <Grid item xs={12}>
          <p className={classes.noResults}>
            {t('noresults', { search: filter })}
          </p>
        </Grid>
      );
  };

  return (
    <div>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.categories') })}
        </title>
      </Helmet>
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
              {renderList(loading, categories)}
            </Grid>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Categories;
