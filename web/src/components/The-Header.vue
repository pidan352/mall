<template>
    <a-layout-header class="header">
        <div id="logoinfo">品优购-运营商后台</div>
        <a-menu
                v-model:selectedKeys="selectedKeys1"
                theme="dark"
                mode="horizontal"
                :style="{ lineHeight: '64px' }"
        >
        </a-menu>
    </a-layout-header>
</template>

<script>
    import {defineComponent} from 'vue';

    export default defineComponent({
        name: 'the-header',
    });
</script>

<style scoped>

</style>