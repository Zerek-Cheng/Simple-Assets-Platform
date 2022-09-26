<template>
  <el-row>
    <el-col :md="{span:16,push: 4}" :xs="{span:22,push:1}">
      <GalleryTable></GalleryTable>
    </el-col>
  </el-row>
</template>
<script>
export default {
  name: 'GalleryView',
  components: {
    GalleryTable: () => import('@/components/GalleryTable.vue')
  }
}
</script>
