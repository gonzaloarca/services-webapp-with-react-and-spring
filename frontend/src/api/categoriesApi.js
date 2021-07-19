import hireNetApi from "./hirenetApi";

export const getCategoriesRequest = (language) =>{
    return hireNetApi.get("/categories",{
      headers:{
        'Accept-Language': language
      }
    });
}

export const getCategoryByIdRequest = (language,id) =>{
    return hireNetApi.get(`/categories/${id}`,{
      headers:{
        'Accept-Language': language
      },
    });
}