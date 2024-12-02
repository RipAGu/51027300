// src/axios.js
import axios from "axios";

const instance = axios.create({
    baseURL: "http://localhost:3000", // 백엔드 API URL
});

export default instance;
