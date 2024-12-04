<template>
  <div class="min-h-screen flex flex-col bg-gray-100">
    <!-- 상단 영역 -->
    <header class="bg-blue-600 text-white px-4 py-3">
      <a href="/board" class="text-lg font-bold text-white hover:underline">
        게시글 상세
      </a>
    </header>


    <!-- 게시글 내용 -->
    <main class="flex-grow p-6">
      <div v-if="post" class="bg-white rounded shadow p-6">
        <!-- 첨부 파일(이미지 또는 링크) -->
        <div v-if="post.attachments.length > 0" class="mb-4">
          <div v-for="attachment in post.attachments" :key="attachment.url">
            <div v-if="attachment.type.startsWith('image/')">
              <img
                :src="attachment.url"
                alt="첨부 이미지"
                class="w-full rounded"
              />
            </div>
            <div v-else>
              <a
                :href="attachment.url"
                target="_blank"
                class="text-blue-600 underline"
              >
                첨부파일 다운로드
              </a>
            </div>
          </div>
        </div>

        <!-- 제목 -->
        <h2 class="text-2xl font-bold text-gray-800 mb-4">{{ post.title }}</h2>

        <!-- 게시글 내용 -->
        <p class="text-gray-700 mb-4">{{ post.content }}</p>

        <!-- 작성자 및 등록일 -->
        <div class="flex justify-between items-center text-sm text-gray-500 border-t pt-4">
          <span>작성자: {{ post.author }}</span>
          <span>등록일: {{ formatDate(post.createdAt) }}</span>
        </div>
      </div>
      <div v-else class="text-center">게시글을 불러오는 중...</div>
    </main>

    <!-- 하단 버튼 -->
    <footer v-if="post?.myPost" class="bg-white py-3 px-6 border-t flex justify-end space-x-4">
      <button
        @click="editPost"
        class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
      >
        수정
      </button>
      <button
        @click="deletePost"
        class="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700"
      >
        삭제
      </button>
    </footer>
  </div>
</template>

<script>
import axiosInstance from "../axios"; // 기본 export를 올바르게 import


export default {
  name: "PostDetailPage",
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      post: null, // 게시글 데이터
    };
  },
  created() {
    this.loadPost(this.id);
  },
  methods: {
    async loadPost(id) {
      try {
        const response = await axiosInstance.get(`http://localhost:8080/board/${id}`);
        this.post = response.data;
      } catch (error) {
        console.error("게시글 로드 실패:", error);
        alert("게시글을 불러오는 데 실패했습니다.");
        this.$router.push("/board");
      }
    },
    formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleDateString("ko-KR", {
        year: "numeric",
        month: "long",
        day: "numeric",
      });
    },
    editPost() {
      this.$router.push(`/edit/${this.id}`);
    },
    deletePost() {
      if (confirm("정말로 이 게시글을 삭제하시겠습니까?")) {
        axiosInstance
          .delete(`http://localhost:8080/board/${this.id}`)
          .then(() => {
            alert("게시글이 삭제되었습니다.");
            this.$router.push("/board");
          })
          .catch((error) => {
            console.error("게시글 삭제 실패:", error);
            alert("게시글 삭제에 실패했습니다.");
          });
      }
    },
  },
};
</script>

<style scoped>
/* 추가 스타일 필요 시 작성 */
</style>