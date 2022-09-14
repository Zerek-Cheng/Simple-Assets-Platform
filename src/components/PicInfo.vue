<template>
  <el-row style="width: 100%">
    <el-card v-if="this.info" id="card">
      <el-col :md="11" :xs="24" id="image">
        <el-image :src="this.$api.getImgUrl(pic)" style="width: 100%;height: 100%;text-align: center"/>
      </el-col>
      <el-col :md="{span:12,push:1}" :xs="24" id="info" style="padding-bottom: 1vh">
        <el-descriptions :title="info.name"
                         :column="1"
                         border
                         direction="vertical"
                         size="medium"
                         :label-style="{'text-align':'center'}">
          <el-descriptions-item v-for="item in this.templates" :label="item[0]" :key="item[0]">
            <el-input disabled :value="item[1]" @click.native="copyCode($event)" :data-clipboard-text="item[1]"/>
          </el-descriptions-item>
        </el-descriptions>
      </el-col>
    </el-card>
  </el-row>
</template>
<script>
export default {
  props: ['pic'],
  name: 'PicInfo',
  data: () => ({
    info: undefined,
    templates: []
  }),
  methods: {
    copyCode(event) {
      const input = event.currentTarget.children[0];
      this.$copyText(input.value).then(() => {
        this.$message({
          message: '复制成功',
          type: 'success'
        });
      }, () => {
        this.$message({
          message: '复制失败',
          type: 'error'
        });
      });
      // navigator.clipboard.writeText(event.currentTarget.children[0].value).then(() => ))
    }
  },
  beforeMount() {
    this.$api.getImgInfo(this.pic).then((res) => {
      if (res.data.code === -1) {
        this.$message.error('图片不存在或状态异常')
        this.$emit('back')
        return;
      }
      this.info = res.data.data;
      const pageUrl = `${window.origin}/pic-info/${this.pic}`
      const originUrl = `${window.origin}${this.$api.getImgUrl(this.pic)}`;
      this.templates.push(...[
        ['Markdown', `[![${this.info.name}](${originUrl})](${pageUrl})]`],
        ['BBCODE', `[url=${pageUrl}/pic-info][img]${originUrl}[/img][/url]`],
        ['HTML', `<a href="${pageUrl}/pic-info" target="_blank"><img src="${originUrl}" ></a>`],
        ['展示页', pageUrl],
        ['直链', originUrl],
      ])
    })
  }
}
</script>
<style lang="scss">
#info .el-descriptions__header {
  justify-content: center;
}

#card .el-card__body {
  display: inline-flex;
  align-items: center;
  @media screen and (max-width: 768px) {
    display: block;
  }
}
</style>
<style scoped>


#image {
  height: 100%;
}
</style>
