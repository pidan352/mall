import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import Home from '../views/Home.vue'
import Main from '../views/Main.vue'
import Brand from '../views/Brand.vue'
import Content from '../views/Content.vue'
import ContentCategory from "../views/ContentCategory.vue";
import Login from "../views/Login.vue"

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'Login',
        component: Login,
    },
    {
        path: '/main',
        name: 'Main',
        component: Main,
        redirect: '/main/home',
        //二级路由不需要斜杠，浏览器路径显示为一级路径/二级路径
        children: [
            {
                path: 'home',
                name: 'Home',
                component: Home,
            },
            {
                path: 'brand',
                name: 'Brand',
                component: Brand,
            },
            {
                path: 'content',
                name: 'Content',
                component: Content,
            },
            {
                path: 'contentCategory',
                name: 'contentCategory',
                component: ContentCategory,
            },
        ]
    },

]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
