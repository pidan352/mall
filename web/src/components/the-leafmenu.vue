<template>
    <a-layout-sider width="200" style="background: #fff">
        <a-menu
                v-model:selectedKeys="selectedKeys2"
                v-model:openKeys="openKeys"
                mode="inline"
                :style="{ height: '100%', borderRight: 0 }"
        >
            <a-sub-menu key="sub1">
                <template #title>
              <span>
                <user-outlined/>
                商家管理
              </span>
                </template>
                <a-menu-item key="1">option1</a-menu-item>
                <a-menu-item key="2">option2</a-menu-item>
                <a-menu-item key="3">option3</a-menu-item>
                <a-menu-item key="4">option4</a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="sub2">
                <template #title>
              <span>
                <laptop-outlined/>
                商品管理
              </span>
                </template>
                <a-menu-item key="5">品牌管理</a-menu-item>
                <a-menu-item key="6">规格管理</a-menu-item>
                <a-menu-item key="7">模板管理</a-menu-item>
                <a-menu-item key="8">分类管理</a-menu-item>
                <a-menu-item key="">商品审核</a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="sub3">
                <template #title>
              <span>
                <notification-outlined/>
                广告管理
              </span>
                </template>
                <a-menu-item key="9">广告类别</a-menu-item>
                <a-menu-item key="10">广告内容</a-menu-item>
            </a-sub-menu>
        </a-menu>
    </a-layout-sider>
</template>

<script>
    import {defineComponent} from 'vue';

    export default defineComponent({
        name: 'the-leafmenu',
    });
</script>

<style scoped>

</style>