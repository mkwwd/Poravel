<script setup>
import { ref, onMounted } from "vue";
import PictureItem from "../memory/pic/PictureItem.vue";
import { useMemberStore } from "../../stores/member";
import { storeToRefs } from "pinia";
import { ListMemory } from "@/api/memory";
import { useRouter } from "vue-router";

const router = useRouter();

const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);

onMounted(() => {
    getMemoryList();
});

const memories = ref([]);

const getMemoryList = () => {
    ListMemory(({ data }) => {
        memories.value = data.memoryList;
    }),
        (error) => {
            console.log(error);
        };
};
const emit = defineEmits(["modalOn"]);

const addPicture = () => {
    if (userInfo.value === null) {
        alert("먼저 로그인 할까요?");
        router.push({ name: "login" });
    } else {
        emit("modalOn");
        alert("사진 등록하기");
    }
};
</script>

<template>
    <div class="memory-area">
        <div class="photos">
            <div class="photo">
                <PictureItem v-for="memory in memories" :memory="memory" :key="memory.memory_pk"></PictureItem>
                <div class="pol-item card-up">
                    <div class="polaroid" style="position: relative" @click="addPicture">
                        <div class="polaroid-img"></div>
                        <div v-if="userInfo === null" class="caption">you?</div>
                        <div v-else class="caption">by {{ userInfo.user_id }} ?</div>
                        <div class="plus">+</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
@import "@/assets/css/polar.css";

.photo {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 30px;
}

.polaroid {
    position: absolute;
    top: 0;
    left: 0;
}

.polaroid-img {
    width: 350px;
    height: 300px;
    background-color: black;
}

.plus {
    cursor: pointer;
    position: absolute;
    top: 120px;
    left: 50%;
    transform: translateX(-50%);
    color: white;
    font-size: 80px;
    font-weight: lighter;
}

.pol-item:hover .plus {
    top: 130px;
    left: 44%;
    transform: scale(1.5, 1.5) rotate(0deg) !important;
    transition: all 0.35s;
}

.caption {
    margin-top: 15px;
}
</style>
