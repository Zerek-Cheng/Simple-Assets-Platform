import axios from 'axios';

export default (http) => {
    return {
        getImgUrl(id) {
            return `${http.defaults.baseURL}/api/img/get/${id}`;
        },
        getCsrf() {
            return axios.request({
                url: '/api/test/csrf',
                method: 'get',
                responseType: 'json'
            })
        },
        getUserInfo() {
            return http.request({
                method: 'get',
                url: '/api/test/user',
                responseType: 'json'
            })
        },
        getLogin(type = 'login') {
            return http.request({
                url: type === 'login' ? '/api/login/goSignin' : '/api/login/goSignup',
                method: 'post',
                data: {
                    redirect: `${window.location.origin}/api/login/callback`,
                    callback: window.location.href
                },
            })
        },
        getProfile(returnUrl = '') {
            return http.request({
                url: '/api/login/goProfile',
                method: 'post',
                data: {
                    returnUrl
                }
            })
        },
        logout() {
            return http.request({
                url: '/api/login/logout',
                method: 'post',
            })
        },
        getImgList(page = 1, length = 12, self = false, search = null) {
            const config = {
                method: 'post',
                url: '/api/img/list',
                data: {
                    current: page,
                    size: length,
                    self,
                }
            };
            if (search) config.data.search = search;
            return http.request(config);
        },
        getImgInfo(id) {
            return http.request({
                method: 'get',
                url: `/api/img/info/${id}`
            })
        },
        editImgInfo(id, data) {
            return http.request({
                method: 'post',
                url: `/api/img/edit/${id}`,
                data,
            })
        },
        delImg(id) {
            return http.request({
                method: 'post',
                url: `/api/img/del/${id}`,
            })
        }
    };
}
