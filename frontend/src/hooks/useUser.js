import {
  getUserByEmailRequest,
  loginRequest,
  getUserByIdRequest,
  registerRequest,
  getRankingsRequest,
  getProfessionalInfoRequest,
} from '../api/usersApi';
const useUserHook = () => {
  const getUserByEmail = async (email) => {
    const response = await getUserByEmailRequest(email);
    return response.data;
  };

  const login = async ({ email, password }) => {
    const response = await loginRequest({ email, password });
    return response.headers.authorization;
  };

  const getUserById = async (id) => {
    const response = await getUserByIdRequest(id);
    return response.data;
  };
  const register = async ({ username, phone, email, password }) => {
    const response = await registerRequest({
      username,
      phone,
      email,
      password,
    });
    return response.data;
  };

  const getRankings = async (userId) => {
    const response = await getRankingsRequest(userId);
    return response.data;
  };

  const getProfessionalInfo = async (userId) => {
    const response = await getProfessionalInfoRequest(userId);
    return response.data;
  };

  return {
    getUserByEmail,
    getUserById,
    login,
    register,
    getRankings,
    getProfessionalInfo,
  };
};
export default useUserHook;
