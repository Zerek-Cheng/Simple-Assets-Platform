<template>
  <PicFlow/>
</template>
<script>
export default {
  name: 'PicView',
  components: {
    PicFlow: () => import('@/components/PicFlow.vue')
  }
}
</script>
