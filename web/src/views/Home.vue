<template>
            <a-layout style="padding: 0 24px 24px">
                <a-layout-content
                        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
                >
                    首页
                </a-layout-content>
            </a-layout>
</template>

<script lang="ts">
    import {defineComponent} from 'vue';
    import HelloWorld from '@/components/HelloWorld.vue'; // @ is an alias to /src

    export default defineComponent({
        name: 'Home',
        components: {
            HelloWorld,
        },
    });
</script>
