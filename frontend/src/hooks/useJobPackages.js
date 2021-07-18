import { useState } from 'react';
import {
  getJobPackageByPostIdAndPackageIdRequest,
  getJobPackagesByPostIdRequest,
} from '../api/packagesApi';
import categoryImageMap from '../utils/categories';
import parse from 'parse-link-header';
const useJobPackagesHook = () => {
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

  const getJobPackagesByPostId = async (postId, page) => {
    const response = await getJobPackagesByPostIdRequest(postId, page);
    setLinks(parse(response.headers.link) || { ...initialLinks });
    return response.data;
  };

  const getJobPackageByPostIdAndPackageId = async (postId, id) => {
    const response = await getJobPackageByPostIdAndPackageIdRequest(postId, id);
    return response.data;
  };

  return {
    getJobPackagesByPostId,
    getJobPackageByPostIdAndPackageId,
    links,
  };
};
export default useJobPackagesHook;
