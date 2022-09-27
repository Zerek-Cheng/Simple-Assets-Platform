<template>
  <el-row>
    <el-drawer
        title="按ESC建或右边的×关闭"
        size="auto"
        :visible.sync="show"
        direction="ttb" id="drawer">
      <span>
        <PicInfo :pic="showId" @back="show=false;showId=null"/>
      </span>
    </el-drawer>
    <el-col :md="{span:18,push:3}" :xs="24" v-if="this.imgUrl&&this.imgUrl.length" id="img-content">
      <img style="object-fit: contain"
           v-for="item in this.$data.imgUrl" :key="item.id"
           :src="`/api/img/get/${item.id}`"
           @click="show=true;showId=item.id"
           class="single-img" :alt="'Image ID-'+item.id" loading="lazy"/>
    </el-col>

    <el-col :md="{span:20,push:2}" :xs="24" v-if="!this.imgUrl">
      <el-col :md="5" :xs="24" v-for="item in 12" :key="item">
        <el-image src="/api/img/get/0">
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
      showId: null,
    }
  },
  components: {
    PicInfo: () => import('./PicInfo.vue')
  },
  methods: {
    loadImgList() {
      const loading = this.$loading();
      this.$api.getImgList(this.page, this.size).then((res) => {
        this.imgUrl = [...res.data.data.img]
      }).finally(() => {
        loading.close();
      })
    },
    getMore() {
      const bottom = document.documentElement.offsetHeight - window.innerHeight - document.documentElement.scrollTop
      if (!this.imgUrl || (0.03 * window.innerHeight < bottom && bottom !== 0)) return;
      this.$api.getImgList(++this.page, this.size).then((res) => {
        if (!res.data.data.hasNext) window.removeEventListener('scroll', this.tGetMore, true)
        this.imgUrl.push(...res.data.data.img)
      })
      console.debug(`加载第${this.page}页`)
    },
  },
  beforeMount() {
    this.tGetMore = this._.debounce(this.getMore, 100)
    this.loadImgList()
    window.addEventListener('scroll', this.tGetMore, true)
  },
  beforeDestroy() {
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
#img-content {
  display: inline-flex;
  flex-wrap: wrap;
  justify-content: space-between;
  gap: 0.2em;
}

.single-img {
  width: auto;
  height: 25vh;
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
