<template>

  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 基础表格
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-button
            type="primary"
            icon="el-icon-delete"
            class="handle-del mr10"
            @click="delAllSelection"
        >批量删除</el-button>
        <el-select v-model="query.address" placeholder="地址" class="handle-select mr10">
          <el-option key="1" label="广东省" value="广东省"></el-option>
          <el-option key="2" label="湖南省" value="湖南省"></el-option>
        </el-select>
        <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
      </div>
      <el-table
          :data="tableData"
          border
          class="table"
          ref="multipleTable"
          header-cell-class-name="table-header"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
        <el-table-column prop="name" label="用户名"></el-table-column>
        <el-table-column label="账户余额">
          <template #default="scope">￥{{ scope.row.money }}</template>
        </el-table-column>
        <el-table-column label="头像(查看大图)" align="center">
          <template #default="scope">
            <el-image
                class="table-td-thumb"
                :src="scope.row.thumb"
                :preview-src-list="[scope.row.thumb]"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column label="状态" align="center">
          <template #default="scope">
            <el-tag
                :type="
                                scope.row.state === '成功'
                                    ? 'success'
                                    : scope.row.state === '失败'
                                    ? 'danger'
                                    : ''
                            "
            >{{ scope.row.state }}</el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="date" label="注册时间"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button
                type="text"
                icon="el-icon-edit"
                @click="handleEdit(scope.$index, scope.row)"
            >编辑</el-button>
            <el-button
                type="text"
                icon="el-icon-delete"
                class="red"
                @click="handleDelete(scope.$index, scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
            background
            layout="total, prev, pager, next"
            :current-page="query.pageIndex"
            :page-size="query.pageSize"
            :total="pageTotal"
            @current-change="handlePageChange"
        ></el-pagination>
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" v-model="editVisible" width="30%">
      <el-form ref="form" :model="form" label-width="70px">
        <el-form-item label="用户名">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">确 定</el-button>
                </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>


export default {
  data() {
    return {
      dataForm: {
        templateName: '',
        templateCode: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      messageLimitVisible: false
    }
  },

  activated() {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/message/template/page'),
        method: 'post',
        data: this.$http.adornData({
          'page': this.pageIndex,
          'limit': this.pageSize,
          conditions: [{
            'field': 'templateCode',
            'value': this.dataForm.templateCode,
            'query': 'like'
          }, {
            'field': 'templateName',
            'value': this.dataForm.templateName,
            'query': 'like'
          }]
        })
      }).then(({data}) => {
        if (data && data.code === 200) {
          this.dataList = data.data.list
          this.totalPage = data.data.totalCount
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val
    },
    // 新增 / 修改
    addOrUpdateHandle(templateCode) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(templateCode)
      })
    },
    // 删除
    deleteHandle(record) {
      const tipsMessage = '是否删除' + record.templateName + '【' + record.templateCode + '】这个消息模板'
      this.$confirm(tipsMessage, '删除提示', {
        confirmButtonText: '确定删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(() => {
        this.visible = false
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/message/template/delete/' + record.templateCode),
          method: 'delete'
        }).then(({data}) => {
          var that = this
          this.$http.showMessage(this, data, function () {
            // 刷新页面
            if (data.code === 200) {
              that.getDataList()
            }
          })
        }).catch(() => {
          this.$http.showError(this, '删除失败！')
        })
      })
    },
    openLimitDialog(templateCode) {
      this.messageLimitVisible = true
      this.$nextTick(() => {
        this.$refs.messageLimit.init(templateCode)
      })
    }

  }
}
</script>
