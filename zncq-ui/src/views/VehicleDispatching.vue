<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="车辆状态" prop="key">
        <el-select v-model="ruleForm.state" clearable style="width: 160px;float: left" placeholder="请选择字段">
          <el-option label="运输中" value="1"></el-option>
          <el-option label="完成运输" value="2"></el-option>
        </el-select>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="订单号:">
          <el-input v-model="ruleForm.orderNo" placeholder="请输入关键字" style="width: 160px;"></el-input>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;"
                     @click="submitForm(1)">搜索
          </el-button>
        </el-form-item>
      </div>
      <el-button style="position:relative;top: -50px;left: 30px" size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
    </el-form>
    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%;position: relative;top:-30px;left: -10px">
      <el-table-column
          fixed
          prop="id"
          label="运单号"
          width="70">
      </el-table-column>
      <el-table-column
          prop="vehicle.name"
          label="车辆名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="vehicle.vehicleNo"
          label="车牌号"
          width="130">
      </el-table-column>
      <el-table-column
          prop="vehicle.admin"
          label="司机"
          width="130">
      </el-table-column>
      <el-table-column
          prop="vehicle.phone"
          label="电话"
          width="130">
      </el-table-column>
      <el-table-column
          prop="startAddress"
          label="出发地"
          width="130">
      </el-table-column>
      <el-table-column
          prop="endAddress"
          label="目的地"
          width="130">
      </el-table-column>
      <el-table-column
          prop="expectedEnddate"
          label="预计送达时间"
          width="200">
      </el-table-column>
      <el-table-column
          prop="state"
          label="车辆状态"
          width="130">
        <template slot-scope="scope">
          <i v-if="scope.row.state == 1" class="iconfont icon-yunshuzhongwuliu"></i>
          <i v-if="scope.row.state == 2" class="iconfont icon-yiwancheng"></i>
          {{ scope.row.state == 1 ? '运输中' : '运输完成' }}
        </template>
      </el-table-column>
      <el-table-column width="150" label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="danger"
              @click="detail(scope.row.order.orderNo)">订单
          </el-button>
          <el-button
              size="mini"
              type="primary"
              @click="update(scope.row.id)">更新
          </el-button>
          <el-dialog append-to-body title="货物清单" :visible.sync="dialogTableVisible" width="55%">
            <el-statistic group-separator="," :precision="0" :value="totalNum" title="总数"
                          style="transform: scale(1.5);position: relative;top: -30px;left: 300px"></el-statistic>
            <el-statistic title="订单号:"
                          style="transform: scale(1.2);position: relative;top: -30px;left: -370px"></el-statistic>
            <span style="position: relative;top: -35px;left: 2px">{{ orderNo }}</span>
            <el-table :data="gridData" style="position: relative;top: -20px" height="250">
              <el-table-column
                  prop="goodName"
                  label="物品名称"
                  width="200">
              </el-table-column>
              <el-table-column
                  prop="goodTypeId"
                  label="物品类型"
                  width="200">
                <template slot-scope="scope">
                  {{ scope.row.goodTypeId == 2 ? '产品' : '设备' }}
                </template>
              </el-table-column>
              <el-table-column
                  prop="num"
                  label="数量"
                  width="200">
              </el-table-column>
              <el-table-column
                  prop="supplier.name"
                  label="供应商"
                  width="200">
              </el-table-column>
            </el-table>
          </el-dialog>
        </template>
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
      dialogTableVisible: false,
      tableData: [{
        vehicleName: '',
      }],
      orderNo: '',
      totalNum: '',
      gridData: [],
      currentPage: 1,
      pageSize: 7,
      total: null,
      ruleForm: {
        state: '',
        orderNo: '',
        pageNum: '',
        size: 7
      },
      rules: {}
    }
  },
  methods: {
    page() {
        if (this.ruleForm.orderNo == '' && this.ruleForm.state == ''){
          this.initTable();
        }else {
          this.submitForm(this.currentPage);
        }
    },
    submitForm(pageNum) {
      this.ruleForm.pageNum = pageNum;
      axios.get('http://localhost:8181/transport', {params: this.ruleForm})
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            // console.log(data)
            if (code == 200) {
              this.total = data.data.total;
              this.tableData = data.data.data;
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].expectedEnddate = new Date(this.tableData[i].expectedEnddate).Format('yyyy-MM-dd hh:mm:ss');
              }
              this.$message({
                message: data.message,
                type: 'success'
              });
            } else {
              this.tableData = [];
              this.total = 0;
            }
          })
          .catch(error => {
            this.$message.error(error.message);
          })
    },
    initTable() {
      axios.get('http://localhost:8181/transport/' + this.currentPage)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            // console.log(data)
            if (code == 200) {
              this.total = data.data.total;
              this.tableData = data.data.data;
              console.log(this.tableData)
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].expectedEnddate = new Date(this.tableData[i].expectedEnddate).Format('yyyy-MM-dd hh:mm:ss');
              }
              console.log(this.tableData)
              this.$message({
                message: data.message,
                type: 'success'
              });
            } else {
              this.tableData = [];
              this.total = 0;
            }
          })
          .catch(error => {
            this.$message.error(error.message);
          })
    },

    detail(orderNo) {
      axios.get('http://localhost:8181/outStorage/good/' + orderNo)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            console.log(data.data)
            if (code == 200) {
              this.orderNo = data.data.orderNo;
              this.gridData = data.data.goods;
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
    update(id){
      this.$prompt('请输入车辆所在地', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({ value }) => {
        axios.put('http://localhost:8181/transport/address',{
          address:value,
          id:id
        })
        .then(resp=>{
          let data = resp.data;
          let code = data.code;
          if (code == 200){
            this.$message.success(data.message)
          }else {
            this.$message.error(data.message)
          }
        })
        .catch(error=>{
          this.$message.error(error.message)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    }
  },
  created() {
    this.initTable();
  }
}
</script>