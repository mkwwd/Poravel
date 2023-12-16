<script setup>
defineProps({ article: Object });

const cut = (val) => {
    const val2 = val + "";
    const val3 = val2.substring(0, 10);
    return val3;
};
</script>

<template>
    <tr class="list_item">
        <th scope="row">{{ article.article_no }}</th>
        <td class="subject">
            <router-link :to="{ name: 'article-view', params: { article_type: article.article_type, article_no: article.article_pk } }" class="article-title">
                {{ article.subject }}
            </router-link>
        </td>
        <td>{{ article.hit }}</td>
        <td>{{ cut(article.register_time) }}</td>
    </tr>
</template>

<style scoped>
.list_item {
    height: 35px;
    border-width: 1px 0 0 0;
    border-style: solid;
    border-color: var(--color-brown-40);
}

.list_item:hover {
    background-color: var(--color-brown-10);
}

td {
    text-align: center;
}

.subject {
    text-align: left;
    width: 60%;
}
</style>
