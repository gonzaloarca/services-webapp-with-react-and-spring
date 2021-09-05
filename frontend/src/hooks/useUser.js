import {
  getUserByEmailRequest,
  loginRequest,
  getUserByIdRequest,
  registerRequest,
  verifyEmailRequest,
  getRankingsRequest,
  getRatesRequest,
  getProfessionalInfoRequest,
  recoverAccountRequest,
  recoverPassRequest,
  changePasswordRequest,
  changeAccountDataRequest,
  uploadUserImageRequest,
} from '../api/usersApi';

const fallbackAvatar = process.env.PUBLIC_URL + '/img/defaultavatar.svg';

const useUserHook = () => {
  const getUserByEmail = async (email) => {
    const response = await getUserByEmailRequest(email);
    return {
      ...response.data,
      image: response.data.image ? response.data.image : fallbackAvatar,
    };
  };

  const login = async ({ email, password }) => {
    const response = await loginRequest({ email, password });
    return response.headers.authorization.split(' ')[1];
  };

  const getUserById = async (id) => {
    const response = await getUserByIdRequest(id);
    return {
      ...response.data,
      image: response.data.image ? response.data.image : fallbackAvatar,
    };
  };

  const register = async (newUser) => {
    const response = await registerRequest({
      ...newUser,
      webPageUrl: process.env.REACT_APP_PAGE_URL + 'token',
    });
    return response.data;
  };

  const verifyEmail = async (data) => {
    const response = await verifyEmailRequest(data);
    return response.data;
  };

  const getRankings = async (userId) => {
    const response = await getRankingsRequest(userId);
    return response.data;
  };

  const getRates = async (userId) => {
    const response = await getRatesRequest(userId);
    return response.data;
  };

  const getProfessionalInfo = async (userId) => {
    const response = await getProfessionalInfoRequest(userId);
    return {
      ...response.data,
      image: response.data.image ? response.data.image : fallbackAvatar,
    };
  };

  const recoverAccount = async (data) => {
    const response = await recoverAccountRequest({
      ...data,
      webPageUrl: process.env.REACT_APP_PAGE_URL + 'change-password',
    });
    return response.data;
  };

  const recoverPass = async (data) => {
    const response = await recoverPassRequest(data);
    return response.data;
  };

  const changePassword = async (data) => {
    const response = await changePasswordRequest(data);
    return response.data;
  };

  const changeAccountData = async (data) => {
    const response = await changeAccountDataRequest(data);
    return response.data;
  };

  const changeAccountImage = async (userId, image) => {
    const formData = new FormData();
    formData.append('file', image);
    const response = await uploadUserImageRequest(userId, formData);
    return response.data;
  };

  return {
    getUserByEmail,
    getUserById,
    login,
    register,
    verifyEmail,
    getRankings,
    getRates,
    getProfessionalInfo,
    recoverAccount,
    recoverPass,
    changePassword,
    changeAccountData,
    changeAccountImage,
  };
};

export default useUserHook;
