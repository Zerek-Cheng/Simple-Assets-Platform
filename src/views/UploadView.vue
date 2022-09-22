<template>
  <el-row>
    <el-col :md="{span:16,push: 4}" :xs="{span:22,push:1}" id="upload-col">
      <el-card id="box-card">
        <el-col slot="header" id="upload-card-header">
          <el-col style="display: inline-block;width: auto;margin-right: 2em;">
            <h1>图片上传</h1>
          </el-col>
          <el-col id="upload-setting" :xs="24" :md="18" v-if="this.user">
            <el-checkbox v-model="isPublic"
                         :value="true"
                         ref="isPublic">
              公共可见
            </el-checkbox>
            <el-date-picker
                v-model="dateLimit"
                type="datetime"
                placeholder="限时[可选]"
                :time-arrow-control="true" ref="datetime">
            </el-date-picker>
            <el-input placeholder="限次[可选]" v-model="timesLimit" max="10000"
                      type="number" ref="timesLimit"
                      id="upload-setting-times-limit">
              <template slot="append">次</template>
            </el-input>
          </el-col>
        </el-col>
        <el-alert
            v-if="!this.user"
            id="login-warn"
            title="未登录"
            type="error"
            description="只有登录后才能上传图片，点击上方按钮登录"
            show-icon>
        </el-alert>
        <el-divider></el-divider>
        <el-upload
            ref="upload"
            :disabled="!user"
            class="upload"
            action="/api/upload"
            list-type="picture"
            :file-list="files"
            :limit="10"
            :auto-upload="false"
            :with-credentials="true"
            :headers="{'X-XSRF-TOKEN': csrf}"
            :http-request="requestUpload"
            :on-success="onSuccess"
            :on-preview="onPreview"
            accept=".jpg,.jpeg,.png,.gif,.bmp,.webp,.svg,.tiff"
            drag
            multiple>
          <div class="el-upload__text">
            <p><i class="el-icon-upload"></i></p>
            将文件拖到此处，或<em>点击这里</em></div>
        </el-upload>
        <el-button v-if="this.user" type="success" id="confirm" @click.prevent.stop="confirmUpload">
          确认上传
        </el-button>
      </el-card>
    </el-col>
  </el-row>
</template>
<script>
import {mapState} from 'vuex';

export default {
  data: () => ({
    files: [],
    isPublic: true,
    dateLimit: null,
    timesLimit: null,
  }),
  watch: {
    isPublic(v) {
      this.$cookies.set('isPublic', v);
    },
    dateLimit(v) {
      if (v && v > Date.now()) {
        this.$cookies.set('dateLimit', v);
        return;
      }
      this.dateLimit = null;
      this.$cookies.remove('dateLimit');
    },
    timesLimit(v) {
      if (v && v > 0) {
        this.$cookies.set('timesLimit', v);
        return;
      }
      this.timesLimit = null;
      this.$cookies.remove('timesLimit');
    },
  },
  computed: {
    ...mapState(['user', 'csrf'])
  },
  beforeMount() {
    this.isPublic = this.$cookies.get('isPublic') === undefined ? true : !!JSON.parse(this.$cookies.get('isPublic'));
    this.dateLimit = this.$cookies.get('dateLimit') === undefined ? null : new Date(this.$cookies.get('dateLimit'));
    this.timesLimit = this.$cookies.get('timesLimit') === undefined ? null : this.$cookies.get('timesLimit');
  },
  methods: {
    completeData(config) {
      const data = {};
      data.isPublic = this.isPublic;
      data.file = config.file;
      if (this.dateLimit) data.dataLimit = this.dateLimit.getTime();
      if (this.timesLimit) data.timesLimit = this.timesLimit;
      return data;
    },
    confirmUpload() { // 确认上传
      this.$refs.upload.submit()
    },
    requestUpload(config) {
      this.$axios.request({
        method: 'post',
        url: config.action,
        headers: {
          'Content-Type': 'multipart/form-data',
        },
        data: this.completeData(config),
      })
          .then((res) => config.onSuccess(res.data, config.file))
          .catch((err) => config.onError(err, config.file))
    },
    onSuccess(res, file) { // 判断上传是否成功
      if (res.code === -1) {
        file.status = 'error'
        this.$message.error(res.message)
      }
    },
    onPreview(file) { // 预览图片
      if (!file.response || file.response.code !== 0) return;
      window.open(this.$router.resolve({
        name: 'pic-info',
        params: {pic: file.response.data.id}
      }).href)
    },
  }
}
</script>
<style lang="scss">
#upload-col .el-card__header {
  padding: 0.5rem 1rem;
  display: inline-block;
  width: 100%;
  height: 100%;
}

.upload {
  margin-top: 1vw;

  .el-upload-dragger, .el-upload {
    width: 100%;
    height: 50vh;
    display: inline-flex;
    align-items: center;
    justify-content: center;
  }
}

#upload-setting-times-limit + .el-input-group__append {
  line-height: 1em;
}

#box-card { // 整个上传卡片
  margin-top: 5%;
  padding-top: 1vh;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

  .el-card__body {
    padding-top: 0.5em;
  }
}
</style>
<style scoped lang="scss">
#upload-setting {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  column-gap: 1em;

  * {
    width: auto;
    font-size: 1rem;
  }

  @media screen and (max-width: 990px) {
    text-align: center;
    flex-wrap: wrap;
    * {
      line-height: 300%;
      width: 100%;
    }
  }
}

#upload-card-header {
  display: inline-flex;
  align-items: center;
  flex-wrap: wrap;
  text-wrap: none;
}

#confirm { // 上传按钮
  margin-top: 1vw;
  width: 100%;
  height: 5vh;
}

#login-warn { //未登录提示
  display: inline-flex;
  align-items: center;
  justify-content: center;
  margin-top: -1vh;
  margin-bottom: 1vh;
  line-height: 1.5rem;
}

</style>
