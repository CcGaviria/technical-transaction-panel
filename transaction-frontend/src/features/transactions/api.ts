import axios from 'axios';

export const api = axios.create({
  baseURL: `http://${window.location.hostname}:8080`,
  timeout: 8000,
});