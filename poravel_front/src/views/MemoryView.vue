<script setup>
import { ref } from "vue";
import BestPicture from "../components/memory/BestPicture.vue";
import PictureList from "../components/memory/PictureList.vue";
import MemoryWrite from "../components/memory/MemoryWrite.vue";
import Footer from "../components/layout/Footer.vue";

const on = ref(false);

const modal = () => {
    on.value = !on.value;
};
</script>

<template>
    <main style="padding-top: 70px">
        <div class="section">
            <div v-if="on">
                <MemoryWrite @modal-off="modal"></MemoryWrite>
            </div>
            <div v-else>
                <div class="best-area shadow">
                    <div class="best-title">Best Photo</div>
                    <BestPicture></BestPicture>
                </div>
                <div class="memory-main" id='top'>
                    <div class="memory-title">PhotoBook</div>
                    <div class="memory-subti">추억을 공유해보세요</div>
                    <div class="memory-share" >
                        <div @click="modal">메모리 공유하기</div>
                        <a href="#top">맨위로</a>
                    </div>
                    <div class="memory-list">
                        <PictureList @modal-on="modal"></PictureList>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>

<style scoped>
.section {
    position: relative;
    font-family: "Hi Melody", sans-serif;
}
.best-area {
    padding: 60px 0;
    width: 100%;
    height: 700px;
    background-color: var(--color-brown-80);
}

.best-title {
    text-align: center;
    font-size: 40px;
    color: white;
}

.best-area::before,
.best-area::after {
    background: linear-gradient(to right, var(--color-brown-70) 0%, rgba(255, 255, 255, 0) 100%);
    content: "";
    height: 700px;
    position: absolute;
    width: 15%;
    z-index: 2;
}

.best-area::before {
    left: 0;
    top: 0;
}

.best-area::after {
    right: 0;
    top: 0;
    transform: rotateZ(180deg);
}

.memory-main {
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin: 100px auto 300px;
}

.memory-title {
    margin-bottom: 30px;
    text-align: center;
    font-size: 50px;
    font-weight: bold;
}

.memory-subti {
    text-align: center;
    font-size: 30px;
    margin-bottom: 50px;
}

.memory-share {
    cursor: pointer;
    position: sticky;
    left: 200px;
    top: 50px;
    text-align: center;
    width: 140px;
    padding: 10px;
    display: flex;
    flex-direction: column;
    gap: 10px;
    border: 1px solid var(--color-brown-70);
}

.memory-list {
    margin: 0 auto;
    width: 70%;
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>
