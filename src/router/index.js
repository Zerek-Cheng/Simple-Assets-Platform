import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'home',
        component: () => import(/* webpackChunkName: "home" */ '@/views/HomeView.vue'),
    },
    {
        path: '/pics',
        name: 'pics',
        component: () => import(/* webpackChunkName: "pics" */ '@/views/PublicPicView.vue'),
    },
    {
        path: '/upload',
        name: 'upload',
        component: () => import(/* webpackChunkName: "upload" */ '@/views/UploadView.vue')
    },
    {
        path: '/gallery',
        name: 'gallery',
        component: () => import(/* webpackChunkName: "gallery" */ '@/views/GalleryView.vue')
    },
    {
        path: '/pic-info/:pic',
        name: 'pic-info',
        component: () => import(/* webpackChunkName: "pic-info" */ '@/views/PicInfoView.vue'),
        props: true
    },
]


const router = new VueRouter({
    mode: 'history',
    base: process.env.VUE_APP_ROUTER_BASE ? process.env.VUE_APP_ROUTER_BASE : '/',
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
