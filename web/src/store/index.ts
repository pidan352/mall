import {createStore} from 'vuex'

declare let SessionStorage: any;


const store = createStore({
    state: {
        user: SessionStorage.get("USER") || {},
        token: SessionStorage.get("TOKEN") || {}
    },
    mutations: {
        setUser: (state, user) => {
            state.user = user
            SessionStorage.set("USER", user)
        },
        SET_TOKEN: (state, token) => {
            state.token = token
            sessionStorage.setItem("TOKEN", token)
        }
    },
    actions: {},
    modules: {}
})

export default createStore({
    state: {},
    mutations: {},
    actions: {},
    modules: {}
})
