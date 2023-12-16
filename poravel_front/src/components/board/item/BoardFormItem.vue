<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, modifyArticle, registArticle } from "@/api/board";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String, articleType: String });
const { article_type } = route.params;

const isUseId = ref(false);

const article = ref({
    article_no: "",
    subject: "",
    content: "",
    user_id: "",
    hit: "",
    register_time: "",
});

if (props.type === "modify") {
    let { article_type, article_no } = route.params;
    console.log(article_no + "번글 얻어와서 수정할거야");
    detailArticle(article_type, article_no, ({ data }) => {
        article.value = data;
    }),
        (error) => {
            console.log(error);
        };
    isUseId.value = true;
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
    () => article.value.subject,
    (value) => {
        let len = value.length;
        if (len == 0 || len > 30) {
            subjectErrMsg.value = "제목은 30자 이하입니다.";
        } else subjectErrMsg.value = "";
    },
    { immediate: true }
);
watch(
    () => article.value.content,
    (value) => {
        let len = value.length;
        if (len == 0 || len > 2000) {
            contentErrMsg.value = "내용은 2000자 이하입니다.";
        } else contentErrMsg.value = "";
    },
    { immediate: true }
);

function onSubmit() {
    // event.preventDefault();

    if (subjectErrMsg.value) {
        alert(subjectErrMsg.value);
    } else if (contentErrMsg.value) {
        alert(contentErrMsg.value);
    } else {
        props.type === "regist" ? writeArticle() : updateArticle();
    }
}

function writeArticle() {
    console.log(article_type)
    console.log(userInfo.value.user_id)
    article.value.user_id = userInfo.value.user_id;
    console.log("글등록하자!!", article.user_id);
    console.log(article)
    registArticle(article_type, article, () => {
        console.log("등록완료");
        if (article_type == "notice") {
            router.push({ name: "notice-list" });
        } else if (article_type == "qna") { 
            console.log("qna등록")
            router.push({ name: "qna-list" });
        }   
    }),
        (error) => {
            console.log(error);
        };
}

function updateArticle() {
    console.log(article.value.article_no + "번글 수정하자!!", article.value);
    modifyArticle(article, () => {
        console.log("수정완료");
        router.push({ name: "notice-list" });
    }),
        (error) => {
            console.log(error);
        };
}

function moveList() {
    if (article_type == "notice") {
        router.push({ name: "notice-list" });
    } else if (article_type == "qna") { 
        router.push({ name: "qna-list" });
    }
}
</script>

<template>
    <form @submit.prevent="onSubmit" class="modify-form">
        <label for="subject" class="form-label">제목</label>
        <input type="text" class="form-control" v-model="article.subject"/>
        <label for="content" class="form-label">내용</label>
        <textarea class="form-control" v-model="article.content" rows="10"></textarea>
        <div class="modify-btn-area">
            <button type="submit" class="btn" v-if="type === 'regist'">글쓰기</button>
            <button type="submit" class="btn" v-else>수정하기</button>
            <button type="button" class="btn" @click="moveList">목록으로</button>
        </div>
    </form>
</template>

<style scoped>
.modify-form {
    display: flex;
    flex-direction: column;
}

.form-label {
    font-size: 15px;
    margin-bottom: 5px;
}

.form-control {
    margin-bottom: 10px;
    padding: 5px 10px;
    border: 1px solid var(--color-brown-40);
    background-color: #fff;
}

.modify-btn-area {
    display: flex;
    justify-content: end;
    gap: 5px;
}

.modify-btn-area button {
    padding: 5px;
}

textarea{
    min-height: 500px;
}
</style>
