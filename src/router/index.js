import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'home',
        component: () => import(/* webpackChunkName: "home" */ '../views/HomeView.vue'),
    },
    {
        path: '/pics',
        name: 'pics',
        component: () => import(/* webpackChunkName: "pics" */ '../views/PicView.vue'),
    },
    {
        path: '/upload',
        name: 'upload',
        component: () => import(/* webpackChunkName: "about" */ '../views/UploadView.vue')
    },
    {
        path: '/pic-info/:pic',
        name: 'pic-info',
        component: () => import(/* webpackChunkName: "about" */ '../views/PicInfoView.vue'),
        props: true
    }
]

function getAbsolutePath() {
    const path = location.pathname
    return path.substring(0, path.lastIndexOf('/') + 1)
}


const router = new VueRouter({
    mode: 'history',
    base: getAbsolutePath(),
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