import { useEffect, useState } from 'react';
import {
  getJobCardsRequest,
  getJobCardsByProIdRequest,
  getOrderByParamByIdRequest,
  getOrderByParamsRequest,
  searchJobCardsRequest,
  relatedJobCardsRequest,
  getJobCardByIdRequest,
} from '../api/jobCardsApi';
import parse from 'parse-link-header';
import categoryImageMap from '../utils/categories';
const useJobCardsHook = () => {
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
  const [links, setLinks] = useState({ ...initialLinks });

  const getJobCards = async (page) => {
    const response = await getJobCardsRequest(page);
    setLinks(parse(response.headers.link) || { ...initialLinks });
    return response.data.map((jobCard) => ({
      ...jobCard,
      imageUrl: jobCard.imageUrl || categoryImageMap.get(jobCard.jobType.id),
    }));
  };

  const getJobCardsByProId = async (props) => {
    const response = await getJobCardsByProIdRequest(props);
    setLinks(parse(response.headers.link) || { ...initialLinks });
    return response.data.map((jobCard) => ({
      ...jobCard,
      imageUrl: jobCard.imageUrl || categoryImageMap.get(jobCard.jobType.id),
    }));
  };

  const searchJobCards = async (queryParams) => {
    const response = await searchJobCardsRequest(queryParams);
    setLinks(parse(response.headers.link) || { ...initialLinks });
    return response.data.map((jobCard) => ({
      ...jobCard,
      imageUrl: jobCard.imageUrl || categoryImageMap.get(jobCard.jobType.id),
    }));
  };

  const relatedJobCards = async (userId) => {
    const response = await relatedJobCardsRequest(userId);
    setLinks(parse(response.headers.link) || { ...initialLinks });
    return response.data.map((jobCard) => ({
      ...jobCard,
      imageUrl: jobCard.imageUrl || categoryImageMap.get(jobCard.jobType.id),
    }));
  };

  const getJobCardById = async (id) => {
    const response = await getJobCardByIdRequest(id);
    return {
      ...response.data,
      imageUrl:
        response.data.imageUrl ||
        categoryImageMap.get(response.data.jobType.id),
    };
  };

  const getOrderByParams = async () => {
    const response = await getOrderByParamsRequest();
    return response.data;
  };

  const getOrderByParamById = async (id) => {
    const response = await getOrderByParamByIdRequest(id);
    return response.data;
  };

  return {
    getJobCards,
    getJobCardsByProId,
    searchJobCards,
    getJobCardById,
    getOrderByParams,
    getOrderByParamById,
    links,
  };
};
export default useJobCardsHook;
