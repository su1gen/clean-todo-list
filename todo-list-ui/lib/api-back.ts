import axios from "axios";


const apiBack = axios.create({
    baseURL: process.env.INTERNAL_API_URL || 'http://127.0.0.1.nip.io:8000/api',
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
    },
    withCredentials: true,
});

apiBack.interceptors.response.use(
    (response) => {
        // Modify the response data here
        return response;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Добавляем токен, если он есть
// api.interceptors.request.use((config) => {
//     const token = getAuthToken();
//     if (token) {
//         config.headers.Authorization = `Bearer ${token}`;
//     }
//     return config;
// });

export default apiBack;