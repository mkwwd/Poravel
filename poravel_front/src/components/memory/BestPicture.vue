<script setup>
import { ref, onMounted } from "vue";
import PictureItem from "../memory/pic/PictureItem.vue";
import { BestMemory } from "@/api/memory";

onMounted(() => {
    getBestList();
});

const best = ref([]);

const getBestList = () => {
    BestMemory(({ data }) => {
        best.value = data.BestMemory;
    }),
        (error) => {
            console.log(error);
        };
};
</script>

<template>
    <div class="slider">
        <div class="slide-track slide">
            <PictureItem v-for="memory in best" :memory="memory" :key="memory.memory_pk"></PictureItem>
        </div>
        <div class="slide-track2 slide">
            <PictureItem v-for="memory in best" :memory="memory"></PictureItem>
        </div>
    </div>
</template>

<style scoped>
.slider {
    height: 90%;
    position: relative;
    width: 120%;
    display: flex;
    place-items: center;
    overflow: hidden;
    left: -10%;
}

.slide-track {
    z-index: 1;
    display: flex;
    gap: 15px;
    width: calc(414px * 10);
    animation: scroll 90s linear infinite;
    margin-left: 15px;
    margin-right: 15px;
}

.slide-track2 {
    display: flex;
    gap: 15px;
    width: calc(420px * 10);
    left: calc(420px * 10);
    animation: scroll2 90s linear infinite;
    margin-right: 20px;
}

.slider:hover .slide {
    animation-play-state: paused;
}

@keyframes scroll {
    0% {
        transform: translateX(0);
    }
    50% {
        transform: translateX(-100%);
    }
    50.01% {
        transform: translateX(100%);
    }
    100% {
        transform: translateX(0);
    }
}

@keyframes scroll2 {
    0% {
        transition: translateX(0);
    }
    100% {
        transform: translateX(-200%);
    }
}
</style>
