"use strict";(globalThis["webpackChunksimple_assets_platform_template"]=globalThis["webpackChunksimple_assets_platform_template"]||[]).push([[852],{852:(t,i,e)=>{e.r(i),e.d(i,{default:()=>c});var s=function(){var t=this,i=t._self._c;return i("el-row",[this.info?i("el-card",{attrs:{id:"card"}},[i("el-col",{attrs:{md:11,xs:24,id:"image"}},[i("img",{staticStyle:{width:"100%",height:"100%","text-align":"center"},attrs:{src:this.$api.getImgUrl(t.pic),alt:"",loading:"lazy"}})]),i("el-col",{staticStyle:{"padding-bottom":"1vh"},attrs:{md:{span:12,push:1},xs:24,id:"info"}},[i("el-descriptions",{attrs:{title:`[已展示 ${this.times} 次]${t.info.name}`,column:1,border:"",direction:"vertical",size:"medium","label-style":{"text-align":"center"}}},t._l(this.templates,(function(e){return i("el-descriptions-item",{key:e[0],attrs:{label:e[0]}},[i("el-input",{attrs:{disabled:"",value:e[1],"data-clipboard-text":e[1]},nativeOn:{click:function(i){return t.copyCode(i)}}})],1)})),1)],1),this.user&&this.info&&this.info.owner===this.user.id?i("el-col",{attrs:{id:"settings",xs:18,md:18}},[i("el-checkbox",{ref:"isPublic",on:{change:function(i){return t.edit({isPublic:i})}},model:{value:t.isPublic,callback:function(i){t.isPublic=i},expression:"isPublic"}},[t._v("公共可见 ")]),i("el-date-picker",{ref:"datetime",attrs:{type:"datetime",placeholder:"限时[可选]","time-arrow-control":!0},on:{change:function(i){t.edit({dateLimit:i?i.getTime():0})}},model:{value:t.dateLimit,callback:function(i){t.dateLimit=i},expression:"dateLimit"}}),i("el-input",{ref:"timesLimit",attrs:{placeholder:"限次[可选]",max:"10000",type:"number",id:"upload-setting-times-limit"},on:{change:function(i){return t.edit({timesLimit:i||0})}},model:{value:t.timesLimit,callback:function(i){t.timesLimit=i},expression:"timesLimit"}},[i("template",{slot:"append"},[t._v("次")])],2)],1):t._e()],1):t._e()],1)},a=[],n=e(6294);const o={props:["pic"],name:"PicInfo",computed:{...(0,n.mapGetters)(["user"])},data:()=>({info:void 0,times:void 0,templates:[],isPublic:void 0,dateLimit:void 0,timesLimit:void 0}),watch:{pic(){this.updateImgInfo()}},methods:{copyCode(t){const i=t.currentTarget.children[0];this.$copyText(i.value).then((()=>{this.$message({message:"复制成功",type:"success"})}),(()=>{this.$message({message:"复制失败",type:"error"})}))},updateImgInfo(){this.$api.getImgInfo(this.pic).then((t=>{if(-1===t.data.code)return this.$message.error("图片不存在或状态异常"),void this.$emit("back");this.info=t.data.data.info,this.times=t.data.data.times,this.isPublic=this.info.isPublic,this.dateLimit=this.info.dateLimit?new Date(1e3*this.info.dateLimit):null,this.timesLimit=this.info.timesLimit?this.info.timesLimit:null;const i=`${window.origin}/pic-info/${this.pic}`,e=`${window.origin}${this.$api.getImgUrl(this.pic)}`;this.templates=[["Markdown",`[![${this.info.name}](${e})](${i})]`],["BBCODE",`[url=${i}/pic-info][img]${e}[/img][/url]`],["HTML",`<a href="${i}/pic-info" target="_blank"><img alt='' src="${e}" ></a>`],["展示页",i],["直链",e]]}))},edit(t){this.$api.editImgInfo(this.pic,t).then((t=>{0===t.data.code?this.$message.success("修改成功"):this.$message.error("修改失败")}))}},beforeMount(){this.updateImgInfo()}},l=o;var r=e(3026),m=(0,r.Z)(l,s,a,!1,null,"41a2ee20",null);const c=m.exports}}]);
//# sourceMappingURL=852-e7b07f7a.js.map