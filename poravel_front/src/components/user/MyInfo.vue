<script setup>
import { ref, computed } from "vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "../../stores/member";
import { checkPw } from "../../api/user";
import router from "../../router";

const memberStore = useMemberStore();
const { VITE_VUE_API_URL } = import.meta.env;

const { profile, userInfo } = storeToRefs(memberStore);
const { replaceImg } = memberStore;

const emit = defineEmits(["changeMode"]);

const popup = ref(false);
const password = ref({
    user_password: "",
});

const completeUrl = computed(() => {
    return `${VITE_VUE_API_URL}image/user/${userInfo.value.user_id}`;
});

const pop = () => {
    popup.value = true;
};

const pwCheck = () => {
    checkPw(password, (response) => {
        alert("확인완료");
        popup.value = false;
        router.push({ name: "mypage-edit" });
    }),
        (error) => {
            console.log("비밀번호가 일치하지 않습니다.");
            console.log(error);
        };
};
</script>

<template>
    <div class="mypage-info shadow" style="margin-top: 150px">
        <div class="myprofile">
            <img class="shadow" :src="completeUrl" @error="replaceImg" />
        </div>
        <div class="info-area">
            <span class="imo"></span>
            <span>{{ userInfo.user_name }}</span>
        </div>
        <div class="info-area">
            <span class="imo"></span>
            <span>{{ userInfo.phone_num }}</span>
        </div>
        <div class="info-area">
            <span class="imo"></span>
            <span>{{ userInfo.email_id }}@{{ userInfo.email_domain }}</span>
        </div>
        <div class="info-area">
            <span class="imo"></span>
            <span>{{ userInfo.address }}</span>
        </div>
        <button class="btn edit-btn shadow" @click="pop">내 정보 수정</button>
    </div>
    <div v-if="popup" class="checkpw">
        <div class="pc-box form-box shadow">
            <form class="pc-item">
                <input type="password" class="form-input" placeholder="password" v-model="password.user_password" />
                <button type="button" class="btn btn-check-pw shadow" @click="pwCheck">비밀번호 확인</button>
            </form>
        </div>
    </div>
</template>

<style scoped>
@import "../../assets/css/mypage.css";
.myprofile {
    position: absolute;
    bottom: 220px;
    left: 50%;
    transform: translateX(-50%);
}

.checkpw {
    z-index: 100;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(0, 0, 0, 0.4);
}

.pc-box {
    width: 300px;
    padding: 35px 40px;
}

.pc-item {
    display: flex;
    flex-direction: column;
    width: 100%;
}

.form-input {
    height: 30px;
    padding: 0 10px;
    box-shadow: inset 0 -5px 10px -10px var(--color-brown-100);
    margin-bottom: 20px;
}

.btn-check-pw {
    padding: 10px;
    border-radius: 5px;
}
</style>
