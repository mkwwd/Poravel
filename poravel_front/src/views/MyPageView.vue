<script setup>
import { ref, onBeforeMount, watchEffect } from "vue";
import { RouterView } from "vue-router";
import MyInfo from "@/components/user/MyInfo.vue";
import MyPageEdit from "@/components/user/MyPageEdit.vue";
import { useMemberStore } from "../stores/member";
import { storeToRefs } from "pinia";
import PictureItem from "../components/memory/pic/PictureItem.vue";
import placeItem from "../components/place/placeItem.vue";

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const { getUserInfo } = memberStore;

const bookmark = ref([]);
const myMemory = ref([]);

const loadData = async () => {
    await getUserInfo();

    myMemory.value = userInfo.value.memoryDtoList;
    bookmark.value = userInfo.value.bookmarkDtoList;
};

onBeforeMount(() => {
    myMemory.value = [];
    bookmark.value = [];
    loadData();
});

watchEffect(() => {
    myMemory.value = userInfo.value.memoryDtoList;
    bookmark.value = userInfo.value.bookmarkDtoList;
});

</script>

<template>
    <main style="padding-top: 200px; margin-bottom: 100px">
        <!-- <div class="mypage-title">MY PAGE</div> -->
        <MyInfo></MyInfo>
        <div class="like-area">
            <div class="title">BookMark</div>
            <div class="bar"></div>
            <div class="item-area">
                <div class="item-inner" v-if="bookmark.value && bookmark.value.length === 0">
                    <router-link :to="{ name: 'place' }">
                        <div>저장한 장소가 없어요</div>
                        <div>장소를 찾으러 가볼까요?</div>
                    </router-link>
                </div>
                <div v-else class="items">
                    <placeItem class="search-item" v-for="place in bookmark" :place="place" :key="place.bookmark_pk" type="mypage"> </placeItem>
                </div>
            </div>
            <div class="title" style="margin-top: 50px">Memory</div>
            <div class="bar"></div>
            <div class="item-area">
                <div class="item-inner" v-if="myMemory.value && myMemory.value.length === 0">
                    <router-link :to="{ name: 'memory' }">
                        <div>공유한 순간이 없어요</div>
                        <div>지금 공유하러 가볼까요?</div>
                    </router-link>
                </div>
                <div v-else class="memories">
                    <PictureItem v-for="memory in myMemory" :memory="memory" :key="memory.memory_pk" type="mypage"></PictureItem>
                </div>
            </div>
        </div>
    </main>
</template>

<style scoped>
.mypage-title {
    padding-bottom: 15px;
    text-align: center;
    font-size: 50px;
    font-weight: bold;
}

.like-area {
    width: 65%;
    margin: 100px auto;
}

.title {
    font-family: "Hi Melody", sans-serif;
    font-size: 50px;
    color: var(--color-brown-80);
    padding-top: 10px;
}

.bar {
    width: 200px;
    height: 1px;
    background-color: var(--color-brown-80);
}

.item-area {
    width: 100%;
    min-height: 200px;
    margin-top: 20px;
    background-color: var(--color-brown-40);
    border-radius: 5px;
    padding: 20px 25px;
}

.item-inner {
    text-align: center;
    padding: 70px;
}

.item-inner div {
    font-family: "Hi Melody", sans-serif;
    font-weight: bold;
    font-size: 25px;
}

.items {
    display: flex;
    align-items: center;
    gap: 10px;
}

.memories {
    display: flex;
    flex-wrap: wrap;
    gap: 15px;
}
</style>
