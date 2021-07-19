import axios from 'axios';

export default axios.create({
  timeout: 5000,
  baseURL: process.env.PUBLIC_URL + '/api/v1',
  headers: {
    Accept: 'application/json',
  },
});
