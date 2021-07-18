import hirenetApi from './hirenetApi';

export const getReviewsByPostIdRequest = (postId, page = 1) => {
  return hirenetApi.get(`/reviews`, {
    params: {
      page: page,
      postId: postId,
    },
  });
};

export const getReviewsByProIdRequest = (proId, page = 1) => {
  return hirenetApi.get(`/reviews`, {
    params: {
      page: page,
      userId: proId,
      role: 'PROFESSIONAL',
    },
  });
};

export const getReviewsRequest = (page = 1) => {
  return hirenetApi.get('/reviews');
};

export const getReviewByContractIdRequest = (contractId) => {
  return hirenetApi.get(`/reviews/${contractId}`);
};

export const createReviewRequest = (review) => {
  return hirenetApi.post('/reviews', review, {
    headers: {
      'Authorization':
        'Bearer ' +
        (localStorage.getItem('token') ||
          sessionStorage.getItem('token') ||
          ''),
    },
  });
};
