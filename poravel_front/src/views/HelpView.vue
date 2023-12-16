<script setup>
import { ref } from "vue";
import { RouterView } from "vue-router";
import Footer from "../components/layout/Footer.vue";

const category = ref("notice");

const change = (type) => {
    console.log(type);
    category.value = type;
};
</script>

<template>
    <main style="padding-top: 70px">
        <div class="info-title-area">
            <router-link :to="{ name: 'notice-list' }" :class="[category === 'notice' ? 'info-title-active' : 'info-title']" @click="change('notice')"> Notice</router-link>
            <router-link :to="{ name: 'qna-list' }" :class="[category === 'qna' ? 'info-title-active' : 'info-title']" @click="change('qna')"> QnA</router-link>
        </div>
        <router-view></router-view>
    </main>
</template>

<style scoped>
.info-title-area {
    min-width: 900px;
    margin: 20px auto 30px;
    display: flex;
    justify-content: center;
}

.info-title {
    width: 120px;
    height: 60px;
    padding: 5px;
    color: var(--color-brown-70);
    text-align: center;
    font-size: 30px;
    font-weight: bold;
}

.info-title-active,
.info-title:hover {
    width: 120px;
    height: 60px;
    padding: 5px;
    color: var(--color-brown-70);
    text-align: center;
    font-size: 30px;
    font-weight: bold;
    color: var(--color-brown-100);
    border-width: 0 0 3px 0;
    border-color: var(--color-brown-100);
    border-style: solid;
}
</style>
