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
        <div style="position: relative ;left: -230px;top: -60px">
          <el-button
              size="mini"
              type="success"
              @click="regist()">出库登记
          </el-button>
          <el-button size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
          <el-dialog :visible.sync="visibleDialog" v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose"
                     title="出库登记">
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
                    <el-select @change="changeGood" v-model="formData.goodId" placeholder="请选择物品名称" clearable
                               :style="{width: '100%'}">
                      <el-option v-for="(item, index) in goodOptions" :key="index" :label="item.goodName"
                                 :value="item.goodId" :disabled="item.disabled"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="物品类型" prop="goodTypeName">
                    <el-input v-model="formData.goodTypeName" :disabled="true" clearable :style="{width: '50%'}">
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
                    <el-select v-model="formData.warehouseId" placeholder="请选择仓库名称" @change="changeWarehouse" clearable
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
                <el-col :span="16">
                  <el-form-item label="运输车牌号" prop="vehicleNo">
                    <el-input v-model="formData.vehicleNo" placeholder="请输入输入运输车牌号" clearable :style="{width: '100%'}">
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="出库时间" prop="outdate">
                    <el-date-picker
                        v-model="formData.outdate"
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
        style="width: 100%;position: relative;top:-50px">
      <el-table-column
          fixed
          prop="orderNo"
          label="订单编号"
          width="320">
      </el-table-column>
      <el-table-column
          prop="good.goodName"
          label="物品名称"
          width="200">
      </el-table-column>
      <el-table-column
          prop="Type"
          label="物品类型"
          width="200">
        <template slot-scope="scope">
          {{scope.row.good.goodTypeId == 1 ?'原料' : (scope.row.good.goodTypeId == 2 ?'产品' : '设备')}}
        </template>
      </el-table-column>

      <el-table-column
          prop="num"
          label="数量"
          width="200">
      </el-table-column>
      <el-table-column
          prop="warehouse.name"
          label="仓库名称"
          width="200">
      </el-table-column>
      <el-table-column
          prop="container.name"
          label="库柜名称"
          width="200">
      </el-table-column>
      <el-table-column
          prop="vehicle.vehicleNo"
          label="运输车牌号"
          width="200">
      </el-table-column>
      <el-table-column
          prop="outdate"
          label="出库时间"
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
      date: null,
      visibleDialog: false,
      tableData: [],
      currentPage: 1,
      pageSize: 7,
      total: null,
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
      formData: {
        orderNo: undefined,
        goodId: undefined,
        goodTypeName: undefined,
        num: undefined,
        warehouseId: undefined,
        containerId: undefined,
        vehicleNo: undefined,
        outdate: null,
      },
      ruleForm: {
        orderNo: '',
        goodName: '',
        fromDate: null,
        toDate: null,
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
          trigger: 'change'
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
        vehicleNo: [{
          required: true,
          message: '请选择运输车牌号',
          trigger: 'change'
        }],
        outdate: [{
          required: true,
          message: '请选择时间',
          trigger: 'change'
        }],
      },
      goodOptions: [],
      warehouseNameOptions: [],
      ContainerNameOptions: [],
    }
  },
  methods: {
    changeGood() {
      for (let i = 0; i < this.goodOptions.length; i++) {
        if (this.goodOptions[i].goodId == this.formData.goodId) {
          this.formData.goodTypeId = this.goodOptions[i].goodTypeId;
        }
      }
      if (this.formData.goodTypeId == 1) {
        this.formData.goodTypeName = '原料'
      } else if (this.formData.goodTypeId == 2) {
        this.formData.goodTypeName = '产品'
      } else {
        this.formData.goodTypeName = '设备'
      }
    },
    changeWarehouse() {
      axios.get('http://localhost:8181/warehouse/container/' + this.formData.warehouseId)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.ContainerNameOptions = data.data.containerVos;
            } else {
              this.ContainerNameOptions = [];
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    onOpen() {
    },
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    close() {
      this.$emit('update:visible', false)
    },
    regist() {
      axios.get('http://localhost:8181/good/findAll')
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.goodOptions = data.data;
            } else {
              this.goodOptions = [];
            }
          })
          .catch(error => {
            this.$message.error(error.message)
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
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.$confirm('登记后无法删除,只允许更改, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.formData.outdate = new Date(this.formData.outdate).Format('yyyy-MM-dd');
          console.log(this.formData)
          axios.post('http://localhost:8181/outStorage', this.formData)
              .then(resp => {
                let data = resp.data;
                let code = data.code;
                if (code == 200) {
                  this.$message.success(data.message);
                  this.page();
                } else {
                  this.$message.error(data.message);
                }
                this.visibleDialog = false;
              })
              .catch(error => {
                this.$message.error(error.message);
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
    page() {
      if (this.ruleForm.orderNo == '' && this.ruleForm.GoodName == '' && this.ruleForm.fromDate == null && this.ruleForm.toDate) {
        this.initTable()
      } else {
        this.submitForm(this.submitForm(this.currentPage));
      }
    },
    initTable() {
      axios.get('http://localhost:8181/outStorage/finish/' + this.currentPage)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            // console.log(data)
            if (code == 200) {
              this.total = data.data.total;
              this.tableData = data.data.data;
              console.log(this.tableData)
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].outdate = new Date(this.tableData[i].outdate).Format('yyyy-MM-dd hh:mm:ss');
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
    submitForm(pageNum) {
      this.ruleForm.pageNum = pageNum;
      if (this.date != null) {
        this.ruleForm.fromDate = new Date(this.date[0]).Format('yyyy-MM-dd')
        this.ruleForm.toDate = new Date(this.date[1]).Format('yyyy-MM-dd')
      }
      console.log(this.ruleForm.fromDate);
      console.log(this.ruleForm)
      let form = {
        orderNo: this.ruleForm.orderNo,
        goodName: this.ruleForm.goodName,
        fromDate: this.ruleForm.fromDate,
        toDate: this.ruleForm.toDate,
        pageNum: this.ruleForm.pageNum,
        size: 7
      }
      console.log(form)
      axios.get('http://localhost:8181/outStorage/query2', {params: form})
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.total = data.data.total;
              this.tableData = data.data.data;
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].outdate = new Date(this.tableData[i].outdate).Format('yyyy-MM-dd hh:mm:ss');
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
      this.ruleForm.fromDate = null;
      this.ruleForm.toDate = null;
    },
  },
  created() {
    this.initTable();
  }
}
</script>