import axios from 'axios'

export default axios.create({
  timeout: 5000,
  baseURL: '/api/v1',
  headers: {
    Accept: 'application/json',
  }
})