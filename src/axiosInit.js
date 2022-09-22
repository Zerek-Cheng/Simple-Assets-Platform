import axios from 'axios';

export default axios.create({
    baseURL: process.env.VUE_APP_AXIOS_BASEURL || window.origin,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
    }
});
