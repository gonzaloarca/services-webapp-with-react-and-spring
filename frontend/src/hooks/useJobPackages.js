import { useState } from 'react';
import {
  getJobPackageByPostIdAndPackageIdRequest,
  getJobPackagesByPostIdRequest,
  createJobPackageRequest,
  editJobPackageRequest,
} from '../api/packagesApi';
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

  const createJobPackage = async (postId, data) => {
    const response = await createJobPackageRequest(postId, data);
    return response.data;
  };

  const editJobPackage = async (jobPackage, postId, packageId) => {
    jobPackage.isActive = true;
    return await editJobPackageRequest(jobPackage, postId, packageId);
  };

  const deleteJobPackage = async (jobPackage, postId) => {
    jobPackage.isActive = false;
    jobPackage.rateType = jobPackage.rateType.id;
    return await editJobPackageRequest(jobPackage, postId, jobPackage.id);
  };

  return {
    getJobPackagesByPostId,
    getJobPackageByPostIdAndPackageId,
    createJobPackage,
    editJobPackage,
    deleteJobPackage,
    links,
  };
};
export default useJobPackagesHook;
