import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/map";

function listSido(success, fail) {
    local.get(`${url}/sido`).then(success).catch(fail);
}

function listCategory(success, fail) {
    local.get(`${url}/category`).then(success).catch(fail);
}

function searchList(param, success, fail) {
    local.get(`${url}`, { params: param }).then(success).catch(fail);
}

function searchDetail(content_id, success, fail) { 
    console.log(content_id);
    local.get(`${url}/${content_id}`).then(success).catch(fail);
}

function searchKeyword(param, success, fail) { 
    local.get(`${url}`, { params: param }).then(success).catch(fail);
}

function savePlace(place, success, fail) { 
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    local.post(`/bookmark`, JSON.stringify(place.value)).then(success).catch(fail);
}

function deleteLike(delItem, success, fail) { 
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    local.delete(`/bookmark/${delItem}`).then(success).catch(fail);
}

function addReview(review, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    local.post(`/review`, JSON.stringify(review.value)).then(success).catch(fail);
}

function deleteReview(review, success, fail) { 
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    local.delete(`/review/${review}`).then(success).catch(fail);
}

export { listSido, listCategory, searchList, searchDetail, searchKeyword, savePlace, deleteLike, addReview, deleteReview };
