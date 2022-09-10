<template>
  <el-row id="footer">
    <el-col :span="24" id="footer-content">
      <p>沪ICP备2022014867-1号 沪公网安备X号</p>
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
<style>
#footer {
  font-size: 0.5rem;
  text-align: center;
  background-color: #555555;
  opacity: 50%;
  box-shadow: 0 -2px 20px #333333;
}

</style>
