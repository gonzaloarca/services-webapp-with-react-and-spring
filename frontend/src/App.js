import { Switch, Route } from 'react-router-dom';
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
import Hire from './pages/Hire';

const App = () => {
  return (
    <>
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
        <Route path="/login" exact component={Login} />
        <Route path="/hire/package/:id" exact component={Hire} />
      </Switch>
      <Footer />
    </>
  );
};

export default App;
