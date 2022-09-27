import Vue from 'vue'
import router from './router'
import store from './store'
import VueClipboard from 'vue-clipboard2';
import App from './App.vue'
import Cookies from 'js-cookie';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'element-ui/lib/theme-chalk/display.css';
import _ from 'lodash';
import backend from './backend';
import axios from 'axios';

Vue.config.productionTip = process.env.NODE_ENV !== 'production'
Vue.config.lang = 'zh-CN'

Vue.use(ElementUI)
Vue.use(VueClipboard)

Vue.prototype._ = _
Vue.prototype.$axios = axios.create({
    baseURL: process.env.VUE_APP_AXIOS_BASEURL || window.origin,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
    }
});
Vue.prototype.$api = backend(Vue.prototype.$axios);
Vue.prototype.$cookies = Cookies;

function onReqError(error) {
    console.log(error);
    /^[4|5]/i[Symbol.search](error.response.status.toString()) !== -1
        ? Vue.prototype.$message.error(`[${error.response.status}]后端处理错误-${error.response.data.message || '未知错误'}`)
        : Vue.prototype.$message.error(error.message);
}

Vue.prototype.$axios.interceptors.request.use((config) => {
    // axios原生json转换为form query raw
    if (config.method === 'post' && config.headers['Content-Type'].indexOf('x-www-form-urlencoded') !== -1) config.data = new URLSearchParams(config.data);
    if (!Vue.prototype.$cookies.get('XSRF-TOKEN') && !store.state.csrf) Vue.prototype.$api.getCsrf().then(() => store.commit('csrf', Vue.prototype.$cookies.get('XSRF-TOKEN')))
    if (Vue.prototype.$cookies.get('XSRF-TOKEN') || store.state.csrf) config.headers['X-XSRF-TOKEN'] = Vue.prototype.$cookies.get('XSRF-TOKEN') || store.getters.csrf;
    return config;
}, onReqError)
Vue.prototype.$axios.interceptors.response.use((config) => {
    if (Vue.prototype.$cookies.get('XSRF-TOKEN')) store.commit('csrf', Vue.prototype.$cookies.get('XSRF-TOKEN'));
    return config;
}, onReqError)

new Vue({
    router,
    store,
    render: (h) => h(App)
}).$mount('#app')
