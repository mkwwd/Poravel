<script setup>
import { ref, watch, onMounted } from "vue";
import { addMemory } from "@/api/memory";

var map;
const positions = ref([]);
const markers = ref([]);

const emit = defineEmits(["modalOff"]);

onMounted(() => {
    initMap();
});

const initMap = () => {
    const container = document.getElementById("memory-map");
    const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
    };
    map = new kakao.maps.Map(container, options);

    // loadMarkers();
};
const infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

const ps = new kakao.maps.services.Places();
const keyword = ref("");

const formData = ref({
    image: null,
    user_name: "",
    location: "",
    comment: "",
    memory_date: "",
    latitude: "",
    logitude: "",
});

ps.keywordSearch(keyword.value, placesSearchCB);

function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        const bounds = new kakao.maps.LatLngBounds();

        for (var i = 0; i < 1; i++) {
            displayMarker(data[i]);
            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
            formData.value.latitude = data[i].y;
            formData.value.logitude = data[i].x;
        }

        console.log(formData.value.latitude);
        console.log(formData.value.logitude);
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
    }
}

// 지도에 마커를 표시하는 함수입니다
function displayMarker(place) {
    // 마커를 생성하고 지도에 표시합니다
    const marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x),
    });

    kakao.maps.event.addListener(marker, "click", function () {
        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + "</div>");
        infowindow.open(map, marker);
    });
}

watch(keyword, (newVal, oldVal) => {
    // 1초 후에 keyword가 변경되지 않으면 실행
    console.log(keyword);
    setTimeout(() => {
        if (newVal === keyword.value) {
            // keywordSearch 함수 호출
            ps.keywordSearch(keyword.value, placesSearchCB);
            formData.value.location = keyword.value;
        }
    }, 100);
});

const submitForm = () => {
    console.log(formData.value.image);
    const dataToSend = new FormData();
    dataToSend.append("image", formData.value.image);
    dataToSend.append("user_name", formData.value.user_name);
    dataToSend.append("location", formData.value.location);
    dataToSend.append("memory_date", formData.value.memory_date);
    dataToSend.append("comment", formData.value.comment);
    dataToSend.append("latitude", formData.value.latitude);
    dataToSend.append("longitude", formData.value.logitude);

    addMemory(dataToSend, (response) => {
        console.log("등록성공");
        modalClose();
    }),
        (error) => {
            console.log(error);
        };
};

const fileUp = (e) => {
    formData.value.image = e.target.files[0];
};

const modalClose = () => {
    emit("modalOff");
};
</script>

<template>
    <div class="memory-write shadow">
        <div id="memory-map"></div>
        <div class="memory-info">
            <form @submit.prevent="submitForm" id="memory-form" enctype="multipart/form-data">
                <div class="memory-item">
                    <label for="user-name">작성자 이름</label>
                    <input type="text" id="user-name" v-model="formData.user_name" />
                </div>
                <div class="memory-item">
                    <label for="location">장소이름</label>
                    <input type="text" id="location" v-model="keyword" />
                </div>
                <div class="memory-item">
                    <label for="image">사진첨부하기</label>
                    <input type="file" accept="image/*" id="image" @change="fileUp" multiple="multiple" />
                </div>
                <div class="memory-item">
                    <label for="memory-date">다녀온 날짜</label>
                    <input type="date" id="memory-date" v-model="formData.memory_date" />
                </div>
                <div class="memory-item detail">
                    <label for="comment" style="height: 32px">한마디</label>
                    <textarea id="comment" v-model="formData.comment"></textarea>
                </div>
                <div class="btn-area">
                    <input type="submit" class="btn" value="등록하기" />
                    <input type="button" class="btn" value="뒤로가기" @click="modalClose" />
                </div>
            </form>
        </div>
    </div>
</template>

<style scoped>
.memory-write {
    font-family: "Hi Melody", sans-serif;
    display: flex;
    width: 60%;
    justify-content: space-evenly;
    gap: 30px;
    min-width: 1100px;
    height: 80vh;
    margin: 30px auto 50px;
    padding: 30px;
    background-color: #fff;
    border-radius: 10px;
}

#memory-map {
    width: 50%;
    height: 100%;
}

.memory-info {
    display: flex;
    align-items: center;
}

#memory-form {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}
.memory-item {
    display: flex;
    flex-direction: row;
    gap: 10px;
}
.memory-item label {
    font-family: "Hi Melody", sans-serif;
    width: 130px;
    font-size: 25px;
    margin-bottom: 10px;
}

#image {
    font-size: 16px;
}

.memory-item input {
    font-family: "Hi Melody", sans-serif;
    font-size: 20px;
    width: 300px;
    height: 30px;
    padding-left: 5px;
    border: 1px solid var(--color-brown-50);
}

textarea,
textarea:focus {
    font-family: "Hi Melody", sans-serif;
    font-size: 20px;
    width: 300px;
    height: 180px;
    padding: 5px;
    border: 1px solid var(--color-brown-50);
}

.btn-area {
    display: flex;
    justify-content: end;
    gap: 5px;
}
.btn {
    font-family: "Hi Melody", sans-serif;
    font-size: 20px;
    background-color: var(--color-brown-70);
    padding: 5px 10px;
}
</style>
