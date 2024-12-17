<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="用户编号:" prop="userId">
        <el-input v-model="ruleForm.userId" placeholder="请输入关键字" style="width: 160px;"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="ip:">
          <el-input v-model="ruleForm.ip" placeholder="请输入关键字" style="width: 160px;"></el-input>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;"
                     @click="submitForm(1)">搜索
          </el-button>
        </el-form-item>
      </div>
    </el-form>
    <el-table
        :data="tableData"
        stripe
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          prop="id"
          label="流水"
          width="100">
      </el-table-column>
      <el-table-column
          prop="userId"
          label="用户编号"
          width="200">
      </el-table-column>
      <el-table-column
          prop="ip"
          label="ip地址"
          width="200">
      </el-table-column>
      <el-table-column
          prop="detail"
          label="操作描述"
          width="200">
      </el-table-column>
      <el-table-column
          prop="doTime"
          label="调用时间/ms"
          width="200">
      </el-table-column>
      <el-table-column
          prop="resultMessage"
          label="结果信息"
          width="200">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
            <el-button
                size="mini"
                slot="reference"
                type="warning"
                @click="detail(scope.row)"
                plain>详情
            </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-drawer
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose">
      <el-descriptions title="日志详情" direction="vertical" :column="4" border>
        <el-descriptions-item label="用户编号" :span="2">{{log.userId}}</el-descriptions-item>
        <el-descriptions-item label="ip" :span="2">{{log.ip}}</el-descriptions-item>
        <el-descriptions-item label="操作描述" :span="2">{{log.detail}}</el-descriptions-item>
        <el-descriptions-item label="执行接口" :span="2">{{log.type}}</el-descriptions-item>
        <el-descriptions-item label="何时调用" :span="4">{{log.doTime}}</el-descriptions-item>
        <el-descriptions-item label="入参信息" :span="4">{{log.args}}</el-descriptions-item>
        <el-descriptions-item label="处理时间/ms" :span="2">{{log.processTime}}</el-descriptions-item>
        <el-descriptions-item label="结果信息" :span="2">{{log.resultMessage}}</el-descriptions-item>
      </el-descriptions>
    </el-drawer>
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
      drawer: false,
      direction: 'rtl',
      tableData: null,
      currentPage: 1,
      pageSize: 7,
      total: null,
      log:{},
      ruleForm: {
        pageNum:'',
        userId:'',
        ip:'',
      },
      rules: {
      }
    }
  },
  methods:{
    detail(row){
      this.log = row;
      this.drawer = true;
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    page(){
      if (this.ruleForm.userId == '' && this.ruleForm.ip == ''){
        this.initTable();
      }else{
        this.submitForm(this.currentPage);
      }
    },
    submitForm(pageNum){
      this.ruleForm.pageNum = pageNum;
      axios.get('http://localhost:8181/user/log',{params:this.ruleForm})
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            if (code == 200){
              this.tableData = data.data.data;
              this.total = data.data.total;
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].doTime = new Date(this.tableData[i].doTime).Format('yyyy-MM-dd hh:mm:ss')
              }
            }else{
              this.tableData = [];
              this.total = 0;
            }
          })
          .catch(error=>{
            this.$message.error(error.message)
          })
    },
    initTable(){
      axios.get('http://localhost:8181/user/log/'+this.currentPage)
            .then(resp=>{
              let data = resp.data;
              let code = data.code;
              if (code == 200){
                this.tableData = data.data.data;
                this.total = data.data.total;
                for (let i = 0; i < this.tableData.length; i++) {
                  this.tableData[i].doTime = new Date(this.tableData[i].doTime).Format('yyyy-MM-dd hh:mm:ss')
                }
              }else{
                this.tableData = [];
                this.total = 0;
              }
            })
            .catch(error=>{
              this.$message.error(error.message)
            })
    },
  },
  created() {
      this.initTable();
  }
}
</script>