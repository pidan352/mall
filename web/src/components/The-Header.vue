<template>
    <a-layout-header class="header">
        <div id="logoinfo">品优购-运营商后台</div>
    </a-layout-header>
</template>

<script>
    import {defineComponent} from 'vue';

    export default defineComponent({
        name: 'the-header',
    });
</script>

<style>
    #logoinfo {
        color: white;
        font-size: 18px;
    }
</style>