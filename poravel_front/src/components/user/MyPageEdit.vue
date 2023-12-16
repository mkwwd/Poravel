<script setup>
import { ref, computed, onMounted, watchEffect } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "../../stores/member";
import { userModify, addUserImg } from "../../api/user";

const memberStore = useMemberStore();
const router = useRouter();
const { VITE_VUE_API_URL } = import.meta.env;

const { isLogin, profile, userInfo } = storeToRefs(memberStore);
const { replaceImg, getUserInfo, deleteUser } = memberStore;

const formData = ref({
    image: null,
});
const submitForm = () => {
    console.log(formData.value.image);
    const dataToSend = new FormData();
    dataToSend.append("image", formData.value.image);
    dataToSend.append("type", "user");
    addUserImg(dataToSend, (response) => {
        console.log("등록성공");
        router.go(router.currentRoute);
    }),
        (error) => {
            console.log(error);
        };
};
const fileUp = async (e) => {
    formData.value.image = e.target.files[0];
    submitForm();
};

onMounted(() => {
    set();
});

const set = () => {
    modifyUser.value = userInfo.value;
};

const modifyUser = ref({
    user_id: "",
    user_password: "",
    user_name: "",
    zip_code: "",
    address: "",
    address_detail: "",
    email_id: "",
    email_domain: "",
    phone_num: "",
});

const print = () => {
    console.log(modifyUser.value.phone_num);
};

// 우편번호
const showApi = () => {
    new daum.Postcode({
        oncomplete: (data) => {
            let addr = "";

            if (data.userSelectedType === "R") {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            modifyUser.value.zip_code = data.zonecode;
            modifyUser.value.address = addr;

            close();
        },
    }).open();
};

// 비밀번호 체크
const pwCheck = ref(false);
const pwValid = ref(false);
const resultpw = ref("");

const check = () => {
    let value = modifyUser.value.user_password;
    if (value.length == 0) {
        pwValid.value = false;
        pwCheck.value = false;
    } else if (value.length < 4 || value.length > 16) {
        pwValid.value = false;
        pwCheck.value = true;
        resultpw.value = "비밀번호는 4자이상 16자 이하입니다.";
    } else if (value.search(/\s/) != -1) {
        pwValid.value = false;
        pwCheck.value = true;
        resultpw.value = "비밀번호는 공백 없이 입력해주세요.";
    } else if (value.search(/[0-9]/) < 0 || value.search(/[a-z]/gi) < 0 || value.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi) < 0) {
        pwValid.value = false;
        pwCheck.value = true;
        resultpw.value = "영문, 숫자, 특수문자를 혼합하여 입력해주세요.";
    } else {
        pwValid.value = true;
        resultpw.value = "사용가능한 비밀번호 입니다.";
    }
};

// 비밀번호 일치 여부
const confirmpw = ref("");
const cpCheck = ref(false);
const cpValid = ref(false);
const resultcp = ref("");

const check2 = () => {
    let value = confirmpw.value;
    if (value.length == 0) {
        cpValid.value = false;
        cpCheck.value = false;
    } else if (modifyUser.value.user_password === value) {
        cpValid.value = true;
        cpCheck.value = false;
    } else {
        cpValid.value = false;
        cpCheck.value = true;
        resultcp.value = "비밀번호가 일치하지 않습니다.";
    }
};

const emit = defineEmits(["changeMode"]);

const modify = async () => {
    console.log("실행ㄱㄱ");
    console.log(modifyUser.value);
    await userModify(modifyUser);
    getUserInfo();
    router.push({ name: "mypage" });
};

const delUser = async () => {
    console.log("탈퇴bb");
    await deleteUser();
    if (!isLogin) {
        alert("탈퇴완료");
        console.log("탈퇴완 => 메인이동");
        router.push("/");
    } else {
        alert("탈퇴실패");
    }
};

const profileImageUrl = computed(() => {
    const userId = userInfo.value.user_id;
    return `${VITE_VUE_API_URL}image/user/${userId}`;
});
</script>

