export const isProfessional = (user) => {
  return (
    user && user.roles.find((role) => role.toUpperCase() === 'PROFESSIONAL')
  );
};

export const extractIdFromUserURL = (userURL) => {
  const userId = userURL.split('/').pop();
  return userId;
};
