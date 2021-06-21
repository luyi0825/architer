<template>
  <div class="mod-role">
    <el-form :inline="true" :model="template" class="demo-form-inline">
      <el-form-item label="模板名称">
        <el-input v-model="template.templateName" placeholder="模板名称"></el-input>
      </el-form-item>
      <el-form-item label="模板编码">
        <el-input v-model="template.templateCode" placeholder="模板名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getData">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
        :data="tableData"
        style="width: 100%">
      <el-table-column
          label="日期"
          width="180">
        <template #default="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.date }}</span>
        </template>
      </el-table-column>
      <el-table-column
          label="姓名"
          width="180">
        <template #default="scope">
          <el-popover effect="light" trigger="hover" placement="top">
            <template #default>
              <p>姓名: {{ scope.row.name }}</p>
              <p>住址: {{ scope.row.address }}</p>
            </template>
            <template #reference>
              <div class="name-wrapper">
                <el-tag size="medium">{{ scope.row.name }}</el-tag>
              </div>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑
          </el-button>
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
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
      tableData: []
    };
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      console.log("888")
      this.$http({
        url: this.$http.adornUrl('/message/template/page'),
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
      }).then(() => {
        console.log(1)
      })
    }
  }

};
</script>

