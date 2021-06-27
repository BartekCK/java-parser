import axios from 'axios';

export const ApiInstance = axios.create({
    baseURL: process.env.NODE_ENV === 'production' ? 'http://ec2-3-135-249-201.us-east-2.compute.amazonaws.com:8080/api/v1' : 'http://localhost:8080'});
