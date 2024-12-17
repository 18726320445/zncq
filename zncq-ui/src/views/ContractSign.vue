<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -30px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="合同编号" prop="key">
        <el-input v-model="ruleForm.contractNo" placeholder="请输入关键字" style="width: 160px;"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="企业名称:">
          <el-input v-model="ruleForm.customerName" placeholder="请输入关键字" style="width: 160px;"></el-input>
        </el-form-item>
        <el-form-item label="创建时间:" style="position: relative;top: -63px;left: 250px">
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
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 360px;top: -41px"
                     @click="submitForm(1)">搜索
          </el-button>
        </el-form-item>
      </div>
      <el-button style="position: relative;top: -60px;left: -70px" size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
    </el-form>

    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%;position: relative;top:-70px">
      <el-table-column
          fixed
          prop="contractNo"
          label="合同编号"
          width="130">
      </el-table-column>
      <el-table-column
          prop="createdate"
          label="创建时间"
          width="200">
      </el-table-column>
      <el-table-column
          prop="signdate"
          label="签署时间"
          width="200">
      </el-table-column>
      <el-table-column
          prop="customer.customerName"
          label="客户企业名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="customer.linkman"
          label="联系人"
          width="130">
      </el-table-column>
      <el-table-column
          prop="customer.phone"
          label="联系电话"
          width="130">
      </el-table-column>
      <el-table-column
          prop="processName"
          label="签署人"
          width="130">
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-dialog append-to-body title="采购清单" :visible.sync="visibleDialog2">
            <div style="position: relative;left: 300px;">
              <el-statistic group-separator="," :precision="2" :value="moneyTotal" :title="title"></el-statistic>
            </div>
            <el-table :data="gridData">
              <el-table-column property="goodName" label="货物名称" width="150"></el-table-column>
              <el-table-column property="goodTypeId" label="货物类型" width="150">{{ typeName }}</el-table-column>
              <el-table-column property="price" label="单价" width="100"></el-table-column>
              <el-table-column property="num" label="数量" width="100"></el-table-column>
              <el-table-column property="supplier.name" label="供应商" width="150"></el-table-column>
            </el-table>

          </el-dialog>
          <el-button
              size="mini"
              type="warning"
              @click="detail(scope.row.contractNo,scope.row.total,scope.row.contractType)">合同清单
          </el-button>
          <el-button
              size="mini"
              type="success"
              :disabled="scope.row.state != 1"
              @click="edit(scope.row)">签署
          </el-button>
          <el-button
              size="mini"
              type="danger"
              :disabled="scope.row.state != 1"
              @click="del(scope.row)">拒签
          </el-button>
        </template>
      </el-table-column>
      <el-dialog append-to-body title="运输选择" :visible.sync="dialogFormVisible">
        <el-form ref="elForm" :model="formData">
          <el-form-item label="运输选择" prop="vehicleId">
            <el-select v-model="formData.vehicleId" placeholder="请选择运输车辆" clearable
                       :style="{width: '100%'}">
              <el-option v-for="(item, index) in vehicleOptions" :key="index" :label="item.name"
                         :value="item.id" :disabled="item.disabled"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="handleConfirm">确 定</el-button>
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
      date:null,
      typeName: '',
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      dialogFormVisible: false,
      visibleDialog2: false,
      /*对话表单字段*/
      formData: {
        processName:'',
        contractNo: '',
        vehicleId: '',
      },
      gridData: [],
      moneyTotal: 111,
      title: "总金额",
      tableData: [],
      currentPage: 1,
      pageSize: 7,
      total: null,
      ruleForm: {
        contractNo: '',
        customerName: '',
        fromDate: null,
        toDate:null,
        pageNum: '',
        size: 7
      },
      rules: {},
      vehicleOptions: [],
    }
  },
  methods: {
    onOpen() {
    },
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    close() {
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.$confirm('你确定要签署吗，签署后无法更改与删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.formData.processName = window.localStorage.getItem('username');
          axios.put('http://localhost:8181/contract/good/agree', this.formData)
              .then(resp => {
                let data = resp.data;
                let code = data.code;
                if (code == 200) {
                  this.$message.success(data.message);
                  this.page();
                } else {
                  this.$message.error(data.message)
                }
                this.dialogFormVisible = false;
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
    page() {
      if (this.ruleForm.contractNo == '' && this.ruleForm.date == null && this.ruleForm.customerName == '') {
        this.initTable();
      }else{
        this.submitForm(this.currentPage);
      }
    },
    edit(row) {
      this.formData.contractNo = row.contractNo;
      let type = row.contractType;
      if (type == 0) {
        this.$confirm('你确定要签署吗，签署后无法更改与删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.formData.processName = window.localStorage.getItem('username');
          console.log(this.formData)
          axios.put('http://localhost:8181/contract/equip/agree',this.formData)
              .then(resp => {
                let data = resp.data;
                let code = data.code;
                if (code == 200) {
                  this.$message.success(data.message);
                } else {
                  this.$message.error(data.message)
                }
                this.page();
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
      } else {
        axios.get('http://localhost:8181/vehicle?maxNum=' + row.num)
            .then(resp => {
              let data = resp.data;
              let code = data.code;
              if (code == 200) {
                this.vehicleOptions = data.data;
                this.dialogFormVisible = true;
              } else {
                this.$message.error(data.data);
              }
            })
            .catch(error => {
              this.$message.error(error.data);
            })
      }
    },
    del(row) {
      this.$confirm('你确定拒签吗, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.formData.contractNo = row.contractNo;
        this.formData.processName = window.localStorage.getItem('username');
        axios.delete('http://localhost:8181/contract/pass',{data:this.formData})
            .then(resp => {
              let data = resp.data;
              let code = data.code;
              if (code == 200){
                this.$message.success(data.message);
              }else {
                this.$message.error(data.message);
              }
              this.page();
            })
            .catch(eor => {
              this.$message.error(eor.message);
            })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    detail(contractNo, total, contractType) {
      if (contractType == 0) {
        this.typeName = '设备';
      } else {
        this.typeName = '产品';
      }
      this.moneyTotal = total;
      axios.get('http://localhost:8181/contract/query/' + contractNo)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.gridData = data.data.goods;
              console.log(this.gridData)
            } else {
              this.gridData = [];
            }
            this.visibleDialog2 = true;
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    initTable() {
      axios.get('http://localhost:8181/contract/state/' + this.currentPage)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.total = data.data.total;
              this.tableData = data.data.data;
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].createdate = new Date(this.tableData[i].createdate).Format('yyyy-MM-dd hh:mm:ss')
                if (this.tableData[i].signdate != null){
                  this.tableData[i].signdate = new Date(this.tableData[i].signdate).Format('yyyy-MM-dd hh:mm:ss')
                }
              }
              console.log(this.tableData);
              this.$message.success(data.message)
            } else {
              this.total = 0;
              this.tableData = [];
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    submitForm(pageNum){
      this.ruleForm.pageNum = pageNum;
      if (this.date != null){
        this.ruleForm.fromDate = new Date(this.date[0]).Format('yyyy-MM-dd');
        this.ruleForm.toDate = new Date(this.date[1]).Format('yyyy-MM-dd');
      }
      axios.get('http://localhost:8181/contract/state/query',{params:{
          contractNo: this.ruleForm.contractNo,
          customerName: this.ruleForm.customerName,
          fromDate: this.ruleForm.fromDate,
          toDate:this.ruleForm.toDate,
          pageNum: this.ruleForm.pageNum,
          size: 7
        }})
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.total = data.data.total;
              this.tableData = data.data.data;
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].createdate = new Date(this.tableData[i].createdate).Format('yyyy-MM-dd hh:mm:ss')
                if (this.tableData[i].signdate != null){
                  this.tableData[i].signdate = new Date(this.tableData[i].signdate).Format('yyyy-MM-dd hh:mm:ss')
                }
              }
              this.$message.success(data.message)
            } else {
              this.total = 0;
              this.tableData = [];
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
      this.ruleForm.fromDate = null;
      this.ruleForm.toDate = null;
    }
  },
  created() {
    this.initTable();
  }
}
</script>