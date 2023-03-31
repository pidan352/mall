<template>
    <a-layout>
        <TheHeader/>
        <a-layout>
            <TheLeafMenu/>
            <router-view/>
        </a-layout>
        <TheFooter/>
    </a-layout>
</template>

<script>
    import {defineComponent} from 'vue';
    import TheHeader from '@/components/The-Header.vue'
    import TheFooter from '@/components/the-footer.vue'
    import TheLeafMenu from '@/components/the-leafmenu.vue'

    export default defineComponent({
        name: 'app',
        components: {
            TheHeader,
            TheFooter,
            TheLeafMenu,
        }
    });
</script>

<style>

</style>
