<script setup>
import { ref, computed, defineProps } from "vue";
import { useRoute, useRouter } from "vue-router";
import { deleteMemory } from "@/api/memory"
import { useMemberStore } from "../../../stores/member";

const memberStore = useMemberStore();
const { getUserInfo } = memberStore;
const router = useRouter();
const { VITE_VUE_API_URL } = import.meta.env;
const props = defineProps({ memory: Object, type: String });

const show = ref(false);

const choose = () => {
    show.value = !show.value;
}; 

const completeImageUrl = computed(() => {
    return `${VITE_VUE_API_URL}image/memory/${props.memory.memory_pk}`;
});

const cut = (val) => {
    const val2 = val + "";
    const val3 = val2.substring(0, 10);
    return val3;
};

const delPic = (memory_pk) => { 
    deleteMemory(memory_pk, () => {
        getUserInfo();  
        router.go();    
        alert("삭제완료");
    }),
    (error) => {
        console.log(error);
    };
}

router.beforeEach((to, from, next) => {
  // 페이지 이동 시 맨 위로 스크롤
  window.scrollTo({ top: 0, behavior: 'smooth' });
  next();
});
</script>

<template>
    <div class="pol-item" :class="show ? 'card-rotate' : 'back-rotate'" @click="choose" :value="memory.memory_pk">
        <div class="polaroid">
            <img class="polaroid-img" :src="completeImageUrl" />
            <div class="caption">by {{ memory.user_name }}</div>
        </div>
        <div class="backside">
            <div class="tag">장소이름</div>
            <div class="ex">{{ memory.location }}</div>
            <div class="tag">다녀온 날</div>
            <div class="ex">{{ cut(memory.memory_date) }}</div>
            <div class="tag">한마디</div>
            <div class="ex">{{ memory.comment }}</div>
            <div class="info">
                <div class="hit">조회수 {{ memory.memory_hit }}</div>
            </div>
            <router-link :to="{ name: 'memory-detail', params: { memory_pk: memory.memory_pk } }" class="more"> 더보기? </router-link>
            <div class="del-btn" v-if="type === 'mypage'" @click='delPic(memory.memory_pk)'>X</div>
        </div>
    </div>
</template>

<style scoped>
@import "@/assets/css/polar.css";

.backside {
    position: absolute;
    top: 0;
    left: 0;
    padding: 1rem;
    width: 382px;
    height: 382px;
    background-color: #fff;
    box-shadow: 0 0.25rem 1rem rgba(0, 0, 0, 0.2);
    transform: rotateY(90deg);
}

.card-rotate .polaroid {
    opacity: 1;
    animation: rotate1 0.5s 1;
    transform: rotateY(90deg);
}
.card-rotate .backside {
    opacity: 1;
    animation: rotate2 0.5s 0.5s 1 backwards;
    transform: rotateY(0);
}

.back-rotate .polaroid {
    opacity: 1;
    animation: back 1s 1;
}

.back-rotate .backside {
    opacity: 0;
}

@keyframes rotate1 {
    0% {
        transform: rotateY(0);
    }
    100% {
        transform: rotateY(90deg);
    }
}
@keyframes rotate2 {
    0% {
        transform: rotateY(-90deg);
    }
    100% {
        transform: rotateY(0deg);
    }
}

@keyframes back {
    0% {
        transform: rotateY(90deg);
    }
    100% {
        transform: rotateY(0deg);
    }
}

@keyframes back2 {
    0% {
        transform: rotateY(0deg);
    }
    100% {
        transform: rotateY(-90deg);
    }
}

.tag {
    font-family: "Hi Melody", sans-serif;
    padding: 10px 10px 0;
    font-size: 23px;
    font-weight: bold;
}

.ex {
    font-family: "Hi Melody", sans-serif;
    padding: 10px 10px 20px;
    font-size: 18px;
}

.info {
    display: flex;
    position: absolute;
    gap: 5px;
    bottom: 15px;
    left: 25px;
}

.hit{
    font-family: "Hi Melody", sans-serif;
}

.more {
    z-index: 50;
    font-family: "Hi Melody", sans-serif;
    cursor: pointer;
    font-size: 20px;
    position: absolute;
    bottom: 15px;
    right: 25px;
}

.more:hover {
    font-size: 25px;
}

.del-btn{
    opacity: 0;
    font-family: "Hi Melody", sans-serif;
    cursor: pointer;
    background-color: var(--color-brown-100);
    width: 20px;
    height: 20px;
    text-align: center;
    line-height: 25px;
    position: absolute;
    top: 5px;
    right: 5px;
    border-radius: 50%;
    color: white;
    font-size: 20px;
}

.backside .del-btn{
    opacity: 1;
}
</style>
