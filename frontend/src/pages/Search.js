import React from 'react';
import NavBar from '../components/NavBar';

const Search = ({ match }) => {
  return (
    <div>
      <NavBar currentSection={'/search'} />
      <h1>Search</h1>
      {match.params.id ? (
        <>Searching by category with id {match.params.id}</>
      ) : (
        <>Searching by all categories</>
      )}
    </div>
  );
};

export default Search;
