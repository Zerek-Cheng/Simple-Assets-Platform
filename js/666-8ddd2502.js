"use strict";(globalThis["webpackChunkelement"]=globalThis["webpackChunkelement"]||[]).push([[666],{9666:(e,t,a)=>{a.r(t),a.d(t,{default:()=>p});var l=function(){var e=this,t=e._self._c;return t("el-row",[t("el-card",{attrs:{id:"gallery-table"},scopedSlots:e._u([{key:"header",fn:function(){return[t("div",{attrs:{id:"title-col"}},[t("h1",[e._v("我的图库")])])]},proxy:!0}])},[t("el-table",{staticStyle:{width:"100%"},attrs:{stripe:!0,border:!0,data:e.tableData}},[t("el-table-column",{attrs:{type:"expand"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("el-col",{attrs:{id:"gallery-table-expand-col"}},[t("el-form",[t("el-form-item",{attrs:{label:"ID"}},[t("span",[e._v(e._s(a.row.id))])]),t("el-form-item",{attrs:{label:"拥有者"}},[t("span",[e._v(e._s(a.row.owner))])]),t("el-form-item",{attrs:{label:"公开展示"}},[t("span",{domProps:{innerHTML:e._s(a.row.isPublic?"是":"否")}})]),a.row.dateLimit?t("el-form-item",{attrs:{label:"时间限制"}},[t("span",[e._v(e._s(a.row.dateLimit))])]):e._e(),a.row.timesLimit?t("el-form-item",{attrs:{label:"次数限制"}},[t("span",[e._v(e._s(a.row.timesLimit))])]):e._e()],1)],1)]}}])}),t("el-table-column",{attrs:{label:"资源ID",prop:"id"}}),t("el-table-column",{attrs:{"show-overflow-tooltip":!0,label:"资源名",prop:"name"}}),t("el-table-column",{attrs:{label:"上传者ID",prop:"owner"}})],1),t("el-pagination",{staticStyle:{display:"flex","justify-content":"center","align-items":"center","margin-top":"1vh","flex-wrap":"wrap-reverse"},attrs:{"current-page":e.pageNum,"page-sizes":[10,20,50,100],"page-size":e.pageSize,total:e.total,layout:"total, sizes, prev, pager, next, jumper"},on:{"current-change":e.updateTable,"size-change":e.updateTable,"update:currentPage":function(t){e.pageNum=t},"update:current-page":function(t){e.pageNum=t},"update:pageSize":function(t){e.pageSize=t},"update:page-size":function(t){e.pageSize=t}}})],1)],1)},r=[];const i={name:"GalleryTable",data(){return{total:0,pageSize:10,pageNum:1,tableData:[]}},methods:{updateTable(){this.$api.getUserImgTotal().then((e=>{this.total=e.data.data})),this.updateTableData(this.pageNum)},updateTableData(){let e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:1;this.$api.getImgList(e,this.pageSize,!0).then((e=>{this.tableData=e.data.data.img}))}},beforeMount(){this.updateTable()}},s=i;var n=a(3026),o=(0,n.Z)(s,l,r,!1,null,null,null);const p=o.exports}}]);
//# sourceMappingURL=666-8ddd2502.js.map