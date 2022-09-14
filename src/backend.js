import axios from 'axios';

export default (http) => {
    return {
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
        getProfile() {
            return http.request({
                url: '/api/login/goProfile',
                method: 'post',
            })
        },
        logout() {
            return http.request({
                url: '/api/login/logout',
                method: 'post',
            })
        },
        getImgUrl(id) {
            return `/api/img/get/${id}`
        },
        getImgList(page = 1, length = 12) {
            return http.request({
                method: 'post',
                url: '/api/img/list',
                data: {
                    current: page,
                    size: length
                }
            })
        },
        getImgInfo(id) {
            return http.request({
                method: 'get',
                url: `/api/img/info/${id}`
            })
        }
    };
}
