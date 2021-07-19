import React, { useEffect, useContext, useState } from 'react';
import NavBar from '../components/NavBar';
import {
  FormControl,
  MenuItem,
  Select,
  Grid,
  Card,
  Divider,
  Chip,
} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { Trans, useTranslation } from 'react-i18next';
import { themeUtils } from '../theme';
import clsx from 'clsx';
import JobCard from '../components/JobCard';
import { useLocation, useHistory } from 'react-router-dom';
import { parse } from 'query-string';
import { ConstantDataContext } from '../context';
import { useJobCards } from '../hooks';
import BottomPagination from '../components/BottomPagination';
import { Helmet } from 'react-helmet';
import {
  LocationOn,
  FilterList,
  Search as SearchIcon,
  Sort,
} from '@material-ui/icons';

const useStyles = makeStyles((theme) => ({
  searchContainer: {
    width: '100%',
    maxWidth: 1400,
  },
  title: {
    fontSize: '1.4em',
    fontWeight: 600,
    margin: '0px 10px',
  },
  categoriesTitle: {
    fontSize: '1.em',
    fontWeight: 600,
    margin: '5px 10px',
    color: themeUtils.colors.darkBlue,
  },
  category: {
    fontSize: '0.9em',
    margin: '4px 20px',
    color: themeUtils.colors.grey,
    cursor: 'pointer',
    transition: 'color 0.1s',
    '&:hover': {
      transition: 'color 0.1s',
      color: themeUtils.colors.darkBlue,
    },
  },
  selectedCategory: {
    color: themeUtils.colors.darkBlue,
    fontWeight: 600,
  },
  filteringBy: {
    fontSize: '0.9em',
    margin: '0px 10px',
  },
  queryRegular: {
    fontSize: themeUtils.fontSizes.base,
    fontWeight: 500,
    opacity: 0.5,
  },
  queryBold: {
    fontSize: themeUtils.fontSizes.h1,
    fontWeight: 600,
    WebkitLineClamp: 3,
    display: '-webkit-box',
    WebkitBoxOrient: 'vertical',
    overflowWrap: 'break-word',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
  },
  zoneFilter: {
    display: 'flex',
    alignItems: 'center',
  },
  zoneContainer: {
    fontSize: themeUtils.fontSizes.sm,
    fontWeight: 500,
    width: 'fit-content',
    display: 'flex',
    alignItems: 'center',
    marginTop: 5,
  },
  filterChip: {
    backgroundColor: themeUtils.colors.aqua,
    color: 'white',
    fontWeight: 600,
    marginRight: 10,
    '&:focus': {
      backgroundColor: themeUtils.colors.aqua,
    },
  },
  noResultsContainer: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    height: 400,
    width: '100%',
    fontWeight: 500,
  },
  noResultsIcon: {
    width: '50%',
    height: '50%',
  },
}));

function useQuery() {
  return parse(useLocation().search);
}

const Search = () => {
  let queryParameters = useQuery();
  const { orderByParams, categories, zones } = useContext(ConstantDataContext);

  const { searchJobCards, links } = useJobCards();
  const [jobCards, setJobCards] = useState(null);
  const [maxPage, setMaxPage] = useState(1);
  const [queryParams, setQueryParams] = React.useState({
    zone: queryParameters.zone || '',
    category: queryParameters.category || '',
    query: queryParameters.query || '',
    orderBy: queryParameters.orderBy || '',
    page: queryParameters.page || 1,
  });
  const classes = useStyles();
  const history = useHistory();
  const { t } = useTranslation();

  const loadJobCards = async () => {
    try {
      const jobCards = await searchJobCards(queryParams);
      setJobCards(jobCards);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    setMaxPage(parseInt(links.last?.page) || parseInt(links.prev?.page) || 1);
  }, [links]);

  useEffect(() => {
    history.replace(
      '/search?' +
        (queryParams.zone ? 'zone=' + queryParams.zone : '') +
        (queryParams.category ? '&category=' + queryParams.category : '') +
        (queryParams.orderBy ? '&orderBy=' + queryParams.orderBy : '') +
        (queryParams.query ? '&query=' + queryParams.query : '') +
        (queryParams.page ? '&page=' + queryParams.page : '')
    );
    loadJobCards();
  }, [queryParams]);

  return (
    <div>
      <Helmet>
        <title>
          {t('title', { section: t('navigation.sections.explore') })}
        </title>
      </Helmet>
      <NavBar
        currentSection={'/search'}
        searchBarSetQueryParams={setQueryParams}
        searchBarQueryParams={queryParams}
      />
      <div className="flex justify-center mt-10">
        <Grid container spacing={3} className={classes.searchContainer}>
          <Grid item xs={3}>
            <Filters
              setQueryParams={setQueryParams}
              queryParams={queryParams}
              categories={categories}
              orderByParams={orderByParams}
            />
          </Grid>
          <Grid item xs={9}>
            <SearchResults
              setQueryParams={setQueryParams}
              queryParams={queryParams}
              categories={categories}
              zones={zones}
              jobs={jobCards}
              maxPage={maxPage}
            />
          </Grid>
        </Grid>
      </div>
    </div>
  );
};

