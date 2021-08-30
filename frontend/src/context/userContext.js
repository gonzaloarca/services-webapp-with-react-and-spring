import { createContext, useState } from "react";

const UserContext = createContext(null);


export const UserContextProvider = ({children}) => {
  
  const [currentUser, setCurrentUser] = useState(null);
  const [token, setToken] = useState(null);

  return (
    <UserContext.Provider value={{currentUser,setCurrentUser,token,setToken}}>
      {children}
    </UserContext.Provider>

  )
}

export default UserContext;
