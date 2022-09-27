<template>
  <el-row>
    <el-card id="gallery-table">
      <template v-slot:header>
        <div id="title-col"><h1>我的图库</h1></div>
      </template>
      <el-table
          :stripe="true"
          :border="true"
          :data="tableData">
        <el-table-column type="expand">
          <template v-slot="props">
            <el-col id="gallery-table-expand-col">
              <el-form>
                <el-form-item label="ID">
                  <span>{{ props.row.id }}</span>
                </el-form-item>
                <el-form-item label="拥有者">
                  <span>{{ props.row.owner }}</span>
                </el-form-item>
                <el-form-item label="公开展示">
                  <span v-html="props.row.isPublic?'是':'否'"></span>
                </el-form-item>
                <el-form-item label="时间限制" v-if="props.row.dateLimit">
                  <span v-html="(new Date(props.row.dateLimit*1000)).toLocaleString()"></span>
                </el-form-item>
                <el-form-item label="次数限制" v-if="props.row.timesLimit">
                  <span>{{ props.row.timesLimit }}</span>
                </el-form-item>
              </el-form>
            </el-col>
          </template>
        </el-table-column>
        <el-table-column
            label="预览"
            prop="name">
          <template v-slot="scope">
            <img width="100%" height="auto" :src="$api.getImgUrl(scope.row.id)" alt="">
          </template>
        </el-table-column>
        <el-table-column
            label="资源ID"
            min-width="200"
            prop="id">
        </el-table-column>
        <el-table-column
            :show-overflow-tooltip="true"
            min-width="200"
            label="资源名"
            prop="name">
        </el-table-column>
        <el-table-column
            min-width="200"
            align="right">
          <template v-slot:header>
            <el-input
                v-model="search"
                size="mini"
                placeholder="搜索"
                clearable
                @change="updateTableData(pageNum)"
            />
          </template>
          <template v-slot="scope">
            <div style="display: flex;justify-content: space-evenly">
              <el-button
                  type="info" round
                  @click="goManage(scope.row)">管理
              </el-button>
              <el-popover placement="top">
                <p>确定删除这个资源吗(操作不可逆)</p>
                <div style="display: flex;justify-content: center">
                  <el-button style="margin-top: 1rem" plain type="warning" size="large"
                             @click="deleteImg(scope.row.id)">确定
                  </el-button>
                </div>
                <template v-slot:reference>
                  <el-button round
                      type="danger">删除
                  </el-button>
                </template>
              </el-popover>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          style="display: flex;justify-content: center;align-items: center;
          margin-top: 1vh;flex-wrap: wrap-reverse"
          @current-change="updateTableData(pageNum)"
          @size-change="updateTableData(pageNum)"
          :current-page.sync="pageNum"
          :page-sizes="[10, 20, 50, 100]"
          :page-size.sync="pageSize"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </el-card>
  </el-row>
</template>
<script>
export default {
  name: 'GalleryTable',
  data() {
    return {
      search: null,
      total: 0,
      pageSize: 10,
      pageNum: 1,
      tableData: []
    }
  },
  methods: {
    updateTableData(page = this.pageNum) {
      this.$api.getImgList(page, this.pageSize, true, this.search ? this.search : null).then((res) => {
        this.tableData = res.data.data.img
        this.total = res.data.data.total
      })
    },
    goManage(row) {
      window.open(this.$router.resolve({name: 'pic-info', params: {pic: row.id}}).href)
    },
    deleteImg(row) {
      this.$api.delImg(row).then(() => {
        this.$message.success('删除成功')
        this.updateTableData()
      })
    }
  },
  beforeMount() {
    this.updateTableData();
  }
}
</script>
<style lang="scss">
#gallery-table {
  label {
    width: 15%;
    color: #99a9bf;
  }
}

#gallery-table-expand-col {
  display: inline-flex;
  justify-content: center;
  align-items: center;

  .el-form {
    width: 100%;
  }

  .el-form-item {
    white-space: nowrap;
    overflow: auto;
  }

  .el-form-item__label {
    width: 20%;
  }

  .el-form-item__content {
    display: inline-block;
    width: auto;
  }
}
</style>
