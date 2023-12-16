<script setup>
import BoardFormItem from '../item/BoardFormItem.vue';


defineProps({ articleType: String });
</script>

<template>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-10">{{ articleType }}</div>
            <div class="col-lg-10 text-start">
                <BoardFormItem type="regist" :article-type="articleType" ></BoardFormItem>
            </div>
        </div>
    </div>
</template>

<style scoped>
.container{
    width: 70%;
    margin: 0 auto;
}
</style>