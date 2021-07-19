export const isProfessional = (user) => {
  return (
    user && user.roles.find((role) => role.toUpperCase() === 'PROFESSIONAL')
  );
};

export const isLoggedIn = () => {
  return localStorage.getItem('token') || sessionStorage.getItem('token');
};
