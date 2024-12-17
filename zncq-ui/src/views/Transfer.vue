<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="货物名称:" prop="key">
        <el-input v-model="ruleForm.goodName" placeholder="请输入关键字" style="width: 160px;"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="订单状态:">
          <el-select v-model="ruleForm.state" clearable style="width: 160px;float: left" placeholder="请选择字段">
            <el-option label="计划中" value="2"></el-option>
            <el-option label="已完成" value="3"></el-option>
          </el-select>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;" @click="submitForm(1)">搜索</el-button>
        </el-form-item>
        <div style="position: relative ;left: -230px">
          <el-button
              size="mini"
              type="success"
              @click="add()">创建移库</el-button>
          <el-button size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
          <el-dialog :visible.sync="visibleDialog" v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" title="创建移库">
            <el-row :gutter="15">
              <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
                <el-col :span="12">
                  <el-form-item  label-width="150px" label="物品名称" prop="goodId">
                    <el-select @change="goodChange" v-model="formData.goodId" placeholder="请选择物品名称" clearable :style="{width: '100%'}">
                      <el-option v-for="(item, index) in goodNameOptions" :key="index" :label="item.goodName"
                                 :value="item.goodId" :disabled="item.disabled"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="物品类型" prop="goodType">
                    <el-input :disabled="true" v-model="formData.goodType" placeholder="物品类型" readonly clearable
                              :style="{width: '100%'}"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="18">
                  <el-form-item label-width="150px" label="物品数量" prop="num">
                    <el-input-number v-model="formData.num" placeholder="物品数量"></el-input-number>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item  label-width="150px" label="原仓库名称" prop="originalWarehouseId">
                    <el-select @change="warehouseChange1" v-model="formData.originalWarehouseId" placeholder="请选择原仓库名称" clearable
                               :style="{width: '100%'}">
                      <el-option v-for="(item, index) in originalWarehouseNameOptions" :key="index"
                                 :label="item.name" :value="item.id" :disabled="item.disabled"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="原库柜名称" prop="originalContainerId">
                    <el-select v-model="formData.originalContainerId" placeholder="请选择原库柜名称" clearable
                               :style="{width: '100%'}">
                      <el-option v-for="(item, index) in originalContainerNameOptions" :key="index"
                                 :label="item.name" :value="item.id" :disabled="item.disabled"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item  label-width="150px" label="移入仓库名称" prop="transferWarehouseId">
                    <el-select @change="warehouseChange2" v-model="formData.transferWarehouseId" placeholder="请选择移入仓库名称" clearable
                               :style="{width: '100%'}">
                      <el-option v-for="(item, index) in transferWarehouseNameOptions" :key="index"
                                 :label="item.name" :value="item.id" :disabled="item.disabled"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label-width="110px" label="移入库柜名称" prop="transferContainerId">
                    <el-select v-model="formData.transferContainerId" placeholder="请选择移入库柜名称" clearable
                               :style="{width: '100%'}">
                      <el-option v-for="(item, index) in transferContainerNameOptions" :key="index"
                                 :label="item.name" :value="item.id" :disabled="item.disabled"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label-width="150px" label="运输车辆" prop="vehicleId">
                    <el-select @change="vehicleChange" v-model="formData.vehicleId" placeholder="请选择移入库柜名称" clearable
                               :style="{width: '100%'}">
                      <el-option v-for="(item, index) in vehicleOptions" :key="index"
                                 :label="item.name" :value="item.id" :disabled="item.disabled"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label-width="150px" label="最大容量" prop="MaxNum">
                    <el-input :disabled="true" v-model="formData.MaxNum" placeholder="" readonly clearable
                              :style="{width: '100%'}"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label-width="150px" label="移库时间" prop="transferdate">
                    <el-date-picker v-model="formData.transferdate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                                    :style="{width: '100%'}" placeholder="请选择移库时间" clearable></el-date-picker>
                  </el-form-item>
                </el-col>
              </el-form>
            </el-row>
            <div slot="footer">
              <el-button @click="close">取消</el-button>
              <el-button type="primary" @click="handleConfirm">确定</el-button>
            </div>
          </el-dialog>
        </div>
      </div>

    </el-form>

    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          fixed
          prop="id"
          label="移库通知编号"
          width="130">
      </el-table-column>
      <el-table-column
          prop="good.goodName"
          label="物品名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="good.goodTypeId"
          label="物品类型"
          width="130">
        <template slot-scope="scope">
          {{scope.row.good.goodTypeId == 1 ?'原料' : (scope.row.good.goodTypeId == 2 ?'产品' : '设备')}}
        </template>
      </el-table-column>
      <el-table-column
          prop="num"
          label="物品数量"
          width="130">
      </el-table-column>
      <el-table-column
          prop="ow.name"
          label="原仓库名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="oc.name"
          label="原库柜名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="tw.name"
          label="移入仓库名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="tc.name"
          label="移入库柜名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="transferdate"
          label="移库时间"
          width="200">
      </el-table-column>
      <el-table-column
          prop="order.state"
          label="移库状态"
          width="130">
        <template slot-scope="scope">
          <i v-if="scope.row.order.state == 2" class="iconfont icon-yunshuzhongwuliu"></i>
          <i v-if="scope.row.order.state != 2" class="iconfont icon-yiwancheng"></i>
          {{scope.row.order.state == 2 ? '计划中' : '已完成'}}
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
      visibleDialog:false,
      tableData: null,
      currentPage: 1,
      pageSize: 7,
      total: null,
      key: '',
      value: '',
      formData: {
        processName: undefined,
        goodId: undefined,
        goodType: undefined,
        num: 0,
        originalWarehouseId: undefined,
        originalContainerId: undefined,
        transferWarehouseId: undefined,
        transferContainerId: undefined,
        transferdate: null,
        vehicleId:undefined,
        MaxNum:undefined,
      },
      ruleForm: {
        goodName:'',
        state:'',
        pageNum: '',
        size: 7
      },
      rules: {
        goodId: [{
          required: true,
          message: '请选择物品名称',
          trigger: 'change'
        }],
        goodType: [{
          required: true,
          trigger: 'blur'
        }],
        num: [{
          required: true,
          message: '物品数量',
          trigger: 'blur'
        }],
        originalWarehouseId: [{
          required: true,
          message: '请选择原仓库名称',
          trigger: 'change'
        }],
        originalContainerId: [{
          required: true,
          message: '请选择原库柜名称',
          trigger: 'change'
        }],
        transferWarehouseId: [{
          required: true,
          message: '请选择移入仓库名称',
          trigger: 'change'
        }],
        transferContainerId: [{
          required: true,
          message: '请选择移入库柜名称',
          trigger: 'change'
        }],
        transferDate: [{
          required: true,
          message: '请选择移库时间',
          trigger: 'change'
        }],
        vehicleId: [{
          required: true,
          message: '请选择运输车辆',
          trigger: 'change'
        }],
      },
      goodNameOptions: [],
      vehicleOptions:[],
      originalWarehouseNameOptions: [],
      originalContainerNameOptions:[],
      transferWarehouseNameOptions: [],
      transferContainerNameOptions: [],
    }
  },
  methods: {
    vehicleChange(){
      for (let i = 0; i < this.vehicleOptions.length; i++) {
        if (this.vehicleOptions[i].id == this.formData.vehicleId){
          this.formData.MaxNum = this.vehicleOptions[i].maxNum;
        }
      }
    },
    goodChange(){
      for (let i = 0; i < this.goodNameOptions.length; i++) {
        if (this.goodNameOptions[i].goodId == this.formData.goodId){
           if (this.goodNameOptions[i].goodTypeId == 1){
             this.formData.goodType = '原料';
           }else if (this.goodNameOptions[i].goodTypeId == 2){
             this.formData.goodType = '产品';
           }else if (this.goodNameOptions[i].goodTypeId == 3){
             this.formData.goodType = '设备';
           }
           return
        }
      }
    },
    warehouseChange1(){
      axios.get('http://localhost:8181/warehouse/container/' + this.formData.originalWarehouseId)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.originalContainerNameOptions = data.data.containerVos;
            } else {
              this.originalContainerNameOptions = [];
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    warehouseChange2(){
      axios.get('http://localhost:8181/warehouse/container/' + this.formData.transferWarehouseId)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.transferContainerNameOptions = data.data.containerVos;
            } else {
              this.transferContainerNameOptions = [];
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    add(){
      axios.get('http://localhost:8181/good/findAll')
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.goodNameOptions = data.data;
            } else {
              this.goodNameOptions = []
              this.$message.error(data.message);
            }
          })
          .catch(error => {
            this.$message.error(error.message);
          })
      axios.get('http://localhost:8181/warehouse')
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.originalWarehouseNameOptions = data.data;
              this.transferWarehouseNameOptions = data.data;
            } else {
              this.warehouseNameOptions = []
              this.$message.error(data.message);
            }
          })
          .catch(error => {
            this.$message.error(error.message);
          })
      axios.get('http://localhost:8181/vehicle/findAll')
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.vehicleOptions = data.data;
            } else {
              this.$message.error(data.data);
            }
          })
          .catch(error => {
            this.$message.error(error.data);
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
        if (this.formData.originalWarehouseId == this.formData.transferWarehouseId){
          this.$message.error("移库的仓库不能相同")
          this.visibleDialog = false;
          return;
        }
        if (this.formData.num > this.formData.MaxNum){
          this.$message.error("运输车辆最大容量小于运输所运输的容量")
          this.visibleDialog = false;
          return;
        }
        this.formData.processName = window.localStorage.getItem('username');
        axios.post('http://localhost:8181/warehouseTransfer',this.formData)
              .then(resp=>{
                let data = resp.data;
                let code = data.code;
                // console.log(data)
                if (code == 200){
                  this.$message.success(data.message);
                }else{
                  this.$message.error(data.message);
                }
                this.page()
                this.visibleDialog = false;
              })
              .catch(error=>{
                this.$message.error(error.message)
              })
        this.close()
      })
    },
    page(){
      if (this.ruleForm.goodName == '' && this.ruleForm.state == ''){
        this.initTable();
      }else{
        this.submitForm(this.currentPage);
      }
    },
    initTable(){
          axios.get('http://localhost:8181/warehouseTransfer/'+this.currentPage)
              .then(resp => {
                let data = resp.data;
                let code = data.code;
                // console.log(data)
                if (code == 200) {
                  this.total = data.data.total;
                  this.tableData = data.data.data;
                  // console.log(this.tableData)
                  for (let i = 0; i < this.tableData.length; i++) {
                    this.tableData[i].transferdate = new Date(this.tableData[i].transferdate).Format('yyyy-MM-dd hh:mm:ss');
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
    submitForm(pageNum){
      this.ruleForm.pageNum = pageNum;
      // console.log(this.ruleForm)
      axios.get('http://localhost:8181/warehouseTransfer/query',{params:this.ruleForm})
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            // console.log(data)
            if (code == 200) {
              this.total = data.data.total;
              this.tableData = data.data.data;
              // console.log(this.tableData)
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].transferdate = new Date(this.tableData[i].transferdate).Format('yyyy-MM-dd hh:mm:ss');
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
    }
  },
  created() {
    this.initTable();
  }
}
</script>