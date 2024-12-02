<template>
    <div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
      <div class="mt-32 sm:mx-auto sm:w-full sm:max-w-sm">
        <form @submit.prevent="handleLogin" class="space-y-6">
          <!-- Email -->
          <div>
            <label for="name" class="block text-sm/6 font-medium text-gray-900">id</label>
            <div class="mt-2">
              <input
                type="text"
                id="name"
                v-model="name"
                autocomplete="username"
                required
                class="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6"
              />
            </div>
          </div>
  
          <!-- Password -->
          <div>
            <label for="password" class="block text-sm/6 font-medium text-gray-900">Password</label>
            <div class="mt-2">
              <input
                type="password"
                id="password"
                v-model="password"
                autocomplete="current-password"
                required
                class="block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6"
              />
            </div>
          </div>
  
          <!-- Submit Button -->
          <div>
            <button
              type="submit"
              class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm/6 font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
            >
              Sign in
            </button>
          </div>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import { useAuthStore } from "../stores/auth"; // Pinia store
  
  export default {
    name: "LoginPage",
    data() {
      return {
        name: "",
        password: "",
      };
    },
    methods: {
      async handleLogin() {
        const authStore = useAuthStore();
  
        try {
          // 백엔드 API 호출
          const response = await fetch("http://localhost:8080/auth/login", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              name: this.name,
              password: this.password,
            }),
          });
  
          if (!response.ok) {
            throw new Error("로그인에 실패했습니다.");
          }
  
          const { token } = await response.json(); // 백엔드가 반환하는 토큰
          authStore.login(token); // Pinia 상태 업데이트
          alert("로그인 성공!");
          this.$router.push("/board"); // 로그인 후 게시판으로 이동
        } catch (error) {
          alert(error.message);
        }
      },
    },
  };
  </script>