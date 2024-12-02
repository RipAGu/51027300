// src/stores/auth.js
import { defineStore } from "pinia";
import axios from "axios";

export const useAuthStore = defineStore("auth", {
    state: () => ({
        user: null,
        isAuthenticated: false,
    }),
    actions: {
        async login(credentials) {
            try {
                const response = await axios.post("/api/login", credentials);
                this.user = response.data.user;
                this.isAuthenticated = true;
            } catch (error) {
                throw new Error("아이디 또는 비밀번호가 일치하지 않습니다.");
            }
        },
        logout() {
            this.user = null;
            this.isAuthenticated = false;
        },
    },
});
