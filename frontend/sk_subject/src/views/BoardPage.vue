<template>
  <div class="min-h-screen flex flex-col bg-gray-100">
    <!-- 상단 네비게이션 -->
    <header class="bg-blue-600 text-white px-4 py-3 flex justify-between items-center">
      <h1 class="text-lg font-bold">게시판</h1>
      <div>
        <button
          v-if="!authStore.isLoggedIn"
          @click="handleLogin"
          class="bg-white text-blue-600 font-semibold py-1 px-4 rounded hover:bg-gray-200"
        >
          로그인
        </button>
        <button
          v-else
          @click="handleLogout"
          class="bg-red-600 text-white font-semibold py-1 px-4 rounded hover:bg-red-700"
        >
          로그아웃
        </button>
      </div>
    </header>

    <!-- 검색 및 게시글 목록 -->
    <main class="flex-grow p-4">
      <!-- 검색 -->
      <div class="flex justify-between items-center mb-4">
        <div class="flex items-center w-full">
          <select v-model="searchType" class="border px-3 py-2 rounded">
            <option value="title">제목</option>
            <option value="username">작성자 ID</option>
          </select>
          <input
            type="text"
            v-model="searchKeyword"
            class="border px-3 py-2 rounded w-full ml-4 mr-8"
            placeholder="검색어를 입력하세요"
          />
          <button
            @click="searchPosts"
            class="bg-blue-600 text-white rounded w-20 h-8 hover:bg-blue-700"
          >
            검색
          </button>
        </div>
        <!-- 글쓰기 버튼 (로그인된 사용자만 표시) -->
        <button
          v-if="authStore.isLoggedIn"
          @click="createPost"
          class="ml-4 bg-green-600 text-white font-semibold px-4 py-2 w-20 rounded hover:bg-green-700"
        >
          글쓰기
        </button>
      </div>

      <!-- 게시글 목록 -->
      <table class="table-auto w-full bg-white rounded shadow border">
        <thead>
          <tr class="bg-gray-200 text-left">
            <th class="px-4 py-3 text-sm font-semibold text-gray-600 border-b">제목</th>
            <th class="px-4 py-3 text-sm font-semibold text-gray-600 border-b">작성자 ID</th>
            <th class="px-4 py-3 text-sm font-semibold text-gray-600 border-b">조회수</th>
            <th class="px-4 py-3 text-sm font-semibold text-gray-600 border-b">첨부파일 여부</th>
            <th class="px-4 py-3 text-sm font-semibold text-gray-600 border-b">등록일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="post in filteredPosts" :key="post.id" class="hover:bg-gray-100">
            <td class="px-4 py-3 text-sm text-gray-700 border-b">
              <router-link :to="`/board/${post.id}`" class="text-blue-500 hover:underline">
                {{ post.title }}
              </router-link>
            </td>
            <td class="px-4 py-3 text-sm text-gray-700 border-b">{{ post.authorName }}</td>
            <td class="px-4 py-3 text-sm text-gray-700 border-b">{{ post.view }}</td>
            <td class="px-4 py-3 text-sm border-b">
              <span v-if="post.hasAttachment" class="text-green-500">O</span>
              <span v-else class="text-red-500">X</span>
            </td>
            <td class="px-4 py-3 text-sm text-gray-700 border-b">
              {{ new Date(post.createdAt).toLocaleDateString() }}
            </td>
          </tr>
        </tbody>
      </table>
    </main>

    <!-- 페이지네이션 -->
    <footer class="bg-white py-3 flex justify-center items-center border-t">
      <button
        v-for="page in totalPages"
        :key="page"
        @click="fetchPosts(page - 1)"
        class="mx-1 px-3 py-1 border rounded hover:bg-gray-200"
        :class="{ 'bg-blue-600 text-white': currentPage === page - 1 }"
      >
        {{ page }}
      </button>
    </footer>
  </div>
</template>

<script>
import { useAuthStore } from "../stores/auth"; // Pinia store for authentication
import axiosInstance from "../axios"; // 기본 export를 올바르게 import

export default {
  name: "BoardPage",
  setup() {
    const authStore = useAuthStore();
    return { authStore };
  },
  data() {
    return {
      posts: [], // 게시글 데이터
      filteredPosts: [], // 검색 결과 데이터
      searchType: "title", // 검색 기준
      searchKeyword: "", // 검색어
      totalPages: 0, // 총 페이지 수
      currentPage: 0, // 현재 페이지 번호
    };
  },
  created() {
    this.fetchPosts(0); // 첫 페이지 데이터 로드
  },
  methods: {
    async fetchPosts(page) {
  try {
    const params = {
      page: page,
      size: 10,
    };

    // 검색 조건이 있을 경우 쿼리 파라미터에 추가
    if (this.searchKeyword) {
      params.searchType = this.searchType;
      params.keyword = this.searchKeyword;
    }

    const response = await axiosInstance.get("/board/all", { params });
    const data = response.data;

    // 데이터 갱신
    this.posts = data.content;
    this.filteredPosts = this.posts;
    this.totalPages = data.totalPages;
    this.currentPage = page; // 현재 페이지 업데이트
  } catch (error) {
    console.error("게시글 목록을 불러오는 데 실패했습니다:", error);
  }
},
async searchPosts() {
  if (!this.searchKeyword) {
    this.fetchPosts(0); // 검색어가 없으면 전체 게시글 표시
    return;
  }

  try {
    // 첫 페이지부터 검색
    const params = {
      searchType: this.searchType,
      keyword: this.searchKeyword,
      page: 0,
      size: 10,
    };

    const response = await axiosInstance.get("/board/all", { params });
    const data = response.data;

    // 검색 결과 갱신
    this.posts = data.content;
    this.filteredPosts = this.posts;
    this.totalPages = data.totalPages;
    this.currentPage = 0; // 검색 결과는 항상 첫 페이지로 설정
  } catch (error) {
    console.error("검색 요청 실패:", error);
    alert("검색 중 오류가 발생했습니다.");
  }
},
    handleLogin() {
      this.$router.push("/login"); // 로그인 페이지로 이동
    },
    handleLogout() {
      this.authStore.logout(); // Pinia 스토어에서 로그아웃 처리
      alert("로그아웃 성공!");
    },
    createPost() {
      this.$router.push("/board/create"); // 글쓰기 페이지로 이동
    },
  },
};
</script>