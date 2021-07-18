import hireNetApi from './hirenetApi';

export const loginRequest = ({ email, password }) => {
  return hireNetApi.post('/login', {
    email: email,
    password: password,
  });
};

export const registerRequest = (props) => {
  return hireNetApi.post('/users', props);
};

export const verifyEmailRequest = ({ id, token }) => {
	console.log("url", `/users/${id}/verify`);
  return hireNetApi.post(`/users/${id}/verify`, { token: token });
};

export const uploadUserImageRequest = ({ id, image }) => {
  return hireNetApi.put(`/users/${id}/image`, {
    image: image,
  });
};

export const getUserByEmailRequest = (email) => {
  return hireNetApi.get('/users', {
    params: {
      email: email,
    },
  });
};

export const getUserByIdRequest = (id) => {
  return hireNetApi.get(`/users/${id}`);
};
export const getRankingsRequest = (id) => {
  return hireNetApi.get(`/users/${id}/rankings`, {
    headers: {
      'Authorization':
        'Bearer ' +
          (localStorage.getItem('token') || sessionStorage.getItem('token')) ||
        '',
    },
  });
};

export const getProfessionalInfoRequest = (id) => {
  return hireNetApi.get(`/users/${id}/professional-info`, {
    headers: {
      'Authorization':
        'Bearer ' +
          (localStorage.getItem('token') || sessionStorage.getItem('token')) ||
        '',
    },
  });
};
