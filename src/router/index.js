import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'home',
        components: {
            default: () => import(/* webpackChunkName: "home" */ '../views/HomeView.vue')
        },
    },
    {
        path: '/pics',
        name: 'pics',
        components: {
            default: () => import(/* webpackChunkName: "pics" */ '../views/PicView.vue')
        },
    },
    {
        path: '/upload',
        name: 'upload',
        component: () => import(/* webpackChunkName: "about" */ '../views/UploadView.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition
        } else {
            return {x: 0, y: 0}
        }
    },
})


export default router
