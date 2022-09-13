<template>
  <el-col :md="{span:16,push:4}" :xs="24" id="imgs">
    <el-image v-for="item in this.$data.imgUrl" :key="item.id"
              :src="`/api/img/get/${item.id}`" :preview-src-list="[`/api/img/get/${item.id}`]" fit="fit">
      <div slot="placeholder" class="image-slot">
        <el-skeleton animated :rows="3" :throttle="100">
          <el-skeleton-item variant="image"></el-skeleton-item>
        </el-skeleton>
      </div>
    </el-image>
    <el-image fit="fill" v-if="!this.imgUrl">
      <div slot="placeholder" class="image-slot" style="width: 80%">
        <el-skeleton animated :rows="3" :throttle="100">
          <el-skeleton-item variant="image"></el-skeleton-item>
        </el-skeleton>
      </div>
    </el-image>
  </el-col>
</template>
<script>
export default {
  name: 'PicFlow',
  components: {},
  data() {
    return {
      imgUrl: [],
      size: 50,
      page: 1,
      hasMore: true,
    }
  },
  methods: {
    loadImgs() {
      this.$axios.request({
        method: 'post',
        url: '/api/img/list',
        data: {
          current: this.page,
          size: 12
        }
      }).then((res) => this.imgUrl.push(...res.data.data))
    },
    getMore() {
      const bottom = document.documentElement.offsetHeight - window.innerHeight - document.documentElement.scrollTop
      if (0.03 * window.innerHeight < bottom && bottom !== 0) {
        return;
      }
      this.page++
      this.$axios.request({
        method: 'post',
        url: '/api/img/list',
        data: {
          current: this.page,
          size: 12
        }
      }).then((res) => {
        if (res.data.data.length < this.size) {
          window.removeEventListener('scroll', this.tGetMore, true)
        }
        this.imgUrl.push(...res.data.data)
      })
      console.info(`加载第${this.page}页`)
    },
  },
  mounted() {
    this.tGetMore = this._.throttle(this.getMore, 1500, {trailing: false})// 7节流
    this.loadImgs()
    window.addEventListener('scroll', this.tGetMore, true)
  },
  destroyed() {
    window.removeEventListener('scroll', this.tGetMore, true)
  }
}
</script>
<style scoped lang="scss">
#imgs {
  display: inline-flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
  align-items: flex-start;

  .el-image {
    display: inline-flex;
    justify-content: center;
    align-items: center;
    border: 1px solid gray;
    min-width: 20%;
    height: 200px;
    margin: 10px;
  }
}

@media screen and (max-width: 768px) {
  #imgs .el-image {
    min-width: 85%;
  }
}
</style>
