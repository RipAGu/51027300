import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "http://localhost:8080", // 백엔드 API 기본 URL
    timeout: 5000, // 요청 제한 시간(ms)
    headers: {
        "Content-Type": "application/json",
    },
});

export default axiosInstance; // 기본 export
