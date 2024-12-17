
<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
    <el-form style="margin-left: -20px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="订单编号:" prop="key">
        <el-input v-model="ruleForm.orderNo" placeholder="请输入关键字" style="width: 160px;"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="仓库名称:">
          <el-input v-model="ruleForm.warehouseName" placeholder="请输入关键字" style="width: 160px;"></el-input>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;" @click="submitForm(1)">搜索</el-button>
        </el-form-item>
      </div>
      <el-button style="position: relative;top: -50px;left: 20px" size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
    </el-form>

    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          fixed
          prop="warehouse.name"
          label="仓库名称"
          width="230">
      </el-table-column>
      <el-table-column
          prop="warehouse.address"
          label="入库地点"
          width="170">
      </el-table-column>
      <el-table-column
          prop="orderNo"
          label="订单号"
          width="310">
      </el-table-column>
      <el-table-column
          prop="warehouse.admin"
          label="仓库负责人"
          width="130">
      </el-table-column>
      <el-table-column
          prop="warehouse.phone"
          label="联系电话"
          width="130">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="danger"
              @click="detail(scope.row.orderNo)">入库清单</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="入库清单" :visible.sync="dialogTableVisible">
      <el-statistic group-separator="," :precision="0" :value="totalNum" :title="title" style="position: relative;top: -30px"></el-statistic>
        <el-table :data="gridData" height="250" border style="width: 100%">
          <el-table-column prop="goodName" label="货物名称" width="180"></el-table-column>
          <el-table-column prop="type" label="货物类型" width="180">{{typeName}}</el-table-column>
          <el-table-column prop="num" label="数量"></el-table-column>
          <el-table-column prop="supplier.name" label="供应商"></el-table-column>
        </el-table>
    </el-dialog>
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
      typeName:'',
      totalNum:0,
      title:'总数',
      dialogTableVisible:false,
      tableData: [],
      gridData:[],
      currentPage: 1,
      pageSize: 7,
      total: null,
      key: '',
      value: '',
      ruleForm: {
        orderNo:'',
        warehouseName:'',
        pageNum: '',
        size: 7
      },
      rules: {

      }
    }
  },
  methods:{
    detail(orderNo){
      axios.get('http://localhost:8181/inStorage/detail/'+orderNo)
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            console.log(data)
            if (code == 200){
              this.gridData = data.data.goods
              console.log(this.gridData)
              if (this.gridData[0].goodTypeId == 1){
                this.typeName = '原料'
              }else {
                this.typeName = '设备'
              }
              this.totalNum = 0;
              for (let i = 0; i < this.gridData.length; i++) {
                this.totalNum  +=  this.gridData[i].num;
              }
            }else {
              this.message.error(data.message)
              this.gridData = [];
            }
          })
          .catch(error=>{
                this.$message.error(error.message)
          })
      this.dialogTableVisible = true;
    },
    initTable(){
      axios.get('http://localhost:8181/inStorage/'+this.currentPage)
            .then(resp=>{
              let data = resp.data;
              let code = data.code;
              if (code == 200){
                this.tableData = data.data.data;
                for (let i = 0; i < this.tableData.length; i++) {
                  if (this.tableData[i].warehouse == null){
                    this.tableData[i].warehouse = {
                      name:'设备仓库未指定,入库登记时指定'
                    }
                  }
                }
                this.total = data.data.total;
              }else {
                this.message.error(data.message)
                this.tableData = []
              }
            })
            .catch(error=>{
              this.$message.error(error.methods)
            })
    },
    submitForm(pageNum){
      this.ruleForm.pageNum = pageNum;
      console.log(this.ruleForm)
      axios.get('http://localhost:8181/inStorage/query',{params:this.ruleForm})
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            if (code == 200){
              this.tableData = data.data.data;
              this.total = data.data.total;
              for (let i = 0; i < this.tableData.length; i++) {
                if (this.tableData[i].warehouse == null){
                  this.tableData[i].warehouse = {
                    name:'设备仓库未指定,入库登记时指定'
                  }
                }
              }
            }else {
              this.message.error(data.message)
              this.tableData = []
            }
          })
          .catch(error=>{
            this.$message.error(error.methods)
          })
    },
    page(){
      if (this.ruleForm.orderNo == '' && this.ruleForm.warehouseName == ''){
        this.initTable();
      }else{
        this.submitForm(this.currentPage);
      }
    },
  },

  created() {
    this.initTable();
  },

}
</script>