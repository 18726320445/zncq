
<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="出库名称:" prop="key">
        <el-input v-model="ruleForm.warehouseName" placeholder="请输入关键字" style="width: 160px;"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="货物名称:">
          <el-input v-model="ruleForm.goodName" placeholder="请输入关键字" style="width: 160px;"></el-input>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;" @click="submitForm('1')">搜索</el-button>
        </el-form-item>
        <el-button
            style="position: relative;left: -230px"
            size="mini"
            type="success"
            plain
            @click="add()">创建消耗</el-button>
        <el-button style="position:relative;left: -230px" size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
      </div>

    </el-form>

    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%;position: relative;top:-30px">
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
          prop="good.goodName"
          label="物品名称"
          width="200">
      </el-table-column>
      <el-table-column
          prop="goodTypeId"
          label="物品类型"
          width="200">
        <template slot-scope="scope">
          {{scope.row.good.goodTypeId == 2 ? '产品' : (scope.row.good.goodTypeId == 3 ? '设备' : '原料')}}
        </template>
      </el-table-column>
      <el-table-column
          prop="num"
          label="数量"
          width="150">
      </el-table-column>
      <el-table-column
          prop="enableNum"
          label="可存储数量"
          width="200">
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="visibleDialog" append-to-body v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" title="生产表单">
      <el-row :gutter="15">
        <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
          <el-col :span="14">
            <el-form-item label-width="110px" label="仓库名称" prop="warehouseId">
              <el-select @change="warehouseChange" v-model="formData.warehouseId" placeholder="请选择仓库名称" clearable
                         :style="{width: '100%'}">
                <el-option v-for="(item, index) in warehouseIdOptions" :key="index" :label="item.name"
                           :value="item.id" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label-width="110px" label="原料容器名称" prop="rawContainerId">
              <el-select @change="change1" v-model="formData.rawContainerId" placeholder="请选择产品容器名称" clearable
                         :style="{width: '100%'}">
                <el-option v-for="(item, index) in rawContainerIdOptions" :key="index" :label="item.name"
                           :value="item.id" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="原料名称" prop="rawName">
              <el-input v-model="formData.rawName" placeholder="原料名称" readonly :disabled='true' clearable
                        :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="13">
            <el-form-item label-width="110px" label="消耗数量" prop="rawNum">
              <el-input-number v-model="formData.rawNum" placeholder="消耗数量"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label-width="110px" label="产品容器名称" prop="goodContainerId">
              <el-select @change="change2" v-model="formData.goodContainerId" placeholder="请选择产品容器名称" clearable
                         :style="{width: '100%'}">
                <el-option v-for="(item, index) in goodContainerIdOptions" :key="index" :label="item.name"
                           :value="item.id" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="goodName">
              <el-input v-model="formData.goodName" placeholder="产品名称" readonly :disabled='true' clearable
                        :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="13">
            <el-form-item label-width="110px" label="产出数量" prop="goodNum">
              <el-input-number v-model="formData.goodNum" placeholder="产出数量"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="13">
            <el-form-item label="日期选择" prop="createDate">
              <el-date-picker v-model="formData.createDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                              :style="{width: '100%'}" placeholder="请选择日期选择" clearable></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
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
      visibleDialog:false,
      tableData: null,
      currentPage: 1,
      pageSize: 5,
      total: null,
      ruleForm: {
        warehouseName: '',
        goodName: '',
        pageNum: '',
        size: 7
      },
      formData: {
        warehouseId: undefined,
        rawContainerId: undefined,
        rawId: undefined,
        rawName:undefined,
        rawNum: undefined,
        goodContainerId: undefined,
        goodId: undefined,
        goodName:undefined,
        goodNum: undefined,
        createDate: null,
      },
      rules: {
        warehouseId: [{
          required: true,
          message: '请选择仓库名称',
          trigger: 'change'
        }],
        rawContainerId: [{
          required: true,
          message: '请选择原料容器名称',
          trigger: 'change'
        }],
        rawId: [{
          required: true,
          message: '原料名称',
          trigger: 'blur'
        }],
        rawNum: [{
          required: true,
          message: '消耗数量',
          trigger: 'blur'
        }],
        goodContainerId: [{
          required: true,
          message: '请选择产品容器名称',
          trigger: 'change'
        }],
        goodId: [{
          required: true,
          message: '产品名称',
          trigger: 'blur'
        }],
        goodNum: [{
          required: true,
          message: '产出数量',
          trigger: 'blur'
        }],
        createDate: [{
          required: true,
          message: '请选择日期选择',
          trigger: 'change'
        }],
      },
      warehouseIdOptions: [],
      rawContainerIdOptions: [],
      goodContainerIdOptions: [],
    }
  },
  methods:{
    change1(){
      axios.get('http://localhost:8181/warehouse/container/'+this.formData.warehouseId+'/'+this.formData.rawContainerId)
            .then(resp=>{
              let date = resp.data;
              let code = date.code;
              if (code == 200){
                this.formData.rawName = date.data.goodName;
                this.formData.rawId = date.data.goodId;
              }else {
                this.$message.error("该容器还未存储货物")
              }
            })
            .catch(error=>{
              this.$message.error(error.message);
            })
    },
    change2(){
      axios.get('http://localhost:8181/warehouse/container/'+this.formData.warehouseId+'/'+this.formData.goodContainerId)
          .then(resp=>{
            let date = resp.data;
            let code = date.code;
            if (code == 200){
              this.formData.goodName = date.data.goodName;
              this.formData.goodId = date.data.goodId;
            }else {
              this.$message.error("该容器还未存储货物")
            }
          })
          .catch(error=>{
            this.$message.error(error.message);
          })
    },
    warehouseChange(){

      axios.get('http://localhost:8181/warehouse/container/type/'+this.formData.warehouseId+'/原料')
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            if (code == 200){
                this.rawContainerIdOptions = data.data;
            }else{
              this.rawContainerIdOptions = []
            }
          })
          .catch(error=>{
            this.$message.error(error.message)
          })
      axios.get('http://localhost:8181/warehouse/container/type/'+this.formData.warehouseId+'/产品')
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            if (code == 200){
                this.goodContainerIdOptions = data.data;
            }else{
              this.goodContainerIdOptions =[]
            }
          })
          .catch(error=>{
            this.$message.error(error.message)
          })
    },
    add(){
      axios.get('http://localhost:8181/warehouse/use')
            .then(resp=>{
              let data = resp.data;
              let code = data.code;
              if (code == 200){
                this.warehouseIdOptions = data.data;
              }else{
                this.warehouseIdOptions =[]
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
      this.goodContainerIdOptions = [];
      this.warehouseIdOptions =[];
      this.rawContainerIdOptions=[];
    },
    close() {
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.formData.createDate = new Date(this.formData.createDate).Format('yyyy-MM-dd');
        let form = {
          warehouseId: this.formData.warehouseId,
          rawContainerId: this.formData.rawContainerId,
          rawId: this.formData.rawId,
          rawName:this.formData.rawName,
          rawNum: this.formData.rawNum,
          goodContainerId: this.formData.goodContainerId,
          goodId: this.formData.goodId,
          goodName:this.formData.goodName,
          goodNum: this.formData.goodNum,
          createDate: this.formData.createDate,
        }
        console.log(form)
        axios.post('http://localhost:8181/stock/pc',form)
              .then(resp=>{
                let data = resp.data;
                let code = data.code;
                if (code == 200){
                  this.$message.success(data.message)
                  this.page();
                }else {
                  this.$message.error(data.message)
                }
              })
              .catch(error=>{
                this.$message.error(error.message)
              })
        this.visibleDialog = false;
        this.close()
      })
    },
    page(){
        if (this.ruleForm.goodName == '' && this.ruleForm.warehouseName == ''){
          this.initTable();
        }else{
          this.submitForm(this.currentPage);
        }
    },
    submitForm(pageNum){
      this.ruleForm.pageNum = pageNum;
      console.log(this.ruleForm);
      axios.get('http://localhost:8181/stock/query',{params:this.ruleForm})
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
    initTable(){
      axios.get('http://localhost:8181/stock/'+this.currentPage)
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
    }
  },
  created() {
    this.initTable();
  }
}
</script>