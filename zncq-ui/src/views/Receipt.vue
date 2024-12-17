<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item style="position: relative;left: 10px" label="订单编号" prop="key">
        <el-input v-model="ruleForm.orderNo" placeholder="请输入关键字" style="width: 160px;"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="车牌号:">
          <el-input v-model="ruleForm.vehicleNo" placeholder="请输入关键字" style="width: 160px;"></el-input>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;"
                     @click="submitForm(1)">搜索
          </el-button>
        </el-form-item>
        <el-button
            style="position: relative;left: -230px"
            size="mini"
            type="success"
            @click="add">回单登记</el-button>
        <el-button style="position:relative;left: -220px;" size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
      </div>
    </el-form>
    <el-table
        :data="tableData"
        stripe
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          prop="orderNo"
          label="订单编号"
          width="310">
      </el-table-column>
      <el-table-column
          prop="orderType"
          label="订单类型"
          width="140">
      </el-table-column>
      <el-table-column
          prop="num"
          label="数量"
          width="80">
      </el-table-column>
      <el-table-column
          prop="vehicle.name"
          label="车辆名称"
          width="140">
      </el-table-column>
      <el-table-column
          prop="vehicle.vehicleNo"
          label="车牌号"
          width="140">
      </el-table-column>
      <el-table-column
          prop="vehicle.admin"
          label="司机名称"
          width="140">
      </el-table-column>
      <el-table-column
          prop="vehicle.phone"
          label="联系电话"
          width="140">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="danger"
              @click="detail(scope.row.orderNo)">清单</el-button>
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
    <el-dialog :visible.sync="visibleDialog"  v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" title="回单">
      <el-row :gutter="15">
        <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
          <el-col :span="24">
            <el-form-item label="订单编号" prop="orderNo">
              <el-input v-model="formData.orderNo" placeholder="请输入订单编号订单编号" clearable
                        prefix-icon='el-icon-caret-right' :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="货物" prop="goodId">
              <el-select @change="goodChange" v-model="formData.goodId" placeholder="请选择货物货物" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in goodOptions" :key="index" :label="item.goodName"
                           :value="item.goodId" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="货物类型" prop="goodType">
              <el-input v-model="formData.goodType" placeholder="请输入货物类型" readonly :disabled='true'
                        clearable :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数量" prop="num">
              <el-input-number v-model="formData.num" placeholder="数量"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="13">
            <el-form-item label="运输车辆" prop="vehicleId">
              <el-select v-model="formData.vehicleId" placeholder="请选择运输车辆" clearable
                         :style="{width: '100%'}">
                <el-option v-for="(item, index) in vehicleOptions" :key="index" :label="item.name"
                           :value="item.id" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="13">
            <el-form-item label="回单日期" prop="enddate">
              <el-date-picker v-model="formData.enddate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                              :style="{width: '100%'}" placeholder="请选择回单日期" clearable></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="登记清单" :visible.sync="dialogTableVisible2">
      <el-table :data="gridData" height="250"  style="width: 100%">
        <el-table-column prop="goodName" label="货物名称" width="180"></el-table-column>
        <el-table-column prop="goodTypeId" label="货物类型" width="180">
          <template slot-scope="scope">
            {{scope.row.goodTypeId == 1 ? '原料' : (scope.row.goodTypeId == 2 ? '产品':'设备')}}
          </template>
        </el-table-column>
        <el-table-column prop="num" label="数量"></el-table-column>
        <el-table-column prop="supplier.name" label="供应商"></el-table-column>
      </el-table>
    </el-dialog>
  </div>

</template>

