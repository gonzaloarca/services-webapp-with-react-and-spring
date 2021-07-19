import { useState } from 'react';
import {
  getJobPostsRequest,
  getJobPostByIdRequest,
  createJobPostRequest,
  addPostImageRequest,
  editJobPostRequest,
} from '../api/jobPostsApi';
import categoryImageMap from '../utils/categories';
import parse from 'parse-link-header';
import { useJobPackages } from '.';
import { extractLastIdFromURL } from '../utils/urlUtils';
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

  const { createJobPackage } = useJobPackages();

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

  const addPostImage = async (postId, image) => {
    const formData = new FormData();
    formData.append('file', image);
    return await addPostImageRequest(postId, formData);
  };

  const createJobPost = async (jobPost) => {
    const response = await createJobPostRequest({
      availableHours: jobPost.hours,
      jobType: jobPost.category,
      title: jobPost.title,
      zones: jobPost.locations,
    });
    const postId = extractLastIdFromURL(response.headers.location);
    const packagesPromises = jobPost.packages.map((pack) => {
      return createJobPackage(postId, pack);
    });
    const imagePromises = Array.from(jobPost.images).map((image) => {
      return addPostImage(postId, image);
    });
    await Promise.all([...packagesPromises, ...imagePromises]);
    return response.headers.location;
  };

  const editJobPost = async (data, postId) => {
    const jobPost = {
      active: true,
      availableHours: data.hours,
      jobType: data.category,
      title: data.title,
      zones: data.locations,
    };
    return await editJobPostRequest(jobPost, postId);
  };

  return {
    getJobPosts,
    getJobPostById,
    createJobPost,
    editJobPost,
    links,
  };
};
export default useJobPostsHook;
