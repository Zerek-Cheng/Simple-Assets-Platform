"use strict";(globalThis["webpackChunkelement"]=globalThis["webpackChunkelement"]||[]).push([[443],{570:(t,e,i)=>{i.r(e),i.d(e,{default:()=>n});var s=function(){var t=this,e=t._self._c;return e("el-row",[e("h1",[t._v("123")])])},o=[];const a={name:"GalleryView"},l=a;var r=i(3026),c=(0,r.Z)(l,s,o,!1,null,null,null);const n=c.exports},2137:(t,e,i)=>{i.r(e),i.d(e,{default:()=>n});var s=function(){var t=this,e=t._self._c;return e("el-row",[e("el-col",{attrs:{md:{span:16,push:4},xs:{span:22,push:1},id:"info"}},[e("PicInfo",{attrs:{pic:t.pic},on:{back:t.back}})],1)],1)},o=[];const a={name:"PicInfoView",props:["pic"],components:{PicInfo:()=>i.e(877).then(i.bind(i,9877))},methods:{back(){window.history.go(-1)}}},l=a;var r=i(3026),c=(0,r.Z)(l,s,o,!1,null,"72302967",null);const n=c.exports},7185:(t,e,i)=>{i.r(e),i.d(e,{default:()=>u});var s=function(){var t=this,e=t._self._c;return e("el-row",[e("el-col",{attrs:{md:{span:16,push:4},xs:{span:22,push:1},id:"upload-col"}},[e("el-card",{attrs:{id:"box-card"}},[e("el-col",{attrs:{slot:"header",id:"upload-card-header"},slot:"header"},[e("el-col",{staticStyle:{display:"inline-block",width:"auto","margin-right":"2em"}},[e("h1",[t._v("图片上传")])]),this.user?e("el-col",{attrs:{id:"upload-setting",xs:24,md:18}},[e("el-checkbox",{ref:"isPublic",attrs:{value:!0},model:{value:t.isPublic,callback:function(e){t.isPublic=e},expression:"isPublic"}},[t._v(" 公共可见 ")]),e("el-date-picker",{ref:"datetime",attrs:{type:"datetime",placeholder:"限时[可选]","time-arrow-control":!0},model:{value:t.dateLimit,callback:function(e){t.dateLimit=e},expression:"dateLimit"}}),e("el-input",{ref:"timesLimit",attrs:{placeholder:"限次[可选]",max:"10000",type:"number",id:"upload-setting-times-limit"},model:{value:t.timesLimit,callback:function(e){t.timesLimit=e},expression:"timesLimit"}},[e("template",{slot:"append"},[t._v("次")])],2)],1):t._e()],1),this.user?t._e():e("el-alert",{attrs:{id:"login-warn",title:"未登录",type:"error",description:"只有登录后才能上传图片，点击上方按钮登录","show-icon":""}}),e("el-divider"),e("el-upload",{ref:"upload",staticClass:"upload",attrs:{disabled:!t.user,action:"/api/upload","list-type":"picture","file-list":t.files,limit:10,"auto-upload":!1,"with-credentials":!0,headers:{"X-XSRF-TOKEN":t.csrf},"http-request":t.requestUpload,"on-success":t.onSuccess,"on-preview":t.onPreview,accept:".jpg,.jpeg,.png,.gif,.bmp,.webp,.svg,.tiff",drag:"",multiple:""}},[e("div",{staticClass:"el-upload__text"},[e("p",[e("i",{staticClass:"el-icon-upload"})]),t._v(" 将文件拖到此处，或"),e("em",[t._v("点击这里")])])]),this.user?e("el-button",{attrs:{type:"success",id:"confirm"},on:{click:function(e){return e.preventDefault(),e.stopPropagation(),t.confirmUpload.apply(null,arguments)}}},[t._v(" 确认上传 ")]):t._e()],1)],1)],1)},o=[],a=i(6294);const l={data:()=>({files:[],isPublic:!0,dateLimit:null,timesLimit:null}),watch:{isPublic(t){this.$cookies.set("isPublic",t)},dateLimit(t){t&&t>Date.now()?this.$cookies.set("dateLimit",t):(this.dateLimit=null,this.$cookies.remove("dateLimit"))},timesLimit(t){t&&t>0?this.$cookies.set("timesLimit",t):(this.timesLimit=null,this.$cookies.remove("timesLimit"))}},computed:{...(0,a.mapState)(["user","csrf"])},beforeMount(){this.isPublic=void 0===this.$cookies.get("isPublic")||!!JSON.parse(this.$cookies.get("isPublic")),this.dateLimit=void 0===this.$cookies.get("dateLimit")?null:new Date(this.$cookies.get("dateLimit")),this.timesLimit=void 0===this.$cookies.get("timesLimit")?null:this.$cookies.get("timesLimit")},methods:{completeData(t){const e={};return e.isPublic=this.isPublic,e.file=t.file,this.dateLimit&&(e.dataLimit=this.dateLimit.getTime()),this.timesLimit&&(e.timesLimit=this.timesLimit),e},confirmUpload(){this.$refs.upload.submit()},requestUpload(t){this.$axios.request({method:"post",url:t.action,headers:{"Content-Type":"multipart/form-data"},data:this.completeData(t)}).then((e=>t.onSuccess(e.data,t.file))).catch((e=>t.onError(e,t.file)))},onSuccess(t,e){-1===t.code&&(e.status="error",this.$message.error(t.message))},onPreview(t){t.response&&0===t.response.code&&window.open(this.$router.resolve({name:"pic-info",params:{pic:t.response.data.id}}).href)}}},r=l;var c=i(3026),n=(0,c.Z)(r,s,o,!1,null,"d72f5972",null);const u=n.exports}}]);
//# sourceMappingURL=about-32838a6a.js.map