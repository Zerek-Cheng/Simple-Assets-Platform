<template>
  <el-row>
    <el-col :md="{span:16,push: 4}" :xs="{span:22,push:1}">
      <el-card class="box-card">
        <div slot="header">
          <h1>图片上传</h1>
        </div>
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
            :disabled="!this.user"
            class="upload"
            action="/api/upload"
            list-type="picture"
            :file-list="files"
            :limit="10"
            :auto-upload="false"
            :with-credentials="true"
            :headers="{'X-XSRF-TOKEN': csrf}"
            :on-success="onSuccess"
            :on-preview="onPreview"
            accept=".jpg,.jpeg,.png,.gif,.bmp,.webp,.svg,.tiff"
            drag
            multiple>
          <div class="el-upload__text">
            <p><i class="el-icon-upload"></i></p>
            将文件拖到此处，或<em>点击这里</em></div>
        </el-upload>
        <el-button v-if="this.user" type="success" id="confirm" @click.prevent.stop="confirmUpload">确认上传</el-button>
      </el-card>
    </el-col>
  </el-row>
</template>
<script>
import {mapState} from 'vuex';

export default {
  data: () => ({
    files: []
  }),
  computed: {
    ...mapState(['user', 'csrf'])
  },
  methods: {
    confirmUpload() {
      this.$refs.upload.submit()
    },
    onSuccess(res, file) {
      if (res.code === -1) {
        file.status = 'error'
        this.$message.error(res.message)
      }
    },
    onPreview(file) {
      if (!file.response || file.response.code !== 0) return;
      window.open(this.$router.resolve({
        name: 'pic-info',
        params: {pic: file.response.data.id}
      }).href)
    }
  }
}
</script>
<style lang="scss">
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
</style>
<style scoped lang="scss">
#confirm {
  margin-top: 1vw;
  width: 100%;
  height: 5vh;
}

.box-card {
  margin-top: 5%;
  padding-top: 1vh;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

#login-warn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  margin-top: -1vh;
  margin-bottom: 1vh;
  line-height: 1.5rem;
}

.el-upload-dragger {
  display: inline-block;
  width: 100%;
}
</style>
