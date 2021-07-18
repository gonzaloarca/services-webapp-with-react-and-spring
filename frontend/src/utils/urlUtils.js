export const extractLastIdFromURL = (userURL) => {
  const userId = userURL.split('/').pop();
  return userId;
};
