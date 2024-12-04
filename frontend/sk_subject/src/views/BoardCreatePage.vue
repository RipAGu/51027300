<template>
    <div class="min-h-screen flex flex-col bg-gray-100">
      <!-- 상단 네비게이션 -->
      <header class="bg-blue-600 text-white px-4 py-3">
      <a href="/board" class="text-lg font-bold text-white hover:underline">
        게시글 작성
      </a>
    </header>
  
      <main class="flex-grow p-6">
        <div class="bg-white rounded shadow p-6">
          <!-- 제목 입력 -->
          <div class="mb-4">
            <label for="title" class="block text-sm font-medium text-gray-700">제목</label>
            <input
              id="title"
              v-model="title"
              type="text"
              class="w-full border rounded px-3 py-2 mt-1"
              placeholder="제목을 입력하세요"
            />
          </div>
  
          <!-- 내용 입력 -->
          <div class="mb-4">
            <label for="content" class="block text-sm font-medium text-gray-700">내용</label>
            <textarea
              id="content"
              v-model="content"
              rows="10"
              class="w-full border rounded px-3 py-2 mt-1 resize-none"
              placeholder="내용을 입력하세요"
            ></textarea>
          </div>
  
          <!-- 파일 업로드 -->
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700">파일/이미지 업로드</label>
            <input
              type="file"
              @change="handleFileUpload"
              accept="image/*,application/pdf"
              class="mt-2"
            />
            <div v-if="attachmentType === 'image'" class="mt-4">
              <img :src="attachmentUrl" alt="미리보기" class="max-w-full rounded shadow" />
            </div>
            <div v-else-if="attachmentType === 'file'" class="mt-4">
              <a
                :href="attachmentUrl"
                target="_blank"
                class="text-blue-600 underline"
              >
                업로드된 파일 보기
              </a>
            </div>
          </div>
  
          <!-- 버튼 -->
          <div class="flex justify-end">
            <button
              @click="submitPost"
              class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
            >
              게시글 작성
            </button>
          </div>
        </div>
      </main>
    </div>
  </template>
  
  <script>
  import axiosInstance from "../axios";
  
  export default {
    name: "BoardCreatePage",
    data() {
      return {
        title: "",
        content: "",
        attachmentUrl: "",
        attachmentType: "", // "image" or "file"
      };
    },
    methods: {
      async handleFileUpload(event) {
        const file = event.target.files[0];
        if (!file) return;
  
        const formData = new FormData();
        formData.append("file", file);
  
        try {
          const response = await axiosInstance.post("/file", formData, {
            headers: { "Content-Type": "multipart/form-data" },
          });
          this.attachmentUrl = "http://localhost:8080" + response.data.url;
          this.attachmentType = file.type.startsWith("image") ? "image" : "file";
        } catch (error) {
          console.error("파일 업로드 실패:", error);
          alert("파일 업로드 중 오류가 발생했습니다.");
        }
      },
      async submitPost() {
        if (!this.title || !this.content) {
          alert("제목과 내용을 모두 입력해주세요.");
          return;
        }
        try {
          await axiosInstance.post("/board", {
            title: this.title,
            content: this.content,
            attachmentUrl: this.attachmentUrl || null,
          });
          alert("게시글이 성공적으로 작성되었습니다.");
          this.$router.push("/board");
        } catch (error) {
          console.error("게시글 작성 실패:", error);
          alert("게시글 작성 중 오류가 발생했습니다.");
        }
      },
    },
  };
  </script>
  
  <style>
  textarea {
    font-family: Arial, sans-serif;
    font-size: 14px;
  }
  </style>