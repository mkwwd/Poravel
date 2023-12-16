<script setup>
import { ref, computed, onMounted } from "vue";
import { getMemory, findMemory } from "@/api/memory";
import { useRoute, useRouter } from "vue-router";
import PictureItem from "../memory/pic/PictureItem.vue";
import Footer from "../layout/Footer.vue";

import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const route = useRoute();
const router = useRouter();

const { VITE_VUE_API_URL } = import.meta.env;
const { memory_pk } = route.params;

var map;
const positions = ref([]);
const markers = ref([]);

const emit = defineEmits(["modalOff"]);

const memory = ref([]);

onMounted(() => {
    Memoryget(memory_pk);
});

const Memoryget = (memory_pk) => {
    console.log(memory_pk);
    getMemory(memory_pk, ({ data }) => {
        memory.value = data.memoryDto;
        initMap();
        Memoryfind();
    }),
        (error) => {
            console.log(error);
        };
};

const param = ref({
    search: "",
});

const same = ref([]);

const Memoryfind = () => {
    param.value.search = memory.value.location;
    console.log(param.value.search);
    findMemory(param.value, ({ data }) => {
        same.value = data.memoryList;
        console.log(data);
    }),
        (error) => {
            console.log(error);
        };
};

const initMap = () => {
    console.log(memory.value.longitude);
    const container = document.getElementById("memory-map");
    const options = {
        center: new kakao.maps.LatLng(memory.value.latitude, memory.value.longitude),
        level: 3,
    };
    map = new kakao.maps.Map(container, options);

    const markerPosition = new kakao.maps.LatLng(memory.value.latitude, memory.value.longitude);

    const marker = new kakao.maps.Marker({
        position: markerPosition,
    });

    marker.setMap(map);

    const mapTypeControl = new kakao.maps.MapTypeControl();

    map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

    const zoomControl = new kakao.maps.ZoomControl();
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
};

const infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

const modalClose = () => {
    emit("modalOff");
};

const completeImageUrl = computed(() => {
    return `${VITE_VUE_API_URL}image/memory/${memory.value.memory_pk}`;
});

const cut = (val) => {
    const val2 = val + "";
    const val3 = val2.substring(0, 10);
    return val3;
};

</script>

<template>
    <main style="padding-top: 70px">
        <div class="memory-container">
            <div class="memory-user">{{ memory.user_name }} 님의 MEMORY</div>
            <div class="polaroid">
                <img class="polaroid-img" :src="completeImageUrl" />
            </div>
            <div class="memory-write shadow">
                <div id="memory-map"></div>
                <div class="memory-info">
                    <div class="memory-item">
                        <div class="memory-title">장소이름</div>
                        <div class="memory-sub">{{ memory.location }}</div>
                    </div>
                    <div class="memory-item">
                        <div class="memory-title">다녀온 날</div>
                        <div class="memory-sub">{{ cut(memory.memory_date) }}</div>
                    </div>
                    <div class="memory-item">
                        <div class="memory-title">한마디</div>
                        <div class="memory-sub">{{ memory.comment }}</div>
                    </div>
                </div>
            </div>
            <div class="same-title">Memory를 공유하는 사람들</div>
            <div class="same-info">
                <PictureItem v-for="memory in same" :memory="memory" :key="memory.memory_pk" v-if="memory.memory_pk !== route.params"></PictureItem>
            </div>
        </div>
    </main>
</template>

<style scoped>
.memory-container {
    font-family: "Hi Melody", sans-serif;
    margin: 50px auto 200px;
    max-width: 1000px;
    width: 60%;
    display: flex;
    flex-direction: column;
    align-items: center;
}
.polaroid {
    width: 600px;
    height: 400px;
    background: #fff;
    margin-top: 20px;
    padding: 1rem;
    box-shadow: 0 0.25rem 1rem rgba(0, 0, 0, 0.2);
}

.polaroid-img {
    width: 100%;
    height: 100%;
}

.memory-user {
    font-family: "Hi Melody", sans-serif;
    font-size: 50px;
    font-weight: bold;
    margin-bottom: 20px;
}

.memory-write {
    display: flex;
    width: 90%;
    height: 500px;
    justify-content: space;
    margin: 50px auto 50px;
    padding: 25px;
    background-color: #fff;
    border-radius: 10px;
}

#memory-map {
    width: 50%;
    height: 100%;
}

.memory-info {
    width: 50%;
    padding: 20px 50px;
}

.memory-item {
    display: flex;
    flex-direction: column;
    margin-bottom: 10px;
}

.memory-title {
    font-family: "Hi Melody", sans-serif;
    font-size: 30px;
    font-weight: bold;
}

.memory-sub {
    font-family: "Hi Melody", sans-serif;
    overflow: auto;
    font-size: 20px;
    margin-bottom: 20px;
}

.same-title {
    font-size: 30px;
    font-weight: bold;
    margin: 30px 0 40px;
}

.same-info {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: center;
    gap: 15px;
}
</style>
