<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { searchDetail, savePlace, addReview, deleteReview } from "@/api/place";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import PictureItem from "../memory/pic/pictureitem.vue";

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const route = useRoute();
const router = useRouter();

const { content_id } = route.params;

const detail = ref([]);

onMounted(() => {
    getDetail(content_id);
});

const getDetail = (content_id) => {
    searchDetail(content_id, ({ data }) => {
        console.log(data);
        detail.value = data.attractionDetail;
        initMap();
    }),
        (error) => {
            console.log(error);
        };
};

var map;

const initMap = () => {
    console.log(detail.value.longitude);
    const container = document.getElementById("map");
    const options = {
        center: new kakao.maps.LatLng(detail.value.latitude, detail.value.longitude),
        level: 3,
    };
    map = new kakao.maps.Map(container, options);

    // 마커가 표시될 위치입니다
    const markerPosition = new kakao.maps.LatLng(detail.value.latitude, detail.value.longitude);

    // 마커를 생성합니다
    const marker = new kakao.maps.Marker({
        position: markerPosition,
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);

    // 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
    const mapTypeControl = new kakao.maps.MapTypeControl();

    // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
    // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
    map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

    // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
    const zoomControl = new kakao.maps.ZoomControl();
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
};

const place = ref({
    content_id: "",
});

const placeSave = () => {
    place.value.content_id = detail.value.content_id;
    console.log(place.value.content_id);
    if (userInfo.value === null) {
        alert("로그인을 해주세요");
        router.push({ name: "login" });
    } else {
        savePlace(place, () => {
            alert("저장완료");
        }),
            (error) => {
                console.log(error);
            };
    }
};

const review = ref({
    content_id: "",
    comment: "",
});

const reviewAdd = () => {
    review.value.content_id = detail.value.content_id;
    console.log(review.value.content_id);
    if (userInfo.value === null) {
        alert("로그인을 해주세요");
        router.push({ name: "login" });
    } else {
        addReview(review, () => {
            console.log("등록성공");
            review.value.comment = "";
            getDetail(content_id);
        }),
            (error) => {
                console.log(error);
            };
    }
};

const delReply = (id, pk) => {
    if (userInfo.value === null) {
        alert("로그인을 해주세요");
        router.push({ name: "login" });
    } else if (userInfo.value.user_id === id) {
        deleteReview(pk, () => {
            alert("삭제성공");
            getDetail(content_id);
        }),
            (error) => {
                console.log(error);
            };
    } else {
        alert("본인의 한마디만 삭제 가능합니다.");
    }
};

router.beforeEach((to, from, next) => {
  // 페이지 이동 시 맨 위로 스크롤
  window.scrollTo({ top: 0, behavior: 'smooth' });
  next();
});
</script>

<template>
    <main class="info-main">
        <div class="detail-container">
            <div class="nav shadow">
                <div class="save" @click="placeSave">장소 담아두기</div>
                <a href="#one">이미지</a>
                <a href="#two">상세정보</a>
                <a href="#three">위치보기</a>
                <a href="#four">memory</a>
            </div>
            <div class="detail-view">
                <div class="detail-title shadow">
                    {{ detail.title }}
                </div>
                <div class="detail-img shadow" id="one">
                    <img :src="detail.first_image" alt="이미지" />
                </div>
                <div id="two"></div>
                <div class="detail-info shadow">
                    <div>
                        {{ detail.overview }}
                    </div>
                </div>
                <div class="detail-add shadow">
                    <img src="../../assets/img/pin.png" alt="" style="width: 20px" />
                    <div>{{ detail.addr1 }}</div>
                </div>
                <div id="three"></div>
                <div class="detail-pos shadow">
                    <div id="map"></div>
                </div>
                <div class="memory-box shadow" id="four">
                    <div class="memory-title" style="text-align: center">Memory</div>
                    <div class="item-inner" v-if="detail.memoryDtoList && detail.memoryDtoList.length === 0">
                        <router-link :to="{ name: 'memory' }">
                            <div class="empty">
                                <div>아직 공유된 메모리가 없어요ㅠㅠ</div>
                                <div>메모리를 채워볼까요?</div>
                            </div>
                        </router-link>
                    </div>
                    <div v-else class="memory-item">
                        <PictureItem v-for="memory in detail.memoryDtoList" :memory="memory" :key="memory.memory_pk"></PictureItem>
                    </div>
                </div>
            </div>
            <div class="review-area">
                <div class="review-container shadow">
                    <div class="review">소곤소곤</div>
                    <div class="review" style="font-size: 20px; font-weight: normal;">한마디 남기고 갈까요?</div>
                    <textarea v-model="review.comment"></textarea>
                    <div class="btn" @click="reviewAdd">남기기</div>
                </div>
                <div v-for="reply in detail.reviewDtoList" class="review-item">
                    <div>"{{ reply.user_id }}" 님의 한마디</div>
                    <div>{{ reply.comment }}</div>
                    <div class="delete" @click="delReply(reply.user_id, reply.review_pk)">X</div>
                </div>
            </div>
        </div>
    </main>
</template>

<style scoped>
.info-main {
    position: relative;
    display: flex;
    justify-content: center;
    margin-right: 200px;
    padding-top: 180px;
}
.save {
    cursor: pointer;
}
.nav {
    position: sticky;
    display: flex;
    flex-direction: column;
    top: 50px;
    gap: 20px;
    width: 180px;
    height: 250px;
    margin: 0;
    padding: 20px;
    background-color: white;
    border-radius: 5px;
}

.nav a,
.nav div,
.review {
    font-family: "Hi Melody", sans-serif;
    font-weight: bold;
    font-size: 20px;
}

.detail-info,
.detail-add,
.detail-pos,
.memory-box {
    width: 850px;
    background-color: #fff;
    margin-top: 20px;
    padding: 20px;
    border-radius: 5px;
}

.detail-title {
    font-size: 25px;
    font-weight: bold;
    text-align: center;
    margin-bottom: 20px;
    width: 850px;
    background-color: #fff;
    padding: 20px;
    border-radius: 5px;
}

.detail-info {
    font-size: 16px;
    line-height: 30px;
}

.detail-container {
    position: relative;
    display: flex;
    flex-direction: row;
    gap: 20px;
    margin-bottom: 300px;
}

.detail-view {
    width: 90%;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.detail-img {
    background-color: #fff;
    padding: 10px;
}
.detail-img img {
    width: 830px;
    height: 450px;
    box-shadow: 0px 5px 10px 0px var(--color-brown-30);
}

.detail-add {
    display: flex;
    align-items: center;
    gap: 5px;
}

#map {
    width: 100%;
    height: 100%;
    border-radius: 10px;
}

.detail-pos {
    height: 500px;
    padding: 10px;
}

.memory-box {
    font-size: 20px;
    font-size: bold;
}

.empty {
    font-family: "Hi Melody", sans-serif;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-bottom: 10px;
}

.empty div{
    font-family: "Hi Melody", sans-serif;
}

.review-area {
    position: absolute; /* 창에서 오른쪽 길이 */
    top: 0; /* 창에서 위에서 부터의 높이 */
    right: -270px;
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.review-container {
    font-family: "Hi Melody", sans-serif;
    display: flex;
    flex-direction: column;
    gap: 10px;
    width: 250px;
    margin: 0;
    padding: 15px;
    background-color: white;
    border-radius: 5px;
    box-shadow: 0px 5px 10px 0px var(--color-brown-30);
}

.review-container textarea {
    font-size: 18px;
    font-family: "Hi Melody", sans-serif;
    border: 1px solid var(--color-brown-30);
    height: 60px;
    padding: 5px;
}

.btn {
    font-family: "Hi Melody", sans-serif;
    padding: 5px 5px 3px;
    font-size: 15px;
    align-self: flex-end;
}

.review-item {
    cursor: pointer;
    font-family: "Hi Melody", sans-serif;
    display: flex;
    flex-direction: column;
    width: 250px;
    background-color: #fff;
    border-radius: 5px;
    padding: 10px;
    gap: 10px;
    box-shadow: 0px 5px 10px 0px var(--color-brown-30);
}

.review-item div{
    font-family: "Hi Melody", sans-serif;
}

.review-item div:nth-child(1) {
    font-size: 20px;
    font-weight: bold;
}

.delete {
    display: none;
    position: absolute;
    right: 0;
    width: 20px;
    height: 20px;
}

.review-item:hover .delete {
    display: flex;
}

.memory-title {
    margin: 20px 0;
    font-family: "Hi Melody", sans-serif;
    font-size: 50px;
}

.memory-item {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 15px;
}
</style>
