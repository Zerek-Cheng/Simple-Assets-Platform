"use strict";(self["webpackChunkelement"]=self["webpackChunkelement"]||[]).push([[177],{6822:(e,t,s)=>{s.r(t),s.d(t,{default:()=>o});var a=function(){var e=this,t=e._self._c;return t("el-row",[t("el-col",{staticClass:"hidden-sm-and-down",attrs:{span:24}},[t("el-carousel",{ref:"imgmd",attrs:{interval:5e3,arrow:"always",height:"40vh",type:"card",id:"img-md"}},e._l(e.img,(function(e){return t("el-carousel-item",{key:e},[t("el-image",{staticStyle:{width:"100%",height:"100%"},attrs:{draggable:"false",src:e,fit:"cover"}})],1)})),1)],1),t("el-col",{staticClass:"hidden-md-and-up",attrs:{span:24}},[t("el-carousel",{ref:"imgxs",attrs:{interval:5e3,arrow:"always",height:"30vh",id:"img-xs"}},e._l(e.img,(function(e){return t("el-carousel-item",{key:e},[t("el-image",{staticStyle:{width:"100%",height:"100%"},attrs:{draggable:"false",src:e,fit:"cover"}})],1)})),1)],1),t("el-col",{attrs:{id:"future",md:{span:20,push:2},xs:24,loading:!0}},[t("el-card",{attrs:{shadow:"hover"}},[t("i",{staticClass:"el-icon-circle-check"}),t("b",[e._v("隐私原则")]),t("p",[e._v("我们仅将您的个人信息用于我们在隐私声明中规定的特定目的，不会以不符合这些目的方式使用您的个人信息。")])]),t("el-card",{attrs:{shadow:"hover"}},[t("i",{staticClass:"el-icon-bank-card"}),t("b",[e._v("低成本")]),t("p",[e._v("支持按量付费模式，按实际使用量付费，无需提前一次性投入；无需运维人员与托管费用，0成本运维")])]),t("el-card",{attrs:{shadow:"hover"}},[t("i",{staticClass:"el-icon-cpu"}),t("b",[e._v("QOS 99.95%")]),t("p",[e._v("如服务可用性低于99.95%，用户可获得10%、25%、100%不等的赔偿。")])]),t("el-card",{attrs:{shadow:"hover"}},[t("i",{staticClass:"el-icon-chat-dot-square"}),t("b",[e._v("服务便捷")]),t("p",[e._v(" 系统将会帮助企业实现7*24小时均可在线，并且对客户搜索关键字进行分析，从而根据强大数据库以及话术库，与客户进行多轮对话。")])])],1)],1)},r=[];const i={name:"HomeView",data(){return{img:"./assets/img/home-1.jpg,./assets/img/home-2.jpg,./assets/img/home-3.jpg,./assets/img/home-4.jpg".split(",")}},methods:{setCursor(e){let t=0,s=0;const a=function(){t=0,s=0};e.addEventListener("touchstart",(e=>{t=e.changedTouches[0].pageX})),e.addEventListener("touchmove",(e=>{s=e.changedTouches[0].pageX})),e.addEventListener("touchend",(()=>{if(0!==s&&t-s!==0)return t-s>0?(a(),this.$refs.imgmd.next(),void this.$refs.imgxs.next()):void(t-s<0&&(a(),this.$refs.imgmd.prev(),this.$refs.imgxs.prev()));a()}))}},mounted(){this.setCursor(document.getElementById("img-md")),this.setCursor(document.getElementById("img-xs"))}},l=i;var c=s(3026),n=(0,c.Z)(l,a,r,!1,null,"18aba068",null);const o=n.exports}}]);
//# sourceMappingURL=home-787b455c.js.map