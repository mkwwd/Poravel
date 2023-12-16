<script setup>
import { RouterView } from "vue-router";
</script>

<template>
    <div class="info-container">
        <RouterView></RouterView>
    </div>
</template>

<style scoped>
.info-container {
    min-width: 1000px;
    width: 70%;
    margin: auto;
}


</style>
