<script setup>
import { ref } from "vue";
import { RouterView, useRouter } from "vue-router";
import { findPwAPI } from "../../api/user.js"

const router = useRouter();

const info = ref({
    user_id: "",
    email_id: "",
    email_domain: "",
});

const success = ref(false);
function findPw() {
    findPwAPI(
        info,
        ({ data }) => {
            if (data.message == "이메일 발송 성공") {
                success.value = true
            }
        },
        (error) => {console.log(error)}
    );
}

</script>

<template>
    <main style="padding-top: 70px">
        <div v-if="success" class="pwfind-box form-box shadow">
            <div class="form-logo brown">Poravel</div>
            <div><span style='font-weight: bold;'>{{info.email_id}}@{{info.email_domain}}</span>로</div> 
            <div>비밀번호가 전송되었습니다.</div>
            <router-link class="btn btn-find-pw shadow" :to="{ name: 'login' }">로그인 페이지로</router-link>
        </div>
        <div v-else class="pwfind-box form-box shadow">
            <div class="form-logo brown">Poravel</div>
            <form id="form-pwfind">
                <input type="text" class="form-input" v-model="info.user_id" placeholder="ID" />
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
                <button type="button" class="btn btn-find-pw shadow" @click="findPw">비밀번호 찾기</button>
            </form>
        </div>
    </main>
</template>

<style scoped>
.pwfind-box {
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

#form-pwfind {
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

.btn-find-pw {
    font-size: 17px;
    margin: 20px 0 0;
    padding: 5px;
    border-radius: 5px;
}
</style>
