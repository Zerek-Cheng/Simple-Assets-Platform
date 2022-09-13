import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        csrf: undefined,
        user: undefined,
    },
    getters: {
        user: (state) => {
            if (state.user) return state.user
            if (localStorage.getItem('user')) return localStorage.getItem('user')
            return null;
        }
    },
    mutations: {
        user(state, user) {
            if (user == null) {
                localStorage.removeItem('user');
                state.user = null;
                return;
            }
            state.user = user;
            localStorage.setItem('user', user);
        },
        csrf(state, token) {
            state.csrf = token;
        },
        login(state, login) {
            state.login = login
        },
    },
})
