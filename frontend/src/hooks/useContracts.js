import {
  getContractsByClientIdAndStateRequest,
  getContractsByProAndStateIdRequest,
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
    createContract,
  };
};
export default useContractsHook;
