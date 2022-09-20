<template>
  <el-row id="imgs">
    <el-drawer
        title="按ESC建或右边的×关闭"
        size="auto"
        :visible.sync="show"
        direction="ttb" id="drawer">
      <span>
        <PicInfo :pic="showId" @back="show=false;showId=undefined"/>
      </span>
    </el-drawer>
    <el-col :md="{span:22,push:1}" :xs="24" v-if="this.imgUrl&&this.imgUrl.length">
      <el-image
          v-for="item in this.$data.imgUrl" :key="item.id"
          :src="`/api/img/get/${item.id}`"
          @click="show=true;showId=item.id"
          fit="contain" class="single-img" lazy>
        <div slot="placeholder" class="image-slot">
          <el-skeleton animated :rows="3" :throttle="100">
            <el-skeleton-item variant="image"></el-skeleton-item>
          </el-skeleton>
        </div>
      </el-image>
    </el-col>

    <el-col :md="{span:22,push:1}" :xs="24" v-if="!this.imgUrl">
      <el-col :md="5" :xs="24" v-for="item in 12" :key="item">
        <el-image src="/api/img/get/unknown">
          <div slot="placeholder" class="image-slot" style="width: 80%">
            <el-skeleton animated :rows="3" :throttle="100">
              <el-skeleton-item variant="image"></el-skeleton-item>
            </el-skeleton>
          </div>
        </el-image>
      </el-col>
    </el-col>
  </el-row>
</template>
<script>
export default {
  name: 'PicFlow',
  data() {
    return {
      imgUrl: undefined,
      size: 20,
      page: 1,
      show: false,
      showId: undefined,
    }
  },
  components: {
    PicInfo: () => import('./PicInfo.vue')
  },
  methods: {
    loadImngList() {
      this.$api.getImgList(this.page, this.size).then((res) => {
        this.imgUrl = [...res.data.data]
      })
    },
    getMore() {
      const bottom = document.documentElement.offsetHeight - window.innerHeight - document.documentElement.scrollTop
      if (!this.imgUrl || (0.03 * window.innerHeight < bottom && bottom !== 0)) return;
      this.page++
      this.$api.getImgList(this.page, this.size).then((res) => {
        if (res.data.data.length < this.size) window.removeEventListener('scroll', this.tGetMore, true)
        this.imgUrl.push(...res.data.data)
      })
      console.debug(`加载第${this.page}页`)
    },
  },
  beforeMount() {
    this.tGetMore = this._.debounce(this.getMore, 100)
    this.loadImngList()
    window.addEventListener('scroll', this.tGetMore, true)
  },
  destroyed() {
    window.removeEventListener('scroll', this.tGetMore, true)
  }
}
</script>
<style lang="scss">
#drawer {
  overflow-y: auto;

  &::-webkit-scrollbar {
    display: none;
  }
}
</style>
<style scoped lang="scss">
.single-img {
  width: 20%;
  height: 25vh;
  margin: 0.25%;
  border: 1px solid gray;
  background-color: rgba(143, 141, 141, 0.15);
  @media screen and (max-width: 990px) {
    width: 80%;
    height: 50vh;
    margin: 1% auto;
    border: 1px solid gray;
  }
}
</style>
