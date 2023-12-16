<script setup>
import { ref, computed, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRouter, useRoute } from "vue-router";
import { useMemberStore } from "../../stores/member";

const { VITE_VUE_API_URL } = import.meta.env;

const router = useRouter();
const route = useRoute();

const isHover = ref(false);

function addActive(e) {
    setTimeout(() => (isHover.value = true));
}

function removeActive(e) {
    setTimeout(() => (isHover.value = false));
    console.log(isHover.value);
}

const memberStore = useMemberStore();

const { isLogin, userInfo } = storeToRefs(memberStore);
const { userLogout, replaceImg } = memberStore;

const logout = async () => {
    console.log("로그아웃!!!!");
    await userLogout();
    if (!isLogin) {
        console.log("로그아웃 성공");
    }
    router.push("/");
};

const completeUrl = computed(() => {
    return `${VITE_VUE_API_URL}image/user/${userInfo.value.user_id}`;
});

const isActive = ref(true);

onMounted(() => {
    console.log("*******************************");
    console.log(router);
    isActive.value = route.name === "main";
    console.log(isActive.value);
});
</script>

<template>
    <header>
        <nav class="nav-bar container">
            <div class="nav-container">
                <router-link :to="{ name: 'main' }">
                    <a id="title" class="nav-area active" href="#">Poravel</a>
                </router-link>
                <ul class="nav-area active">
                    <li class="nav-item">
                        <router-link :to="{ name: 'place' }" class="nav-link" style="padding-top: 2px">여행정보</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :to="{ name: 'memory' }" class="nav-link">Memory</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :to="{ name: 'help' }" class="nav-link">HelpDesk</router-link>
                    </li>
                </ul>
            </div>
            <div class="nav-container">
                <template v-if="isLogin">
                    <ul class="nav-area">
                        <li class="nav-item">
                            <img class="profile shadow" :src="completeUrl" @error="replaceImg" />
                        </li>
                        <li class="nav-item">
                            <span style="font-weight: bold">{{ userInfo.user_id }}</span
                            >님 환영합니다
                        </li>
                        <li :class="[{ 'is-hover': isHover }, 'nav-item']" @mouseenter="addActive" @mouseleave="removeActive">
                            ▼
                            <ul class="nav-detail bg-color">
                                <li class="nav-item">
                                    <router-link to="/" @click.prevent="logout" class="nav-link">Logout</router-link>
                                </li>
                                <div class="line"></div>
                                <li class="nav-item">
                                    <router-link :to="{ name: 'mypage' }" class="nav-link">Mypage</router-link>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </template>
                <template v-else>
                    <ul class="nav-area active">
                        <li class="nav-item">
                            <router-link :to="{ name: 'login' }" class="nav-link">Login</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link :to="{ name: 'join' }" class="nav-link">Join</router-link>
                        </li>
                    </ul>
                </template>
            </div>
        </nav>
    </header>
</template>

<style scoped>
header {
    z-index: 100;
    position: absolute;
    min-width: 750px;
    display: flex;
    align-items: center;
    padding: 20px;
    width: 100%;
    height: 70px;
}

.nav-bar {
    display: flex;
    justify-content: space-between;
}

.nav-bar a,
.nav-item {
    cursor: pointer;
    font-size: 17px;
    font-weight: 500;
    display: flex;
    justify-content: center;
    align-items: center;
}

.container {
    width: 100%;
    display: flex;
}

.nav-container {
    display: flex;
}

.nav-area {
    display: flex;
    gap: 15px;
}

#title {
    font-weight: 600;
    font-size: 40px;
    margin-right: 15px;
}

.is-hover {
    position: relative;
}

.nav-detail {
    background-color: inherit;
    z-index: 100;
    display: none;
    position: absolute;
    top: 30px;
    right: 0px;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
    width: 90px;
    height: 100px;
    border-radius: 5px;
    box-shadow: 0px 0px 10px 0px var(--shadow);
}

.is-hover .nav-detail {
    display: flex;
}

.line {
    width: 70px;
    height: 1px;
    background: rgba(137, 87, 76, 0.1);
}

.profile {
    width: 35px;
    height: 30px;
    border-radius: 50%;
}

.nav-link:hover {
    font-weight: 600;
    box-shadow: 0px 5px 5px -5px var(--shadow);
}
</style>
