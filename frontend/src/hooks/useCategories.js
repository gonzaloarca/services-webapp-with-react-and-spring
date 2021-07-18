import {
  getCategoriesRequest,
  getCategoryByIdRequest,
} from '../api/categoriesApi';

import categoryImageMap from '../utils/categories';
const useCategoriesHook = () => {
  const language = navigator.language || navigator.userLanguage || 'en-US';
  const getCategories = async () => {
    const response = await getCategoriesRequest(language);
    return response.data.map((category) => ({
      ...category,
      image: categoryImageMap.get(category.id),
    }));
  };
  const getCategoryById = async (zoneId) => {
    const response = await getCategoryByIdRequest(language, zoneId);
    return { ...response.data, image: categoryImageMap.get(response.data.id) };
  };
  return {
    getCategories,
    getCategoryById,
  };
};
export default useCategoriesHook;
