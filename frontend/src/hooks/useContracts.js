import {
  getContractsByClientIdAndStateRequest,
  getContractsByProAndStateIdRequest,
  changeContractStateRequest,
  getContractStatesRequest,
} from '../api/contractsApi';
const useContractsHook = () => {
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
    const states = await getContractStatesRequest();
    const map = new Map();
    states.forEach((state) => map.put(state.description, state.id));
    return map;
  };

  return {
    getContractsByClientIdAndState,
    getContractsByProAndStateId,
    changeContractStateClient,
    changeContractStatePro,
    getContractStates,
  };
};
export default useContractsHook;
