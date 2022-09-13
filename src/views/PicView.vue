<template>
  <PicFlow/>
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
