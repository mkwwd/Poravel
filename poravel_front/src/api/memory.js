import { localAxios, localFileAxios } from "@/util/http-commons";

const local = localAxios();
const localFile = localFileAxios();

const url = "/memory";

function ListMemory(success, fail) {
    local.get(`${url}/list`).then(success).catch(fail);
}

function BestMemory(success, fail) {
    local.get(`${url}/best`).then(success).catch(fail);
}

function addMemory(dataToSend, success, fail) {
    console.log(dataToSend);
    localFile.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    localFile.post(`${url}`, dataToSend).then(success).catch(fail);
}

function getMemory(memorypk, success, fail) {
    localFile.get(`${url}/${memorypk}`).then(success).catch(fail);
}

function findMemory(param, success, fail) {
    local.get(`${url}/list`, { params: param }).then(success).catch(fail);
}

function deleteMemory(delItem, success, fail) { 
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    local.delete(`/memory/${delItem}`).then(success).catch(fail);
}
export { ListMemory, BestMemory, addMemory, getMemory, findMemory, deleteMemory };
