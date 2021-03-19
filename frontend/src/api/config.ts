import axios from 'axios';

export const ApiInstance = axios.create({ baseURL: 'http://localhost:8080/' });
