<script setup>
import { ref, onMounted, onUnmounted, computed } from "vue";
import PictureItem from "../components/memory/pic/PictureItem.vue";
import { BestMemory } from "@/api/memory";

const inMove = ref(false);
const inMoveDelay = 400;
const activeSection = ref(0);
const offsets = ref([]);
let touchStartY = 0;

function calculateSectionOffsets() {
    const sections = document.getElementsByTagName("section");
    const length = sections.length;

    for (let i = 0; i < length; i++) {
        const sectionOffset = sections[i].offsetTop;
        offsets.value.push(sectionOffset);
    }
}

function handleMouseWheel(e) {
    if (e.wheelDelta < 30 && !inMove.value) {
        moveUp();
    } else if (e.wheelDelta > 30 && !inMove.value) {
        moveDown();
    }

    e.preventDefault();
    return false;
}

function handleMouseWheelDOM(e) {
    if (e.detail > 0 && !inMove.value) {
        moveUp();
    } else if (e.detail < 0 && !inMove.value) {
        moveDown();
    }

    return false;
}

function moveDown() {
    inMove.value = true;
    activeSection.value--;

    if (activeSection.value < 0) activeSection.value = offsets.value.length - 1;

    scrollToSection(activeSection.value, true);
}

function moveUp() {
    inMove.value = true;
    activeSection.value++;

    if (activeSection.value > offsets.value.length - 1) activeSection.value = 0;

    scrollToSection(activeSection.value, true);
}

function scrollToSection(id, force = false) {
    if (inMove.value && !force) return false;

    activeSection.value = id;
    inMove.value = true;

    const section = document.getElementsByTagName("section")[id];
    if (section) {
        document.getElementsByTagName("section")[id].scrollIntoView({ behavior: "smooth" });
    }

    setTimeout(() => {
        inMove.value = false;
    }, inMoveDelay);
}

function touchStart(e) {
    e.preventDefault();
    touchStartY = e.touches[0].clientY;
}

function touchMove(e) {
    if (inMove.value) return false;
    e.preventDefault();

    const currentY = e.touches[0].clientY;

    if (touchStartY < currentY) {
        moveDown();
    } else {
        moveUp();
    }

    touchStartY = 0;
    return false;
}

onMounted(() => {
    getBestList();
    calculateSectionOffsets();

    window.addEventListener("DOMMouseScroll", handleMouseWheelDOM); // Mozilla Firefox
    window.addEventListener("mousewheel", handleMouseWheel, { passive: false }); // Other browsers

    window.addEventListener("touchstart", touchStart, { passive: false }); // mobile devices
    window.addEventListener("touchmove", touchMove, { passive: false }); // mobile devices
});

