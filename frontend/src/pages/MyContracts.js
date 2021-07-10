import React from 'react';
import NavBar from '../components/NavBar';

const MyContracts = () => {
  return (
    <div>
      <NavBar currentSection={'/my-contracts'} />
      <h1>My Contracts</h1>
    </div>
  );
};

export default MyContracts;
