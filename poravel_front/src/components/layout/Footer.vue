<script setup></script>

<template>
    <div class="footer-container">
        <div class="bar"></div>
        <div class="footer-area">
            <img src="" alt="eo" />
            <div class="footer-info">
                <div>본 사이트의 콘텐츠는 저작권법의 보호를 받는 바 무단 전재, 복사, 배포 등을 금합니다.</div>
                <div>Copyright &copy;SSAFY</div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.footer-container {
    position: absolute;
    bottom: 0;
    width: 100%;
}
.bar {
    width: 100%;
    height: 1px;
    background-color: var(--color-brown-100);
}
.footer-area {
    margin: 30px auto;
    width: 80%;
    gap: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
}
.footer-info {
}
</style>
