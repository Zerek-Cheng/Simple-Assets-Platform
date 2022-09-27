<template>
  <el-row id="head-container">
    <el-col :md="{span:14,push:5}" :xs="24">
      <el-col :xs="24" class="hidden-md-and-up" id="h-left">
        <el-link id="header-site-logo" @click="$router.push({name: 'home'})">
          {{ SITE_NAME }}
        </el-link>
      </el-col>
      <el-col :md="7" id="h-left">
        <el-link id="header-site-logo" @click="$router.push({name: 'home'})" class="hidden-sm-and-down">
          {{ SITE_NAME }}
        </el-link>
        <el-menu
            :default-active="$route.path"
            id="h-menu"
            class="flex-center"
            mode="horizontal"
            background-color="transparent"
            text-color="#fff"
            active-text-color="#ffd04b">
          <el-menu-item index="/" @click="$router.push({name: 'home'})">
            <span>首页</span>
          </el-menu-item>
          <el-menu-item index="/pics" @click="$router.push({name: 'pics'})">
            <span>展示</span>
          </el-menu-item>
          <el-menu-item index="/upload" @click="$router.push({name: 'upload'})">
            <span>上传</span>
          </el-menu-item>
        </el-menu>
      </el-col>
      <el-col :md="4" :xs="24" id="h-right" v-if="!this.user">
        <el-menu
            :default-active="$route.path"
            id="h-menu-right"
            class="flex-center"
            mode="horizontal"
            background-color="transparent"
            text-color="#fff"
            active-text-color="#ffd04b">
          <el-menu-item index="/login" @click="goAuth('login')">
            <span>登入</span>
          </el-menu-item>
          <el-menu-item index="/reg" @click="goAuth('register')">
            <span>注册</span>
          </el-menu-item>
        </el-menu>
      </el-col>
      <el-col :md="2" :xs="24" id="h-right" v-if="this.user"><!--已登录状态-->
        <el-menu
            :default-active="$route.path"
            id="h-menu-user"
            class="flex-center"
            mode="horizontal"
            background-color="transparent"
            text-color="#fff"
            active-text-color="#ffd04b">
          <el-submenu index="/user" popper-class="avatar-submenu"
                      :show-timeout="10" :hide-timeout="10">
            <template slot="title">
              <el-avatar :src="this.user.avatar" shape="square" size="large"/>
            </template>
            <el-menu-item index="/gallery"
                          @click="$router.push({name: 'gallery'})">我的图库
            </el-menu-item>
            <el-menu-item index="/price"
                          @click="$router.push({name: 'price'})">套餐&费用
            </el-menu-item>
            <el-menu-item index="/safe"
                          @click="profile">个人中心
            </el-menu-item>
            <el-menu-item index="/logout"
                          @click="logout">退出
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-col>
    </el-col>
  </el-row>
</template>
<script>
import {mapGetters} from 'vuex';

export default {
  name: 'WebHead',
  data: () => ({
    SITE_NAME: process.env.VUE_APP_SITE_NAME,
  }),
  computed: {
    ...mapGetters(['user'])
  },
  methods: {
    goAuth(type) {
      this.$api.getLogin(type).then((res) => {
        if (res.data.code === 0) {
          window.location.href = res.data.data;
        } else {
          this.$message.warning('授权服务器异常...请稍后再试...');
        }
      });
    },
    logout() {
      this.$api.logout().then((res) => {
        this.$store.commit('user', null);
        if (res.data.code === 0) this.$message.success('退出成功');
      });
    },
    profile() {
      this.$api.getProfile(window.location.href).then((res) => {
        if (res.data.code === 0) window.location.href = res.data.data;
      });
    }
  }
}
</script>
<style lang="scss">
.el-submenu__title:hover {
  background-color: transparent !important;
}

@media screen and (max-width: 990px) {
  .el-menu-item {
    width: 100vw;
  }
  .avatar-submenu {
    left: 0 !important;
  }
}

.el-menu--popup-bottom-start {
  display: inline;
  text-align: center;

  .el-menu-item {
    background-color: #363636 !important;
    white-space: nowrap;
    &:hover {
      background-color: #606266 !important;
    }
  }
}
</style>
<style scoped lang="scss">
// header整体
#head-container {
  background-color: #24292F;
  box-shadow: 0 2px 10px black;

  .el-menu {
    display: inline-flex;
    flex-wrap: nowrap;
    width: 100%;
    border-bottom: none !important;
  }

  .el-menu-item {
    flex: 1 1 100%;
    &:hover {
      background-color: #606266 !important;
    }
  }

  // 左侧菜单
  #h-left {
    white-space: nowrap;
    text-align: center;
  }

  // 右侧登录登录注册
  #h-right {
    float: right;
    text-align: center;
  }

  // 登录后的用户选项
  #h-menu-user {
    justify-content: center;
  }
}

// 顶部站点名称
#header-site-logo {
  font-size: 2rem;
  background: linear-gradient(to left top, blue 0%, #20a9a4 45%, #14ded7 100%);
  -webkit-background-clip: text;
  color: transparent;
  padding-right: 1vh;
}


</style>
