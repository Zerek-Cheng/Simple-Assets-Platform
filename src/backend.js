export default (axios) => {
    return {
        localUser(user) {
            if (user !== null) localStorage.setItem('user', JSON.stringify(user));
            return user === null ? JSON.parse(localStorage.getItem('user')) : user;
        },
        testCsrf() {
            return axios.request({
                url: '/api/test/csrf',
                method: 'get',
                responseType: 'json'
            })
        },
        testUser() {
            return axios.request({
                method: 'get',
                url: '/api/test/user',
                responseType: 'json'
            })
        },
        goSignin(type = 'login') {
            return axios.request({
                url: type === 'login' ? '/api/login/goSignin' : '/api/login/goSignup',
                method: 'post',
                data: {
                    redirect: `${window.location.origin}/api/login/callback`,
                    callback: window.location.href
                },
            })
        },
        goProfile() {
            return axios.request({
                url: '/api/login/goProfile',
                method: 'post',
            })
        },
        logout() {
            return axios.request({
                url: '/api/login/logout',
                method: 'post',
            })
        }
    };
}
