<template>
  <el-row :xs="24" id="content">
    <PicFlow/>
  </el-row>
</template>
<script>
export default {
  name: 'PicView',
  components: {
    // eslint-disable-next-line import/no-unresolved
    PicFlow: () => import('@/components/PicFlow.vue')
  }
}
</script>
<style scoped>
#content {
  text-align: center;
}
</style>
