<script setup>
import { computed } from "vue";

const props = defineProps({ currentPage: Number, totalPage: Number });
const emit = defineEmits(["pageChange"]);

const navigationSize = parseInt(import.meta.env.VITE_ARTICLE_NAVIGATION_SIZE);

const startPage = computed(() => {
    return parseInt((props.currentPage - 1) / navigationSize) * navigationSize + 1;
});

const endPage = computed(() => {
    let lastPage = parseInt((props.currentPage - 1) / navigationSize) * navigationSize + navigationSize;
    return props.totalPage < lastPage ? props.totalPage : lastPage;
});

const endRange = computed(() => {
    return parseInt((props.totalPage - 1) / navigationSize) * navigationSize < props.currentPage;
});

function range(start, end) {
    const list = [];
    for (let i = start; i <= end; i++) list.push(i);
    return list;
    //   return Array(end - start + 1)
    //     .fill()
    //     .map((val, i) => start + i);
}

function onPageChange(pg) {
    console.log(pg + "로 이동!!!");
    emit("pageChange", pg);
}
</script>

<template>
    <div class="page-area">
        <ul class="page-nav">
            <li class="page-item">
                <a class="page-link" @click="onPageChange(1)">&lt;&lt;</a>
            </li>
            <li class="page-item">
                <a class="page-link" @click="onPageChange(startPage == 1 ? 1 : startPage - 1)">&lt;</a>
            </li>
            <template v-for="pg in range(startPage, endPage)" :key="pg">
                <li :class="currentPage === pg ? 'page-item active' : 'page-item'">
                    <a class="page-link" @click="onPageChange(pg)">{{ pg }}</a>
                </li>
            </template>
            <li class="page-item">
                <a class="page-link" @click="onPageChange(endRange ? totalPage : endPage + 1)">&gt;</a>
            </li>
            <li class="page-item"><a class="page-link" @click="onPageChange(totalPage)">&gt;&gt;</a></li>
        </ul>
    </div>
</template>

<style scoped>
.page-area {
    margin: auto;
}
.page-nav {
    display: flex;
}
.page-item {
    cursor: pointer;
    text-align: center;
    width: 30px;
    height: 30px;
    border: 1px solid var(--color-brown-20);
}

.page-item.active {
    background-color: var(--color-brown-70);
}

.page-item.active a {
    color: white;
}

.page-link {
    display: inline-block;
    width: 100%;
    height: 100%;
    line-height: 30px;
    color: var(--color-brown-60);
}
</style>
