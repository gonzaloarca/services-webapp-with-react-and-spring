import { useState } from 'react';
import {
  getContractsByClientIdAndStateRequest,
  getContractsByProAndStateIdRequest,
  changeContractStateRequest,
  getContractStatesRequest,
} from '../api/contractsApi';
const useContractsHook = () => {
  const [states, setStates] = useState(new Map());
  const getContractsByClientIdAndState = async (clientId, state, page) => {
    const response = await getContractsByClientIdAndStateRequest(
      clientId,
      state,
      page
    );
    return response.data;
  };
  const getContractsByProAndStateId = async (proId, state, page) => {
    const response = await getContractsByProAndStateIdRequest(
      proId,
      state,
      page
    );
    return response.data;
  };

  const changeContractStatePro = async (
    contractId,
    state,
    newScheduledDate
  ) => {
    const response = await changeContractStateRequest(
      contractId,
      state,
      newScheduledDate,
      'professional'
    );
    return response.data;
  };

  const changeContractStateClient = async (
    contractId,
    state,
    newScheduledDate
  ) => {
    const response = await changeContractStateRequest(
      contractId,
      state,
      newScheduledDate,
      'client'
    );
    return response.data;
  };

  const getContractStates = async () => {
    const response = await getContractStatesRequest();
    return response.data;
  };

  return {
    getContractsByClientIdAndState,
    getContractsByProAndStateId,
    changeContractStateClient,
    changeContractStatePro,
    getContractStates,
    states,
  };
};
export default useContractsHook;
