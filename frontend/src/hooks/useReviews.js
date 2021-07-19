import { useState } from 'react';
import {
  getReviewsRequest,
  getReviewsByPostIdRequest,
  getReviewsByProIdRequest,
  getReviewByContractIdRequest,
  createReviewRequest,
} from '../api/reviewsApi';
import parse from 'parse-link-header';
const useReviewsHook = () => {
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
  const getReviews = async (page) => {
    const response = await getReviewsRequest(page);
    setLinks(parse(response.headers.link) || { ...initialLinks });
    return response.data;
  };

  const getReviewByContractId = async (id) => {
    const response = await getReviewByContractIdRequest(id);
    return response.data;
  };

  const getReviewsByPostId = async (postId, page) => {
    const response = await getReviewsByPostIdRequest(postId, page);
    setLinks(parse(response.headers.link) || { ...initialLinks });
    return response.data;
  };
  const getReviewsByProId = async (proId, page) => {
    const response = await getReviewsByProIdRequest(proId, page);
    setLinks(parse(response.headers.link) || { ...initialLinks });
    return response.data;
  };
  const createReview = async (review) => {
    const response = await createReviewRequest(review);
    return response.data;
  };

  return {
    getReviews,
    getReviewByContractId,
    getReviewsByPostId,
    getReviewsByProId,
    createReview,
    links,
  };
};
export default useReviewsHook;
