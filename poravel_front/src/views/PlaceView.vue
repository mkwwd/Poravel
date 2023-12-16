<script setup>
import { ref, watch, onMounted } from "vue";
import { listSido, listCategory, searchList, searchKeyword } from "@/api/place";
import { useRoute, useRouter } from "vue-router";

import KakaoMap from "../components/common/KakaoMap.vue";
import PlaceItem from "../components/place/placeItem.vue";

onMounted(() => {
    getSido();
    getCategory();
});

const route = useRoute();
const router = useRouter();
const sido = ref([]);
const category = ref([]);

const getSido = () => {
    listSido(({ data }) => {
        sido.value = data;
        console.log(sido.value);
    }),
        (error) => {
            console.log(error);
        };
};

const getCategory = () => {
    listCategory(({ data }) => {
        category.value = data;
        console.log(category.value);
    }),
        (error) => {
            console.log(error);
        };
};

const result = ref([]);
const pickSido = ref("");
const pickCategory = ref("");
const selectPlace = ref({});

watch(pickCategory, () => {
    console.log(pickSido.value);
    if (pickCategory.value === " ") {
        console.log("1" + pickCategory.value);
        return;
    }else if (pickSido.value === "" && pickCategory.value !== "") {
        pickCategory.value = " ";
        alert("지역을 먼저 선택해 주세요");
        console.log(pickCategory.value);
    } else {
        getSearchList();
    }
});

const key = ref({
    sido_code: "",
    category_pk: "",
});

const getSearchList = () => {
    key.value.sido_code = pickSido.value;
    key.value.category_pk = pickCategory.value;
    console.log(key.value);
    searchList(key.value, ({ data }) => {
        result.value = data.attractionList;
    }),
        (error) => {
            console.log(error);
        };
};

const view = (place) => {
    selectPlace.value = place;
};

const box = ref(false);

const other = () => {
    box.value = !box.value;
};

const param = ref({
    title: "",
});

const search = () => {
    console.log(param.value.title);
    searchKeyword(param.value, ({ data }) => {
        result.value = data.attractionList;
        console.log(result.value);
        pickCategory.value = " ";
        pickSido.value = " ";
    }),
        (error) => {
            console.log(error);
        };
    console.log("아프지말자");
};

const reset = () => { 
    pickCategory.value = " ";
}
</script>

<template>
    <main style="padding-top: 70px">
        <div class="check-header">
            <div class="check-area">
                <div v-for="(item, index) in sido.sidoList" :key="index" class="check-item">
                    <input type="radio" name="sidoList" :id="item.sido_name" v-model="pickSido" :value="item.sido_code" class="none" />
                    <label :for="item.sido_name" :class="pickSido === item.sido_code ? 'select' : 'text'" @click='reset'>{{ item.sido_name }}</label>
                </div>
            </div>
            <div class="check-area2">
                <div v-for="(item, index) in category.categoryList" :key="index" class="check-item">
                    <input type="radio" name="categoryList" :id="item.category_title" v-model="pickCategory" :value="item.category_pk" class="none" />
                    <label :for="item.category_title" :class="pickCategory === item.category_pk ? 'select' : 'text'">{{ item.category_title }}</label>
                </div>
                <div style="position: relative">
                    <div class="text" @click="other">다른것을 원하시나요?</div>
                    <div v-if="box" class="keyword-area">
                        <input type="text" class="keyword-input" v-model="param.title" @keyup.enter='search'/>
                        <input type="button" class="btn" value="검색하기" @click="search" />
                    </div>
                </div>
            </div>
        </div>

        <KakaoMap :places="result" :selectPlace="selectPlace"></KakaoMap>

        <div class="search-area">
            <PlaceItem v-for="place in result" :place="place" :key="place.content_id" @mouseenter="view(place)"> </PlaceItem>
        </div>
    </main>
</template>

<style scoped>
.check-header {
    min-width: 1000px;
    position: absolute;
    display: flex;
    flex-direction: column;
    z-index: 10;
    width: 100%;
}

.check-area,
.check-area2 {
    display: flex;
    align-items: center;
    width: 100%;
    text-align: center;
    color: white;
    height: 35px;
    border: 1px solid var(--color-brown-50);
    background-color: var(--color-brown-30);
}

.keyword-area {
    position: absolute;
    top: 35px;
    left: -30px;
    align-self: end;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 20px;
    gap: 5px;
    background-color: var(--color-brown-30);
}

.keyword-input {
    padding: 10px;
    height: 30px;
}

.btn {
    width: 100%;
}

.check-area {
    justify-content: space-evenly;
}

.check-area2 {
    justify-content: center;
    gap: 40px;
}

.text {
    cursor: pointer;
    color: var(--color-brown-100);
    font-size: 17px;
    font-weight: bold;
}

.text:hover,
.select {
    color: white;
    font-weight: normal;
    padding: 5px;
    border-radius: 5px;
    background-color: var(--color-brown-100);
}

.search-area {
    min-width: 100vw;
    cursor: pointer;
    z-index: 50;
    position: fixed;
    bottom: 0;
    padding: 10px;
    display: flex;
    gap: 10px;
    white-space: nowrap;
    background-color: var(--color-brown-30);
    overflow-x: auto;
}
</style>
