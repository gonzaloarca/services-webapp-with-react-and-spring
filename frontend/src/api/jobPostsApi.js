import hirenetApi from './hirenetApi';

export const getJobPostsRequest = (page = 1) => {
  return hirenetApi.get('/job-posts', {
    params: {
      page: page,
    },
  });
};

export const getJobPostByIdRequest = (id) => {
  return hirenetApi.get(`/job-posts/${id}`);
};

export const createJobPostRequest = (jobPost) => {
  return hirenetApi.post('/job-posts', jobPost, {
    headers: {
      'Authorization':
        'Bearer ' +
        (localStorage.getItem('token') ||
          sessionStorage.getItem('token') ||
          ''),
    },
  });
};

export const editJobPostRequest = (jobPost, id) => {
  return hirenetApi.put(`/job-posts/${id}`, jobPost, {
    headers: {
      'Authorization':
        'Bearer ' +
        (localStorage.getItem('token') ||
          sessionStorage.getItem('token') ||
          ''),
    },
  });
};

export const addPostImageRequest = (jobPostId, file) => {
  return hirenetApi.post(`/job-posts/${jobPostId}/images`, file, {
    headers: {
      'Authorization':
        'Bearer ' +
        (localStorage.getItem('token') ||
          sessionStorage.getItem('token') ||
          ''),
      'Content-type': 'multipart/form-data',
    },
  });
};
