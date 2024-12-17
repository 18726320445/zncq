<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="合同编号" prop="key">
        <el-input v-model="ruleForm.contractNo" placeholder="请输入关键字" style="width: 160px;"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="合同名称:">
          <el-input v-model="ruleForm.contractName" placeholder="请输入关键字" style="width: 160px;"></el-input>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;"
                     @click="submitForm(1)">搜索
          </el-button>
        </el-form-item>
        <div style="position: relative ;left: -230px">
          <el-button
              size="mini"
              type="success"
              @click="add()">拟定合同
          </el-button>
          <el-button size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
          <el-dialog :visible.sync="visibleDialog1" v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose"
                     title="创建合同">
            <el-row :gutter="15">
              <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
                <el-col :span="14">
                  <el-form-item label="合同名称" prop="contractName">
                    <el-input v-model="formData.contractName" placeholder="请输入合同名称" clearable
                              prefix-icon='el-icon-s-order' :style="{width: '100%'}"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="14">
                  <el-form-item label="创建者" prop="createName">
                    <el-input v-model="formData.createName" placeholder="请输入创建者" clearable
                              :style="{width: '100%'}"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="14">
                  <el-form-item label="客户名称" prop="customerId">
                    <el-select v-model="formData.customerId" placeholder="请选择企业名称" clearable
                               :style="{width: '100%'}">
                      <el-option v-for="(item, index) in customerOptions" :key="index" :label="item.customerName"
                                 :value="item.id" :disabled="item.disabled"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="11">
                  <el-form-item label="合同类型" prop="contractType">
                    <el-select v-model="formData.contractType" placeholder="请选择合同类型" clearable
                               :style="{width: '100%'}">
                      <el-option v-for="(item, index) in contractTypeOptions" :key="index" :label="item.name"
                                 :value="item.id" :disabled="item.disabled"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="22">
                  <el-form-item label="合同简介" prop="introduction">
                    <el-input v-model="formData.introduction" type="textarea" placeholder="请输入合同简介"
                              :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
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
        stripe
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          fixed
          prop="contractNo"
          label="合同编号"
          width="160">
      </el-table-column>
      <el-table-column
          prop="contractName"
          label="合同名称"
          width="160">
      </el-table-column>
      <el-table-column
          prop="customer.customerName"
          label="客户企业名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="customer.linkman"
          label="联系人"
          width="100">
      </el-table-column>
      <el-table-column
          prop="customer.phone"
          label="联系电话"
          width="130">
      </el-table-column>
      <el-table-column
          prop="createdate"
          label="创建时间"
          width="200">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="danger"
              :disabled="scope.row.state != 0"
              @click="edit(scope.row.contractType,scope.row.contractNo)">编辑订单
          </el-button>
          <el-button
              size="mini"
              type="warning"
              @click="detail(scope.row)">状态
          </el-button>
          <el-button
              size="mini"
              type="primary"
              :disabled="scope.row.state != 0"
              @click="commit(scope.row.contractNo)">提交
          </el-button>
        </template>
        <el-dialog append-to-body title="合同清单" :visible.sync="visibleDialog2" width="70%" @close="onClose1">
          <div style="position: relative ;left:0">
            <el-button
                size="mini"
                type="success"
                @click="addGood()">添加
            </el-button>
          </div>
          <el-table height="250"  :data="gridData">
            <el-table-column property="goodName" label="货物名称" width="130"></el-table-column>
            <el-table-column property="goodTypeId" label="货物类型" width="130">{{goodTypeName}}</el-table-column>
            <el-table-column property="price" label="单价" width="130"></el-table-column>
            <el-table-column property="num" label="数量" width="130"></el-table-column>
            <el-table-column property="supplier.name" label="供应商"></el-table-column>
            <el-table-column>
              <template slot-scope="scope">
                <el-button
                    size="mini"
                    type="primary"
                    @click="edit2(scope.row)">编辑
                </el-button>
                <el-button
                    size="mini"
                    type="danger"
                    @click="del(scope.row.goodId)">删除
                </el-button>
                <el-button
                    size="mini"
                    type="warning"
                    @click="detail2(scope.row)">详情
                </el-button>
              </template>
              <el-drawer append-to-body
                         title="货物详情"
                         :visible.sync="drawer"
                         :direction="direction"
                         :before-close="handleClose">
                <el-descriptions title="" direction="vertical" :column="2" border>
                  <el-descriptions-item label="货物名称">{{ goodDetail.goodName }}</el-descriptions-item>
                  <el-descriptions-item label="货物类型">{{goodTypeName}}</el-descriptions-item>
                  <el-descriptions-item label="描述" :span="2">{{ goodDetail.detail }}</el-descriptions-item>
                  <el-descriptions-item label="单价">{{ goodDetail.price }}</el-descriptions-item>
                  <el-descriptions-item label="供应商">
                    <el-tag size="small">{{ goodDetail.supplier.name}}</el-tag>
                  </el-descriptions-item>
                  <el-descriptions-item label="地址">{{ goodDetail.supplier.address }}</el-descriptions-item>
                  <el-descriptions-item label="联系人">{{ goodDetail.supplier.linkMan }}</el-descriptions-item>
                  <el-descriptions-item label="电话">{{ goodDetail.supplier.phone }}</el-descriptions-item>
                  <el-descriptions-item label="邮箱">{{ goodDetail.supplier.email }}</el-descriptions-item>
                  <el-descriptions-item label="备注" :span="2">{{ goodDetail.supplier.remarks }}</el-descriptions-item>
                </el-descriptions>
              </el-drawer>
            </el-table-column>
          </el-table>
        </el-dialog>
      </el-table-column>
      <el-drawer
          append-to-body
          title="合同状态详情"
          :visible.sync="stateDrawer"
          :direction="direction"
          :before-close="handleClose">
        <el-descriptions title="" direction="vertical" :column="2" border>
          <el-descriptions-item label="订单号">{{ contractDetail.orderNo }}</el-descriptions-item>
          <el-descriptions-item label="合同编号">{{ contractDetail.contractNo }}</el-descriptions-item>
          <el-descriptions-item label="合同名称" :span="2">{{ contractDetail.contractName }}</el-descriptions-item>
          <el-descriptions-item label="客户企业名称">
            <el-tag size="medium"> {{ contractDetail.customer.customerName }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="地址">{{ contractDetail.customer.address }}</el-descriptions-item>
          <el-descriptions-item label="联系人">{{ contractDetail.customer.linkman }}</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{ contractDetail.customer.phone }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ contractDetail.createdate }}</el-descriptions-item>
          <el-descriptions-item label="签署时间">{{ contractDetail.signdate }}</el-descriptions-item>
        </el-descriptions>
        <div style="position: relative;top: 100px;left: 25px">
          <el-steps :space="200" :active="contractDetail.state" finish-status="success">
            <el-step title="未签署"></el-step>
            <el-step title="已签署"></el-step>
            <el-step title="交付完成"></el-step>
          </el-steps>
          '
        </div>
      </el-drawer>
      <el-dialog append-to-body :visible.sync="visibleDialog3" v-bind="$attrs" v-on="$listeners"
                 @open="onOpen" @close="onClose3" title="修改信息">
        <el-row :gutter="15">
          <el-form ref="elForm2" :model="formData2" :rules="rules" size="medium" label-width="100px">
            <el-col :span="14">
              <el-form-item label="货物名称" prop="goodId">
                <el-select :disabled="flag" v-model="formData2.goodId" placeholder="请选择货物名称" clearable :style="{width: '100%'}">
                  <el-option v-for="(item, index) in goodOptions" :key="index" :label="item.goodName"
                             :value="item.goodId" :disabled="item.disabled"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="14">
              <el-form-item label="数量" prop="num">
                <el-input-number v-model="formData2.num" placeholder="数量"></el-input-number>
              </el-form-item>
            </el-col>
          </el-form>
        </el-row>
        <div slot="footer">
          <el-button @click="close">取消</el-button>
          <el-button type="primary" @click="addGoodHandleConfirm">确定</el-button>
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
      size: 'medium',
      flag:false,
      drawer: false,
      stateDrawer: false,
      direction: 'rtl',
      visibleDialog1: false,
      visibleDialog2: false,
      visibleDialog3: false,
      visibleDialog4: false,
      goodTypeName:'',
      contractDetail: {
        customer: {}
      },
      goodDetail: {
        supplier:{}
      },
      formData: {
        customerId: undefined,
        contractName: undefined,
        contractType: undefined,
        createName: undefined,
        introduction: undefined,
      },
      formData2: {
        contractType: undefined,
        contractNo: undefined,
        goodId: undefined,
        num: undefined,
      },
      gridData: [],
      tableData: [{}],
      currentPage: 1,
      pageSize: 7,
      total: null,
      key: '',
      value: '',
      ruleForm: {
        contractNo: '',
        contractName: '',
        pageNum: '',
        size: 7
      },
      rules: {
        contractName: [{
          required: true,
          message: '请输入合同名称',
          trigger: 'blur'
        }],
        createName: [{
          required: true,
          message: '请输入创建者',
          trigger: 'blur'
        }],
        customerId: [{
          required: true,
          message: '请选择企业名称',
          trigger: 'change'
        }],
        contractType: [{
          required: true,
          message: '请选择合同类型',
          trigger: 'change'
        }],
        introduction: [{
          required: true,
          message: '请输入合同简介',
          trigger: 'blur'
        }],
        goodId: [{
          required: true,
          message: '请选择货物名称',
          trigger: 'change'
        }],
        num: [{
          required: true,
          message: '数量',
          trigger: 'blur'
        }],
      },
      customerOptions: [],
      contractTypeOptions: [{
        "name": "设备购入合同",
        "id": 0
      }, {
        "name": "产品出售合同",
        "id": 1
      }],
      goodOptions: [],
    }
  },
  methods: {
    commit(contractNo){
      this.$confirm('提交后将无法修改合约内容确定提交吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.put('http://localhost:8181/contract/commit/'+contractNo)
              .then(resp=>{
                let data = resp.data;
                let code = data.code;
                if (code == 200){
                  this.$message.success(data.message);
                  this.page();
                }else{
                  this.$message.error(data.message)
                }
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
    },
    add() {
      axios.get('http://localhost:8181/customer')
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.customerOptions = data.data;
            } else {
              this.customerOptions = [];
            }
            this.visibleDialog1 = true;
          })
          .catch(error => {
            this.$message.error(error.message);
          })
    },
    submitForm(pageNum) {
      this.ruleForm.pageNum = pageNum;
      axios.get('http://localhost:8181/contract/query',{params:this.ruleForm})
            .then(resp=>{
              let data = resp.data;
              let code = data.code;
              if (code == 200) {
                this.total = data.data.total;
                this.tableData = data.data.data;
                for (let i = 0; i < this.tableData.length; i++) {
                  this.tableData[i].createdate = new Date(this.tableData[i].createdate).Format('yyyy-MM-dd hh:mm:ss')
                }
                console.log(this.tableData);
                this.$message.success(data.message)
              } else {
                this.total = 0;
                this.tableData = [];
              }
            })
            .catch(error=>{
                this.$message.error(error.message)
            })
    },
    page() {
      if (this.ruleForm.contractNo == '' && this.ruleForm.contractName == '') {
        this.initTable();
      } else {
        this.submitForm(this.currentPage)
      }
    },
    detail(row) {
      this.contractDetail = row;
      this.stateDrawer = true;
    },
    detail2(row){
      this.goodDetail = row;
      this.drawer = true;
    },
    del(goodId){
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.formData2.goodId = goodId;
        axios.delete('http://localhost:8181/contract/good',{data:this.formData2})
            .then(resp => {
              let data = resp.data;
              let code = data.code;
              if (code == 200) {
                this.$message.success(data.message)
                this.edit(this.formData2.contractType,this.formData2.contractNo)
              } else {
                this.$message.error(data.message)
              }
              this.visibleDialog3 = false;
              this.formData2.goodId = '';
              this.formData2.num = '';
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
    },
    edit(contractType,contractNo) {
      console.log(contractType)
      console.log(contractNo)
      if (contractType == 0){
        this.goodTypeName = '设备'
      }else{
        this.goodTypeName = '产品'
      }
      this.formData2.contractType = contractType;
      this.formData2.contractNo = contractNo;
      axios.get('http://localhost:8181/contract/query/' + contractNo)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              setTimeout(()=>{this.gridData = data.data.goods;},10);
            } else {
              this.gridData = [];
              this.$message.error(data.message)
            }
            this.visibleDialog2 = true;
          })
          .catch(error => {
            this.$message.error(error.message);
          })

    },
    edit2(row) {
      this.formData2.goodId = row.goodId
      this.formData2.num = row.num;
      this.goodOptions = [{
        goodName:row.goodName,
        goodId:row.goodId,
      }]
      this.flag = true;
      this.visibleDialog3 = true;
    },
    onOpen() {
    },
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    onClose1() {
      this.gridData = [];
    },
    onClose3() {
      this.$refs['elForm2'].resetFields()
      this.formData2.goodId = undefined;
      this.formData2.num = '';
      console.log(this.formData2.goodId)
    },
    close() {
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        console.log(this.formData)
        axios.post('http://localhost:8181/contract', this.formData)
            .then(resp => {
              let data = resp.data;
              let code = data.code;
              if (code == 200) {
                this.$message.success(data.message)
                this.page();
              } else {
                this.$message.error(data.message)
              }
              this.visibleDialog1 = false;
              this.$refs['elForm'].resetFields()
            })
            .catch(error => {
              this.$message.error(error.message)
            })
        this.close()
      })
    },
    addGoodHandleConfirm() {
      this.$refs['elForm2'].validate(valid => {
        if (!valid) return
        console.log(this.formData2.goodId)
        if (!this.flag){
          console.log(this.formData2)
          axios.post('http://localhost:8181/contract/good', this.formData2)
              .then(resp => {
                let data = resp.data;
                let code = data.code;
                if (code == 200) {
                  this.$message.success(data.message)
                  this.edit(this.formData2.contractType,this.formData2.contractNo)
                } else {
                  this.$message.error(data.message)
                }
                this.visibleDialog3 = false;
                this.formData2.goodId = '';
                this.formData2.num = '';
              })
              .catch(error => {
                this.$message.error(error.message)
              })
        }else{
          axios.put('http://localhost:8181/contract/good', this.formData2)
                .then(resp => {
                let data = resp.data;
                let code = data.code;
                if (code == 200) {
                  this.$message.success(data.message)
                  this.edit(this.formData2.contractType,this.formData2.contractNo)
                } else {
                  this.$message.error(data.message)
                }
                this.visibleDialog3 = false;

              })
              .catch(error => {
                this.$message.error(error.message)
              })
        }
        this.close()
      })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    },
    addGood() {
      this.flag = false;
      let url = 'http://localhost:8181/good/type?contractType=' + this.formData2.contractType
      for (let i = 0; i < this.gridData.length; i++) {
        url = url + '&ids=' + this.gridData[i].goodId
      }
      console.log(url)
      axios.get(url)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.$message.success(data.message)
              this.goodOptions = data.data;
            } else {
              this.$message.error(data.message)
              this.goodOptions = []
            }
            this.visibleDialog3 = true;
          })
          .catch(error => {
            this.$message.error(error.message)
          })

    },
    initTable() {
      axios.get('http://localhost:8181/contract/' + this.currentPage)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.total = data.data.total;
              this.tableData = data.data.data;
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].createdate = new Date(this.tableData[i].createdate).Format('yyyy-MM-dd hh:mm:ss')
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
  },
  created() {
    this.initTable()
  }
}
</script>