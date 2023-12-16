<script setup>
import { ref } from "vue";
import { RouterView, useRouter } from "vue-router";
import { findIdAPI } from "../../api/user.js"

const router = useRouter();

const info = ref({
    user_name: "",
    email_id: "",
    email_domain: "",
});

const success = ref(false);
const user_id = ref("");
function findId() {
    console.log(info.value)
    findIdAPI(
        info,
        ({ data }) => {
            if (data.user_id != undefined) {
                success.value = true
                user_id.value = data.user_id;
            }
            console.log(data.user_id)
        },
        (error) => {console.log(error)}
    );
}
</script>

<template>
    <main style="padding-top: 70px">
        <div v-if="success" class="idfind-box form-box shadow">
            <div class="form-logo brown">Poravel</div>
            <div>{{info.user_name}}님의 아이디는 <span style='font-weight: bold;'>{{ user_id }}</span>입니다.</div>
            <router-link class="btn btn-find-id shadow" :to="{ name: 'login' }">로그인 하러가기</router-link>
        </div>
        <div v-else class="idfind-box form-box shadow">
            <div class="form-logo brown">Poravel</div>
            <form id="form-idfind">
                <input type="text" class="form-input" v-model="info.user_name" placeholder="이름" />
                <div class="form-input email-area">
                    <input type="text" class="email" v-model="info.email_id" placeholder="Email" />
                    <span class="fc-5 anno">@</span>
                    <input type="text" class="email-domain fc-5" v-model="info.email_domain" />
                    <!-- <select name="emailDomain" v-model="joinUser.email_domain" class="email-domain fc-5">
                            <option selected>Domain</option>
                            <option value="ssafy.com">ssafy.com</option>
                            <option value="google.com">google.com</option>
                            <option value="naver.com">naver.com</option>
                            <option value="kakao.com">kakao.com</option>
                        </select> -->
                </div>
    
                <!-- <input type="text" class="form-input" v-model="loginUser.userid" placeholder="ID" /> -->
                <!-- <input type="password" class="form-input" v-model="loginUser.userpwd" placeholder="Password" /> -->
                <button type="button" class="btn btn-find-id shadow" @click="findId">아이디 찾기</button>
            </form>
        </div>
    </main>
</template>

<style scoped>
.idfind-box {
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

#form-idfind {
    display: flex;
    flex-direction: column;
    width: 80%;
    gap: 10px;
}

.email-area {
    display: flex;
    justify-content: space-between;
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
    margin-right: 5px;
    padding-top: 3px;
}

.form-input {
    height: 30px;
    padding: 0 20px;
    box-shadow: inset 0 -5px 10px -10px var(--color-brown-100);
    margin-bottom: 15px;
}

.btn-find-id {
    font-size: 17px;
    margin: 20px 0 0;
    padding: 5px;
    border-radius: 5px;
}
</style>
