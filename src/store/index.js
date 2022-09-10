import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        csrf: undefined,
        login: false,
        user: undefined,
    },
    getters: {
        user: () => {
            try {
                return JSON.parse(localStorage.getItem('user'))
            } catch (e) {
                return null;
            }
        },
    },
    mutations: {
        csrf(state, token) {
            state.csrf = token;
        },
        login(state, login) {
            state.login = login
        },
        uesr(state, user) {
            state.user = user
        }
    },
})
