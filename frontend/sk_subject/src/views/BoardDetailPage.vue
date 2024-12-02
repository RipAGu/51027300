<template>
  <div class="min-h-screen flex flex-col bg-gray-100">
    <!-- 상단 영역 -->
    <header class="bg-blue-600 text-white px-4 py-3">
      <h1 class="text-lg font-bold">게시글 상세</h1>
    </header>

    <!-- 게시글 내용 -->
    <main class="flex-grow p-6">
      <div class="bg-white rounded shadow p-6">
        <!-- 첨부 파일(이미지 또는 링크) -->
        <div v-if="post.attachment.type === 'image'" class="mb-4">
          <img
            :src="post.attachment.url"
            alt="첨부 이미지"
            class="w-full rounded"
          />
        </div>
        <div v-else-if="post.attachment.type === 'file'" class="mb-4">
          <a
            :href="post.attachment.url"
            target="_blank"
            class="text-blue-600 underline"
          >
            첨부파일 다운로드
          </a>
        </div>

        <!-- 제목 -->
        <h2 class="text-2xl font-bold text-gray-800 mb-4">{{ post.title }}</h2>

        <!-- 게시글 내용 -->
        <p class="text-gray-700 mb-4">{{ post.content }}</p>

        <!-- 작성자 및 등록일 -->
        <div class="flex justify-between items-center text-sm text-gray-500 border-t pt-4">
          <span>작성자: {{ post.author }}</span>
          <span>등록일: {{ post.date }}</span>
        </div>
      </div>
    </main>

    <!-- 하단 버튼 -->
    <footer class="bg-white py-3 px-6 border-t flex justify-end space-x-4">
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
    loadPost(id) {
      // 서버에서 게시글 데이터를 가져오는 로직 (예시 데이터 사용)
      const examplePosts = [
        {
          id: "1",
          title: "첫 번째 게시글",
          content: "첫 번째 게시글 내용",
          author: "user1",
          date: "2024-12-01",
          attachment: {
            type: "image",
            url: "https://via.placeholder.com/800x400",
          },
        },
        {
          id: "2",
          title: "두 번째 게시글",
          content: "두 번째 게시글 내용",
          author: "user2",
          date: "2024-12-02",
          attachment: {
            type: "file",
            url: "https://example.com/sample-file.pdf",
          },
        },
      ];
      this.post = examplePosts.find((post) => post.id === id) || null;

      if (!this.post) {
        alert("게시글을 찾을 수 없습니다.");
        this.$router.push("/board");
      }
    },
    editPost() {
      this.$router.push(`/edit/${this.id}`);
    },
    deletePost() {
      if (confirm("정말로 이 게시글을 삭제하시겠습니까?")) {
        console.log("게시글 삭제:", this.id);
        this.$router.push("/board");
      }
    },
  },
};
</script>