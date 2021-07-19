import {
  getContractsByClientIdAndStateRequest,
  getContractsByProAndStateIdRequest,
  changeContractStateRequest,
  getContractStatesRequest,
  createContractRequest,
  putContractImage,
} from '../api/contractsApi';
import { extractLastIdFromURL } from '../utils/urlUtils';

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


  const createContract = async (clientId, jobPackageId, data) => {
    const contractInfo = {
      clientId: clientId,
      description: data.description,
      jobPackageId: jobPackageId,
      scheduledDate: data.date.toISOString(),
    };
    const response = await createContractRequest(contractInfo);
    const contractId = extractLastIdFromURL(response.headers.location);

    const formData = new FormData();
    formData.append('file', data.image);

    await putContractImage(contractId, formData);

    return response.data;
  };

  return {
    getContractsByClientIdAndState,
    getContractsByProAndStateId,
    changeContractStateClient,
    changeContractStatePro,
    getContractStates,
    createContract,
  };
};
export default useContractsHook;
