import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/board";

function ListArticle(article_type, param, success, fail) {
    local.get(`${url}/${article_type}`, { params: param }).then(success).catch(fail);
}

function detailArticle(article_type, article_no, success, fail) {
    local.get(`${url}/${article_type}/${article_no}`).then(success).catch(fail);
}

function registArticle(article_type, article, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    local.post(`${url}/${article_type}`, JSON.stringify(article.value)).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
    console.log("글 수정 가고있니");
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    local.put(`${url}`, JSON.stringify(article.value)).then(success).catch(fail);
}

function deleteArticle(articlepk, success, fail) {
    console.log("삭제가고있니");
    console.log(articlepk);
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    local.delete(`${url}/${articlepk}`).then(success).catch(fail);
}

function addReply(article_type, articlepk, reply, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    local.post(`${url}/${article_type}/${articlepk}`, JSON.stringify(reply.value)).then(success).catch(fail);
}

function deleteReply(article_type, replypk, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    local.delete(`${url}/${article_type}/${replypk}`).then(success).catch(fail);
}

export { ListArticle, detailArticle, registArticle, modifyArticle, deleteArticle, addReply, deleteReply };
