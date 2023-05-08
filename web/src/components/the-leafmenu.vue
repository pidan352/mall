<template>
    <a-layout-sider width="200" style="background: #fff">
        <a-menu
                v-model:selectedKeys="selectedKeys"
                :open-keys="openKeys"
                mode="inline"
                :style="{ height: '100%', borderRight: 0 }"
                @openChange="onOpenChange"
        >

            <a-sub-menu key="sub1">
                <template #title>
                    <span><user-outlined/>商家管理</span>
                </template>
                <a-menu-item key="1">商家审核</a-menu-item>
                <a-menu-item key="2">商家管理</a-menu-item>
            </a-sub-menu>


            <a-sub-menu key="sub2">
                <template #title>
                    <span><laptop-outlined/>商品管理</span>
                </template>
                <a-menu-item key="3">
                    <router-link to="/main/brand">品牌管理</router-link>
                </a-menu-item>
                <a-menu-item key="4">规格管理</a-menu-item>
                <a-menu-item key="5">模板管理</a-menu-item>
                <a-menu-item key="6">分类管理</a-menu-item>
                <a-menu-item key="7">商品审核</a-menu-item>
            </a-sub-menu>


            <a-sub-menu key="sub3">
                <template #title>
                    <span><notification-outlined/>广告管理</span>
                </template>
                <a-menu-item key="8">
                    <router-link to="/main/contentCategory"> 广告类别</router-link>
                </a-menu-item>
                <a-menu-item key="9">
                    <router-link to="/main/content">广告内容</router-link>
                </a-menu-item>
            </a-sub-menu>
        </a-menu>
    </a-layout-sider>
</template>

<!-- 一定要加lang='ts'，否则不能正确识别ts语法-->
<script lang="ts">
    import {defineComponent, reactive, toRefs} from 'vue';
    import {UserOutlined, LaptopOutlined, NotificationOutlined} from '@ant-design/icons-vue';

    export default defineComponent({
        name: 'the-leafmenu',

        //引入的icon
        components: {
            UserOutlined,
            LaptopOutlined,
            NotificationOutlined,
        },

        setup() {
            //只展开当前父菜单
            const state = reactive({
                rootSubmenuKeys: ['sub1', 'sub2', 'sub3'],
                openKeys: [''],
                selectedKeys: [],
            });
            const onOpenChange = (openKeys: string[]) => {
                const latestOpenKey = openKeys.find(key => state.openKeys.indexOf(key) === -1);
                if (state.rootSubmenuKeys.indexOf(latestOpenKey!) === -1) {
                    state.openKeys = openKeys;
                } else {
                    state.openKeys = latestOpenKey ? [latestOpenKey] : [];
                }
            };

            return {
                ...toRefs(state),
                onOpenChange,
            };
        }
    })
</script>

<style>

</style>