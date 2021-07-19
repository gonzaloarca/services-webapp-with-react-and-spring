import hirenetApi from './hirenetApi';

export const getJobPackagesByPostIdRequest = (postId, page = 1) => {
  return hirenetApi.get(`/job-posts/${postId}/packages`, {
    params: {
      page: page,
    },
  });
};

export const getJobPackageByPostIdAndPackageIdRequest = (postId, packageId) => {
  return hirenetApi.get(`/job-posts/${postId}/packages/${packageId}`);
};

export const createJobPackageRequest = (postId, jobPackage) => {
  return hirenetApi.post(`/job-posts/${postId}/packages `, jobPackage, {
    headers: {
      'Authorization':
        'Bearer ' +
        (localStorage.getItem('token') ||
          sessionStorage.getItem('token') ||
          ''),
    },
  });
};

export const editJobPackageRequest = (jobPackage, postId, packageId) => {
  return hirenetApi.put(
    `/job-posts/${postId}/packages/${packageId}`,
    jobPackage,
    {
      headers: {
        'Authorization':
          'Bearer ' +
          (localStorage.getItem('token') ||
            sessionStorage.getItem('token') ||
            ''),
      },
    }
  );
};
