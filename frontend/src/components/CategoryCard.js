import { makeStyles } from '@material-ui/core/styles';
import { Skeleton } from '@material-ui/lab';
import React from 'react';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';

const useStyles = makeStyles((theme) => ({
  categoryContainer: {
    position: 'relative',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 10,
  },
  categoryOverlay: {
    width: '100%',
    height: '100%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    position: 'absolute',
    zIndex: 2,
    fontWeight: 500,
    fontSize: '1.3rem',
    color: 'white',
    borderRadius: 10,
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    transition: 'background-color 0.1s',
    '&:hover': {
      backgroundColor: 'rgba(80, 80, 80, 0.5)',
      transition: 'background-color 0.1s',
    },
  },
  categoryImage: {
    height: '100%',
    width: '100%',
    objectFit: 'cover',
    borderRadius: 10,
  },
}));

const CategoryCard = ({
  showAll = false,
  category = null,
  height = 175,
  isLoading = false,
}) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return isLoading ? (
    <Skeleton
      className={classes.categoryContainer}
      variant="rect"
      height={height}
      width={'100%'}
    />
  ) : (
    <Link to={showAll ? '/search' : `/search?&category=${category.id}`}>
      <div className={classes.categoryContainer} style={{ height: height }}>
        <div className={classes.categoryOverlay}>
          {showAll
            ? t('home.morecategories').toUpperCase()
            : category.description.toUpperCase()}
        </div>
        <img
          className={classes.categoryImage}
          src={
            showAll
              ? process.env.PUBLIC_URL + '/img/morecategories1.svg'
              : category.image
          }
          alt=""
        />
      </div>
    </Link>
  );
};

export default CategoryCard;
