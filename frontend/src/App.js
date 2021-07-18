import jwt from 'jwt-decode';

import { Switch, Route } from 'react-router-dom';
import { useContext, useEffect, useState } from 'react';
import './App.css';
import Analytics from './pages/Analytics';
import Categories from './pages/Categories';
import CreateJobPost from './pages/CreateJobPost';
import { Home } from './pages/Home';
import MyContracts from './pages/MyContracts';
import Search from './pages/Search';
import Footer from './components/Footer';
import JobPost from './pages/JobPost';
import Profile from './pages/Profile';
import ScrollToTop from './utils/ScrollToTop';
import Login from './pages/Login';
import Register from './pages/Register';
import Hire from './pages/Hire';
import Account from './pages/Account';
import RecoverPass from './pages/RecoverPass';
import ChangePass from './pages/ChangePass';
import VerifyEmail from './pages/VerifyEmail';
import { UserContext, CategoriesAndZonesContext } from './context';
import { useUser, useCategories, useZones } from './hooks';
const App = () => {
  const { setCurrentUser, setToken, currentUser } = useContext(UserContext);
  const { getUserByEmail } = useUser();
  const { getCategories } = useCategories();
  const { getZones } = useZones();
  const [zones, setZones] = useState([]);
  const [categories, setCategories] = useState([]);
  /*
   * This function saves the current user in the context if the user is logged in.
   */
  const saveUserData = async (email) => {
    try {
      const user = await getUserByEmail(email);
      if (user) {
        setCurrentUser(user);
      }
    } catch (e) {
      console.log(e);
    }
  };

  /*
   * This functions load the categories and zones from the server.
   */
  const loadCategories = async () => {
    try {
      const categories = await getCategories();
      setCategories(categories);
    } catch (e) {
      console.log(e);
    }
  };

  const loadZones = async () => {
    try {
      const zones = await getZones();
      setZones(zones);
    } catch (e) {
      console.log(e);
    }
  };

  /*
   * This effect will be called when the page it's first loaded
   * It will check if the user is logged in and if so it will set the token and user data in the context.
   * It will also load the categories and zones data from the server.
   */
  useEffect(() => {
    const localStorageToken = localStorage.getItem('token');
    const sessionStorageToken = sessionStorage.getItem('token');
    if (localStorageToken && localStorageToken !== '') {
      const decoded = jwt(localStorageToken);
      console.log(decoded);
      if (decoded.exp * 1000 <= Date.now()) {
        localStorage.removeItem('token');
        setToken(null);
        setCurrentUser(null);
      } else {
        console.log('saving user in context', decoded.sub);
        saveUserData(decoded.sub);
        setToken(localStorageToken);
      }
    } else if (sessionStorageToken && sessionStorageToken !== '') {
      const decoded = jwt(sessionStorageToken);
      if (decoded.exp * 1000 <= Date.now()) {
        sessionStorage.removeItem('token');
        setToken(null);
        setCurrentUser(null);
      } else {
        console.log('saving user in context', decoded.sub);
        saveUserData(decoded.sub);
        setToken(sessionStorageToken);
      }
    }

      loadCategories();
      loadZones();
  }, []);

  return (
    <>
      <CategoriesAndZonesContext.Provider
        value={{ categories: categories, zones: zones }}
      >
        <ScrollToTop />
        <Switch>
          <Route path="/" exact component={Home} />
          <Route path="/categories" exact component={Categories} />
          <Route path="/create-job-post" exact component={CreateJobPost} />
          <Route path="/my-contracts" exact component={MyContracts} />
          <Route path="/analytics" exact component={Analytics} />
          <Route path="/search/:id" exact component={Search} />
          <Route path="/search" exact component={Search} />
          <Route path="/job/:id" exact component={JobPost} />
          <Route path="/profile/:id" exact component={Profile} />
          {!currentUser && <Route path="/login" exact component={Login} />}
          <Route path="/register" exact component={Register} />
          <Route path="/hire/package/:id" exact component={Hire} />
          <Route path="/account" exact component={Account} />
          <Route path="/recover" exact component={RecoverPass} />
          <Route path="/change-password" exact component={ChangePass} />
          <Route path="/token" exact component={VerifyEmail} />
          {/* TODO: 404 not found */}
          <Route path="/" component={Home} />
        </Switch>
        <Footer />
      </CategoriesAndZonesContext.Provider>
    </>
  );
};

export default App;
