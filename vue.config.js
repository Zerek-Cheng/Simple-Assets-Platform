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
        'https://unpkg.com/element-ui/lib/index.js'
    ]
}

module.exports = defineConfig({
    pages: {
        index: {
            // page 的入口
            entry: 'src//main.js',
            // 模板来源
            template: 'public/index.html',
            // 在 dist/index.html 的输出
            filename: 'index.html',
            // 当使用 title 选项时，
            // template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
            title: '标题',
            // 在这个页面中包含的块，默认情况下会包含
            // 提取出来的通用 chunk 和 vendor chunk。
            chunks: ['chunk-vendors', 'chunk-common', 'index']
        },
    },
    css: {
        extract: process.env.NODE_ENV === 'production',
        loaderOptions: {
            sass: {
                additionalData: '@import "~@/assets/scss/variables.scss";'
            }
        }
    },
    transpileDependencies: true,
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
        if (process.env.NODE_ENV === 'production') {
            config.externals({
                'element-ui': 'ELEMENT',
                'vue-router': 'VueRouter',
                vue: 'Vue',
                vuex: 'Vuex',
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
