const {defineConfig} = require('@vue/cli-service')

const cdn = {
    css: [
        'https://unpkg.com/element-ui/lib/theme-chalk/index.css'
    ],
    js: [
        // // vue
        'https://cdn.jsdelivr.net/npm/vue@2.7.10/dist/vue.min.js',
        // vue-router
        'https://cdn.jsdelivr.net/npm/vue-router@3.6.4/dist/vue-router.min.js',
        // vuex
        'https://cdn.jsdelivr.net/npm/vuex@3.6.2/dist/vuex.min.js',
        'https://unpkg.com/element-ui/lib/index.js',
        'https://cdn.jsdelivr.net/npm/axios@0.27.2/dist/axios.min.js',
        'https://cdn.jsdelivr.net/npm/lodash@4.17.21/lodash.min.js',
    ]
}

module.exports = defineConfig({
    publicPath: process.env.NODE_ENV === 'production' ? './' : '/',
    pages: {
        index: {
            entry: 'src//main.js',
            template: 'public/index.html',
            filename: 'index.html',
            title: 'Simple Assets Platform - Demo',
            chunks: ['chunk-vendors', 'chunk-common', 'index']
        },
    },
    css: {
        extract: process.env.NODE_ENV === 'production',
        loaderOptions: {
            sass: {
                additionalData: '@import "~@/variables.scss";'
            }
        }
    },
    transpileDependencies: false,
    devServer: {
        port: 8080,
        hot: true,
        proxy: {
            '/api': {
                target: 'http://127.0.0.1:8081',
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    },
    chainWebpack: (config) => {
        config.performance.set('hints', false)
        if (process.env.NODE_ENV === 'production') {
            config.externals({
                'element-ui': 'ELEMENT',
                'vue-router': 'VueRouter',
                vue: 'Vue',
                vuex: 'Vuex',
                axios: 'axios',
                lodash: '_'
            })
            config.plugin('html-index')
                .tap((args) => {
                    args[0].cdn = cdn
                    return args
                })
            config.plugin('extract-css').tap((args) => {
                args[0].filename = 'css/[name]-[contenthash:8].css'
                args[0].chunkFilename = 'css/[name]-[contenthash:8].css'
                return args;
            })
        } else {
            config.devtool('source-map')
        }
        config.output.filename('js/[name]-[contenthash:8].js').chunkFilename('js/[name]-[contenthash:8].js').end()
    },
})
