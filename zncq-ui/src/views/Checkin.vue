<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="订单号:" prop="key">
        <el-input v-model="ruleForm.orderNo" placeholder="请输入关键字" style="width: 160px;"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="货物名称:">
          <el-input v-model="ruleForm.goodName" placeholder="请输入关键字" style="width: 160px;"></el-input>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 450px;top: -0.5px"
                     @click="submitForm(1)">搜索
          </el-button>
        </el-form-item>
        <el-form-item style="position: relative;top: -63.5px;left: 250px" label="日期范围:">
          <el-date-picker
              v-model="date"
              type="daterange"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
        <div style="position: relative ;left: -230px">
          <el-button
              style="position: relative;top: -70px"
              size="mini"
              type="success"
              @click="add()">入库登记
          </el-button>
          <el-button size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
          <el-dialog :visible.sync="visibleDialog" v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose"
                     title="入库登记">
            <el-row :gutter="15">
              <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
                <el-col :span="24">
                  <el-form-item label="订单编号" prop="orderNo">
                    <el-input v-model="formData.orderNo" placeholder="请输入订单编号" clearable :style="{width: '100%'}">
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="物品名称" prop="goodId">
                    <el-select @change="goodChange" v-model="formData.goodId" placeholder="请选择物品名称" clearable :style="{width: '100%'}">
                      <el-option v-for="(item, index) in GoodOptions" :key="index" :label="item.goodName"
                                 :value="item.goodId" :disabled="item.disabled"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="物品类型" prop="goodTypeName">
                    <el-input v-model="formData.goodTypeName" placeholder="货物类型" disabled clearable
                              :style="{width: '80%'}">
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="数量" prop="num">
                    <el-input-number v-model="formData.num" placeholder="数量"></el-input-number>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="仓库名称" prop="warehouseId">
                    <el-select @change="warehouseChange" v-model="formData.warehouseId" placeholder="请选择仓库名称" clearable
                               :style="{width: '100%'}">
                      <el-option v-for="(item, index) in warehouseNameOptions" :key="index" :label="item.name"
                                 :value="item.id" :disabled="item.disabled"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="库柜名称" prop="containerId">
                    <el-select v-model="formData.containerId" placeholder="请选择库柜名称" clearable
                               :style="{width: '100%'}">
                      <el-option v-for="(item, index) in ContainerNameOptions" :key="index" :label="item.name"
                                 :value="item.id" :disabled="item.disabled"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="入库时间" prop="indate">
                    <el-date-picker
                        v-model="formData.indate"
                        type="date"
                        placeholder="选择日期"
                        align="right"
                        :picker-options="pickerOptions">
                    </el-date-picker>
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
        style="width: 100%;position: relative;top:-70px;">
      <el-table-column
          fixed
          prop="orderNo"
          label="订单编号"
          width="300">
      </el-table-column>
      <el-table-column
          prop="good.goodName"
          label="物品名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="container.type"
          label="物品类型"
          width="90">
      </el-table-column>
      <el-table-column
          prop="num"
          label="数量"
          width="80">
      </el-table-column>
      <el-table-column
          prop="warehouse.name"
          label="仓库名称"
          width="180">
      </el-table-column>
      <el-table-column
          prop="container.name"
          label="库柜名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="indate"
          label="入库时间"
          width="200">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="primary"
              @click="edit(scope.row)">编辑
          </el-button>
        </template>
      </el-table-column>
      <el-dialog :visible.sync="visibleDialog2" v-bind="$attrs" append-to-body v-on="$listeners" @open="onOpen" @close="onClose"
                 title="修改登记记录">
        <el-row :gutter="15">
          <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
            <el-col :span="24">
              <el-form-item label="数量" prop="num">
                <el-input-number v-model="formData.num" placeholder="数量"></el-input-number>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="库柜名称" prop="containerId">
                <el-select v-model="formData.containerId" placeholder="请选择库柜名称" clearable
                           :style="{width: '100%'}">
                  <el-option v-for="(item, index) in ContainerNameOptions" :key="index" :label="item.name"
                             :value="item.id" :disabled="item.disabled"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="入库时间" prop="indate">
                <el-date-picker
                    v-model="formData.indate"
                    type="date"
                    placeholder="选择日期"
                    align="right"
                    :picker-options="pickerOptions">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-form>
        </el-row>
        <div slot="footer">
          <el-button @click="close">取消</el-button>
          <el-button type="primary" @click="handleConfirm2">确定</el-button>
        </div>
      </el-dialog>
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
      date: null,
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      visibleDialog: false,
      visibleDialog2:false,
      tableData: null,
      currentPage: 1,
      pageSize: 7,
      total: null,
      key: '',
      value: '',
      formData: {
        orderNo: undefined,
        goodId: undefined,
        goodTypeName: undefined,
        goodTypeId: undefined,
        num: undefined,
        warehouseId: undefined,
        containerId: undefined,
        indate: null,
      },
      ruleForm: {
        orderNo:null,
        goodName:null,
        fromDate:null,
        toDate:null,
        pageNum: '',
        size: 7
      },
      rules: {
        orderNo: [{
          required: true,
          message: '请输入订单编号',
          trigger: 'blur'
        }],
        goodId: [{
          required: true,
          message: '请选择物品名称',
          trigger: 'change'
        }],
        goodTypeName: [{
          required: true,
          message: '请选择物品类型',
          trigger: 'blur'
        }],
        num: [{
          required: true,
          message: '数量',
          trigger: 'blur'
        }],
        warehouseId: [{
          required: true,
          message: '请选择仓库名称',
          trigger: 'change'
        }],
        containerId: [{
          required: true,
          message: '请选择库柜名称',
          trigger: 'change'
        }],
        indate: [{
          required: true,
          message: '请选择入库日期',
          trigger: 'change'
        }],
      },
      warehouseNameOptions: [],
      ContainerNameOptions: [],
      GoodOptions: [],
    }
  },
  methods: {
    onOpen() {
    },
    onClose() {
      this.$refs['elForm'].resetFields()
      this.formData = {};
    },
    close() {
      this.$emit('update:visible', false)
    },
    add() {
      axios.get('http://localhost:8181/good/RawAndEquip')
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.GoodOptions = data.data;
            } else {
              this.GoodOptions = [];
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
              this.warehouseNameOptions = data.data;
            } else {
              this.warehouseNameOptions = []
              this.$message.error(data.message);
            }
          })
          .catch(error => {
            this.$message.error(error.message);
          })
      this.visibleDialog = true;
    },
    warehouseChange(){
      axios.get('http://localhost:8181/warehouse/container/'+this.formData.warehouseId)
            .then(resp=>{
                let data = resp.data;
                let code = data.code;
                if (code == 200){
                  this.ContainerNameOptions = data.data.containerVos;
                }else{
                  this.ContainerNameOptions = [];
                }
            })
            .catch(error=>{
                  this.$message.error(error.message)
            })

    },
    goodChange(){
      for (let i = 0; i < this.GoodOptions.length; i++) {
        if (this.GoodOptions[i].goodId == this.formData.goodId){
          this.formData.goodTypeId = this.GoodOptions[i].goodTypeId;
        }
      }
      if (this.formData.goodTypeId == 1){
        this.formData.goodTypeName = '原料'
      }else if(this.formData.goodTypeId == 2){
        this.formData.goodTypeName = '产品'
      }else{
        this.formData.goodTypeName = '设备'
      }
    },
    edit(row){
        axios.get('http://localhost:8181/warehouse/container/'+row.warehouse.id)
              .then(resp=>{
                let data = resp.data;
                let code =data.code;
                if (code == 200){
                    this.ContainerNameOptions = data.data.containerVos;
                }else{
                  this.ContainerNameOptions = [];
                }
              })
              .catch(error=>{
                  this.$message.error(error.message)
              })
      axios.get('http://localhost:8181/inStorage/',{params:{
          orderNo:row.orderNo,
          goodId:row.good.goodId
        }})
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            if (code == 200){
              this.formData = data.data;
            }else{
              this.$message.error(data.message);
            }
          })
          .catch(error=>{
            this.$message.error(error.message)
          })
        this.visibleDialog2 = true;
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.$confirm('你确定要入库登记，登记后要无法删除只能修改, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.formData.indate =  new Date(this.formData.indate).Format('yyyy-MM-dd')
          console.log(this.formData);
          axios.post('http://localhost:8181/inStorage', this.formData)
              .then(resp => {
                let data = resp.data;
                let code = data.code;
                if (code == 200) {
                  this.$message.success(data.message)
                } else {
                  this.$message.error(data.message)
                }
                this.page();
                this.$refs['elForm'].resetFields();
                this.visibleDialog = false;
              })
              .catch(error => {
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
    handleConfirm2(){
      this.formData.indate =  new Date(this.formData.indate).Format('yyyy-MM-dd')
      console.log(this.formData);
      axios.put('http://localhost:8181/inStorage', this.formData)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.$message.success(data.message)
            } else {
              this.$message.error(data.message)
            }
            this.page();
            this.$refs['elForm'].resetFields();
            this.visibleDialog2 = false;
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    page() {
          if (this.ruleForm.orderNo == null && this.ruleForm.toDate == null && this.ruleForm.fromDate == null && this.ruleForm.goodName == null){
            this.initTable();
          }else {
            this.submitForm(this.currentPage);
          }
    },
    submitForm(pageNum) {
          this.ruleForm.pageNum = pageNum;
          if (this.date != null){
            this.ruleForm.fromDate = new Date(this.date[0]).Format('yyyy-MM-dd')
            this.ruleForm.toDate = new Date(this.date[1]).Format('yyyy-MM-dd')
          }
          axios.get('http://localhost:8181/inStorage/query2',{params:this.ruleForm})
              .then(resp => {
                let data = resp.data;
                let code = data.code;
                console.log(data)
                if (code == 200) {
                  this.total = data.data.total;
                  this.tableData = data.data.data;
                  for (let i = 0; i < this.tableData.length; i++) {
                    this.tableData[i].indate = new Date().Format('yyyy-MM-dd hh:mm:ss');
                  }
                  this.$message({
                    message: data.message,
                    type: 'success'
                  });
                } else {
                  this.tableData = [];
                  this.total = 0;
                }
                this.ruleForm.fromDate = null;
                this.ruleForm.toDate = null;
              })
              .catch(error => {
                this.$message.error(error.message);
              })
    },
    initTable() {
      axios.get('http://localhost:8181/inStorage/finish/' + this.currentPage)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            // console.log(data)
            if (code == 200) {
              this.total = data.data.total;
              this.tableData = data.data.data;
              console.log(this.tableData)
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].indate = new Date(this.tableData[i].indate).Format('yyyy-MM-dd hh:mm:ss');
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
