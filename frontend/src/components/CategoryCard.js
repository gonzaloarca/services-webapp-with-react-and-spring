import { makeStyles } from '@material-ui/core/styles';
import React from 'react';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { themeUtils } from '../theme';

const useStyles = makeStyles((theme) => ({
  categoryContainer: {
    position: 'relative',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 10,
    transition: 'box-shadow 0.2s',
    '&:hover': {
      boxShadow: themeUtils.shadows.cardHoverShadow,
      transition: 'box-shadow 0.2s',
    },
  },
  categoryOverlay: {
    width: '100%',
    height: '100%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    position: 'absolute',
    zIndex: 2,
    backdropFilter: 'blur(5px) brightness(70%)',
    WebkitBackdropFilter: 'blur(5px) brightness(70%)',
    fontWeight: 500,
    fontSize: '1.3rem',
    color: 'white',
    borderRadius: 10,
  },
  categoryImage: {
    height: '100%',
    width: '100%',
    objectFit: 'cover',
    borderRadius: 10,
  },
}));

const CategoryCard = ({ showAll = false, category = null, height = 175 }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <Link to={showAll ? '/search' : `/search/${category.id}`}>
      <div className={classes.categoryContainer} style={{ height: height }}>
        <div className={classes.categoryOverlay}>
          {showAll
            ? t('home.morecategories').toUpperCase()
            : category.description.toUpperCase()}
        </div>
        <img
          className={classes.categoryImage}
          src={showAll ? `${process.env.PUBLIC_URL}/img/morecategories1.svg` :category.image}
          alt=""
        />
      </div>
    </Link>
  );
};

export default CategoryCard;
