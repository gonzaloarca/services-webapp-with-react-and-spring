import { makeStyles } from '@material-ui/core';
import React from 'react';

const useStyles = makeStyles((theme) => ({
  siteHeader: {
    width: '100%',
    height: 100,
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 20,
    boxShadow: '0 3px 6px rgba(0, 0, 0, 0.16)',
    overflow: 'hidden',
  },
  title: {
    position: 'absolute',
    fontSize: '2rem',
    fontWeight: 'bold',
    color: 'white',
  },
}));

const SectionHeader = ({
  sectionName,
  image = <img src="/img/sectionbg.svg" alt="" />,
}) => {
  const classes = useStyles();

  return (
    <div className={classes.siteHeader}>
      <h1 className={classes.title}>{sectionName}</h1>
      {image}
    </div>
  );
};

export default SectionHeader;
