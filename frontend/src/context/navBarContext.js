import { createContext, useState } from 'react';
import { parse } from 'query-string';
import { useLocation } from 'react-router';

const NavBarContext = createContext(null);

function useQuery() {
  return parse(useLocation().search);
}

export const NavBarContextProvider = ({ children }) => {
  let queryParameters = useQuery();

  const [searchBarQueryParams, setSearchBarQueryParams] = useState({
    zone: queryParameters.zone || '',
    category: queryParameters.category || '',
    query: queryParameters.query || '',
    orderBy: queryParameters.orderBy || '',
    page: queryParameters.page || 1,
  });

  const [navBarProps,setNavBarProps] = useState({currentSection: '', isTransparent: false});
  const [searchBarValue, setSearchBarValue] = useState('');
  return (
    <NavBarContext.Provider
      value={{
        searchBarQueryParams,
        setSearchBarQueryParams,
        navBarProps,
        setNavBarProps,
        searchBarValue,
        setSearchBarValue,
      }}
    >
      {children}
    </NavBarContext.Provider>
  );
};

export default NavBarContext;
