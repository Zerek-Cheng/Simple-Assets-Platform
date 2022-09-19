import Vue from 'vue'
import router from './router'
import store from './store'
import VueClipboard from 'vue-clipboard2';
import App from './App.vue'
import Cookies from 'js-cookie';
import axios from './axiosInit';
import qs from 'qs';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'element-ui/lib/theme-chalk/display.css';
import _ from 'lodash';
import backend from './backend';

Vue.config.productionTip = process.env.NODE_ENV !== 'production'
Vue.config.lang = 'zh-CN'
Vue.use(ElementUI)
Vue.use(VueClipboard)

Vue.prototype._ = _
Vue.prototype.$axios = axios;
Vue.prototype.$api = backend(axios);

function onReqError(error) {
    /^[4|5]/i[Symbol.search](error.response.status.toString()) !== -1
        ? Vue.prototype.$message.error('后端异常，请联系管理员')
        : Vue.prototype.$message.error(error.message);
}

Vue.prototype.$axios.interceptors.request.use((config) => {
    if (config.method === 'post') config.data = qs.stringify(config.data);
    if (!Cookies.get('XSRF-TOKEN') && !store.state.csrf) Vue.prototype.$api.getCsrf().then(() => store.commit('csrf', Cookies.get('XSRF-TOKEN')))
    if (Cookies.get('XSRF-TOKEN') || store.state.csrf) config.headers['X-XSRF-TOKEN'] = Cookies.get('XSRF-TOKEN') || store.getters.csrf;
    return config;
}, onReqError)
Vue.prototype.$axios.interceptors.response.use((config) => {
    if (Cookies.get('XSRF-TOKEN')) store.commit('csrf', Cookies.get('XSRF-TOKEN'));
    return config;
}, onReqError)

new Vue({
    router,
    store,
    render: (h) => h(App)
}).$mount('#app')
