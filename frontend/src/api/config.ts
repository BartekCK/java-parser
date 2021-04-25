import axios from 'axios';

export const ApiInstance = axios.create({
    baseURL: process.env.API_DOMAIN || 'http://localhost:8080/api/v1',
});
