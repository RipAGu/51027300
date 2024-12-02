import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
    state: () => ({
        isLoggedIn: !!sessionStorage.getItem("userToken"), // 세션에서 초기 로그인 상태 확인
    }),
    actions: {
        login(token) {
            sessionStorage.setItem("userToken", token); // 토큰 저장
            this.isLoggedIn = true; // 로그인 상태 업데이트
        },
        logout() {
            sessionStorage.removeItem("userToken"); // 토큰 제거
            this.isLoggedIn = false; // 로그인 상태 업데이트
        },
    },
});
