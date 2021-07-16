import { Card, CardActionArea, makeStyles } from '@material-ui/core';
import React from 'react';

const useStyles = makeStyles((theme) => ({
  contractTitle: {
    fontSize: '1.2rem',
    fontWeight: 500,
    WebkitLineClamp: 3,
    display: '-webkit-box',
    WebkitBoxOrient: 'vertical',
    overflowWrap: 'break-word',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
  },
}));

const ContractCard = ({ contract }) => {
  const classes = useStyles();
  return (
    <Card>
      <CardActionArea>
        <h3 className={classes.contractTitle}>{contract.jobTitle}</h3>
      </CardActionArea>
    </Card>
  );
};

export default ContractCard;