<script>
export default {
  data() {
    return {
      visibleDialog:false,
      dialogTableVisible2:false,
      gridData:null,
      tableData: null,
      currentPage: 1,
      pageSize: 7,
      total: null,
      formData: {
        loginNumber:undefined,
        orderNo: undefined,
        goodId: undefined,
        goodType: undefined,
        num: undefined,
        vehicleId: undefined,
        enddate: null,
      },
      ruleForm: {
        orderNo: '',
        vehicleNo: '',
        pageNum: '',
        size: 7
      },
      rules: {
        orderNo: [{
          required: true,
          message: '请输入订单编号订单编号',
          trigger: 'blur'
        }],
        goodId: [{
          required: true,
          message: '请选择货物货物',
          trigger: 'change'
        }],
        goodType: [{
          required: true,
          message: '请输入货物类型',
          trigger: 'blur'
        }],
        num: [{
          required: true,
          message: '数量',
          trigger: 'blur'
        }],
        vehicleId: [{
          required: true,
          message: '请选择运输车辆',
          trigger: 'change'
        }],
        enddate: [{
          required: true,
          message: '请选择回单日期',
          trigger: 'change'
        }],
      },
      goodOptions:[],
      vehicleOptions:[],
    }
  },
  methods: {
    initTable(){
        axios.get('http://localhost:8181/transport/order/'+this.currentPage)
            .then(resp=>{
              let data = resp.data;
              let code = data.code;
              console.log(data)
              if (code == 200){
                this.$message.success(data.message);
                this.tableData = data.data.data;
                this.total = data.data.total;
              }else{
                this.$message.error(data.message);
                this.tableData = [];
                this.total = 0;
              }
            })
            .catch(error=>{
              this.$message.error(error.message);
            })
    },
    submitForm(pageNum){
      this.ruleForm.pageNum = pageNum;
      axios.get('http://localhost:8181/transport/order/query',{params:this.ruleForm})
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            if (code == 200){
              this.$message.success(data.message);
              this.tableData = data.data.data;
              this.total = data.data.total;
            }else{
              this.$message.error(data.message);
              this.tableData = [];
              this.total = 0;
            }
          })
          .catch(error=>{
            this.$message.error(error.message);
          })
    },
    detail(orderNo){
      axios.get('http://localhost:8181/transport/order/register/'+orderNo)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.gridData = data.data;
              this.$message.success(data.message);
            } else {
              this.gridData = [];
              this.$message.error(data.message);
            }
            this.dialogTableVisible2 = true;
          })
          .catch(error => {
            this.$message.error(error.message)
          })

    },
    page(){
      if (this.ruleForm.orderNo == '' && this.ruleForm.vehicleNo ==''){
       this.initTable();
      }else{
        this.submitForm(this.currentPage);
      }
    },
    goodChange(){
      for (let i = 0; i < this.goodOptions.length; i++) {
        if (this.formData.goodId == this.goodOptions[i].goodId){
          if (this.goodOptions[i].goodTypeId == 1){
            this.formData.goodType = '原料';
          }else if (this.goodOptions[i].goodTypeId == 2){
            this.formData.goodType = '产品';
          }else if (this.goodOptions[i].goodTypeId == 3){
            this.formData.goodType = '设备';
          }
          break;
        }
      }
    },
    add(){
      axios.get('http://localhost:8181/good/findAll')
            .then(resp=>{
              let data = resp.data;
              let code = data.code;
              if (code == 200){
                this.goodOptions = data.data;
              }else{
                this.goodOptions = [];
              }
            })
            .catch(error=>{
              this.$message.error(error.message)
            })
      axios.get('http://localhost:8181/vehicle/findAll2')
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            if (code == 200){
              this.vehicleOptions = data.data;
            }else{
              this.vehicleOptions = [];
            }
          })
          .catch(error=>{
            this.$message.error(error.message)
          })
      this.visibleDialog = true;
    },
    onOpen() {},
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    close() {
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.$confirm('提交后无法修改和删除你确定提交？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.formData.loginNumber = window.localStorage.getItem('username');
          console.log(this.formData)
          axios.post('http://localhost:8181/transport/receipt',this.formData)
                .then(resp=>{
                  let data = resp.data;
                  let code = data.code;
                  if (code == 200){
                    this.$message.success(data.message)
                  }else{
                    this.$message.error(data.message)
                  }
                  this.visibleDialog = false;
                })
                .catch(error=>{
                  this.$message.error(error.message)
                })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
        this.close()
      })
    },
  },
  created() {
    this.initTable();
  }

}
</script>