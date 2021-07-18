import { useState } from 'react'
import { getJobCardsRequest, searchJobCardsRequest} from '../api/jobCardsApi'
import parse from 'parse-link-header'
const useJobCardsHook = ()  =>{
  const [links,setLinks] = useState(1);


  const getJobCards = async (page) => {
    const response = await getJobCardsRequest(page);
    setLinks(parse(response.headers.link));
    return response.data;
  }
  const searchJobCards = async (queryParams) => {
    const response = await searchJobCardsRequest(queryParams);
    return response.data;
  }
  return {
    getJobCards,
    searchJobCards,
    links
  }
}
export default useJobCardsHook;