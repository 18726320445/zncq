<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -40px"   ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -62px;left: 455px">
        <div style="position: relative ;left: -414px;top: 60px">
          <el-button
              size="mini"
              type="success"
              @click="visibleDialog = true">添加角色
          </el-button>
          <el-button size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
        </div>
      </div>
    </el-form>
    <el-dialog :visible.sync="visibleDialog" v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose"
               title="添加角色">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="formData.roleName" placeholder="请输入角色名称" clearable :style="{width: '100%'}">
          </el-input>
        </el-form-item>
        <el-form-item label="角色键名" prop="roleKey">
          <el-input v-model="formData.roleKey" placeholder="请输入角色键名" clearable :style="{width: '100%'}"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input  v-model="formData.remark" placeholder="请输入备注" clearable
                    :style="{width: '100%'}"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="tableData"
        stripe
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          fixed
          prop="id"
          label="角色编号"
          width="200">
      </el-table-column>
      <el-table-column
          prop="roleName"
          label="角色名称"
          width="200">
      </el-table-column>
      <el-table-column
          prop="roleKey"
          label="角色键名"
          width="200">
      </el-table-column>
      <el-table-column
          prop="remark"
          label="备注"
          width="400">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="primary"
              @click="del(scope.row.id)">删除
          </el-button>
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
      visibleDialog: false,
      tableData: [{}],
      currentPage: 1,
      pageSize: 7,
      total: null,
      formData: {
        roleName: undefined,
        roleKey: undefined,
        remark: undefined,
      },
      rules: {
        roleName: [{
          required: true,
          message: '请输入角色名称',
          trigger: 'blur'
        }],
        roleKey: [{
          required: true,
          message: '请输入角色键名',
          trigger: 'blur'
        }],
        remark: [{
          required: true,
          message: '请输入备注',
          trigger: 'blur'
        }],
      },
    }
  },
  methods: {
    del(id) {
      this.$confirm('此操作将永久删除你所选的所有选项, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(id)
        axios.delete('http://localhost:8181/role/'+id)
            .then(resp => {
              let data = resp.data;
              let code = data.code;
              if (code == 200) {
                this.$message.success(data.message);
                this.page();
              } else {
                this.$message.error(data.message)
              }

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
    onOpen() {

    },
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    close() {
      this.$refs['elForm'].resetFields()
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        let form = {
          roleName: this.formData.roleName,
          roleKey: this.formData.roleKey,
          remark: this.formData.remark,
        }
        axios.post('http://localhost:8181/role',form)
              .then(resp=>{
                let data = resp.data;
                let code = data.code;
                if (code == 200){
                  this.$message.success(data.message)
                }else {
                  this.$message.error(data.message)
                }
                this.page()
              })
              .catch(error=>{
                this.$message.error(error.data)
              })
        this.visibleDialog = false;
      })
    },
    page() {
        this.initTable();
    },
    initTable() {
      axios.get('http://localhost:8181/role/' + this.currentPage)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.total = data.data.total;
              this.tableData = data.data.data;
              this.$message({
                type: 'success',
                message: data.message
              })
            } else {
              this.$message.error(data.message)
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