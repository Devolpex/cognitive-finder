import axios from "axios";
import { env } from "../config/env";
import { useKeycloak } from "../context/KeycloakProvider";
// Create an instance of axios
const axiosInstance = axios.create({
  baseURL: env.api.url,
  headers: {
    "Content-Type": "application/json",
  },
});

// Request interceptor
axiosInstance.interceptors.request.use(
  (config) => {
    // Add any custom logic or modify request config before sending the request
    // For example, you can add an Authorization token if available
    const token = localStorage.getItem("token"); 
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    // Handle request error
    return Promise.reject(error);
  }
);

// Response interceptor
axiosInstance.interceptors.response.use(
  (response) => {
    // Any custom logic for handling response data
    return response;
  },
  (error) => {
    // Handle response error
    if (error.response.status === 401) {
      // Redirect to login page or handle token expiration
      console.log("Unauthorized, logging out...");
      // Add your logout logic here
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
