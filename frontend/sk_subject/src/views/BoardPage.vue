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
            <option value="author">작성자 ID</option>
          </select>
          <input
            type="text"
            v-model="searchKeyword"
            class="border px-3 py-2 rounded w-full mx-2"
            placeholder="검색어를 입력하세요"
          />
          <button
            @click="searchPosts"
            class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            검색
          </button>
        </div>
  
        <!-- 게시글 목록 -->
        <table class="table-auto w-full bg-white rounded shadow">
          <thead>
            <tr class="bg-gray-200">
              <th class="px-4 py-2">제목</th>
              <th class="px-4 py-2">작성자 ID</th>
              <th class="px-4 py-2">조회수</th>
              <th class="px-4 py-2">첨부파일 여부</th>
              <th class="px-4 py-2">등록일</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="post in filteredPosts" :key="post.id" class="border-t">
              <td class="px-4 py-2">{{ post.title }}</td>
              <td class="px-4 py-2">{{ post.author }}</td>
              <td class="px-4 py-2 text-center">{{ post.views }}</td>
              <td class="px-4 py-2 text-center">
                <span v-if="post.hasFile" class="text-green-500">O</span>
                <span v-else class="text-red-500">X</span>
              </td>
              <td class="px-4 py-2">{{ post.date }}</td>
            </tr>
          </tbody>
        </table>
      </main>
  
      <!-- 페이지네이션 -->
      <footer class="bg-white py-3 flex justify-center items-center border-t">
        <button
          v-for="page in totalPages"
          :key="page"
          @click="currentPage = page"
          class="mx-1 px-3 py-1 border rounded hover:bg-gray-200"
          :class="{ 'bg-blue-600 text-white': currentPage === page }"
        >
          {{ page }}
        </button>
      </footer>
    </div>
  </template>
  
  <script>
  export default {
    name: "BoardPage",
    data() {
      return {
        posts: [
          // 예제 데이터
          { id: 1, title: "첫 번째 글", author: "user1", views: 123, hasFile: true, date: "2024-12-01" },
          { id: 2, title: "두 번째 글", author: "user2", views: 45, hasFile: false, date: "2024-12-02" },
          // 추가 데이터 삽입
        ],
        searchType: "title",
        searchKeyword: "",
        currentPage: 1,
        postsPerPage: 10,
      };
    },
    computed: {
      filteredPosts() {
        const startIndex = (this.currentPage - 1) * this.postsPerPage;
        const endIndex = startIndex + this.postsPerPage;
        return this.posts
          .filter((post) =>
            post[this.searchType].toLowerCase().includes(this.searchKeyword.toLowerCase())
          )
          .slice(startIndex, endIndex);
      },
      totalPages() {
        return Math.ceil(this.posts.length / this.postsPerPage);
      },
    },
    methods: {
      handleLogin() {
        // 로그인 페이지로 이동
        this.$router.push("/login");
      },
      searchPosts() {
        this.currentPage = 1; // 검색 시 첫 페이지로 이동
      },
    },
  };
  </script>
  
  <style scoped>
  /* 필요한 추가 스타일 작성 */
  </style>