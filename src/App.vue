<template>
  <el-row id="container">
    <div id="background">
      <ul class="circles">
        <li v-for="item in 10" :key="item"></li>
      </ul>
    </div>
    <WebHead></WebHead>
    <el-alert
        id="home-notice"
        class="hover-light suspended"
        title="本页面为非商用页面"
        description="仅用于项目演示 Design By Zerek-Cheng"
        type="warning" show-icon :closable="false"
        center style="opacity: 60%;height: auto;line-height: 100%;font-size: 1vh"/>
    <router-view id="content"></router-view>
    <el-backtop :bottom="100" :visibility-height="50"></el-backtop>
    <WebBottom id="footer"/>
  </el-row>
</template>
<script>
// eslint-disable-next-line import/no-unresolved
import WebHead from '@/components/WebHead.vue';
// eslint-disable-next-line import/no-unresolved
import WebBottom from '@/components/WebBottom.vue';
import Cookies from 'js-cookie';

export default {
  name: 'app',
  components: {
    WebHead, WebBottom
  },
  methods: {
    async updateData() {
      const csrfData = await this.$api.getCsrf();
      this.$store.commit('csrf', csrfData.data.data.token ? csrfData.data.data.token : Cookies.get('XSRF-TOKEN'));
      const userData = await this.$api.getUserInfo();
      this.$store.commit('user', userData.data.code === 0 ? userData.data.data.principal : null);
    }
  },
  created() {
    this.updateData();
  }
}
</script>
<style lang="scss">
@import "App.scss";

#container {
  min-height: 100%;
}

#footer {
  position: fixed;
  width: 100%;
  bottom: 0;
}

#content {
  margin-bottom: 5vh;
}

#home-notice {
  z-index: 100;
}

</style>
