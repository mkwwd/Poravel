import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/MainView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "main",
            component: MainView,
        },
        {
            path: "/place",
            name: "place",
            component: () => import("../views/PlaceView.vue"),
        },
        {
            path: "/place/:content_id",
            name: "place-detail",
            component: () => import("../components/place/placeDetail.vue"),
        },
        {
            path: "/memory",
            name: "memory",
            component: () => import("../views/MemoryView.vue"),
        },
        {
            path: "/memory/:memory_pk",
            name: "memory-detail",
            component: () => import("../components/memory/MemoryDetail.vue"),
        },
        {
            path: "/login",
            name: "login",
            // route level code-splitting
            // this generates a separate chunk (About.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import("../views/LoginView.vue"),
        },
        {
            path: "/join",
            name: "join",
            component: () => import("../views/JoinView.vue"),
        },
        {
            path: "/findId",
            name: "find-id",
            component: () => import("../components/user/IdFind.vue"),
        },
        {
            path: "/findpw",
            name: "find-pw",
            component: () => import("../components/user/PwFind.vue"),
        },
        {
            path: "/mypage",
            name: "mypage",
            component: () => import("../views/MyPageView.vue"),
        },
        {
            path: "/mypageEdit",
            name: "mypage-edit",
            component: () => import("../components/user/MyPageEdit.vue"),
        },
        {
            path: "/help",
            name: "help",
            component: () => import("../views/HelpView.vue"),
            redirect: { name: "notice-list" },
            children: [
                {
                    path: "/notice",
                    name: "notice",
                    component: () => import("../views/TheNoticeView.vue"),
                    redirect: { name: "notice-list" },
                    children: [
                        {
                            path: "list",
                            name: "notice-list",
                            component: () => import("@/components/board/notice/BoardList.vue"),
                        },
                        {
                            path: "write/:article_type",
                            name: "article-write",
                            component: () => import("@/components/board/notice/BoardWrite.vue"),
                        },
                        {
                            path: "view/:article_type/:article_no",
                            name: "article-view",
                            component: () => import("@/components/board/notice/BoardDetail.vue"),
                        },
                        {
                            path: "modify/:article_type/:article_no",
                            name: "notice-modify",
                            component: () => import("@/components/board/notice/BoardModify.vue"),
                        },
                    ],
                },
                {
                    path: "/qna",
                    name: "qna",
                    component: () => import("../views/QnaView.vue"),
                    redirect: { name: "qna-list" },
                    children: [
                        {
                            path: "list",
                            name: "qna-list",
                            component: () => import("@/components/board/qna/QnaList.vue"),
                        },
                        {
                            path: "write/:article_type",
                            name: "qna-write",
                            component: () => import("@/components/board/qna/QnaWrite.vue"),
                        },
                        {
                            path: "view/:article_type/:article_no",
                            name: "qna-view",
                            component: () => import("@/components/board/qna/QnaDetail.vue"),
                        },
                        {
                            path: "view/:article_type/:article_no",
                            name: "qna-modify",
                            component: () => import("@/components/board/qna/QnaModify.vue"),
                        },
                    ],
                },
            ],
        },
    ],
});

export default router;
