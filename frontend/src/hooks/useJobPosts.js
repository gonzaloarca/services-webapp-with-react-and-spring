import { useState } from 'react';
import { getJobPostsRequest, getJobPostByIdRequest } from '../api/jobPostsApi';
import categoryImageMap from '../utils/categories';
import parse from 'parse-link-header';
const useJobPostsHook = () => {
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
  const getJobPosts = async (page) => {
    const response = await getJobPostsRequest(page);
    setLinks(parse(response.headers.link) || { ...initialLinks });
    return response.data.map((jobPost) => ({
      ...jobPost,
      images:
        jobPost.images.length > 0
          ? jobPost.images
          : [categoryImageMap.get(jobPost.jobType.id)],
    }));
  };

  const getJobPostById = async (id) => {
    const response = await getJobPostByIdRequest(id);
    return {
      ...response.data,
      images:
        response.data.images.length > 0
          ? response.data.images
          : [categoryImageMap.get(response.data.jobType.id)],
    };
  };

  return {
    getJobPosts,
    getJobPostById,
    links,
  };
};
export default useJobPostsHook;
