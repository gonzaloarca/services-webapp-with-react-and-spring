import React from 'react';

const Search = ({ match }) => {
  return (
    <div>
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
