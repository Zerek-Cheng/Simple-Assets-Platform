(()=>{"use strict";var e={7486:(e,t,r)=>{const o=Vue;var n=r.n(o);const a=VueRouter;var s=r.n(a);n().use(s());const i=[{path:"/",name:"home",component:()=>r.e(177).then(r.bind(r,905))},{path:"/pics",name:"pics",component:()=>r.e(509).then(r.bind(r,6314))},{path:"/upload",name:"upload",component:()=>r.e(650).then(r.bind(r,2471))},{path:"/gallery",name:"gallery",component:()=>r.e(121).then(r.bind(r,3612))},{path:"/pic-info/:pic",name:"pic-info",component:()=>r.e(862).then(r.bind(r,2137)),props:!0}],l=new(s())({mode:"history",base:"/Simple-Assets-Platform/",routes:i,scrollBehavior(e,t,r){return r||{x:0,y:0}}}),u=l;var c=r(6294),d=r.n(c);n().use(d());const p=new(d().Store)({state:{csrf:void 0,user:void 0},getters:{user:e=>e.user?e.user:localStorage.getItem("user")?localStorage.getItem("user"):null},mutations:{user(e,t){if(null==t)return localStorage.removeItem("user"),void(e.user=null);e.user=t,localStorage.setItem("user",t)},csrf(e,t){e.csrf=t}}});var m=r(1884),h=r.n(m),g=function(){var e=this,t=e._self._c;return t("el-row",{attrs:{id:"container"}},[t("div",{attrs:{id:"background"}},[t("ul",{staticClass:"circles"},e._l(10,(function(e){return t("li",{key:e})})),0)]),t("WebHead"),e.isDemo?t("el-alert",{staticClass:"hover-light suspended",staticStyle:{opacity:"60%",height:"auto","line-height":"100%","font-size":"1vh"},attrs:{id:"home-notice",title:"本页面为非商用页面",description:"仅用于项目演示 Design By Zerek-Cheng",type:"warning","show-icon":"",closable:!1,center:""}}):e._e(),t("router-view",{attrs:{id:"content"}}),t("el-backtop",{attrs:{bottom:100,"visibility-height":50}}),t("WebBottom",{attrs:{id:"footer"}})],1)},f=[],v=(r(9312),function(){var e=this,t=e._self._c;return t("el-row",{attrs:{id:"head-container"}},[t("el-col",{attrs:{md:{span:14,push:5},xs:24}},[t("el-col",{staticClass:"hidden-md-and-up",attrs:{xs:24,id:"h-left"}},[t("el-link",{attrs:{id:"header-site-logo"},on:{click:function(t){return e.$router.push({name:"home"})}}},[e._v(" "+e._s(e.SITE_NAME)+" ")])],1),t("el-col",{attrs:{md:7,id:"h-left"}},[t("el-link",{staticClass:"hidden-sm-and-down",attrs:{id:"header-site-logo"},on:{click:function(t){return e.$router.push({name:"home"})}}},[e._v(" "+e._s(e.SITE_NAME)+" ")]),t("el-menu",{staticClass:"flex-center",attrs:{"default-active":e.$route.path,id:"h-menu",mode:"horizontal","background-color":"transparent","text-color":"#fff","active-text-color":"#ffd04b"}},[t("el-menu-item",{attrs:{index:"/"},on:{click:function(t){return e.$router.push({name:"home"})}}},[t("span",[e._v("首页")])]),t("el-menu-item",{attrs:{index:"/pics"},on:{click:function(t){return e.$router.push({name:"pics"})}}},[t("span",[e._v("展示")])]),t("el-menu-item",{attrs:{index:"/upload"},on:{click:function(t){return e.$router.push({name:"upload"})}}},[t("span",[e._v("上传")])])],1)],1),this.user?e._e():t("el-col",{attrs:{md:4,xs:24,id:"h-right"}},[t("el-menu",{staticClass:"flex-center",attrs:{"default-active":e.$route.path,id:"h-menu-right",mode:"horizontal","background-color":"transparent","text-color":"#fff","active-text-color":"#ffd04b"}},[t("el-menu-item",{attrs:{index:"/login"},on:{click:function(t){return e.goAuth("login")}}},[t("span",[e._v("登入")])]),t("el-menu-item",{attrs:{index:"/reg"},on:{click:function(t){return e.goAuth("register")}}},[t("span",[e._v("注册")])])],1)],1),this.user?t("el-col",{attrs:{md:2,xs:24,id:"h-right"}},[t("el-menu",{staticClass:"flex-center",attrs:{"default-active":e.$route.path,id:"h-menu-user",mode:"horizontal","background-color":"transparent","text-color":"#fff","active-text-color":"#ffd04b"}},[t("el-submenu",{attrs:{index:"/user","popper-class":"avatar-submenu","show-timeout":10,"hide-timeout":10}},[t("template",{slot:"title"},[t("el-avatar",{attrs:{src:this.user.avatar,shape:"square",size:"large"}})],1),t("el-menu-item",{attrs:{index:"/gallery"},on:{click:function(t){return e.$router.push({name:"gallery"})}}},[e._v("我的图库 ")]),t("el-menu-item",{attrs:{index:"/price"},on:{click:function(t){return e.$router.push({name:"price"})}}},[e._v("套餐&费用 ")]),t("el-menu-item",{attrs:{index:"/safe"},on:{click:e.profile}},[e._v("个人中心 ")]),t("el-menu-item",{attrs:{index:"/logout"},on:{click:e.logout}},[e._v("退出 ")])],2)],1)],1):e._e()],1)],1)}),b=[];const y={name:"WebHead",data:()=>({SITE_NAME:"Demo Site"}),computed:{...(0,c.mapGetters)(["user"])},methods:{goAuth(e){this.$api.getLogin(e).then((e=>{0===e.data.code?window.location.href=e.data.data:this.$message.warning("授权服务器异常...请稍后再试...")}))},logout(){this.$api.logout().then((e=>{this.$store.commit("user",null),0===e.data.code&&this.$message.success("退出成功")}))},profile(){this.$api.getProfile(window.location.href).then((e=>{0===e.data.code&&(window.location.href=e.data.data)}))}}},w=y;var E=r(3026),k=(0,E.Z)(w,v,b,!1,null,"37bd4f1d",null);const x=k.exports;var S=function(){var e=this,t=e._self._c;return t("el-row",{attrs:{id:"footer"}},[t("el-col",{attrs:{span:24,id:"footer-content"}},[t("p",[e._v("沪ICP备2022014867-1号 沪公网安备X号")]),t("p",[e._v("Powered By Vue 2.7.10 & ElementUi 2.15.9")])])],1)},$=[];const T={name:"WebBottom",data:()=>({input:""})},A=T;var C=(0,E.Z)(A,S,$,!1,null,"8daf9d56",null);const O=C.exports,P={name:"app",data(){return{isDemo:"true"}},components:{WebHead:x,WebBottom:O},methods:{async updateData(){const e=await this.$api.getUserInfo();this.$store.commit("user",0===e.data.code?e.data.data.principal:null)}},created(){this.updateData()}},I=P;var N=(0,E.Z)(I,g,f,!1,null,null,null);const j=N.exports;var U=r(6131);const L=ELEMENT;var R=r.n(L);const q=_;var B=r.n(q);const D=axios;var F=r.n(D);const M=e=>({getImgUrl(t){return`${e.defaults.baseURL}/api/img/get/${t}`},getCsrf(){return F().request({url:"/api/test/csrf",method:"get",responseType:"json"})},getUserInfo(){return e.request({method:"get",url:"/api/test/user",responseType:"json"})},getLogin(){let t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"login";return e.request({url:"login"===t?"/api/login/goSignin":"/api/login/goSignup",method:"post",data:{redirect:`${window.location.origin}/api/login/callback`,callback:window.location.href}})},getProfile(){let t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"";return e.request({url:"/api/login/goProfile",method:"post",data:{returnUrl:t}})},logout(){return e.request({url:"/api/login/logout",method:"post"})},getImgList(){let t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:1,r=arguments.length>1&&void 0!==arguments[1]?arguments[1]:12,o=arguments.length>2&&void 0!==arguments[2]&&arguments[2],n=arguments.length>3&&void 0!==arguments[3]?arguments[3]:null;const a={method:"post",url:"/api/img/list",data:{current:t,size:r,self:o}};return n&&(a.data.search=n),e.request(a)},getImgInfo(t){return e.request({method:"get",url:`/api/img/info/${t}`})},editImgInfo(t,r){return e.request({method:"post",url:`/api/img/edit/${t}`,data:r})},delImg(t){return e.request({method:"post",url:`/api/img/del/${t}`})}});function V(e){console.log(e),-1!==/^[4|5]/i[Symbol.search](e.response.status.toString())?n().prototype.$message.error(`[${e.response.status}]后端处理错误-${e.response.data.message||"未知错误"}`):n().prototype.$message.error(e.message)}n().config.productionTip=!1,n().config.lang="zh-CN",n().use(R()),n().use(h()),n().prototype._=B(),n().prototype.$axios=F().create({baseURL:{VUE_APP_ROUTER_BASE:"/Simple-Assets-Platform/",NODE_ENV:"production",VUE_APP_SITE_TITLE:"Simple Assets Platform",VUE_APP_SITE_NAME:"Demo Site",VUE_APP_SITE_DEMO:"true",VUE_APP_HOME_IMGS:"./assets/img/home-1.jpg,./assets/img/home-2.jpg,./assets/img/home-3.jpg,./assets/img/home-4.jpg",BASE_URL:""}.VUE_APP_AXIOS_BASEURL||window.origin,withCredentials:!0,headers:{"Content-Type":"application/x-www-form-urlencoded"}}),n().prototype.$api=M(n().prototype.$axios),n().prototype.$cookies=U.Z,n().prototype.$axios.interceptors.request.use((e=>("post"===e.method&&-1!==e.headers["Content-Type"].indexOf("x-www-form-urlencoded")&&(e.data=new URLSearchParams(e.data)),n().prototype.$cookies.get("XSRF-TOKEN")||p.state.csrf||n().prototype.$api.getCsrf().then((()=>p.commit("csrf",n().prototype.$cookies.get("XSRF-TOKEN")))),(n().prototype.$cookies.get("XSRF-TOKEN")||p.state.csrf)&&(e.headers["X-XSRF-TOKEN"]=n().prototype.$cookies.get("XSRF-TOKEN")||p.getters.csrf),e)),V),n().prototype.$axios.interceptors.response.use((e=>(n().prototype.$cookies.get("XSRF-TOKEN")&&p.commit("csrf",n().prototype.$cookies.get("XSRF-TOKEN")),e)),V),new(n())({router:u,store:p,render:e=>e(j)}).$mount("#app")},6294:e=>{e.exports=Vuex}},t={};function r(o){var n=t[o];if(void 0!==n)return n.exports;var a=t[o]={exports:{}};return e[o].call(a.exports,a,a.exports,r),a.exports}r.m=e,(()=>{var e=[];r.O=(t,o,n,a)=>{if(!o){var s=1/0;for(c=0;c<e.length;c++){for(var[o,n,a]=e[c],i=!0,l=0;l<o.length;l++)(!1&a||s>=a)&&Object.keys(r.O).every((e=>r.O[e](o[l])))?o.splice(l--,1):(i=!1,a<s&&(s=a));if(i){e.splice(c--,1);var u=n();void 0!==u&&(t=u)}}return t}a=a||0;for(var c=e.length;c>0&&e[c-1][2]>a;c--)e[c]=e[c-1];e[c]=[o,n,a]}})(),(()=>{r.n=e=>{var t=e&&e.__esModule?()=>e["default"]:()=>e;return r.d(t,{a:t}),t}})(),(()=>{r.d=(e,t)=>{for(var o in t)r.o(t,o)&&!r.o(e,o)&&Object.defineProperty(e,o,{enumerable:!0,get:t[o]})}})(),(()=>{r.f={},r.e=e=>Promise.all(Object.keys(r.f).reduce(((t,o)=>(r.f[o](e,t),t)),[]))})(),(()=>{r.u=e=>"js/"+({121:"gallery",177:"home",509:"pics",650:"upload",862:"pic-info"}[e]||e)+"-"+{121:"c25ffeb9",177:"8ae4dd31",188:"5992116f",509:"422f1235",650:"b076d3b0",862:"c0cf72f3",877:"3202abaa",965:"27596453"}[e]+".js"})(),(()=>{r.miniCssF=e=>"css/"+({177:"home",650:"upload",862:"pic-info"}[e]||e)+"-"+{177:"dc6bd0ea",188:"a4e8b572",650:"01546b37",862:"4d2a868b",877:"b3f7cbdd",965:"355e1c73"}[e]+".css"})(),(()=>{r.g=function(){if("object"===typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"===typeof window)return window}}()})(),(()=>{r.o=(e,t)=>Object.prototype.hasOwnProperty.call(e,t)})(),(()=>{var e={},t="element:";r.l=(o,n,a,s)=>{if(e[o])e[o].push(n);else{var i,l;if(void 0!==a)for(var u=document.getElementsByTagName("script"),c=0;c<u.length;c++){var d=u[c];if(d.getAttribute("src")==o||d.getAttribute("data-webpack")==t+a){i=d;break}}i||(l=!0,i=document.createElement("script"),i.charset="utf-8",i.timeout=120,r.nc&&i.setAttribute("nonce",r.nc),i.setAttribute("data-webpack",t+a),i.src=o),e[o]=[n];var p=(t,r)=>{i.onerror=i.onload=null,clearTimeout(m);var n=e[o];if(delete e[o],i.parentNode&&i.parentNode.removeChild(i),n&&n.forEach((e=>e(r))),t)return t(r)},m=setTimeout(p.bind(null,void 0,{type:"timeout",target:i}),12e4);i.onerror=p.bind(null,i.onerror),i.onload=p.bind(null,i.onload),l&&document.head.appendChild(i)}}})(),(()=>{r.r=e=>{"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})}})(),(()=>{r.p=""})(),(()=>{var e=(e,t,r,o)=>{var n=document.createElement("link");n.rel="stylesheet",n.type="text/css";var a=a=>{if(n.onerror=n.onload=null,"load"===a.type)r();else{var s=a&&("load"===a.type?"missing":a.type),i=a&&a.target&&a.target.href||t,l=new Error("Loading CSS chunk "+e+" failed.\n("+i+")");l.code="CSS_CHUNK_LOAD_FAILED",l.type=s,l.request=i,n.parentNode.removeChild(n),o(l)}};return n.onerror=n.onload=a,n.href=t,document.head.appendChild(n),n},t=(e,t)=>{for(var r=document.getElementsByTagName("link"),o=0;o<r.length;o++){var n=r[o],a=n.getAttribute("data-href")||n.getAttribute("href");if("stylesheet"===n.rel&&(a===e||a===t))return n}var s=document.getElementsByTagName("style");for(o=0;o<s.length;o++){n=s[o],a=n.getAttribute("data-href");if(a===e||a===t)return n}},o=o=>new Promise(((n,a)=>{var s=r.miniCssF(o),i=r.p+s;if(t(s,i))return n();e(o,i,n,a)})),n={826:0};r.f.miniCss=(e,t)=>{var r={177:1,188:1,650:1,862:1,877:1,965:1};n[e]?t.push(n[e]):0!==n[e]&&r[e]&&t.push(n[e]=o(e).then((()=>{n[e]=0}),(t=>{throw delete n[e],t})))}})(),(()=>{var e={826:0};r.f.j=(t,o)=>{var n=r.o(e,t)?e[t]:void 0;if(0!==n)if(n)o.push(n[2]);else{var a=new Promise(((r,o)=>n=e[t]=[r,o]));o.push(n[2]=a);var s=r.p+r.u(t),i=new Error,l=o=>{if(r.o(e,t)&&(n=e[t],0!==n&&(e[t]=void 0),n)){var a=o&&("load"===o.type?"missing":o.type),s=o&&o.target&&o.target.src;i.message="Loading chunk "+t+" failed.\n("+a+": "+s+")",i.name="ChunkLoadError",i.type=a,i.request=s,n[1](i)}};r.l(s,l,"chunk-"+t,t)}},r.O.j=t=>0===e[t];var t=(t,o)=>{var n,a,[s,i,l]=o,u=0;if(s.some((t=>0!==e[t]))){for(n in i)r.o(i,n)&&(r.m[n]=i[n]);if(l)var c=l(r)}for(t&&t(o);u<s.length;u++)a=s[u],r.o(e,a)&&e[a]&&e[a][0](),e[a]=0;return r.O(c)},o=globalThis["webpackChunkelement"]=globalThis["webpackChunkelement"]||[];o.forEach(t.bind(null,0)),o.push=t.bind(null,o.push.bind(o))})();var o=r.O(void 0,[998],(()=>r(7486)));o=r.O(o)})();
//# sourceMappingURL=index-20b11cee.js.map