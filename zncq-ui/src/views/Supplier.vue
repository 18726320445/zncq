<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="供应商名称:">
        <el-input v-model="ruleForm.name" placeholder="请输入关键字" style="width: 160px;"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="地址:">
          <el-input v-model="ruleForm.address" placeholder="请输入关键字" style="width: 160px;"></el-input>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;" @click="submitForm()">搜索</el-button>
        </el-form-item>
        <div style="position: relative ;left: -230px">
          <el-button
              size="mini"
              type="success"
              @click="visibleDialog = true">添加</el-button>
          <el-button
              size="mini"
              type="danger"
              :disabled="multipleSelection.length == 0"
              @click="del()">删除</el-button>
          <el-button size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
        </div>
        <el-dialog :visible.sync="visibleDialog"  v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" title="添加供应商信息">
          <el-row :gutter="15">
            <el-form ref="formData" :model="formData" :rules="rules" size="medium" label-width="100px">
              <el-col :span="14">
                <el-form-item label="供应商名称" prop="name">
                  <el-input v-model="formData.name" placeholder="请输入供应商名称" clearable
                            :style="{width: '100%'}"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="19">
                <el-form-item label="供应商地址" prop="address">
                  <el-input v-model="formData.address" placeholder="请输入供应商地址" clearable :style="{width: '100%'}">
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="14">
                <el-form-item label="主要联系人" prop="linkman">
                  <el-input v-model="formData.linkman" placeholder="请输入主要联系人" clearable :style="{width: '100%'}">
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="14">
                <el-form-item label="联系电话" prop="phone">
                  <el-input v-model="formData.phone" placeholder="请输入联系电话" clearable :style="{width: '100%'}">
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="14">
                <el-form-item label="E-mail地址" prop="email">
                  <el-input v-model="formData.email" placeholder="请输入E-mail地址" clearable :style="{width: '100%'}">
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="19">
                <el-form-item label="备注" prop="remarks">
                  <el-input v-model="formData.remarks" type="textarea" placeholder="请输入备注"
                            :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
                </el-form-item>
              </el-col>
            </el-form>
          </el-row>
          <div slot="footer">
            <el-button @click="resetForm()">重置</el-button>
            <el-button type="primary" @click="handleConfirm">确定</el-button>
          </div>
        </el-dialog>
      </div>
    </el-form>

    <el-table
        :data="tableData"
        border
        stripe
        @selection-change="handleSelectionChange"
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          prop="id"
          type="selection"
          width="50">
      </el-table-column>
      <el-table-column
          prop="name"
          label="供应商名称"
          width="150">
      </el-table-column>
      <el-table-column
          width="200"
          prop="address"
          label="地址">
      </el-table-column>
      <el-table-column
          prop="linkman"
          label="主要联系人"
          width="130">
      </el-table-column>
      <el-table-column
          prop="phone"
          label="联系电话"
          width="130">
      </el-table-column>
      <el-table-column
          prop="email"
          label="E-mail地址"
          width="200">
      </el-table-column>
      <el-table-column
          prop="remarks"
          label="备注"
          width="200">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="primary"
              @click="edit(scope.row.id)">编辑</el-button>
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
      tableData:[],
      currentPage: 1,
      pageSize: 7,
      total: null,
      key: '',
      value: '',
      formData: {
        id:'',
        name: '',
        address: '',
        linkman: '',
        phone: '',
        email: '',
        remarks: '',
      },
      ruleForm: {
        name: '',
        address: '',
        pageNum: '',
      },
      multipleSelection:[],
      rules: {
        name: [{
          required: true,
          message: '请输入供应商名称',
          trigger: 'blur'
        }],
        address: [{
          required: true,
          message: '请输入供应商地址',
          trigger: 'blur'
        }],
        linkman: [{
          required: true,
          message: '请输入主要联系人',
          trigger: 'blur'
        }],
        phone: [{
          required: true,
          message: '请输入联系电话',
          trigger: 'blur'
        }, {
          pattern: /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/,
          message: '手机号格式错误',
          trigger: 'blur'
        }],
        email: [{
          required: true,
          message: '请输入E-mail地址',
          trigger: 'blur'
        },{
          pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
          message: '邮箱格式错误',
          trigger: 'blur'
        }],
        remarks: [{
          required: true,
          message: '请输入备注',
          trigger: 'blur'
        }],
      }
    }
  },
  methods: {
    onOpen() {},
    onClose() {
      this.$refs['formData'].resetFields()
    },
    resetForm() {
      this.$emit('update:visible', false)
      // this.formData = {};
      this.$refs['formData'].resetFields()
    },
    //全选
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection = val;
    },
    handleConfirm() {
      this.$refs['formData'].validate(valid => {
        if (!valid) return
        console.log(this.formData.id);
        if(this.formData.id != ''){
          axios.put('http://localhost:8181/supplier',this.formData)
              .then(resp=>{
                let code = resp.data.code;
                let data = resp.data
                // console.log(data)
                if (code == 200){
                  this.$message({
                    message: data.message,
                    type: 'success'
                  });
                  this.initTable();
                }else {
                  this.$message.error(data.message);
                }
                this.$refs['formData'].resetFields();
                this.visibleDialog = false;
              })
              .catch(error=>{
                this.$message.error(error.message);
              })
        }else{
          axios.post('http://localhost:8181/supplier',this.formData)
              .then(resp=>{
                let code = resp.data.code;
                let data = resp.data
                if (code == 200){
                  this.$message({
                    message: data.message,
                    type: 'success'
                  });
                  this.initTable();
                }else {
                  this.$message.error(data.message);
                }
                this.$refs['formData'].resetFields();
                this.visibleDialog = false;
              })
              .catch(error=>{
                this.$message.error(error.message);
              })
        }
      });
    },
    initTable(){
      axios.get('http://localhost:8181/supplier/' + this.currentPage)
        .then(resp=>{
          let data = resp.data;
          let code = data.code;
          // console.log(data)
          if (code == 200){
            this.total = data.data.total;
            this.tableData = data.data.data;
            this.$message({
              message: data.message,
              type: 'success'
            });
          }else {
            this.$message.error(data.message);
          }
        })
        .catch(error=>{
          this.$message.error(error.message);
        })
    },
    page(){
      if (this.ruleForm.address.trim() != '' || this.ruleForm.name.trim() != ''){
        this.submitForm();
      }else {
        this.initTable()
      }
    },
    del(){
      console.log(this.multipleSelection)
      let url = 'http://localhost:8181/supplier?';
      for (let i = 0; i < this.multipleSelection.length; i++) {
        url = url+'id=' + this.multipleSelection[i].id+"&";
      }
      url = url.substr(0,url.length - 1)
      this.$confirm('此操作将永久删除你所选的所有选项, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete(url)
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            if (code == 200){
              this.initTable();
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
            }else {
              this.$message.error(data.message);
            }
          })
          .catch(error=>{
              this.$message.error(error.message)
          });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    edit(id){
      axios.get('http://localhost:8181/supplier/supplierOne/'+id)
        .then(resp=>{
          let data = resp.data;
          let code = data.code;
          console.log(data)
          if(code == 200){
            this.visibleDialog = true;
            setTimeout(()=>{
              this.formData = data.data;
            },100)
          }else {
            this.$message.error(data.message);
          }

        })
      .catch(error=>{
        this.$message.error(error.message);
      })
    },
    submitForm(){
      this.ruleForm.pageNum = this.currentPage;
      console.log(this.ruleForm.name);
      axios.get('http://localhost:8181/supplier/query',{params:this.ruleForm})
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            this.total = data.data.total;
            if (code == 200){
              this.tableData = data.data.data;
              this.total = data.data.total;
            }else {
              this.$message({
                type: 'warning',
                message: data.message
              });
            }
          })
          .catch(error=>{

          })
    },
  },
  created(){
    this.initTable();
  }
}
</script>