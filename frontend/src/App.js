import { Switch, Route, Router } from 'react-router-dom';
import history from './history';
import './App.css';
import { Home } from './pages/Home';

const App = () => {
  return (
    <Router history={history}>
      <Switch>
        <Route path="/" exact component={Home} />
      </Switch>
    </Router>
  );
};

export default App;
