<template>
  <div>
    <el-header>
      <el-form :inline="true" :model="template" class="demo-form-inline">
        <el-form-item label="模板名称">
          <el-input v-model="template.templateName" placeholder="模板名称"></el-input>
        </el-form-item>
        <el-form-item label="模板编码">
          <el-input v-model="template.templateCode" placeholder="模板名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="getData">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-plus" @click="openAddCaptchaTemplateDialog">添加模板</el-button>
        </el-form-item>
      </el-form>
    </el-header>

    <el-table row-key="id"
              :data="tableData"
              highlight-current-row="true"
              border="true"
              style="width: 100%">
      <el-table-column
          prop="templateName"
          label="模板名称"
          style="width: 20%">
      </el-table-column>
      <el-table-column
          prop="templateCode"
          label="模板编码"
          style="width: 20%">
      </el-table-column>
      <el-table-column
          prop="expireTime"
          label="过期时间"
          style="width: 20%">
      </el-table-column>
      <el-table-column
          prop="captchaLength"
          label="验证码长度"
          style="width: 20%">
      </el-table-column>
      <el-table-column
          prop="captchaType"
          label="验证码类型"
          style="width: 20%">
      </el-table-column>
      <el-table-column
          label="修改时间" :formatter="timestampToTime"
          width="180">
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
              size="mini"
              @click="openUpdateCaptchaTemplateDialog(scope.row.id)">编辑
          </el-button>
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index,scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-footer>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage4"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="100"
          style="float:right"
          layout="total, sizes, prev, pager, next, jumper"
          :total="400">
      </el-pagination>
    </el-footer>
    <el-dialog
        :title="(addCaptcha?'添加验证模板':'修改验证码模板')"
        v-model="dialogVisible"
        width="30%"
        :before-close="handleClose">
      <el-form ref="form" :model="dialogForm" label-width="100px">
        <el-form-item label="模板名称" required>
          <el-input v-model="dialogForm.templateName"></el-input>
        </el-form-item>
        <el-form-item label="模板编码" required="">
          <el-input v-model="dialogForm.templateCode"></el-input>
        </el-form-item>
        <el-form-item label="验证码长度" required>
          <el-input-number v-model="dialogForm.captchaLength"/>
        </el-form-item>
        <el-form-item label="过期时间" required>
          <el-input-number v-model="dialogForm.expireTime"/>
        </el-form-item>
        <el-form-item label="验证码类型">
          <el-select v-model="dialogForm.captchaType" placeholder="请选择活动区域">
            <el-option label="图片验证码" value="1"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="saveData()">确认</el-button>
          <el-button @click="cancelShowDialog()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>

</template>

<script>

export default {
  name: "templateInfo",
  data() {
    return {
      template: {
        templateName: '',
        templateCode: ''
      },
      addCaptcha: true,
      dialogForm: {
        id: 0,
        templateName: '',
        captchaCode: '',
        captchaLength: 4,
        expireTime: 0,
        captchaType: 0
      },
      tableData: [],
      dialogVisible: false
    };
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      console.log("888")
      this.$http({
        url: this.$http.adornUrl('/message/captchaTemplateApi/pageQuery'),
        method: 'post',
        data: this.$http.adornData({
          'page': this.pageIndex,
          'limit': this.pageSize,
          conditions: [{
            'field': 'templateCode',
            'value': this.template.templateCode,
            'condition': 'like'
          }, {
            'field': 'templateName',
            'value': this.template.templateName,
            'condition': 'like'
          }]
        })
      }).then(({data}) => {
        this.tableData = data.data.list;
      })
    },
    openAddCaptchaTemplateDialog() {
      this.dialogVisible = true;
    },
    openUpdateCaptchaTemplateDialog(id) {

      this.$http({
        url: this.$http.adornUrl('/message/captchaTemplateApi/findById/' + id),
        method: 'get',
      }).then(({data}) => {
        if (data.code === 200) {
          this.dialogForm = data.data;
          this.dialogForm.id = id;
          this.dialogVisible = true;
        }
      })

    },
    /**
     *取消显示dialog
     */
    cancelShowDialog() {
      this.dialogVisible = false;
    },
    /**
     *保存数据
     */
    saveData() {
      var saveUrl = '/message/captchaTemplateApi/';
      if (this.dialogForm.id && this.dialogForm.id > 0) {
        saveUrl += "update"//更新
      } else {
        //  添加
        saveUrl += "add"
      }
      this.$http({
        url: this.$http.adornUrl(saveUrl),
        method: 'post',
        data: this.$http.adornData(this.dialogForm)
      }).then(({data}) => {
        if (data.code === 200) {
          this.$message({
            message: '保存成功',
            type: 'success',
            center: true
          });
          this.getData();
          this.dialogVisible = false;
        }

      })
    },
    /**
     * 删除
     * @param row
     */
    handleDelete(index, row) {
      console.log("delete:", row)
      let id = row.id;
      this.$http({
        url: this.$http.adornUrl('/message/captchaTemplateApi/delete/' + id),
        method: 'post'
      }).then(() => {
        this.$message({
          message: '删除成功',
          center: true
        });
        this.getData();
      })
    },
    timestampToTime(row) {
      if (row.updateTime) {
        var date = new Date(row.updateTime) //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var Y = date.getFullYear() + '-'
        var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
        var D = date.getDate() + ' '
        var h = date.getHours() + ':'
        var m = date.getMinutes() + ':'
        var s = date.getSeconds()
        return Y + M + D + h + m + s;
      } else {
        return "";
      }

    },
  }

};
</script>

