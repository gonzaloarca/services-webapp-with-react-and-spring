import {
  faCheckCircle,
  faTimesCircle,
  faCalendarAlt,
  faExclamationCircle,
  faInfoCircle,
  faStar,
  faTimes,
  faUserCircle,
} from '@fortawesome/free-solid-svg-icons';
import { themeUtils } from '../theme';

export const contractStateDataMap = {
  'PENDING_APPROVAL': {
    state: 'PENDING',
  },
  'APPROVED': {
    state: 'ACTIVE',
  },
  'CLIENT_REJECTED': {
    clientMessage: 'mycontracts.contractstate.yourejected',
    proMessage: 'mycontracts.contractstate.clientrejected',
    state: 'FINALIZED',
    color: themeUtils.colors.red,
    icon: faTimesCircle,
  },
  'PRO_REJECTED': {
    clientMessage: 'mycontracts.contractstate.prorejected',
    proMessage: 'mycontracts.contractstate.yourejected',
    state: 'FINALIZED',
    color: themeUtils.colors.red,
    icon: faTimesCircle,
  },
  'CLIENT_CANCELLED': {
    clientMessage: 'mycontracts.contractstate.youcancelled',
    proMessage: 'mycontracts.contractstate.clientcancelled',
    state: 'FINALIZED',
    color: themeUtils.colors.red,
    icon: faTimesCircle,
  },
  'PRO_CANCELLED': {
    clientMessage: 'mycontracts.contractstate.procancelled',
    proMessage: 'mycontracts.contractstate.youcancelled',
    state: 'FINALIZED',
    color: themeUtils.colors.red,
    icon: faTimesCircle,
  },
  'COMPLETED': {
    clientMessage: 'mycontracts.contractstate.finalized',
    proMessage: 'mycontracts.contractstate.finalized',
    state: 'FINALIZED',
    color: themeUtils.colors.darkGreen,
    icon: faCheckCircle,
  },
  'CLIENT_MODIFIED': {
    clientMessage: 'mycontracts.contractstate.yourescheduled',
    proMessage: 'mycontracts.contractstate.prorescheduled',
    state: 'PENDING',
    color: themeUtils.colors.yellow,
    icon: faExclamationCircle,
  },
  'PRO_MODIFIED': {
    clientMessage: 'mycontracts.contractstate.clientrescheduled',
    proMessage: 'mycontracts.contractstate.',
    state: 'PENDING',
    color: themeUtils.colors.yellow,
    icon: faExclamationCircle,
  },
};

export const contractActionsMap = {
  'ACTIVE': [
    {
      label: 'mycontracts.contractactions.finalize',
      onClick: () => {
        console.log('FINALIZING CONTRACT');
      },
      icon: faCheckCircle,
      color: themeUtils.colors.green,
      roles: ['CLIENT', 'PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.cancel',
      onClick: () => {
        console.log('CANCELLING CONTRACT');
      },
      icon: faTimes,
      color: themeUtils.colors.red,
      roles: ['CLIENT', 'PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.reschedule',
      onClick: () => {
        console.log('RESCHEDULING CONTRACT');
      },
      icon: faCalendarAlt,
      color: themeUtils.colors.yellow,
      roles: ['CLIENT', 'PROFESSIONAL'],
    },
  ],
  'PENDING': [
    {
      label: 'mycontracts.contractactions.approve',
      onClick: () => {
        console.log('APPROVING CONTRACT');
      },
      icon: faCheckCircle,
      color: themeUtils.colors.green,
      roles: ['PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.reject',
      onClick: () => {
        console.log('REJECTING CONTRACT');
      },
      icon: faTimes,
      color: themeUtils.colors.red,
      roles: ['PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.reschedule',
      onClick: () => {
        console.log('RESCHEDULING CONTRACT');
      },
      icon: faCalendarAlt,
      color: themeUtils.colors.yellow,
      roles: ['CLIENT', 'PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.reviewreschedule',
      onClick: () => {
        console.log('REVIEWING CONTRACT RESCHEDULING');
      },
      icon: faCalendarAlt,
      color: themeUtils.colors.yellow,
      roles: ['CLIENT'],
    },
  ],
  'FINALIZED': [
    {
      label: 'mycontracts.contractactions.details',
      onClick: () => {
        console.log('OPENING CONTRACT DETAILS');
      },
      icon: faInfoCircle,
      color: themeUtils.colors.blue,
      roles: ['CLIENT', 'PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.contact',
      onClick: () => {
        console.log('OPENING USER DETAILS');
      },
      icon: faUserCircle,
      color: themeUtils.colors.aqua,
      roles: ['CLIENT', 'PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.rate',
      onClick: () => {
        console.log('RATING CONTRACT');
      },
      icon: faStar,
      color: themeUtils.colors.yellow,
      roles: ['CLIENT'],
    },
  ],
};
