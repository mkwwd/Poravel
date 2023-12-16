<script setup>
import { ref, computed, defineProps } from "vue";
import { useRoute, useRouter } from "vue-router";
import { deleteLike } from "@/api/place"
import { useMemberStore } from "../../stores/member";

const route = useRoute();
const router = useRouter();

const props = defineProps({ place: Object, type: String });

const memberStore = useMemberStore();
const { getUserInfo } = memberStore;

const moveDetail = (content_id) => {
    router.push({ name: "place-detail", params: { content_id } });
};


const delLike = (bookmark_pk) => { 

  deleteLike(bookmark_pk, () => {
    alert("삭제완료");
    getUserInfo();  
    router.go();    
  }),
  (error) => {
      console.log(error);
  };
}
</script>

<template>
  <div class='search-item'>
    <div class="search-img">
      <img :src="place.first_image" alt="이미지" />
    </div>
    <div class="search-detail">
      <div class="detail-title">
        {{ place.title }}
      </div>
      <div class="detail-main">
        {{ place.addr1 }}
      </div>
      <div class="moreInfo" @click="moveDetail(place.content_id)">자세히</div>
    </div>
    <div class="del-btn" v-if="type === 'mypage'" @click='delLike(place.bookmark_pk)'>X</div>
  </div>
    
</template>

<style scoped>
.search-item {
    position: relative;
    width: 150px;
    min-width: 150px;
    height: 200px;
    transition: width 1s;
}
.search-img {
    width: 100%;
    height: 100%;
}

.search-img img {
    border-radius: 5px;
    width: 100%;
    height: 100%;
}

.search-detail {
    position: absolute;
    width: 100%;
    height: 20%;
    padding: 10px;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.3);
    transition: height 1s;
}

.detail-title {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    margin-bottom: 5px;
    font-size: 20px;
    color: white;
}
.detail-main {
    width: 100%;
    display: none;
    color: white;
    text-wrap: wrap;
}
.moreInfo {
  cursor: pointer;
    display: none;
    position: absolute;
    right: 5px;
    bottom: 5px;
    color: white;
}

.moreInfo:hover {
    font-size: 20px;
}

.search-item:hover {
    width: 200px;
}
.search-item:hover .search-detail {
    height: 100%;
}

.search-item:hover .detail-main,
.search-item:hover .moreInfo,
.search-item:hover .del-btn {
    display: block;
}

.del-btn{
  font-family: "Hi Melody", sans-serif;
  cursor: pointer;
  display: none;
  background-color: var(--color-brown-100);
  width: 20px;
  height: 20px;
  text-align: center;
  line-height: 25px;
  position: absolute;
  top: -10px;
  right: -5px;
  border-radius: 50%;
  color: white;
  font-size: 20px;
}
</style>