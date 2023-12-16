<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { ListArticle } from "@/api/board";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/board/item/BoardListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

const router = useRouter();

const selectOption = ref([
    { text: "검색조건", value: "" },
    { text: "글번호", value: "article_no" },
    { text: "제목", value: "subject" },
    { text: "작성자아이디", value: "user_id" },
]);

const articleType = ref("notice");
const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const totalArticleCount = ref();

const param = ref({
    pgno: currentPage.value,
    key: "",
    word: "",
});

onMounted(() => {
    getArticleList();
});

const changeKey = (val) => {
    console.log("BoarList에서 선택한 조건 : " + val);
    param.value.key = val;
};

const getArticleList = () => {
    console.log("서버에서 글목록 얻어오자!!!", param.value);
    console.log(param.value.key);
    console.log(param.value.word);
    // API 호출
    ListArticle(articleType.value, param.value, ({ data }) => {
        articles.value = data.articles;
        currentPage.value = data.currentPage;
        totalPage.value = data.totalPageCount;
        totalArticleCount.value = data.totalArticleCount;
    }),
        (error) => {
            console.log(error);
        };
};

const onPageChange = (val) => {
    console.log(val + "번 페이지로 이동 준비 끝!!!");
    currentPage.value = val;
    param.value.pgno = val;
    getArticleList();
};

const moveWrite = () => {
    if (userInfo.value === null) {
        alert("로그인을 해주세요");
        router.push({ name: "login" });
    } else if (userInfo.value.user_grade === "admin") {
        router.push({ name: "article-write", params: { article_type: articleType.value } });
    } else {
        alert("관리자가 아닙니다.");
    }
};
</script>

<template>
    <div class="info-area">
        <div class="info-sub">
            <div>총 {{ totalArticleCount }}건</div>
            <div class="search-area">
                <form class="search-form">
                    <VSelect :selectOption="selectOption" @onKeySelect="changeKey" class="search-key" />
                    <div class="">
                        <input type="text" class="search-input" v-model="param.word" placeholder="검색어입력" @keyup.enter="getArticleList" />
                    </div>
                    <button class="btn btn-search" type="button" @click="getArticleList">검색</button>
                </form>
            </div>
        </div>
        <table class="info-main">
            <thead>
                <tr>
                    <th scope="col">번호</th>
                    <th scope="col">제목</th>
                    <th scope="col">조회수</th>
                    <th scope="col">작성일</th>
                </tr>
            </thead>
            <tbody>
                <BoardListItem v-for="article in articles" :key="article.article_no" :article="article"></BoardListItem>
            </tbody>
        </table>
        <div class="btn-area">
            <button type="button" class="btn btn-regist" @click="moveWrite">글쓰기</button>
        </div>
        <PageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></PageNavigation>
    </div>
</template>

<style scoped>
.info-area {
    margin: 0 auto 40px;
    display: flex;
    flex-direction: column;
}

.info-sub {
    display: flex;
    justify-content: space-between;
}

.search-form {
    display: flex;
    gap: 5px;
}

.search-key {
    width: 80px;
    height: 30px;
    border: 1px solid var(--color-brown-50);
}

.search-input {
    width: 130px;
    height: 30px;
    padding: 5px;
    border: 1px solid var(--color-brown-50);
}

.btn-search {
    padding: 5px;
}
.info-main {
    margin-top: 10px;
    border-color: var(--color-brown-60);
    border-style: solid;
    border-width: 2px 0 2px 0;
}

table {
    border-collapse: collapse;
}

thead {
    height: 35px;
    background-color: var(--color-brown-30);
}

thead th {
    font-weight: bold;
}

th:nth-child(1) {
    width: 5%;
    text-align: center;
}

th:nth-child(2) {
    text-align: left;
    width: 80%;
}

.btn-area {
    align-self: self-end;
}
.btn-regist {
    margin-top: 10px;
    font-size: 12px;
    padding: 5px;
}
</style>
