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
    state: 'PENDING_APPROVAL',
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
    state: 'CLIENT_MODIFIED',
    color: themeUtils.colors.yellow,
    icon: faExclamationCircle,
  },
  'PRO_MODIFIED': {
    clientMessage: 'mycontracts.contractstate.clientrescheduled',
    proMessage: 'mycontracts.contractstate.yourescheduled',
    state: 'PRO_MODIFIED',
    color: themeUtils.colors.yellow,
    icon: faExclamationCircle,
  },
};

export const contractActionsMap = {
  'ACTIVE': [
    {
      label: 'mycontracts.contractactions.details',
      icon: faInfoCircle,
      color: themeUtils.colors.blue,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'details',
    },
    {
      label: 'mycontracts.contractactions.contact',
      icon: faUserCircle,
      color: themeUtils.colors.aqua,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'contact',
    },
    {
      label: 'mycontracts.contractactions.finalize',
      icon: faCheckCircle,
      color: themeUtils.colors.green,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'finalize',
    },
    {
      label: 'mycontracts.contractactions.cancel',
      onClick: () => {
        console.log('CANCELLING CONTRACT');
      },
      icon: faTimes,
      color: themeUtils.colors.red,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'cancel',
    },
    {
      label: 'mycontracts.contractactions.reschedule',
      icon: faCalendarAlt,
      color: themeUtils.colors.yellow,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'reschedule',
      show: 'wasRescheduled',
    },
  ],
  'PENDING_APPROVAL': [
    {
      label: 'mycontracts.contractactions.details',
      icon: faInfoCircle,
      color: themeUtils.colors.blue,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'details',
    },
    {
      label: 'mycontracts.contractactions.contact',
      icon: faUserCircle,
      color: themeUtils.colors.aqua,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'contact',
    },
    {
      label: 'mycontracts.contractactions.approve',
      onClick: () => {
        console.log('APPROVING CONTRACT');
      },
      icon: faCheckCircle,
      color: themeUtils.colors.green,
      roles: ['PROFESSIONAL'],
      action: 'approve',
    },
    {
      label: 'mycontracts.contractactions.reject',
      icon: faTimes,
      color: themeUtils.colors.red,
      roles: ['PROFESSIONAL'],
      action: 'reject',
    },
    {
      label: 'mycontracts.contractactions.reschedule',
      icon: faCalendarAlt,
      color: themeUtils.colors.yellow,
      roles: ['PROFESSIONAL'],
      action: 'reschedule',
    },
    {
      label: 'mycontracts.contractactions.cancel',
      onClick: () => {
        console.log('CANCELLING CONTRACT');
      },
      icon: faTimes,
      color: themeUtils.colors.red,
      roles: ['CLIENT'],
      action: 'cancel',
    },
  ],
  'CLIENT_MODIFIED': [
    {
      label: 'mycontracts.contractactions.details',
      icon: faInfoCircle,
      color: themeUtils.colors.blue,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'details',
    },
    {
      label: 'mycontracts.contractactions.contact',
      icon: faUserCircle,
      color: themeUtils.colors.aqua,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'contact',
    },
    {
      label: 'mycontracts.contractactions.reviewreschedule',
      icon: faCalendarAlt,
      color: themeUtils.colors.yellow,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'reviewreschedule',
      showActionButtons: ['PROFESSIONAL'],
    },
    {
      label: 'mycontracts.contractactions.cancel',
      onClick: () => {
        console.log('CANCELLING CONTRACT');
      },
      icon: faTimes,
      color: themeUtils.colors.red,
      roles: ['CLIENT'],
      action: 'cancel',
    },
  ],
  'PRO_MODIFIED': [
    {
      label: 'mycontracts.contractactions.details',
      icon: faInfoCircle,
      color: themeUtils.colors.blue,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'details',
    },
    {
      label: 'mycontracts.contractactions.contact',
      icon: faUserCircle,
      color: themeUtils.colors.aqua,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'contact',
    },
    {
      label: 'mycontracts.contractactions.reviewreschedule',
      icon: faCalendarAlt,
      color: themeUtils.colors.yellow,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'reviewreschedule',
      showActionButtons: ['CLIENT'],
    },
    {
      label: 'mycontracts.contractactions.cancel',
      onClick: () => {
        console.log('CANCELLING CONTRACT');
      },
      icon: faTimes,
      color: themeUtils.colors.red,
      roles: ['PROFESSIONAL'],
      action: 'cancel',
    },
  ],
  'FINALIZED': [
    {
      label: 'mycontracts.contractactions.details',
      icon: faInfoCircle,
      color: themeUtils.colors.blue,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'details',
    },
    {
      label: 'mycontracts.contractactions.contact',
      icon: faUserCircle,
      color: themeUtils.colors.aqua,
      roles: ['CLIENT', 'PROFESSIONAL'],
      action: 'contact',
    },
    {
      label: 'mycontracts.contractactions.rate',
      icon: faStar,
      color: themeUtils.colors.yellow,
      roles: ['CLIENT'],
      action: 'rate',
    },
  ],
};
