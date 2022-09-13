import Vue from 'vue'
import router from './router'
import store from './store'
import App from './App.vue'
import Cookies from 'js-cookie';
import axios from './axiosInit';
import qs from 'qs';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'element-ui/lib/theme-chalk/display.css';
import _ from 'lodash';
import backend from './backend';

Vue.config.productionTip = false
Vue.config.lang = 'zh-CN';
Vue.use(ElementUI);

Vue.prototype._ = _;
Vue.prototype.$axios = axios;
Vue.prototype.$api = backend(axios);

Vue.prototype.$axios.interceptors.request.use((config) => {
    if (config.method === 'post') config.data = qs.stringify(config.data);
    if (!Cookies.get('XSRF-TOKEN')) Vue.prototype.$api.testCsrf().then(() => store.commit('csrf', Cookies.get('XSRF-TOKEN')))
    if (Cookies.get('XSRF-TOKEN')) config.headers['X-XSRF-TOKEN'] = store.getters.csrf;
    return config;
}, (error) => Vue.prototype.$message.error(error))
Vue.prototype.$axios.interceptors.response.use((config) => {
    if (Cookies.get('XSRF-TOKEN')) store.commit('csrf', Cookies.get('XSRF-TOKEN'));
    return config;
}, (error) => Vue.prototype.$message.error(error))

new Vue({
    router,
    store,
    render: (h) => h(App)
}).$mount('#app')
