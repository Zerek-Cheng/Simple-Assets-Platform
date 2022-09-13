<template>
  <el-row>
    <el-col :md="{span:14,push: 5}" :xs="{span:22,push:1}" id="info">
      <PicInfo :pic="pic" @back="back"/>
    </el-col>
  </el-row>
</template>
<script>
export default {
  props: ['pic'],
  components: {
    // eslint-disable-next-line import/no-unresolved
    PicInfo: () => import('@/components/PicInfo.vue')
  },
  name: 'PicInfoView',
  methods: {
    back() {
      window.history.go(-1)
    }
  }
}
</script>
<style scoped>
#info {
  margin-top: 3%;
}
</style>
