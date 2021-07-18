import React, { useEffect } from 'react';
import NavBar from '../components/NavBar';
import {
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  Grid,
  Card,
  Divider,
} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { useTranslation } from 'react-i18next';
import { themeUtils } from '../theme';
import clsx from 'clsx';
import JobCard from '../components/JobCard';
import { useLocation } from 'react-router-dom';
import { useHistory } from 'react-router-dom';

const jobs = [
  {
    avgRate: 3,
    contractsCompleted: 3,
    price: 2000.1,
    imageUrl: `${process.env.PUBLIC_URL}/img/babysitting.jpeg`,
    jobPost: {
      id: 3,
      uri: 'http://localhost:8080/job-posts/8',
    },
    jobType: {
      description: 'Babysitting',
      id: 7,
    },
    rateType: {
      description: 'ONE_TIME',
      id: 2,
    },
    reviewsCount: 2,
    title: 'Niñero turno mañana',
    zones: [
      {
        description: 'Retiro',
        id: 28,
      },
      {
        description: 'Nuñez',
        id: 20,
      },
      {
        description: 'Colegiales',
        id: 9,
      },
    ],
  },
  {
    avgRate: 3,
    contractsCompleted: 3,
    price: 1500,
    imageUrl: `${process.env.PUBLIC_URL}/img/babysitting.jpeg`,
    jobPost: {
      id: 7,
      uri: 'http://localhost:8080/job-posts/8',
    },
    jobType: {
      description: 'Babysitting',
      id: 7,
    },
    rateType: {
      description: 'HOURLY',
      id: 2,
    },
    reviewsCount: 2,
    title:
      'Niñero turno mañanaaaa ajofejo jaofjaeo aehfeah ofgeafg aoeifgaeof goafg oaeg efeoia',
    zones: [
      {
        description: 'Retiro',
        id: 28,
      },
      {
        description: 'Nuñez',
        id: 20,
      },
      {
        description: 'Colegiales',
        id: 9,
      },
    ],
  },
  {
    avgRate: 3,
    contractsCompleted: 3,
    imageUrl: `${process.env.PUBLIC_URL}/img/babysitting.jpeg`,
    jobPost: {
      id: 8,
      uri: 'http://localhost:8080/job-posts/8',
    },
    jobType: {
      description: 'Babysitting',
      id: 7,
    },
    rateType: {
      description: 'TBD',
      id: 2,
    },
    reviewsCount: 2,
    title: 'Niñero turno mañana',
    zones: [
      {
        description: 'Retiro',
        id: 28,
      },
      {
        description: 'Nuñez',
        id: 20,
      },
      {
        description: 'Colegiales',
        id: 9,
      },
    ],
  },
  {
    avgRate: 3,
    contractsCompleted: 3,
    price: 1500,
    imageUrl: `${process.env.PUBLIC_URL}/img/babysitting.jpeg`,
    jobPost: {
      id: 2,
      uri: 'http://localhost:8080/job-posts/8',
    },
    jobType: {
      description: 'Babysitting',
      id: 7,
    },
    rateType: {
      description: 'TBD',
      id: 2,
    },
    reviewsCount: 0,
    title: 'Niñero turno mañana',
    zones: [
      {
        description: 'Retiro',
        id: 28,
      },
      {
        description: 'Nuñez',
        id: 20,
      },
      {
        description: 'Colegiales',
        id: 9,
      },
    ],
  },
];

const useStyles = makeStyles((theme) => ({
  searchContainer: {
    width: '100%',
    maxWidth: 1400,
  },
  title: {
    fontSize: '1.4em',
    fontWeight: 'bold',
    margin: '0px 20px',
    WebkitLineClamp: 3,
    display: '-webkit-box',
    WebkitBoxOrient: 'vertical',
    overflowWrap: 'break-word',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
  },
  categoriesTitle: {
    fontSize: '1.em',
    fontWeight: 'bold',
    margin: '5px 20px',
    color: themeUtils.colors.darkBlue,
  },
  category: {
    fontSize: '0.9em',
    margin: '2px 40px',
    color: themeUtils.colors.grey,
    cursor: 'pointer',
  },
  selectedCategory: {
    color: 'black',
    fontWeight: 'bold',
  },
}));

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

