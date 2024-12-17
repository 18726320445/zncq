
<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="字段：" prop="key">
        <el-select v-model="ruleForm.key" style="width: 160px;float: left" placeholder="请选择字段">
          <el-option label="物品编号" value="rawId"></el-option>
          <el-option label="物品名称" value="rawName"></el-option>
          <el-option label="物品类型" value="rawType"></el-option>
        </el-select>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="值：">
          <el-input v-model="ruleForm.value" placeholder="请输入关键字" style="width: 160px;"></el-input>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;" @click="submitForm('ruleForm')">搜索</el-button>
        </el-form-item>

      </div>

    </el-form>

    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          fixed
          prop="orderId"
          label="订单编号"
          width="200">
      </el-table-column>
      <el-table-column
          prop="warehouseName"
          label="仓库名称"
          width="200">
      </el-table-column>
      <el-table-column
          prop="goodName"
          label="物品名称"
          width="200">
      </el-table-column>
      <el-table-column
          prop="type"
          label="物品类型"
          width="200">
      </el-table-column>
      <el-table-column
          prop="goodNum"
          label="数量"
          width="200">
      </el-table-column>
      <el-table-column
          prop="expectDate"
          label="预计出库时间"
          width="200">
      </el-table-column>
      <el-table-column
          prop="realDate"
          label="实际出库时间"
          width="200">
      </el-table-column>
      <el-table-column
          prop="state"
          label="订单状态"
          width="200">
      </el-table-column>
    </el-table>
    <el-pagination style="margin-top: 20px;float: right"
                   background
                   layout="prev, pager, next"
                   :page-size="pageSize"
                   :total="total"
                   :current-page.sync="currentPage"
                   @current-change="page">
    </el-pagination>
  </div>

</template>

<script>
export default {
  data() {
    return {
      tableData: null,
      currentPage: 1,
      pageSize: 5,
      total: null,
      key: '',
      value: '',
      ruleForm: {
        key: '',
        value: '',
        page: '',
        size: 5
      },
      rules: {
        key: [
          { required: true, message: '请选择字段', trigger: 'change' }
        ]
      }
    }
  },
}
</script>