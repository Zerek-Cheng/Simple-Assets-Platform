import axios from 'axios';

export default axios.create({
    baseURL: window.origin,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
    }
});
