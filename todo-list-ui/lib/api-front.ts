import axios from "axios";
import {showToastErrors} from "@/lib/utils";

const apiFront = axios.create({
    baseURL: process.env.NEXT_PUBLIC_API_URL || 'http://127.0.0.1.nip.io:8000/api',
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
    },
    withCredentials: true,
});

apiFront.interceptors.response.use(
    (response) => {
        // Modify the response data here
        return response;
    },
    (error) => {

        // if (error.response?.status === 401) {
        //     if (typeof window !== "undefined") {
        //         window.location.href = "/login";
        //     }
        // }
        // else if (error.response?.status === 404) {
        //     if (typeof window !== "undefined") {
        //         notFound()
        //     }
        // }
        // else {
            const errorMessages = error?.response?.data?.message || error?.response?.data?.messages
            showToastErrors(errorMessages)
        // }

        return Promise.reject(error);
    }
);

export default apiFront;