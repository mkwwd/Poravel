<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle, addReply } from "@/api/board";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import ReplyItem from "../item/replyItem.vue";

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

const star = (val) => {
    const val2 = val + "";
    const pre = val2.substring(0, 2);
    const length = val2.length;
    const suf = val2.substring(2, length);
    const star = "*".repeat(suf.length);
    const val3 = pre + star;
    return val3;
};

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
        console.log(article);
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
        alert("로그인을 해주세요");
        router.push({ name: "login" });
    } else if (userInfo.value.user_id === article.user_id) {
        router.push({ name: "qna-modify", params: { article_type, article_no } });
    } else {
        alert("본인의 글만 수정 가능합니다.");
    }
}

function onDeleteArticle() {
    if (userInfo.value === null) {
        alert("로그인을 해주세요");
        router.push({ name: "login" });
    } else if (userInfo.value.user_id === article.user_id) {
        deleteArticle(article.value.article_pk, () => {
            console.log("삭제완료");
            router.push({ name: "qna-list" });
        }),
            (error) => {
                console.log(error);
            };
    } else {
        alert("본인의 글만 삭제 가능합니다.");
    }
}

const reply = ref({
    content: "",
});

function add() {
    if (userInfo.value === null) {
        alert("로그인을 해주세요");
        router.push({ name: "login" });
    } else if (userInfo.value.user_grade === "admin") {
        addReply(article_type, article_no, reply, () => {
            reply.value.content = "";
            console.log("등록성공");
            getArticle();
        }),
            (error) => {
                console.log(error);
            };
    } else {
        alert("관리자가 아닙니다.");
    }
}
</script>

<template>
    <div class="detail-area">
        <div class="detail-head">
            <div class="detail-title">{{ article.subject }}</div>
            <div class="detail-user">{{ star(article.user_id) }}</div>
            <div class="detail-hit">{{ article.hit }}</div>
            <div class="detail-day">{{ cut(article.register_time) }}</div>
        </div>
        <div class="detail-main">
            {{ article.content }}
        </div>
    </div>
    <div style="font-weight: bold; font-size: 20px">답변</div>
    <div class="reply-area">
        <ReplyItem v-for="reply in article.answerList" :reply="reply" @reply-update="getArticle"></ReplyItem>
        <div style="position: relative">
            <textarea class="input-area" v-model="reply.content"></textarea>
            <div class="reply-add-btn" @click="add">답변등록</div>
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
    flex-basis: 80%;
}

.detail-user {
    font-weight: bold;
    flex-basis: 5%;
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
    padding: 20px;
}

.reply-area {
    margin-top: 10px;
    padding: 20px;
    border: 2px solid var(--color-brown-70);
}

.input-area {
    width: 100%;
    height: 100px;
    padding: 10px;
    margin-top: 10px;
    font-size: 14px;
    border-width: 1px;
    border-color: var(--color-brown-70);
    border-radius: 5px;
}

.reply-add-btn {
    cursor: pointer;
    position: absolute;
    bottom: 10px;
    right: 15px;
    padding: 5px;
    font-size: 12px;
}

.reply-add-btn:hover {
    font-size: 13px;
    font-weight: bold;
}
.detail-btn-area {
    margin-top: 10px;
    margin-bottom: 400px;
    display: flex;
    justify-content: end;
    gap: 5px;
}
.detail-btn-area button {
    padding: 5px;
}
</style>
