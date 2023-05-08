import {createApp} from 'vue'
import App from './App.vue';
import router from './router'
import store from './store'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css';
import axios from "axios";

createApp(App).use(store).use(router).use(Antd).mount('#app')

//对axios请求作统一处理
axios.interceptors.request.use(function (config) {
    // console.log('请求参数：', config);
    const token = store.state.token;
    if (token != {}) {
        config.headers.authorization = token;
        // console.log("请求headers增加token:", token);
    }
    return config;
}, error => {
    return Promise.reject(error);
});


//对axios请求的响应作统一处理
// axios.interceptors.response.use(function (response) {
//     // console.log('返回结果：', response);
//     return response;
// }, error => {
//     return Promise.reject(error);
// });