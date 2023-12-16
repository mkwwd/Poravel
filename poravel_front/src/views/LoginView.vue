<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "../stores/member";

const router = useRouter();

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;

const loginUser = ref({
    user_id: "",
    user_password: "",
});

const login = async () => {
    if (loginUser.value.user_id === "" || loginUser.value.user_password === "") {
        alert("아이디와 비번 모두 입력해주세요");
        return;
    }
    await userLogin(loginUser.value);
    console.log("isLogin: ", isLogin);
    if (isLogin.value) {
        getUserInfo();
        alert("로그인 성공");
        router.push("/");
    } else {
        alert("아이디랑 비번을 다시 확인해주세요");
    }
};
</script>

<template>
    <main style="padding-top: 70px">
        <div class="login-box form-box shadow">
            <div class="form-logo brown">Poravel</div>
            <form id="form-login">
                <input type="text" class="form-input" v-model="loginUser.user_id" placeholder="ID" />
                <input type="password" class="form-input" @keyup.enter="login" v-model="loginUser.user_password" placeholder="Password" />
                <!-- <div class="save-check">
                    <input id="saveid" type="checkbox" />
                    <label for="saveid"></label>
                    <span class="fc-5">Remember Me</span>
                </div> -->
                <button type="button" class="btn btn-login shadow" @click="login">LOGIN</button>
            </form>
            <div class="select-area">
                <router-link :to="{ name: 'find-id' }" class="fc-5">Find ID</router-link>
                <span class="fc-5">/</span>
                <router-link :to="{ name: 'find-pw' }" class="fc-5">Find Password</router-link>
                <span class="fc-5">/</span>
                <router-link :to="{ name: 'join' }" class="fc-5">Join</router-link>
            </div>
        </div>
    </main>
</template>

<style scoped>
.login-box {
    background: #fff;
    margin: 150px auto auto;
    padding: 130px 30px 40px;
    width: 400px;
}

.form-logo {
    position: absolute;
    top: 40px;
    font-weight: 600;
    font-size: 40px;
}

#form-login {
    display: flex;
    flex-direction: column;
    width: 80%;
    gap: 10px;
}

.form-input {
    height: 30px;
    padding: 0 20px;
    box-shadow: inset 0 -5px 10px -10px var(--color-brown-100);
    margin-bottom: 15px;
}

.save-check {
    display: flex;
    gap: 5px;
    font-size: 13px;
    align-items: center;
    align-self: flex-end;
}

input[type="checkbox"] {
    display: none;
}

input[type="checkbox"] + label {
    margin-top: 1px;
    display: inline-block;
    width: 12px;
    height: 12px;
    border-radius: 50%;
    border: 3px solid var(--color-brown-100);
}

input[id="saveid"]:checked + label {
    background-color: var(--color-brown-100);
}

.btn-login {
    font-size: 17px;
    height: 40px;
    margin-bottom: 10px;
    border-radius: 10px;
}

.select-area a {
    font-size: 12px;
}
</style>
