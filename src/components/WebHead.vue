<template>
  <el-row id="head-container">
    <el-col :md="{span:14,push:5}" :xs="24">
      <el-col :xs="24" class="hidden-md-and-up" id="h-left">
        <el-link type="primary" id="h-site-url" href="/">Demo</el-link>
      </el-col>
      <el-col :md="7" :xs="24" id="h-left">
        <el-link type="primary" id="h-site-url" href="/" class="hidden-sm-and-down">Demo</el-link>
        <el-menu
            :default-active="$route.path"
            id="h-menu"
            class="flex-center"
            mode="horizontal"
            background-color="transparent"
            text-color="#fff"
            active-text-color="#ffd04b">
          <el-menu-item index="/" @click="$router.push('/')">
            <span>首页</span>
          </el-menu-item>
          <el-menu-item index="/pics" @click="$router.push('/pics')">
            <span>展示</span>
          </el-menu-item>
          <el-menu-item index="/upload" @click="$router.push('/upload')">
            <span>上传</span>
          </el-menu-item>
        </el-menu>
      </el-col>
      <el-col :md="4" :xs="24" id="h-right" v-if="!login">
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
    </el-col>
    <el-col :md="2" :xs="24" id="h-right" v-if="login"><!--已登录状态-->
      <el-menu
          :default-active="$route.path"
          id="h-menu-user"
          class="flex-center"
          mode="horizontal"
          background-color="transparent"
          text-color="#fff"
          active-text-color="#ffd04b">
        <el-submenu index="/user" popper-class="avatar-submenu" :show-timeout="10" :hide-timeout="10">
          <template slot="title">
            <el-avatar :src="user.avatar" shape="square" size="large"/>
          </template>
          <el-menu-item index="/price" @click="$router.push('/price')">套餐&费用</el-menu-item>
          <el-menu-item index="/safe" @click="profile">安全中心</el-menu-item>
          <el-menu-item index="/logout" @click="logout">退出</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-col>
  </el-row>
</template>
<script>
import {mapGetters, mapState} from 'vuex';

export default {
  name: 'WebHead',
  data: () => ({
    input: ''
  }),
  computed: {
    ...mapState(['login']),
    ...mapGetters(['user'])
  },
  methods: {
    goAuth(type) {
      this.$axios.request({
        url: type === 'login' ? '/api/login/goSignin' : '/api/login/goSignup',
        method: 'post',
        data: {
          redirect: `${window.location.origin}/api/login/callback`,
          callback: window.location.href
        },
      }).then((res) => {
        if (res.data.code === 0) {
          window.location.href = res.data.data;
        } else {
          this.$message.warning('授权服务器异常...请稍后再试...');
        }
      });
    },
    logout() {
      this.$axios.request({
        url: '/api/login/logout',
        method: 'post',
      }).then((res) => {
        if (res.data.code === 0) {
          this.$message.success('退出成功');
          window.location.reload();
        } else {
          this.$message.warning('授权服务器异常...请稍后再试...');
        }
      });
    },
    profile() {
      this.$axios.request({
        url: '/api/login/goProfile',
        method: 'post',
      }).then((res) => {
        if (res.data.code === 0) {
          const url = res.data.data;
          window.location.href = url;
        } else {
          this.$message.warning('授权服务器异常...请稍后再试...');
        }
      });
    }
  }
}
</script>
<style lang="scss">
.el-submenu__title:hover {
  background-color: transparent !important;
}

@media screen and (max-width: 768px) {
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
  }

  .el-menu-item:hover {
    background-color: #606266 !important;
  }
}
</style>
<style scoped lang="scss">
#head-container {
  background-color: #363636;
  box-shadow: 0 1px 10px #333333;
}

#h-site-url {
  font-size: 2em;
  letter-spacing: 1rem;
  background: linear-gradient(to left top, blue 0%, #20a9a4 45%, #14ded7 100%);
  -webkit-background-clip: text;
  color: transparent;
}

.el-menu {
  display: inline-flex;
  flex-wrap: nowrap;
  width: 100%;
  border-bottom: none !important;
}

.el-menu-item {
  flex: 1 1 100%;
}

#h-left {
  white-space: nowrap;
  text-align: center;
}

#h-right {
  float: right;
  text-align: center;
}

#h-menu-user {
  justify-content: center;
}
</style>
