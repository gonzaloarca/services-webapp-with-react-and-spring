import React from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles, Card } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
  card: {
    borderRadius: '10px',
  },
  completed_works: {
    borderRadius: '50%',
    'width': '50px',
    'height': '50px',
    'lineHeight': '50px',
    'fontSize': '20px',
    'color': 'white',
    'textAlign': 'center',
    'fontWeight': 'bold',
    'background': '#485696',
  },
  completed_works_outline: {
    borderRadius: '50%',
    'width': '70px',
    'height': '70px',
    'background': '#fcb839',
    'display': 'flex',
    'justifyContent': 'center',
    'alignItems': 'center',
  },
}));

const TimesHiredCard = ({ count }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  return (
    <Card classes={{ root: classes.card }}>
      <div className="flex justify-center items-center my-3">
        <div className={classes.completed_works_outline}>
          <div className={classes.completed_works}>{count}</div>
        </div>
        <div className="ml-1 font-semibold">
          {count === 1 ? t('profile.countsingular') : t('profile.countplural')}
        </div>
      </div>
    </Card>
  );
};

export default TimesHiredCard;
