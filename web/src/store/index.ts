import {createStore} from 'vuex'

//这里的数据是存到浏览器缓存中的
const store = createStore({
    //需要存的数据
    state: {
        // user: SessionStorage.get("USER") || {},
        token: sessionStorage.getItem("TOKEN") || {},
    },
    mutations: {
        // setUser: (state, user) => {
        //     state.user = user
        //     SessionStorage.set("USER", user)
        // },
        SET_TOKEN: (state, token) => {
            state.token = token
            sessionStorage.setItem("TOKEN", token)
        },
    },
    actions: {},
    modules: {}
});

export default store
