<template>
  <el-row>
    <el-col :span="24" class="hidden-sm-and-down">
      <el-carousel :interval="5000" arrow="always" height="40vh" type="card" id="img-md" ref="imgmd">
        <el-carousel-item v-for="item in img" :key="item">
          <el-image draggable="false"
                    style="width: 100%; height: 100%;"
                    :src="item"
                    fit="cover"/>
        </el-carousel-item>
      </el-carousel>
    </el-col>
    <el-col :span="24" class="hidden-md-and-up">
      <el-carousel :interval="5000" arrow="always" height="30vh" id="img-xs" ref="imgxs">
        <el-carousel-item v-for="item in img" :key="item">
          <el-image draggable="false"
                    style="width: 100%; height: 100%;"
                    :src="item"
                    fit="cover"/>
        </el-carousel-item>
      </el-carousel>
    </el-col>
    <el-col id="future" :md="{span:20,push:2}" :xs="24" :loading="true">
      <el-card shadow="hover">
        <i class="el-icon-circle-check"/>
        <b>隐私原则</b>
        <p>我们仅将您的个人信息用于我们在隐私声明中规定的特定目的，不会以不符合这些目的方式使用您的个人信息。</p>
      </el-card>
      <el-card shadow="hover">
        <i class="el-icon-bank-card"/>
        <b>低成本</b>
        <p>支持按量付费模式，按实际使用量付费，无需提前一次性投入；无需运维人员与托管费用，0成本运维</p>
      </el-card>
      <el-card shadow="hover">
        <i class="el-icon-cpu"/>
        <b>QOS 99.95%</b>
        <p>如服务可用性低于99.95%，用户可获得10%、25%、100%不等的赔偿。</p>
      </el-card>
      <el-card shadow="hover">
        <i class="el-icon-chat-dot-square"/>
        <b>服务便捷</b>
        <p>
          系统将会帮助企业实现7*24小时均可在线，并且对客户搜索关键字进行分析，从而根据强大数据库以及话术库，与客户进行多轮对话。</p>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: 'HomeView',
  data() {
    return {
      img: process.env.VUE_APP_HOME_IMGS.split(',')
    };
  },
  methods: {
    setCursor(box) {
      let startPoint = 0;
      let stopPoint = 0;
      // 重置坐标
      const resetPoint = function () {
        startPoint = 0;
        stopPoint = 0;
      };
      // 手指按下
      box.addEventListener('touchstart', (e) => {
        startPoint = e.changedTouches[0].pageX;
      });
      // 手指滑动
      box.addEventListener('touchmove', (e) => {
        // 手指滑动后终点位置X的坐标
        stopPoint = e.changedTouches[0].pageX;
      });
      // 当手指抬起的时候，判断图片滚动离左右的距离
      box.addEventListener('touchend', () => {
        if (stopPoint === 0 || startPoint - stopPoint === 0) {
          resetPoint();
          return;
        }
        if (startPoint - stopPoint > 0) {
          resetPoint();
          this.$refs.imgmd.next();
          this.$refs.imgxs.next();
          return;
        }
        if (startPoint - stopPoint < 0) {
          resetPoint();
          this.$refs.imgmd.prev();
          this.$refs.imgxs.prev();
        }
      })
    }
  },
  mounted() {
    this.setCursor(document.getElementById('img-md'));
    this.setCursor(document.getElementById('img-xs'));
  }
}
</script>
<style scoped lang="scss">

.el-carousel__item {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  background: gray;
}

#future {
  margin-top: 1vh;
  text-align: center;
  display: inline-flex;
  justify-content: space-around;
  gap: 3vw;
  flex-wrap: nowrap;

  .el-card {
    border-radius: 1vw;
    background: #f2f2f2;
    display: inline-flex;
    justify-content: center;
    align-items: flex-start;
    font-size: 1vw;
    color: #333;
    text-align: center;
    flex: 1 1 0;

    i, b {
      padding-bottom: 1vh;
      white-space: nowrap;
    }

    i {
      display: inline-block;
      font-size: 10vw;
      width: 100%;
      text-align: center;
      color: deepskyblue;
    }

    b {
      display: inline-block;
      font-size: 1.5vw;
      width: 100%;
    }

    p {
      font-size: 1em;
      text-align: left;
      text-indent: 2em;
    }
  }

  .el-card:hover {
    box-shadow: 0 0 10px #333;
  }

  @media screen and (max-width: 768px) {
    flex-wrap: wrap;
    justify-content: space-evenly;
    .el-card {
      width: 95%;
      margin-bottom: 1vh;
      flex: none;

      i {
        font-size: 10vh;
      }

      b {
        font-size: 5vw;
        margin-bottom: 0;
      }

      p {
        font-size: 5vw;
        display: none;
      }

      &:hover p {
        display: block;
      }
    }
  }
}
</style>
