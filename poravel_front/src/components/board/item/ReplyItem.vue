<script setup>
import { deleteReply } from "@/api/board";
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const emit = defineEmits(["replyUpdate"]);

defineProps({ reply: Object });

const route = useRoute();
const router = useRouter();
const { article_type } = route.params;

function replydel(reply_pk) {
    if (userInfo.value === null) {
        alert("로그인을 해주세요");
        router.push({ name: "login" });
    } else if (userInfo.value.user_grade === "admin") {
        console.log(reply_pk);
        deleteReply(article_type, reply_pk, () => {
            console.log("삭제완료");
            emit("replyUpdate");
        }),
            (error) => {
                console.log(error);
            };
    } else {
        alert("관리자가 아닙니다.");
    }
}
</script>

<template>
    <div style="position: relative">
        <div class="reply-id">
            {{ reply.user_id }}
        </div>
        <div class="reply-content">
            {{ reply.content }}
        </div>
        <div class="button-area">
            <input type="button" value="삭제하기" class="re-btn" @click="replydel(reply.reply_pk)" />
        </div>
    </div>
</template>

<style scoped>
.reply-id {
    font-size: 17px;
    font-weight: bold;
    margin-bottom: 5px;
}

.reply-content {
    margin-bottom: 20px;
    padding-bottom: 10px;
    font-size: 14px;
    border-color: var(--color-brown-60);
    border-style: solid;
    border-width: 0px 0 1px 0;
}

.button-area {
    position: absolute;
    bottom: 10px;
    right: 0;
    display: flex;
    gap: 5px;
}

.re-btn {
    cursor: pointer;
    padding: 3px;
    border: 1px solid var(--color-brown-60);
}
</style>
