<template>
  <div class="min-h-screen flex flex-col bg-gray-100">
    <!-- 상단 네비게이션 -->
    <header class="bg-blue-600 text-white px-4 py-3 flex justify-between items-center">
      <h1 class="text-lg font-bold">게시판</h1>
      <button
        @click="handleLogin"
        class="bg-white text-blue-600 font-semibold py-1 px-4 rounded hover:bg-gray-200"
      >
        로그인
      </button>
    </header>

    <!-- 검색 및 게시글 목록 -->
    <main class="flex-grow p-4">
      <!-- 검색 -->
      <div class="flex justify-between items-center mb-4">
        <select v-model="searchType" class="border px-3 py-2 rounded">
          <option value="title">제목</option>
          <option value="authorName">작성자 ID</option>
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
import axiosInstance from "../axios";

export default {
  name: "BoardPage",
  data() {
    return {
      posts: [], // 게시글 데이터
      searchType: "title", // 검색 기준
      searchKeyword: "", // 검색어
      totalPages: 0, // 총 페이지 수
      currentPage: 0, // 현재 페이지 번호
    };
  },
  created() {
    this.fetchPosts(0); // 첫 페이지 데이터 로드
  },
  computed: {
    filteredPosts() {
      // 검색 필터 적용
      if (!this.searchKeyword) return this.posts;
      return this.posts.filter((post) =>
        post[this.searchType]?.toLowerCase().includes(this.searchKeyword.toLowerCase())
      );
    },
  },
  methods: {
    async fetchPosts(page) {
      try {
        const response = await axiosInstance.get(`/board/all?page=${page}&size=10`);
        const data = response.data;
        this.posts = data.content; // 게시글 목록
        this.totalPages = data.totalPages; // 총 페이지 수
        this.currentPage = page; // 현재 페이지 업데이트
      } catch (error) {
        console.error("게시글 목록을 불러오는 데 실패했습니다:", error);
      }
    },
    searchPosts() {
      this.fetchPosts(0); // 검색 시 첫 페이지로 이동
    },
    handleLogin() {
      this.$router.push("/login"); // 로그인 페이지로 이동
    },
  },
};
</script>

<style scoped>
/* 추가 스타일 필요 시 여기에 작성 */
</style>