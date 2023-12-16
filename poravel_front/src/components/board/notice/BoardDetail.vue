<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle } from "@/api/board";
import { deleteArticle } from "@/api/board";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { article_type, article_no } = route.params;

const article = ref({});

onMounted(() => {
    getArticle();
});

const cut = (val) => {
    const val2 = val + "";
    const val3 = val2.substring(0, 10);
    return val3;
};

// const item = ref({
//     article_pk: 0,
// });

const getArticle = () => {
    // const { articleno } = route.params;
    console.log(article_type);
    console.log(article_no + "번글 얻으러 가자!!!");
    detailArticle(article_type, article_no, ({ data }) => {
        article.value = data;
    }),
        (error) => {
            console.log(error);
        };

    // API 호출
};

function moveList() {
    router.push({ name: "notice-list" });
}

function moveModify() {
    if (userInfo.value === null) {
        alert("로그인을 해주세요")
        router.push({ name: "login" })
    } else if (userInfo.value.user_grade === "admin"){
        router.push({ name: "notice-modify", params: { article_type, article_no } });
    }
    else { 
        alert("관리자가 아닙니다.")
    }
}

function onDeleteArticle() {

    if (userInfo.value === null) {
        alert("로그인을 해주세요")
        router.push({ name: "login" })
    } else if (userInfo.value.user_grade === "admin") {
        deleteArticle(article.value.article_pk, () => {
        console.log("삭제완료");
        router.push({ name: "notice-list" });
    }),
        (error) => {
            console.log(error);
        };
    } else { 
        alert("관리자가 아닙니다.")
    }
}
</script>

<template>
    <div class="detail-area">
        <div class="detail-head">
            <div class="detail-title">{{ article.subject }}</div>
            <div class="detail-hit">{{ article.hit }}</div>
            <div class="detail-day">{{ cut(article.register_time) }}</div>
        </div>
        <div class="detail-main">
            {{ article.content }}
        </div>
    </div>
    <div class="detail-btn-area">
        <button type="button" class="btn" @click="moveList">목록으로</button>
        <button type="button" class="btn" @click="moveModify">수정하기</button>
        <button type="button" class="btn" @click="onDeleteArticle">삭제하기</button>
    </div>
</template>

<style scoped>
.detail-area {
    margin-top: 50px;
    margin-bottom: 10px;
    min-height: 500px;
    border-color: var(--color-brown-70);
    border-style: solid;
    border-width: 2px 0 2px 0;
}

.detail-head {
    display: flex;
    align-items: center;
    height: 35px;
    padding: 20px;
    border-color: var(--color-brown-70);
    border-style: solid;
    border-width: 0px 0 2px 0;
}

.detail-title {
    font-weight: bold;
    flex-basis: 85%;
}

.detail-hit {
    font-weight: bold;
    text-align: right;
    flex-basis: 5%;
}

.detail-day {
    font-weight: bold;
    text-align: end;
    flex-basis: 10%;
}

.detail-main {
    white-space: pre-line;
    padding: 20px;
}

.detail-btn-area {
    margin-bottom: 40px;
    display: flex;
    justify-content: end;
    gap: 5px;
}
.detail-btn-area button {
    padding: 5px;
}
</style>
