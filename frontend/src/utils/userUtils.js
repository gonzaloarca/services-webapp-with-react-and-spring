export const isProfessional = (user) => {
  return user && user.roles.find((role) => role.toUpperCase() === 'PROFESSIONAL');
};
