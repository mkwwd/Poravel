<script setup>
import { ref, watch, onMounted } from "vue";

var map;
const positions = ref([]);
const markers = ref([]);
const hasLoadedCurrentLocation = ref(false);

const props = defineProps({ places: Array, selectPlace: Object });

watch(
    () => props.selectPlace.value,
    () => {
        // 이동할 위도 경도 위치를 생성합니다
        var moveLatLon = new kakao.maps.LatLng(props.selectPlace.latitude, props.selectPlace.longitude);
        // 지도 중심을 부드럽게 이동시킵니다
        // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
        map.panTo(moveLatLon);
    },
    { deep: true }
);

onMounted(() => {
    initMap();
});

watch(
    () => props.places.value,
    () => {
        console.log(props.places.value);
        if (props.places.length == 0) {
            alert("검색결과가 존재하지 않습니다.");
            return;
        }
        positions.value = [];
        props.places.forEach((place) => {
            console.log(place);
            let obj = {};
            obj.latlng = new kakao.maps.LatLng(place.latitude, place.longitude);
            obj.title = place.title;
            positions.value.push(obj);
        });
        loadMarkers();
    },
    { deep: true }
);

const initMap = () => {
    const container = document.getElementById("map");
    const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
    };
    map = new kakao.maps.Map(container, options);

    // loadMarkers();
};

// 지도에 마커와 인포윈도우를 표시하는 함수입니다
function displayMarker(locPosition, message) {
    // 마커를 생성합니다
    const marker = new kakao.maps.Marker({
        map: map,
        position: locPosition,
    });

    const iwContent = message, // 인포윈도우에 표시할 내용
        iwRemoveable = true;

    // 인포윈도우를 생성합니다
    const infowindow = new kakao.maps.InfoWindow({
        content: iwContent,
        removable: iwRemoveable,
    });

    // 인포윈도우를 마커위에 표시합니다
    infowindow.open(map, marker);

    // 지도 중심좌표를 접속위치로 변경합니다
    map.setCenter(locPosition);
}

const loadMarkers = () => {
    // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
    deleteMarkers();

    // 마커 이미지를 생성합니다
    //   const imgSrc = require("@/assets/map/markerStar.png");
    // 마커 이미지의 이미지 크기 입니다
    //   const imgSize = new kakao.maps.Size(24, 35);
    //   const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

    // 마커를 생성합니다
    markers.value = [];
    positions.value.forEach((position) => {
        const marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: position.latlng, // 마커를 표시할 위치
            title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
            clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
            // image: markerImage, // 마커의 이미지
        });
        markers.value.push(marker);
    });

    // 4. 지도를 이동시켜주기
    // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
    const bounds = positions.value.reduce((bounds, position) => bounds.extend(position.latlng), new kakao.maps.LatLngBounds());

    map.setBounds(bounds);
};

const deleteMarkers = () => {
    if (markers.value.length > 0) {
        markers.value.forEach((marker) => marker.setMap(null));
    }
};
</script>

<template>
    <div id="map"></div>
</template>

<style scoped>
#map {
    width: 100%;
    height: 100vh;
}
</style>
