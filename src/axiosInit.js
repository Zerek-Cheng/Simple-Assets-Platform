import axios from 'axios';
import Cookies from 'js-cookie';

axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

axios.request({
    url: '/api/test/csrf',
    method: 'get',
    responseType: 'json'
}).then((req) => {
    Cookies.set('XSRF-TOKEN', req.data.data.token)
})


export default axios.create({
    withCredentials: true
});
