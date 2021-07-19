import hireNetApi from "./hirenetApi";


export const getZonesRequest = () =>{
  return hireNetApi.get("/zones");
}

export const getZoneByIdRequest = (id) =>{
  return hireNetApi.get(`/zones/${id}`);
}