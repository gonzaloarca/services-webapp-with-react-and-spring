import {
  getRateTypesRequest,
  getRateTypeByIdRequest,
} from '../api/rateTypesApi';
const useRateTypesHook = () => {
  const getRateTypes = async () => {
    const response = await getRateTypesRequest();
    return response.data;
  };
  const getRateTypeById = async (zoneId) => {
    const response = await getRateTypeByIdRequest(zoneId);
    return response.data;
  };
  return {
    getRateTypes,
    getRateTypeById,
  };
};
export default useRateTypesHook;
