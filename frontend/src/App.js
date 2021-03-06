import jwt from 'jwt-decode';

import { Switch, Route } from 'react-router-dom';
import { useContext, useEffect } from 'react';
import { Helmet } from 'react-helmet';
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
import RecoverAccount from './pages/RecoverAccount';
import RecoverPass from './pages/RecoverPass';
import VerifyEmail from './pages/VerifyEmail';
import { UserContext } from './context';
import {
  useUser,
} from './hooks';
import Packages from './pages/Packages';
import AddPackage from './pages/AddPackage';
import EditPackage from './pages/EditPackage';
import RegisterSuccess from './pages/RegisterSuccess';
import { useTranslation } from 'react-i18next';
import { isProfessional } from './utils/userUtils';
import Error404 from './pages/Error404';
import EditJobPost from './pages/EditJobPost';
import JobPostSuccess from './pages/JobPostSuccess';
import Error from './pages/Error';
import NavBar from './components/NavBar';

const App = () => {
  const { setCurrentUser, setToken, currentUser } = useContext(UserContext);
  const { getUserByEmail } = useUser();
  
  const { t } = useTranslation();
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
      // console.log(e);
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
      if (decoded.exp * 1000 <= Date.now()) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        setToken(null);
        setCurrentUser(null);
      } else {
        saveUserData(decoded.sub);
        setToken(localStorageToken);
      }
    } else if (sessionStorageToken && sessionStorageToken !== '') {
      const decoded = jwt(sessionStorageToken);
      if (decoded.exp * 1000 <= Date.now()) {
        sessionStorage.removeItem('token');
        sessionStorage.removeItem('user');
        setToken(null);
        setCurrentUser(null);
      } else {
        saveUserData(decoded.sub);
        setToken(sessionStorageToken);
      }
    }
    return () => {
      setToken(null);
      setCurrentUser(null);
    }
  }, []);

  return (
    <>
        <Helmet>
          <title>{t('defaulttitle')}</title>
        </Helmet>
        <NavBar />
        <ScrollToTop />
        <Switch>
          <Route path="/" exact component={Home} />
          <Route path="/categories" exact component={Categories} />
          <Route path="/create-job-post" exact component={CreateJobPost} />
          <Route
            path="/my-contracts/:activeTab?/:activeState?"
            exact
            component={MyContracts}
          />
          {isProfessional(currentUser) && (
            <Route path="/analytics" exact component={Analytics} />
          )}
          <Route path="/search" exact component={Search} />
          <Route path="/job/:id" exact component={JobPost} />
          <Route
            path="/job/:id/success/:edit?"
            exact
            component={JobPostSuccess}
          />
          <Route path="/job/:id/edit" exact component={EditJobPost} />
          <Route path="/job/:id/packages" exact component={Packages} />
          <Route path="/job/:id/packages/add" exact component={AddPackage} />
          <Route
            path="/job/:id/packages/:packId/edit"
            exact
            component={EditPackage}
          />
          <Route path="/profile/:id/:activeTab?" exact component={Profile} />
          {!currentUser && <Route path="/login" exact component={Login} />}
          {!currentUser && (
            <Route path="/register" exact component={Register} />
          )}
          {!currentUser && (
            <Route path="/register/success" exact component={RegisterSuccess} />
          )}
          <Route path="/hire/:postId/package/:id" exact component={Hire} />
          <Route path="/account/:activeTab?" exact component={Account} />
          <Route path="/recover" exact component={RecoverAccount} />
          <Route path="/change-password" exact component={RecoverPass} />
          <Route path="/token" exact component={VerifyEmail} />
          <Route path="/error" exact component={Error} />
          <Route path="/" component={Error404} />
        </Switch>
        <Footer />
    </>
  );
};

export default App;