const SearchResults = ({
  queryParams,
  setQueryParams,
  categories,
  zones,
  jobs,
  maxPage,
}) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const [loadingJobs, setLoadingJobs] = useState(true);

  useEffect(() => {
    if (jobs) setLoadingJobs(false);
  }, [jobs]);

  let zoneStr;

  if (
    queryParams.zone === '' ||
    queryParams.zone < 0 ||
    queryParams.zone >= zones.length
  )
    zoneStr = '';
  else {
    let zoneAux = zones.find((zone) => zone.id === parseInt(queryParams.zone));
    zoneStr = !zoneAux ? '' : zoneAux.description;
  }
  let categoryStr;
  if (
    queryParams.category === '' ||
    queryParams.category < 0 ||
    queryParams.category >= categories.length
  )
    categoryStr = '';
  else {
    let categoryAux = categories.find(
      (category) => category.id === parseInt(queryParams.category)
    );
    categoryStr = !categoryAux ? '' : categoryAux.description;
  }

  const getHeader = (zone, query) => {
    if (zone === '') {
      if (query === '') {
        return <div className={classes.queryBold}>{t('search.results')}</div>;
      } else {
        return (
          <Trans
            i18nKey="search.queryresults"
            components={{
              regular: <div className={classes.queryRegular} />,
              bold: <div className={classes.queryBold} />,
            }}
            values={{ query: query }}
          />
        );
      }
    }

    if (query === '') {
      return (
        <Trans
          i18nKey="search.zoneresults"
          components={{
            zonecontainer: <div className={classes.zoneContainer} />,
            icon: <LocationOn />,
          }}
          values={{ zone: zone }}
        />
      );
    } else
      return (
        <Trans
          i18nKey="search.query&zoneresults"
          components={{
            regular: <div className={classes.queryRegular} />,
            bold: <div className={classes.queryBold} />,
            bottom: <div className={classes.zoneFilter} />,
            zonecontainer: <div className={classes.zoneContainer} />,
            icon: <LocationOn />,
          }}
          values={{ query: query, zone: zone }}
        />
      );
  };

  return (
    <Card className="p-5">
      <div className={classes.title}>
        {getHeader(zoneStr, queryParams.query)}
      </div>
      <Divider className="mt-2 mb-5" />
      <div className="flex justify-between items-center mb-5">
        {queryParams.query === '' && queryParams.category === '' ? (
          <div></div>
        ) : (
          //   Debe ser un div para que ocupe el espacio y siga bien alineado todo
          <div className="flex items-center">
            <p className={classes.filteringBy}>{t('search.filteringby')}</p>
            {queryParams.category !== '' && (
              <Chip
                className={classes.filterChip}
                label={categoryStr}
                icon={<FilterList />}
                onDelete={() =>
                  setQueryParams({ ...queryParams, category: '' })
                }
              />
            )}
            {queryParams.query !== '' && (
              <Chip
                className={classes.filterChip}
                label={queryParams.query}
                icon={<SearchIcon />}
                onDelete={() => setQueryParams({ ...queryParams, query: '' })}
              />
            )}
          </div>
        )}
      </div>
      <Grid container spacing={3}>
        {loadingJobs ? (
          [1, 2, 3, 4].map((i) => (
            <Grid key={i} item xs={12} sm={6} md={4} lg={3}>
              <JobCard job={{}} isLoading />
            </Grid>
          ))
        ) : jobs.length > 0 ? (
          jobs.map((i) => (
            <Grid key={i.id} item xs={12} sm={6} md={4} lg={3}>
              <JobCard job={i} />
            </Grid>
          ))
        ) : (
          <div className={classes.noResultsContainer}>
            <SearchIcon className={classes.noResultsIcon} />
            <p className="text-center">{t('search.noresults')}</p>
          </div>
        )}
      </Grid>
      <BottomPagination
        maxPage={maxPage}
        setQueryParams={setQueryParams}
        queryParams={queryParams}
      />
    </Card>
  );
};

const Filters = ({
  queryParams,
  setQueryParams,
  orderByParams,
  categories,
}) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const { zones } = useContext(ConstantDataContext);

  return (
    <Card className="p-5">
      <p className={classes.title}>{t('search.filters')}</p>
      <Divider className="my-5" />
      <p className={classes.categoriesTitle}>{t('search.location')}</p>
      <FormControl fullWidth size="small" variant="outlined" className="mb-5">
        <Select
          startAdornment={<LocationOn className="opacity-50 mr-2" />}
          inputProps={{
            classes: {
              select: 'text-sm font-medium',
            },
          }}
          displayEmpty
          value={queryParams.zone}
          onChange={(event) => {
            setQueryParams({ ...queryParams, zone: event.target.value });
          }}
        >
          <MenuItem value="">
            <em>{t('search.alllocations')}</em>
          </MenuItem>
          {zones.map((zone) => (
            <MenuItem key={zone.id} value={zone.id}>
              {zone.description}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
      <p className={classes.categoriesTitle}>{t('search.orderby')}</p>
      <FormControl fullWidth size="small" variant="outlined" className="mb-5">
        <Select
          startAdornment={<Sort className="opacity-50 mr-2" />}
          inputProps={{
            classes: {
              select: 'text-sm font-medium',
            },
          }}
          displayEmpty
          value={queryParams.orderBy}
          onChange={(event) => {
            setQueryParams({ ...queryParams, orderBy: event.target.value });
          }}
        >
          <MenuItem value="" disabled>
            <em>{t('search.criteria')}</em>
          </MenuItem>
          {orderByParams.map((order) => (
            <MenuItem key={order.id} value={order.id}>
              {order.description}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
      <p className={classes.categoriesTitle}>{t('search.categories')}</p>
      {categories.map((category) => (
        <p
          key={category.id}
          className={clsx(
            classes.category,
            category.id === parseInt(queryParams.category)
              ? classes.selectedCategory
              : ''
          )}
          onClick={() => {
            setQueryParams({ ...queryParams, category: category.id });
          }}
        >
          {category.description}
        </p>
      ))}
    </Card>
  );
};

export default Search;
