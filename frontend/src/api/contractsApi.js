import hireNetApi from './hirenetApi';

export const getContractsByClientIdAndStateRequest = (
  clientId,
  state,
  page = 1
) => {
  return hireNetApi.get('/contracts', {
    params: {
      userId: clientId,
      type: 'hired',
      state: state,
      page: page,
    },
    headers: {
      'Authorization':
        'Bearer ' +
        (localStorage.getItem('token') ||
          sessionStorage.getItem('token') ||
          ''),
    },
  });
};

export const getContractStatesRequest = () => {
  return hireNetApi.get('/contracts/states');
};

export const getContractsByProAndStateIdRequest = (proId, state, page = 1) => {
  return hireNetApi.get('/contracts', {
    params: {
      userId: proId,
      type: 'offered',
      state: state,
      page: page,
    },
    headers: {
      'Authorization':
        'Bearer ' +
        (localStorage.getItem('token') ||
          sessionStorage.getItem('token') ||
          ''),
    },
  });
};

export const changeContractStateRequest = (
  contractId,
  state,
  newScheduledDate
) => {
  return hireNetApi.put(
    '/contracts/' + contractId,
    {
      newState: state,
      newScheduledDate: newScheduledDate,
    },
    {
      headers: {
        'Authorization':
          'Bearer ' +
          (localStorage.getItem('token') ||
            sessionStorage.getItem('token') ||
            ''),
      },
    }
  );
};

export const createContractRequest = (contractInfo) => {
  return hireNetApi.post(`/contracts`, contractInfo, {
    headers: {
      'Authorization':
        'Bearer ' +
        (localStorage.getItem('token') ||
          sessionStorage.getItem('token') ||
          ''),
    },
  });
};

export const putContractImage = (contractId, formData) => {
  return hireNetApi.put(`/contracts/${contractId}/image`, formData, {
    headers: {
      'Authorization':
        'Bearer ' +
        (localStorage.getItem('token') ||
          sessionStorage.getItem('token') ||
          ''),
    },
  });
};
