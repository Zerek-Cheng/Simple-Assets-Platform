<template>
  <el-row>
    <el-card id="gallery-table">
      <template v-slot:header>
        <div id="title-col"><h1>我的图库</h1></div>
      </template>
      <el-table
          :stripe="true"
          :border="true"
          :data="tableData"
          style="width: 100%">
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
                  <span>{{ props.row.dateLimit }}</span>
                </el-form-item>
                <el-form-item label="次数限制" v-if="props.row.timesLimit">
                  <span>{{ props.row.timesLimit }}</span>
                </el-form-item>
              </el-form>
            </el-col>
          </template>
        </el-table-column>
        <el-table-column
            label="资源ID"
            prop="id">
        </el-table-column>
        <el-table-column
            :show-overflow-tooltip="true"
            label="资源名"
            prop="name">
        </el-table-column>
        <el-table-column
            label="上传者ID"
            prop="owner">
        </el-table-column>
      </el-table>
      <el-pagination
          style="display: flex;justify-content: center;align-items: center;
          margin-top: 1vh;flex-wrap: wrap-reverse"
          @current-change="updateTable"
          @size-change="updateTable"
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
      total: 0,
      pageSize: 10,
      pageNum: 1,
      tableData: []
    }
  },
  methods: {
    updateTable() {
      this.$api.getUserImgTotal().then((res) => {
        this.total = res.data.data
      })
      this.updateTableData(this.pageNum);
    },
    updateTableData(page = 1) {
      this.$api.getImgList(page, this.pageSize, true).then((res) => {
        this.tableData = res.data.data.img
      })
    }
  },
  beforeMount() {
    this.updateTable();
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
  width: 100%;
  justify-content: center;
  align-items: center;
  .el-form{
    width: 100%;
  }
  .el-form-item{
    white-space: nowrap;
    overflow: auto;
  }
  .el-form-item__label{
    width: 20%;
  }
  .el-form-item__content{
    display: inline-block;
    width: auto;
  }
}
</style>
