<template>
  <el-row id="footer">
    <el-col :span="24" id="footer-content">
      <p><a href="https://beian.miit.gov.cn/">沪ICP备2022014867-1号</a></p>
      <p>Powered By Vue 2.7.10 & ElementUi 2.15.9</p>
    </el-col>
  </el-row>
</template>
<script>
export default {
  name: 'WebBottom',
  data: () => ({
    input: ''
  }),
}
</script>
<style lang="scss" scoped>
#footer {
  font-size: 0.5rem;
  text-align: center;
  background-color: gray;
  opacity: 50%;
  box-shadow: 0 -2px 20px #333333;

  &:hover {
    opacity: 75%;
    box-shadow: 0;
  }
}

</style>
