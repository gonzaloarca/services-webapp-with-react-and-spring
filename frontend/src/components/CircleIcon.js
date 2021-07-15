import { makeStyles } from '@material-ui/core';
import clsx from 'clsx';
import React from 'react';

const useStyles = makeStyles((theme) => ({
  circleContainer: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
}));

const CircleIcon = ({ color, size, children, className = '', style = {} }) => {
  const classes = useStyles();
  return (
    <div
      style={{
        backgroundColor: color,
        width: size,
        height: size,
        minWidth: size,
        minHeight: size,
        borderRadius: '50%',
        ...style,
      }}
      className={clsx(classes.circleContainer, className)}
    >
      {children}
    </div>
  );
};

export default CircleIcon;
