import { getUserByEmailRequest, loginRequest } from '../api/usersApi';
const useUserHook = () => {

  const getUserByEmail = async (email) => {
    const response = await getUserByEmailRequest(email);
    return response.data;
  };

  const login = async ({ email, password }) => {
    const response = await loginRequest({ email, password });
    return response.headers.authorization;
  };

  return {
    getUserByEmail,
    login,
  };
};
export default useUserHook;