<template>
    <main style="padding-top: 70px">
        <form @submit.prevent="submitForm" class="mypage-info mypage-modify shadow" action="">
            <div class="title">My profile</div>
            <div class="myprofile">
                <img class="profile-img shadow" :src="profileImageUrl" @error="replaceImg" @fileUp="fileChange" />
                <div class="profile-edit">
                    <label for="image">
                        <div class="btn-upload">+</div>
                    </label>
                    <input type="file" name="image" id="image" @change="fileUp" />
                </div>
            </div>
            <div class="info-area mypage-input">
                <span class="imo"></span>
                <input type="text" class="fc-5" v-model="modifyUser.user_id" readonly />
            </div>
            <div class="info-area mypage-input">
                <span class="imo"></span>
                <input type="text" class="fc-5" v-model="modifyUser.user_name" readonly />
            </div>
            <div class="info-area mypage-input">
                <span class="imo"></span>
                <input type="text" v-model="modifyUser.phone_num" class="myinput" @keyup="print" />
            </div>
            <div class="info-area mypage-input">
                <span class="imo"></span>
                <input type="text" id="email" class="email" v-model="modifyUser.email_id" />
                <span class="anno">@</span>
                <select name="emailDomain" id="emailDomain" v-model="modifyUser.email_domain" class="email-domain">
                    <option selected>{{ modifyUser.email_domain }}</option>
                    <option value="ssafy.com">ssafy.com</option>
                    <option value="google.com">google.com</option>
                    <option value="naver.com">naver.com</option>
                    <option value="kakao.com">kakao.com</option>
                </select>
            </div>
            <div class="postcode info-area mypage-input">
                <span class="imo"></span>
                <input type="text" v-model="modifyUser.zip_code" />
                <input type="button" class="post-btn btn" @click="showApi" value="우편번호 찾기" />
            </div>
            <div class="info-area mypage-input">
                <span class="imo"></span>
                <input type="text" class="myinput" v-model="modifyUser.address" />
            </div>
            <div class="info-area mypage-input">
                <span class="imo"></span>
                <input type="text" class="myinput" v-model="modifyUser.address_detail" />
            </div>
            <div class="info-area mypage-input" style="position: relative">
                <span class="imo"></span>
                <input type="password" class="myinput" placeholder="새 비밀번호" v-model="modifyUser.user_password" @keyup="check" />
                <div v-if="pwCheck" :class="pwValid ? 'is-valid' : 'no-valid'" class="result-view">{{ resultpw }}</div>
            </div>
            <div class="info-area mypage-input" style="position: relative">
                <span class="imo"></span>
                <input type="password" class="myinput" placeholder="새 비밀번호 확인" @keyup="check2" v-model="confirmpw" />
                <div v-if="cpCheck" :class="cpValid ? 'is-valid' : 'no-valid'" class="result-view">{{ resultcp }}</div>
            </div>
            <div class="btn-area">
                <input type="button" class="btn shadow" style="margin-right: 5px" @click="modify" value="수정하기" />
                <input type="button" class="btn shadow" style="background: rgba(211, 47, 11, 0.71)" @click="delUser" value="탈퇴하기" />
            </div>
        </form>
    </main>
</template>

<style scoped>
@import "../../assets/css/mypage.css";
.mypage-modify {
    min-width: 550px;
}
.title {
    text-align: center;
    font-size: 40px;
}

.myprofile {
    width: 30%;
    position: relative;
    margin: 10px auto 20px;
}
.profile-edit {
    position: absolute;
    bottom: 5px;
    right: 20px;
}
.btn-upload {
    cursor: pointer;
    display: flex;
    width: 30px;
    height: 30px;
    justify-content: center;
    align-items: center;
    padding-bottom: 5px;
    border-radius: 50%;
    color: white;
    font-size: 25px;
    background-color: rgba(137, 87, 76, 0.9);
}

#image {
    display: none;
}

.info-area {
    margin: 0 40px 20px;
}

.myinput {
    width: 100%;
}
.mypage-modify {
    padding: 40px 50px 100px;
    gap: 30px;
}
.mypage-input {
    height: 30px;
    box-shadow: inset 0 -5px 10px -10px var(--color-brown-100);
}
.mypage-input input {
    font-size: 16px;
}

.email {
    width: 50%;
}
.email-domain {
    width: 50%;
    background: inherit;
    border: none;
}
.anno {
    margin-bottom: 2px;
    margin-right: 5px;
}

.postcode {
    position: relative;
}

.post-btn {
    position: absolute;
    right: 0;
    bottom: 5px;
    padding: 5px;
    border-radius: 5px;
}

.result-view {
    position: absolute;
    left: 5px;
    top: 30px;
}

.no-valid {
    color: red;
}

.is-valid {
    color: var(--color-brown-100);
}
</style>
