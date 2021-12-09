import userContext,{UserContextProvider} from './userContext';
import categoriesZonesAndOrderByContext,{ConstantDataContextProvider} from './constantDataContext';
import navBarContext  from './navBarContext';

export const UserContext = userContext;
export const ConstantDataContext = categoriesZonesAndOrderByContext;
export const NavBarContext = navBarContext;

export {ConstantDataContextProvider as ConstantDataProvider , UserContextProvider};