import { Switch, Route } from 'react-router-dom';
import './App.css';
import Analytics from './pages/Analytics';
import Categories from './pages/Categories';
import CreateJobPost from './pages/CreateJobPost';
import { Home } from './pages/Home';
import MyContracts from './pages/MyContracts';

const App = () => {
  return (
    <Switch>
      <Route path="/" exact component={Home} />
      <Route path="/categories" exact component={Categories} />
      <Route path="/create-job-post" exact component={CreateJobPost} />
      <Route path="/my-contracts" exact component={MyContracts} />
      <Route path="/analytics" exact component={Analytics} />
    </Switch>
  );
};

export default App;
