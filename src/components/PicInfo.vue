<template>
  <el-row>
    <el-card v-if="this.info" id="card">
      <el-col :md="11" :xs="24" id="image">
        <img :src="this.$api.getImgUrl(pic)" alt=""
             style="width: 100%;height: 100%;text-align: center;" loading="lazy"/>
      </el-col>
      <el-col :md="{span:12,push:1}" :xs="24" id="info" style="padding-bottom: 1vh">
        <el-descriptions :title="`[已展示 ${this.times} 次]${info.name}`"
                         :column="1"
                         border
                         direction="vertical"
                         size="medium"
                         :label-style="{'text-align':'center'}">
          <el-descriptions-item v-for="item in this.templates" :label="item[0]" :key="item[0]">
            <el-input disabled :value="item[1]"
                      @click.native="copyCode($event)"
                      :data-clipboard-text="item[1]"/>
          </el-descriptions-item>
        </el-descriptions>
      </el-col>
      <el-col id="settings" :xs="18" :md="18" v-if="this.user && this.info && this.info.owner===this.user.id">
        <el-checkbox v-model="isPublic" ref="isPublic"
                     @change="edit({isPublic: $event})">公共可见
        </el-checkbox>
        <el-date-picker
            v-model="dateLimit"
            type="datetime"
            placeholder="限时[可选]"
            :time-arrow-control="true" ref="datetime"
            @change="edit({dateLimit: $event?$event.getTime():0})">
        </el-date-picker>
        <el-input placeholder="限次[可选]" max="10000"
                  v-model="timesLimit"
                  type="number" ref="timesLimit"
                  id="upload-setting-times-limit"
                  @change="edit({timesLimit: $event?$event:0})">
          <template slot="append">次</template>
        </el-input>
      </el-col>
    </el-card>
  </el-row>
</template>
<script>
import {mapGetters} from 'vuex';

export default {
  props: ['pic'],
  name: 'PicInfo',
  computed: {
    ...mapGetters(['user'])
  },
  data: () => ({
    info: undefined,
    times: undefined,
    templates: [],
    isPublic: undefined,
    dateLimit: undefined,
    timesLimit: undefined
  }),
  watch: {
    pic() {
      this.updateImgInfo();
    },
  },
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
    },
    updateImgInfo() {
      this.$api.getImgInfo(this.pic).then((res) => {
        if (res.data.code === -1) {
          this.$message.error('图片不存在或状态异常')
          this.$emit('back')
          return;
        }
        this.info = res.data.data.info;
        this.times = res.data.data.times;
        this.isPublic = this.info.isPublic;
        this.dateLimit = !this.info.dateLimit ? null : new Date(this.info.dateLimit * 1000);
        this.timesLimit = !this.info.timesLimit ? null : this.info.timesLimit;
        const pageUrl = `${window.location.origin}${this.$router.resolve({
          name: 'pic-info',
          params: {pic: this.pic}
        }).href}`;
        const originUrl = `${this.$api.getImgUrl(this.pic)}`;
        this.templates = [
          ['Markdown', `[![${this.info.name}](${originUrl})](${pageUrl})]`],
          ['BBCODE', `[url=${pageUrl}/pic-info][img]${originUrl}[/img][/url]`],
          ['HTML', `<a href="${pageUrl}/pic-info" target="_blank"><img alt='' src="${originUrl}" ></a>`],
          ['展示页', pageUrl],
          ['直链', originUrl],
        ]
      })
    },
    edit(data) {
      this.$api.editImgInfo(this.pic, data)
          .then((r) => {
            r.data.code === 0 ? this.$message.success('修改成功') : this.$message.error('修改失败');
          });
    }
  },
  beforeMount() {
    this.updateImgInfo();
  }
}
</script>
<style scoped lang="scss">
#settings {
  margin: 1vh 0;
  gap: 1em;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  width: 100%;
  text-align: center;

  * {
    width: auto;
    @media screen and (max-width: 990px) {
      width: 100%;
    }
  }
}

#info {
  align-self: flex-start;

  .el-descriptions__header {
    justify-content: center;
  }
}

#card .el-card__body {
  display: inline-flex;
  align-items: center;
  @media screen and (max-width: 768px) {
    display: block;
  }
}
</style>
