<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="订单编号:" prop="key">
        <el-input v-model="ruleForm.orderNo" placeholder="请输入关键字" style="width: 160px;"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;" @click="submitForm(1)">
          搜索
        </el-button>
      </div>
      <el-button style="position: relative;top: -50px;left: 40px" size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
    </el-form>

    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          fixed
          prop="orderNo"
          label="订单编号"
          width="310">
      </el-table-column>
      <el-table-column
          prop="vehicle.name"
          label="车辆名称"
          width="200">
      </el-table-column>
      <el-table-column
          prop="vehicle.vehicleNo"
          label="车牌号码"
          width="130">
      </el-table-column>
      <el-table-column
          prop="vehicle.admin"
          label="司机名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="vehicle.phone"
          label="联系电话"
          width="130">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="danger"
              @click="detail(scope.row.orderNo)">出库清单
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="入库清单" :visible.sync="dialogTableVisible">
      <el-statistic group-separator="," :precision="0" :value="totalNum" :title="title"
                    style="position: relative;top: -30px"></el-statistic>
      <el-table :data="gridData" height="250" border style="width: 100%">
        <el-table-column prop="goodName" label="货物名称" width="180"></el-table-column>
        <el-table-column prop="type" label="货物类型" width="180">
          <template slot-scope="scope">
            {{scope.row.goodTypeId == 1 ? "原料" : (scope.row.goodTypeId == 2 ? "产品" : "设备")}}
          </template>
        </el-table-column>
        <el-table-column prop="num" label="数量"></el-table-column>
        <el-table-column prop="supplier.name" label="供应商"></el-table-column>
      </el-table>
    </el-dialog>
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
      totalNum: '',
      title: '总数',
      typeName: '',
      dialogTableVisible: false,
      tableData: [{}],
      currentPage: 1,
      pageSize: 7,
      total: null,
      gridData: [],
      ruleForm: {
        orderNo: '',
        pageNum: '',
        size: 7
      },
      rules: {}
    }
  },
  methods: {
    submitForm(pageNum) {
      this.ruleForm.pageNum = pageNum;
      axios.get('http://localhost:8181/outStorage/query', {params: this.ruleForm})
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.tableData = data.data.data;
              this.total = data.data.total;
              this.$message.success(data.message)
            } else {
              this.tableData = [];
              this.total = 0;
              this.$message.error(data.message)
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    detail(orderNo) {
      axios.get('http://localhost:8181/outStorage/good/' + orderNo)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.gridData = data.data.goods;
              console.log(this.gridData)
              if (this.gridData[0].goodTypeId == 2) {
                this.typeName = '产品'
              } else {
                this.typeName = '设备'
              }
              this.totalNum = data.data.totalNum;
              this.$message.success(data.message);
              this.dialogTableVisible = true;
            } else {
              this.gridData = [];
              this.totalNum = 0;
              this.$message.error(data.message);
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    page() {
      if (this.ruleForm.orderNo == '') {
        this.initTable();
      } else {
        this.submitForm(this.currentPage);
      }
    },
    initTable() {
      axios.get('http://localhost:8181/outStorage/' + this.currentPage)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            console.log(data.data.data);
            if (code == 200) {
              this.tableData = data.data.data;
              this.total = data.data.total;
              this.$message.success(data.message)
            } else {
              this.tableData = [];
              this.total = 0;
              this.$message.error(data.message)
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
  },
  created() {
    this.initTable();
  }
}
</script>