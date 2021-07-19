import { getZonesRequest, getZoneByIdRequest } from '../api/zonesApi'
const useZonesHook = ()  =>{
  const getZones = async () => {
    const response = await getZonesRequest();
    return response.data;
  }
  const getZoneById = async (zoneId) => {
    const response = await getZoneByIdRequest(zoneId);
    return response.data;
  }
  return {
    getZones,
    getZoneById
  }
}
export default useZonesHook;