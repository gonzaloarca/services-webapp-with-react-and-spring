import {
  getContractsByClientIdAndStateRequest,
  getContractsByProAndStateIdRequest,
  changeContractStateRequest,
  getContractStatesRequest,
  createContractRequest,
  putContractImage,
} from '../api/contractsApi';
import { useState } from 'react';
import { extractLastIdFromURL } from '../utils/urlUtils';
import parse from 'parse-link-header';
import categoryImageMap from '../utils/categories';

const useContractsHook = () => {
  const initialLinks = {
    last: {
      page: 1,
      rel: 'last',
      url: null,
    },
    next: {
      page: 1,
      rel: 'next',
      url: null,
    },
    prev: {
      page: 1,
      rel: 'prev',
      url: null,
    },
    first: {
      page: 1,
      rel: 'first',
      url: null,
    },
  };
  const [links, setLinks] = useState(initialLinks);
  const getContractsByClientIdAndState = async (clientId, state, page) => {
    const response = await getContractsByClientIdAndStateRequest(
      clientId,
      state,
      page
    );
    setLinks(parse(response.headers.link) || { ...initialLinks });
    return response.data.map((contract) => ({
      ...contract,
      postImage: contract.postImage
        ? contract.postImage
        : categoryImageMap.get(contract.jobType.id),
    }));
  };

  const getContractsByProAndStateId = async (proId, state, page) => {
    const response = await getContractsByProAndStateIdRequest(
      proId,
      state,
      page
    );
    setLinks(parse(response.headers.link) || { ...initialLinks });
    return response.data.map((contract) => ({
      ...contract,
      postImage: contract.postImage
        ? contract.postImage
        : categoryImageMap.get(contract.jobType.id),
    }));
  };

  const changeContractState = async (contractId, state, newScheduledDate) => {
    const response = await changeContractStateRequest(
      contractId,
      state,
      newScheduledDate
    );
    return response.data;
  };

  const getContractStates = async () => {
    const response = await getContractStatesRequest();
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

    if (data.image) {
      const formData = new FormData();
      formData.append('file', data.image);
      await putContractImage(contractId, formData);
    }

    return response.data;
  };

  const freeLinks = () => {
    setLinks(initialLinks);
  };

  return {
    getContractsByClientIdAndState,
    getContractsByProAndStateId,
    changeContractState,
    getContractStates,
    createContract,
    links,
    freeLinks,
  };
};
export default useContractsHook;
