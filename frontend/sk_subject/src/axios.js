import axios from "axios";
import { useAuthStore } from "./stores/auth";

const axiosInstance = axios.create({
    baseURL: "http://localhost:8080", // 백엔드 API 기본 URL
    timeout: 5000, // 요청 제한 시간(ms)
    headers: {
        "Content-Type": "application/json",
    },
});

// 요청 인터셉터 추가
axiosInstance.interceptors.request.use(
    (config) => {
        const token = sessionStorage.getItem("userToken"); // 토큰 가져오기
        console.log("토큰:", token);
        if (token) {
            config.headers.Authorization = `Bearer ${token}`; // Authorization 헤더 추가
            console.log(
                "Authorization 헤더 추가됨:",
                config.headers.Authorization
            );
        } else {
            console.log("토큰이 없습니다.");
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default axiosInstance; // 기본 export
