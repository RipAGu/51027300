import { createRouter, createWebHistory } from "vue-router";
import LoginPage from "../views/LoginPage.vue"; // 로그인 페이지 컴포넌트
import BoardPage from "@/views/BoardPage.vue";
import BoardDetailPage from "@/views/BoardDetailPage.vue";
import BoardEditPage from "@/views/BoardEditPage.vue";
import BoardCreatePage from "@/views/BoardCreatePage.vue";

const routes = [
    { path: "/login", name: "LoginPage", component: LoginPage },
    { path: "/board", name: "BoardPage", component: BoardPage },
    {
        path: "/board/:id",
        name: "BoardDetailPage",
        component: BoardDetailPage,
        props: true,
    },
    {
        path: "/create",
        name: "BoardCreatePage",
        component: BoardCreatePage,
    },
    {
        path: "/edit/:id",
        name: "BoardEditPage",
        component: BoardEditPage,
        props: true,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
