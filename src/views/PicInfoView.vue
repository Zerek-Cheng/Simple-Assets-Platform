<template>
  <el-row>
    <el-col :md="{span:16,push: 4}" :xs="{span:22,push:1}" id="info">
      <PicInfo :pic="pic" @back="back"/>
    </el-col>
  </el-row>
</template>
<script>
export default {
  name: 'PicInfoView',
  props: ['pic'],
  components: {
    PicInfo: () => import('@/components/PicInfo.vue')
  },
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
