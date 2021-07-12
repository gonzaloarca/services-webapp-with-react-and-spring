import React from 'react';
import NavBar from '../components/NavBar';

const JobPost = ({ match }) => {
  return (
    <>
      <NavBar currentSection={'/search'} />
      Viendo jobpost con id {match.params.id}
    </>
  );
};

export default JobPost;
