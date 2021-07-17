import React from 'react';
import NavBar from '../components/NavBar';

const Profile = ({ match }) => {
  return (
    <>
      <NavBar currentSection={'/search'} />
      <h1>Viendo profesional con id {match.params.id}</h1>
    </>
  );
};

export default Profile;
