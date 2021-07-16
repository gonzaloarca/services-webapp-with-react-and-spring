import { Card, CardActionArea, makeStyles } from '@material-ui/core';
import React from 'react';
import { useTranslation } from 'react-i18next';
import { themeUtils } from '../theme';

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
  stateContainer: {
    display: 'flex',
    justifyContent: 'start',
    alignItems: 'center',
    width: '100%',
    color: 'white',
    fontSize: '0.9rem',
    fontWeight: 500,
  },
}));

const ContractStateHeader = ({ contract, isOwner }) => {
  const classes = useStyles();
  const { t } = useTranslation();

  const contractStateDataMap = {
    'PENDING_APPROVAL': {
      message: '',
      state: 'PENDING',
    },
    'APPROVED': {
      message: '',
      state: 'ACTIVE',
    },
    'CLIENT_REJECTED': {
      message: t(
        'mycontracts.contractstate.' + (isOwner ? 'client' : 'you') + 'rejected'
      ),
      state: 'FINALIZED',
      color: themeUtils.colors.red,
    },
    'PRO_REJECTED': {
      message: t(
        'mycontracts.contractstate.' + (isOwner ? 'you' : 'pro') + 'rejected'
      ),
      state: 'FINALIZED',
      color: themeUtils.colors.red,
    },
    'CLIENT_CANCELLED': {
      message: t(
        'mycontracts.contractstate.' +
          (isOwner ? 'client' : 'you') +
          'cancelled'
      ),
      state: 'FINALIZED',
      color: themeUtils.colors.red,
    },
    'PRO_CANCELLED': {
      message: t(
        'mycontracts.contractstate.' + (isOwner ? 'you' : 'pro') + 'cancelled'
      ),
      state: 'FINALIZED',
      color: themeUtils.colors.red,
    },
    'COMPLETED': {
      message: t('mycontracts.contractstate.finalized'),
      state: 'FINALIZED',
      color: themeUtils.colors.darkGreen,
    },
    'CLIENT_MODIFIED': {
      message: t(
        'mycontracts.contractstate.' +
          (isOwner ? 'client' : 'you') +
          'rescheduled'
      ),
      state: 'PENDING',
      color: themeUtils.colors.yellow,
    },
    'PRO_MODIFIED': {
      message: t(
        'mycontracts.contractstate.' + (isOwner ? 'you' : 'pro') + 'rescheduled'
      ),
      state: 'PENDING',
      color: themeUtils.colors.yellow,
    },
  };

  const stateData = contractStateDataMap[contract.state.description];

  return stateData.message === '' ? (
    <></>
  ) : (
    <div
      className={classes.stateContainer}
      style={{ backgroundColor: stateData.color }}
    >
      {stateData.message}
    </div>
  );
};
const ContractCard = ({ contract, isOwner }) => {
  const classes = useStyles();
  return (
    <Card>
      <ContractStateHeader contract={contract} isOwner={isOwner} />
      <CardActionArea>
        <h3 className={classes.contractTitle}>{contract.jobTitle}</h3>
      </CardActionArea>
    </Card>
  );
};

export default ContractCard;
