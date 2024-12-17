<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="仓库名称:" prop="key">
        <el-input v-model="ruleForm.warehouseName" placeholder="请输入关键字" style="width: 160px;"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="设备名称:">
          <el-input v-model="ruleForm.goodName" placeholder="请输入关键字" style="width: 160px;"></el-input>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;" @click="submitForm(1)">搜索</el-button>
        </el-form-item>
      </div>
      <el-button style="position: relative;top: -50px;left: 40px" size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
    </el-form>

    <el-table
        :data="tableData"
        stripe
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          fixed
          prop="warehouse.name"
          label="仓库名称"
          width="205">
      </el-table-column>
      <el-table-column
          prop="goodRawVo.goodName"
          label="设备名称"
          width="205">
      </el-table-column>
      <el-table-column
          prop="goodRawVo.supplier.name"
          label="供应商"
          width="200">
      </el-table-column>
      <el-table-column
          prop="goodRawVo.supplier.linkman"
          label="联系人"
          width="200">
      </el-table-column>
      <el-table-column
          prop="goodRawVo.supplier.name"
          label="联系电话"
          width="200">
      </el-table-column>
      <el-table-column
          prop="num"
          label="数量"
          width="185">
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
      tableData: null,
      currentPage: 1,
      pageSize: 7,
      total: null,
      key: '',
      value: '',
      ruleForm: {
        goodName:'',
        warehouseName:'',
        pageNum: '',
        size: 7
      },
      rules: {
      }
    }
  },
  methods:{
    page(){
        if (this.ruleForm.goodName == '' && this.ruleForm.warehouseName == ''){
          this.initTable();
        }else{
          this.submitForm(this.currentPage);
        }
    },
    submitForm(pageNum){
      this.ruleForm.pageNum = pageNum;

      axios.get('http://localhost:8181/good/equip/warehouse',{params:this.ruleForm})
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
            this.$message.error(error.message)
          })
    },
    initTable(){
      axios.get('http://localhost:8181/good/equip/warehouse/' + this.currentPage)
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
              this.$message.error(error.message)
            })
    },
  },
  created() {
    this.initTable();
  }
}
</script>