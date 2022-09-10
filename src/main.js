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

Vue.config.productionTip = false
Vue.config.lang = 'zh-CN';
Vue.use(ElementUI);

Vue.prototype.$axios = axios;
Vue.prototype.$axios.interceptors.request.use((config) => {
    if (config.method === 'post') {
        config.data = qs.stringify(config.data);
    }
    config.headers['X-XSRF-TOKEN'] = Cookies.get('XSRF-TOKEN');
    return config;
})
// Vue.prototype.$axios.interceptors.response.use(() => {
//     if (Cookies.get('XSRF-TOKEN')) {
//         store.commit('csrf', Cookies.get('XSRF-TOKEN'));
//     }
// })

new Vue({
    router,
    store,
    render: (h) => h(App)
}).$mount('#app')
