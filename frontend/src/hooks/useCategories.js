import {
  getCategoriesRequest,
  getCategoryByIdRequest,
} from '../api/categoriesApi';
const useCategoriesHook = () => {
  const categoryImageMap = new Map();
  categoryImageMap.set(0, process.env.PUBLIC_URL + '/img/plumbing.jpeg');
  categoryImageMap.set(1, process.env.PUBLIC_URL + '/img/electrician.jpeg');
  categoryImageMap.set(2, process.env.PUBLIC_URL + '/img/carpentry.jpeg');
  categoryImageMap.set(3, process.env.PUBLIC_URL + '/img/catering.png');
  categoryImageMap.set(4, process.env.PUBLIC_URL + '/img/painter.jpeg');
  categoryImageMap.set(5, process.env.PUBLIC_URL + '/img/teaching.jpeg');
  categoryImageMap.set(6, process.env.PUBLIC_URL + '/img/cleaning.png');
  categoryImageMap.set(7, process.env.PUBLIC_URL + '/img/babysitting.jpeg');

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
    categoryImageMap,
  };
};
export default useCategoriesHook;
