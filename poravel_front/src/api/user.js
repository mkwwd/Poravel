import { localAxios, localFileAxios } from "@/util/http-commons";

const local = localAxios();
const localFile = localFileAxios();

async function userConfirm(param, success, fail) {
    console.log("param", param);
    await local.post(`/user`, param).then(success).catch(fail);
    console.log("userConfirm ok");
}

async function findUser(success, fail) {
    console.log("user정보찾기");
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`/user`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
    local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
    await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`/user/logout`).then(success).catch(fail);
}

async function checkId(userid, success, fail) {
    await local.get(`/user/idcheck/${userid}`).then(success).catch(fail);
}

async function join(joinUser, success, fail) {
    await local.post(`/user/join`, JSON.stringify(joinUser.value)).then(success).catch(fail);
}

async function userModify(modifyUser, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    console.log(JSON.stringify(modifyUser.value));
    await local.put(`/user`, JSON.stringify(modifyUser.value)).then(success).catch(fail);
}

async function userDelete(success, fail) {
    console.log("user정보삭제");
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.delete(`/user`).then(success).catch(fail);
}

async function findIdAPI(info, success, fail) {
    await local.post(`/user/findid`, JSON.stringify(info.value)).then(success).catch(fail);
}

async function findPwAPI(info, success, fail) {
    await local.post(`/user/findpw`, JSON.stringify(info.value)).then(success).catch(fail);
}

function addUserImg(dataToSend, success, fail) {
    console.log(dataToSend)
    localFile.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    localFile.post(`/image`, dataToSend).then(success).catch(fail);
}

function checkPw(pw, success, fail) { 
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    local.post(`/user/check`, JSON.stringify(pw.value)).then(success).catch(fail);
}
export { userConfirm, findUser, tokenRegeneration, logout, checkId, join, userModify, userDelete, findIdAPI, findPwAPI, addUserImg, checkPw };
