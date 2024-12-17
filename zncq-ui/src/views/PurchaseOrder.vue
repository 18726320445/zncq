<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="采购编号:" prop="purchaseNo" style="position: relative;left: 10px">
        <el-input v-model="ruleForm.purchaseNo" placeholder="请输入关键字" style="width: 160px"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="时间范围:">
          <el-date-picker
              v-model="date"
              unlink-panels
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
          </el-date-picker>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 380px;top: -42px"
                     @click="submitForm(1)">搜索
          </el-button>
        </el-form-item>
        <el-button style="position: relative;top: -50px;left: -230px" size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
      </div>

    </el-form>

    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          fixed
          prop="purchaseNo"
          label="采购编号"
          width="130">
      </el-table-column>
<!--      <el-table-column-->
<!--          fixed-->
<!--          prop="orderNo"-->
<!--          label="订单编号"-->
<!--          width="310">-->
<!--      </el-table-column>-->
      <el-table-column
          prop="createName"
          label="采购申请人"
          width="130">
      </el-table-column>
      <el-table-column
          prop="num"
          label="数量"
          width="130">
      </el-table-column>
      <el-table-column
          prop="createdate"
          label="采购时间"
          width="160">
      </el-table-column>
      <el-table-column
          prop="total"
          label="总价"
          width="130">
      </el-table-column>
      <el-table-column
          prop="processName"
          label="审核人"
          width="130">
      </el-table-column>
      <el-table-column
          prop="state"
          label="采购状态"
          width="130">
        <template slot-scope="scope">
          <el-tag :type="scope.row.state == '审核通过' ? 'success' : (scope.row.state == '审核未通过' ? 'danger' : 'warning')">{{scope.row.state}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="warning"
              @click="detail(scope.row.purchaseNo)">采购清单
          </el-button>
          <el-button
              size="mini"
              type="success"
              :disabled="scope.row.state != '未审核'"
              @click="agree(scope.row.purchaseNo)">通过
          </el-button>
          <el-button
              size="mini"
              type="danger"
              :disabled="scope.row.state != '未审核'"
              @click="no(scope.row.purchaseNo)">驳回
          </el-button>
        </template>
      </el-table-column>
      <el-dialog  title="采购清单" :visible.sync="visibleDialog2" append-to-body width="70%">
        <div style="position: relative;left: 300px;">
          <el-statistic group-separator="," :precision="2" :value="moneyTotal" :title="title"></el-statistic>
        </div>
        <el-table height="250" :data="gridData">
          <el-table-column property="goodId" label="货物编号" width="150"></el-table-column>
          <el-table-column property="goodName" label="货物名称" width="200"></el-table-column>
          <el-table-column property="goodType" label="货物类型">原料</el-table-column>
          <el-table-column property="price" label="单价"></el-table-column>
          <el-table-column property="num" label="数量"></el-table-column>
          <el-table-column property="supplier.name" label="供应商"></el-table-column>
        </el-table>

      </el-dialog>
      <el-dialog title="仓库选择" :visible.sync="visibleDialog1" width="20%" :close="onClose" append-to-body>
        <el-form  :model="purchaseForm">
          <el-form-item label="请选择" label-width="150">
            <el-select v-model="purchaseForm.warehouseId" placeholder="请选择仓库">
              <el-option v-for="(item, index) in warehouseOptions" :key="index" :label="item.name"
                         :value="item.id" :disabled="item.disabled"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="yes">确 定</el-button>
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
      date: '',
      visibleDialog1: false,
      visibleDialog2: false,
      gridData: [],
      moneyTotal: '',
      title: "总金额",
      tableData: [{}],
      currentPage: 1,
      pageSize: 7,
      total: null,
      key: '',
      value: '',
      purchaseForm:{
        warehouseId:'',
        purchaseNo:'',
      },
      ruleForm: {
        purchaseNo: '',
        fromDate: null,
        toDate: null,
        state:1,
        pageNum: '',
        size: 7
      },
      rules: {

      },
      warehouseOptions:[],
    }
  },
  methods: {
    onOpen() {
    },
    onClose() {
      this.purchaseForm.purchaseNo = '';
      this.purchaseForm.warehouseId = '';
    },
    close() {
      this.$emit('update:visible', false)
    },
    page() {
      if (this.ruleForm.purchaseNo == '' && this.ruleForm.fromDate == null && this.ruleForm.toDate == null){
        this.initTable();
      }else{
        this.submitForm(this.currentPage)
      }
    },
    agree(purchaseNo) {
        this.purchaseForm.purchaseNo = purchaseNo;
        axios.get('http://localhost:8181/warehouse/')
            .then(resp=>{
              let data = resp.data;
              let code = data.code;
              if (code == 200){
                this.warehouseOptions = data.data;
                this.visibleDialog1 = true;
              }else{
                this.warehouseOptions = [];
              }
            })
            .catch(error=>{
              this.$message.error(error.data)
            })
    },
    yes(){
        let user = window.localStorage.getItem("username")
        axios.put('http://localhost:8181/purchase/warehouse/yes/'+this.purchaseForm.purchaseNo+'/'+user+'/'+this.purchaseForm.warehouseId)
            .then(resp=>{
              let data = resp.data;
              let code = data.code;
              if (code == 200){
                this.$message.success(data.message)
              }else{
                this.$message.error(data.message)
              }
              this.visibleDialog1 = false;
              this.page();
            })
            .catch(error=>{
              this.$message.error(error.message)
            })
    },
    no(purchaseNo) {
      this.$confirm('你确定取消该采购订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete('http://localhost:8181/purchase/no/'+purchaseNo)
            .then(resp=>{
              let data = resp.data;
              let code = data.code;
              if (code == 200){
                this.$message.success(data.message)
              }else{
                this.$message.error(data.message)
              }
              this.page();
            })
            .catch(error=>{
              this.$message.error(error.message)
            })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消该操作'
        });
      });
    },
    initTable() {
      axios.get('http://localhost:8181/purchase/state/' + this.currentPage + "/1")
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.$message.success(data.message);
              this.total = data.data.total;
              this.tableData = data.data.data;
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].createdate = new Date(this.tableData[i].createdate).Format('yyyy-MM-dd hh:mm:ss')
                switch (this.tableData[i].state) {
                  case 1:
                    this.tableData[i].state = '未审核';

                    break;
                  case 2:
                    this.tableData[i].state = '审核通过';
                    break;
                  case -1:
                    this.tableData[i].state = '审核未通过';
                    break;
                }
              }
            } else {
              this.tableData = [];
              this.$message.error(data.message)
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    submitForm(pageNum) {
      console.log(this.date)
      this.ruleForm.pageNum = pageNum;
      if (this.date != null){
        this.ruleForm.fromDate = new Date(this.date[0]).Format("yyyy-MM-dd");
        this.ruleForm.toDate = new Date(this.date[1]).Format("yyyy-MM-dd")
      }
      axios.get('http://localhost:8181/purchase/state/query',{params:this.ruleForm})
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.$message.success(data.message);
              this.total = data.data.total;
              this.tableData = data.data.data;
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].createdate = new Date(this.tableData[i].createdate).Format('yyyy-MM-dd hh:mm:ss')
                switch (this.tableData[i].state) {
                  case 1:
                    this.tableData[i].state = '未审核';
                    break;
                  case 2:
                    this.tableData[i].state = '审核通过';
                    break;
                  case 3:
                    this.tableData[i].state = '审核未通过';
                    break;
                }
              }
            } else {
              this.tableData = [];
              this.$message.success(data.message)
            }
            this.ruleForm.fromDate = null;
            this.ruleForm.toDate = null;
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    detail(purchaseNo) {
      console.log(purchaseNo)
      axios.get('http://localhost:8181/purchase/query/' + purchaseNo)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              setTimeout(() => {
                this.moneyTotal = data.data.total;
                this.gridData = data.data.goods;
              }, 100)
            } else {
              this.moneyTotal = 0;
              this.gridData = [];
            }
            this.visibleDialog2 = true;
          })
          .catch(error => {
            this.$message.error(error.message);
          })

    },
  },
  created() {
    this.initTable();
  }
}
</script>
