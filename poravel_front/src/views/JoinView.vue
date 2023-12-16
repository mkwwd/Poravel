<script setup>
import { ref, watch } from "vue";
import { checkId } from "@/api/user";
import { join } from "@/api/user";
import { useRouter } from "vue-router";

const router = useRouter();

const joinUser = ref({
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

// 아이디 체크
const idCheck = ref(false);
const idValid = ref(false);
const resultid = ref("");

watch(
    () => joinUser.value.user_id,
    (value) => {
        console.log(value);
        if (value.length == 0) {
            idValid.value = false;
            idCheck.value = false;
        } else if (value.length < 4 || value.length > 16) {
            idValid.value = false;
            idCheck.value = true;
            resultid.value = "아이디는 4자이상 16자 이하입니다.";
        } else if (value.search(/\s/) != -1) {
            idValid.value = false;
            idCheck.value = true;
            resultid.value = "아이디는는 공백 없이 입력해주세요.";
        } else if (value.search(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/) > 0) {
            idValid.value = false;
            idCheck.value = true;
            resultid.value = "한글은 사용 불가 합니다.";
        } else {
            checkId(value, ({ data }) => {
                if (data.usable) {
                    idValid.value = true;
                    resultid.value = "사용가능한 아이디 입니다.";
                } else {
                    idValid.value = false;
                    resultid.value = "사용중인 아이디 입니다.";
                }
                console.log(data);
            }),
                (error) => {
                    console.log(error);
                };
        }
    }
);

// 비밀번호 체크
const pwCheck = ref(false);
const pwValid = ref(false);
const resultpw = ref("");

watch(
    () => joinUser.value.user_password,
    (value) => {
        console.log(value);
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
    }
);

// 비밀번호 일치 여부
const confirmpw = ref("");
const cpCheck = ref(false);
const cpValid = ref(false);
const resultcp = ref("");

watch(
    () => confirmpw.value,
    (value) => {
        if (value.length == 0) {
            cpValid.value = false;
            cpCheck.value = false;
        } else if (joinUser.value.user_password === value) {
            cpValid.value = true;
            cpCheck.value = false;
        } else {
            cpValid.value = false;
            cpCheck.value = true;
            resultcp.value = "비밀번호가 일치하지 않습니다.";
        }
    }
);

const focus = ref(false);

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

            joinUser.value.zip_code = data.zonecode;
            joinUser.value.address = addr;

            console.log(joinUser.value.zip_code);
            console.log(joinUser.value.address);
            close();
        },
    }).open();
};

function gojoin() {
    if (idValid && pwValid && cpValid) {
        join(joinUser, ({ data }) => {
            if (data.success) {
                alert("회원가입 성공");
                router.push("/");
            }
        }),
            (error) => {
                console.log(error);
            };
    } else {
        console.log("실패");
        alert("다시 확인해주세요");
    }
}
</script>

<template>
    <main style="padding-top: 70px">
        <div class="join-box form-box shadow">
            <div class="form-logo brown">Poravel</div>
            <form id="form-join">
                <input type="text" class="form-input" v-model="joinUser.user_id" placeholder="ID" />
                <div v-if="idCheck" :class="idValid ? 'is-valid' : 'no-valid'" class="result-view">{{ resultid }}</div>
                <input type="password" class="form-input" v-model="joinUser.user_password" placeholder="Password" />
                <div v-if="pwCheck" :class="pwValid ? 'is-valid' : 'no-valid'" class="result-view">{{ resultpw }}</div>
                <input type="password" class="form-input" v-model="confirmpw" placeholder="Comfirm Password" />
                <div v-if="cpCheck" :class="cpValid ? 'is-valid' : 'no-valid'" class="result-view">{{ resultcp }}</div>
                <input type="text" class="form-input" v-model="joinUser.user_name" placeholder="Username" />
                <div class="postcode">
                    <input type="text" class="form-input" v-model="joinUser.zip_code" placeholder="postcode" />
                    <input type="button" class="btn find-btn" @click="showApi" value="우편번호 찾기" />
                </div>
                <div class="wrap"></div>
                <input type="text" class="form-input" v-model="joinUser.address" placeholder="Address" />
                <input type="text" class="form-input address-detail" v-on:focus="focus" v-model="joinUser.address_detail" placeholder="Address detail" />
                <div class="form-input email-area">
                    <input type="text" class="email" v-model="joinUser.email_id" placeholder="Email" />
                    <span class="fc-5 anno">@</span>
                    <select name="emailDomain" v-model="joinUser.email_domain" class="email-domain fc-5">
                        <option selected>Domain</option>
                        <option value="ssafy.com">ssafy.com</option>
                        <option value="google.com">google.com</option>
                        <option value="naver.com">naver.com</option>
                        <option value="kakao.com">kakao.com</option>
                    </select>
                </div>
                <input type="text" class="form-input" v-model="joinUser.phone_num" placeholder="Phone Number" />
                <button type="button" class="btn btn-join shadow" @click="gojoin">JOIN</button>
            </form>
        </div>
    </main>
</template>

<style scoped>
.join-box {
    background: #fff;
    margin: 60px auto;
    padding: 100px 30px 40px;
    width: 450px;
}
.form-logo {
    position: absolute;
    top: 40px;
    font-weight: 600;
    font-size: 40px;
}
#form-join {
    display: flex;
    flex-direction: column;
    width: 80%;
}

.form-input {
    height: 30px;
    padding: 0 20px;
    margin-top: 30px;
    box-shadow: inset 0 -5px 10px -10px var(--color-brown-100);
}

.result-view {
    height: 20px;
    padding: 10px;
    font-size: 10px;
}

.no-valid {
    color: red;
}

.is-valid {
    color: var(--color-brown-100);
}

.postcode {
    position: relative;
}

.find-btn {
    position: absolute;
    bottom: 0;
    right: 0;
    padding: 8px;
    border-radius: 5px;
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
.btn-join {
    margin-top: 40px;
    font-size: 17px;
    height: 40px;
    border-radius: 10px;
}
</style>
