<script setup>
import { ref } from "vue";
defineProps({chooseOption : Array})
</script>

<template>
    <div v-for='option in chooseOption'>
        <input :id='option.si' type="radio" name="chooseOption" value="0" checked="checked" />
        <label for="radio1" >관광지유형</label>
    </div>
        <div v-for="option in chooseOption" :key="option.value" :value="option.value" ></div>
</template>

<style scoped></style>
