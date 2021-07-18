import hireNetApi from "./hirenetApi";

export const loginRequest = ({email,password}) =>{
    return hireNetApi.post("/login",{
        email: email,
        password: password
    });
}

export const getUserByEmailRequest = (email) =>{
    return hireNetApi.get("/users",{
      params:{
        email: email
      }
    });
}