"use strict";(self["webpackChunkelement"]=self["webpackChunkelement"]||[]).push([[348],{9348:(t,e,i)=>{i.r(e),i.d(e,{default:()=>l});var s=function(){var t=this,e=t._self._c;return e("el-row",{staticStyle:{width:"100%"}},[this.info?e("el-card",{attrs:{id:"card"}},[e("el-col",{attrs:{md:11,xs:24,id:"image"}},[e("el-image",{staticStyle:{width:"100%",height:"100%","text-align":"center"},attrs:{src:this.$api.getImgUrl(t.pic)}})],1),e("el-col",{staticStyle:{"padding-bottom":"1vh"},attrs:{md:{span:12,push:1},xs:24,id:"info"}},[e("el-descriptions",{attrs:{title:t.info.name,column:1,border:"",direction:"vertical",size:"medium","label-style":{"text-align":"center"}}},t._l(this.templates,(function(i){return e("el-descriptions-item",{key:i[0],attrs:{label:i[0]}},[e("el-input",{attrs:{disabled:"",value:i[1],"data-clipboard-text":i[1]},nativeOn:{click:function(e){return t.copyCode(e)}}})],1)})),1)],1)],1):t._e()],1)},a=[];i(3167);const n={props:["pic"],name:"PicInfo",data:()=>({info:void 0,templates:[]}),methods:{copyCode(t){const e=t.currentTarget.children[0];this.$copyText(e.value).then((()=>{this.$message({message:"复制成功",type:"success"})}),(()=>{this.$message({message:"复制失败",type:"error"})}))}},beforeMount(){this.$api.getImgInfo(this.pic).then((t=>{if(-1===t.data.code)return this.$message.error("图片不存在或状态异常"),void this.$emit("back");this.info=t.data.data;const e=`${window.origin}/pic-info/${this.pic}`,i=`${window.origin}${this.$api.getImgUrl(this.pic)}`;this.templates.push(["Markdown",`[![${this.info.name}](${i})](${e})]`],["BBCODE",`[url=${e}/pic-info][img]${i}[/img][/url]`],["HTML",`<a href="${e}/pic-info" target="_blank"><img src="${i}" ></a>`],["展示页",e],["直链",i])}))}},r=n;var o=i(3026),c=(0,o.Z)(r,s,a,!1,null,"34f4ded9",null);const l=c.exports}}]);
//# sourceMappingURL=348-a5f00e76.js.map