const Search = () => {
  let queryParameters = useQuery();
  const [values, setValues] = React.useState({
    zone: !queryParameters.get('zone') ? '' : queryParameters.get('zone'),
    category: !queryParameters.get('category')
      ? ''
      : queryParameters.get('category'),
    query: !queryParameters.get('query') ? '' : queryParameters.get('query'),
    orderBy: !queryParameters.get('orderBy')
      ? ''
      : queryParameters.get('orderBy'),
  });
  const classes = useStyles();
  const history = useHistory();

  useEffect(() => {
    history.push(
      '/search?zone=' +
        values.zone +
        '&category=' +
        values.category +
        '&orderBy=' +
        values.orderBy +
        '&query=' +
        values.query
    );
  }, [values]);

  return (
    <div>
      <NavBar currentSection={'/search'} searchPageSetValues={setValues} searchPageValues={values} />
      <div className="flex justify-center mt-10">
        <Grid container spacing={3} className={classes.searchContainer}>
          <Grid item xs={3}>
            <Filters
              category={values.category}
              orderBy={values.orderBy}
              setValues={setValues}
              values={values}
            />
          </Grid>
          <Grid item xs={9}>
            <SearchResults zone={values.zone} query={values.query} />
          </Grid>
        </Grid>
      </div>
    </div>
  );
};

const SearchResults = ({ zone, query }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  const zoneStr = 'Boedo'; // TODO: debe ser a partir del id en zone
  return (
    <Card className="p-5">
      <p className={classes.title}>
        {zone === ''
          ? query === ''
            ? t('search.results')
            : t('search.queryresults', { query: query })
          : query === ''
          ? t('search.zoneresults', { zone: zoneStr })
          : t('search.query&zoneresults', { zone: zoneStr, query: query })}
      </p>
      <Divider className="m-5" />

      <Grid container spacing={3}>
        {jobs.length > 0 ? (
          jobs.map((i) => (
            <Grid key={i.jobPost.id} item xs={12} sm={6} md={4} lg={3}>
              <JobCard job={i} />
            </Grid>
          ))
        ) : (
          <p className={clsx('text-center m-10')}>{t('search.noresults')}</p>
        )}
      </Grid>
    </Card>
  );
};

const Filters = ({ category, orderBy, values, setValues }) => {
  const classes = useStyles();
  const { t } = useTranslation();
  const categories = [
    'Plumbing',
    'Plectrician',
    'Carpentry',
    'Catering',
    'Painter',
    'Teaching',
    'Cleaning',
    'Babysitting',
  ]; //TODO: RECIBIR DE LA API
  const possibleOrders = [
    'Most hired',
    'Least hired',
    'Highest rated',
    'Lowest rated',
  ]; //TODO: RECIBIR DE LA API
  return (
    <Card className="p-5">
      <p className={classes.title}>{t('search.filters')}</p>
      <Divider className="m-5" />
      <div className="flex justify-center m-6">
        <FormControl variant="filled" className="w-48">
          <InputLabel id="order-select">{t('search.orderby')}</InputLabel>
          <Select
            labelId="order-select"
            value={orderBy}
            onChange={(event) => {
              setValues({ ...values, orderBy: event.target.value });
            }}
          >
            <MenuItem value="">
              <em>{t('nonselected')}</em>
            </MenuItem>
            {possibleOrders.map((orderStr, index) => (
              <MenuItem key={index} value={index}>
                {orderStr}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </div>
      <Divider className="mx-5 mb-4" />
      <p className={classes.categoriesTitle}>{t('search.categories')}</p>
      {categories.map((categoryString, index) => (
        <p
          key={index}
          className={clsx(
            classes.category,
            index === category ? classes.selectedCategory : ''
          )}
          onClick={() => {
            setValues({ ...values, category: index });
          }}
        >
          {categoryString}
        </p>
      ))}
    </Card>
  );
};

export default Search;
