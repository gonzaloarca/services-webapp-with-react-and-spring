import {
  getContractsByClientIdAndStateRequest,
  getContractsByProAndStateIdRequest,
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
  return {
    getContractsByClientIdAndState,
    getContractsByProAndStateId,
  };
};
export default useContractsHook;
