import {
  getUserByEmailRequest,
  loginRequest,
  getUserByIdRequest,
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

  return {
    getUserByEmail,
    getUserById,
    login,
  };
};
export default useUserHook;