// Cleanup listeners on component unmount
onUnmounted(() => {
    window.removeEventListener("DOMMouseScroll", handleMouseWheelDOM);
    window.removeEventListener("mousewheel", handleMouseWheel, { passive: false });
    window.removeEventListener("touchstart", touchStart);
    window.removeEventListener("touchmove", touchMove);
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

const limitedBest = computed(() => {
    return best.value.slice(0, 6);
});


</script>
<template>
    <div id="app">
        <div class="sections-menu">
            <span
                class="menu-point"
                v-bind:class="{ active: activeSection == index }"
                v-on:click="scrollToSection(index)"
                v-for="(offset, index) in offsets"
                v-bind:key="index"
                v-title="'Go to section ' + (index + 1)"
            >
            </span>
        </div>
        <section class="fullpage one">
            <div class="one-area">
                <div class="one-title">Poravel</div>
                <div class="one-sub">:아름다운 순간, 사진으로 담다</div>
            </div>
        </section>
        <section class="fullpage two" style="position: relative">
            <div class="two-title">시원한 바다</div>
        </section>
        <section class="fullpage three" style="position: relative">
            <div class="three-title">신비로운 숲</div>
        </section>
        <section class="fullpage four" style="position: relative">
            <div class="four-title">고요한 바위</div>
        </section>
        <section class="fullpage five">
            <div class="five-title">모두 잘 담으셨나요?</div>
        </section>
        <section class="fullpage six">
            <div class="six-title">기억을 공유해주세요</div>
        </section>
        <section class="fullpage seven">
            <div class="picture-area">
                <div class="pic-item" v-for="memory in limitedBest">
                    <PictureItem :memory="memory" :key="memory.memory_pk"></PictureItem>
                </div>
            </div>
        </section>
    </div>
</template>
<style scoped>
body {
    margin: 0;
    color: #fff;
    overflow: hidden;
}

h2 {
    position: fixed;
}

.fullpage {
    height: 100vh;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}

.one-area {
    display: flex;
    flex-direction: column;
}

.one-title {
    font-family: "JeonjuCraftMjB";
    font-size: 350px;
    color: white;
    opacity: 0.6;
    animation: ani 1s infinite alternate;
}

.one-sub {
    font-family: "JeonjuCraftMjB";
    align-self: flex-end;
    font-size: 40px;
    color: white;
    opacity: 0.6;
    padding-right: 20px;
    animation: ani 1s infinite alternate;
}

@keyframes ani {
    0% {
        transform: translate(0, 0);
    }
    100% {
        transform: translate(0, 10px);
    }
}

.two-title,
.three-title,
.four-title,
.five-title,
.six-title {
    position: absolute;
    font-family: "JeonjuCraftMjB";
    font-size: 70px;
    color: white;
    opacity: 0.6;
    animation: ani 1s infinite alternate;
}

.two-title {
    top: 50px;
    left: 80px;
}
.three-title {
    bottom: 60px;
}
.four-title {
    top: 50px;
    right: 80px;
}

h1 {
    font-size: 6em;
    margin: 0;
    text-align: center;
    padding: 0 1rem;
}

p {
    font-size: 1em;
}

.fullpage a,
.fullpage span {
    text-decoration: none;
    font-weight: 600;
    background: rgba(255, 255, 255, 0.2);
    padding: 5px 10px;
    color: #fff;
    margin-left: 5px;
}

.picture-area {
    width: 70%;
    display: flex;
    gap: 30px;
    flex-wrap: wrap;
}

.pic-item {
    filter: grayscale(100%);
}

.pic-item:hover {
    filter: none;
}

section.black {
    background-color: #000;
}

.one {
    background-size: cover;
    background-image: url("../assets/img/22_1.jpg");
}

.two {
    background-size: cover;
    background-image: url("../assets/img/22_2.jpg");
}
.three {
    background-size: cover;
    background-image: url("../assets/img/forest.jpg");
}
.four {
    background-size: cover;
    background-image: url("../assets/img/44444.jpg");
}

.five {
    background-size: cover;
    background-image: url("../assets/img/clouds1.jpg");
}

.six {
    background-size: cover;
    background-image: url("../assets/img/clouds2.jpg");
}

.seven {
    background-size: cover;
    background-image: url("../assets/img/clouds3.jpg");
}

.black {
    background-color: black;
}

h1.black {
    color: #000;
}

.sections-menu {
    position: fixed;
    right: 1rem;
    top: 50%;
    transform: translateY(-50%);
}

.sections-menu .menu-point {
    border-radius: 50%;
    width: 10px;
    height: 10px;
    background-color: #fff;
    display: block;
    margin: 1rem 0;
    opacity: 0.6;
    transition: 0.4s ease-in-out all;
    cursor: pointer;
}

.sections-menu .menu-point.active {
    opacity: 1;
    transform: scale(1.5);
}

.sections-menu .menu-point:hover {
    opacity: 1;
    transform: scale(1.2);
}

@media screen and (max-width: 1200px) {
    h1 {
        font-size: 2.5em;
    }
}
</style>
