<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="库柜名称:" style="position: relative;left: 10px">
        <el-input v-model="ruleForm.name" placeholder="请输入关键字" style="width: 160px;"></el-input>
        <span style="position: relative;left: 10px">类型:</span>
        <el-select v-model="ruleForm.type" filterable placeholder="请选择" style="width: 150px;position: relative;left: 20px">
          <el-option
              v-for="item in typeOption"
              :key="item"
              :label="item"
              :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -62px;left: 455px">
        <el-form-item label="最大容量:" style="position: relative;left: 10px">
          <el-input v-model="ruleForm.maxCapacity" placeholder="请输入关键字" style="width: 160px;"></el-input>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;"
                     @click="submitForm(1)">搜索
          </el-button>
        </el-form-item>
        <div style="position: relative ;left: -414px">
          <el-button
              size="mini"
              type="success"
              @click="visibleDialog = true">添加
          </el-button>
          <el-button
              :disabled="multipleSelection.length == 0"
              size="mini"
              type="danger"
              @click="del()">删除
          </el-button>
          <el-button size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
          <el-dialog :visible.sync="visibleDialog" v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose"
                     title="添加库柜">
            <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
              <el-form-item label="库柜名称" prop="name">
                <el-input v-model="formData.name" placeholder="请输入库柜名称" clearable :style="{width: '100%'}">
                </el-input>
              </el-form-item>
              <el-form-item label="供应商" prop="type">
                <el-select v-model="formData.type" placeholder="请选择类型" clearable
                           :style="{width: '100%'}">
                  <el-option v-for="(item, index) in typeOption" :key="index" :label="item"
                             :value="item" :disabled="item.disabled"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="最大容量" prop="maxCapacity">
                <el-input v-model="formData.maxCapacity" placeholder="请输入存储最大容量" clearable
                          :style="{width: '100%'}"></el-input>
              </el-form-item>
            </el-form>
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
        @selection-change="handleSelectionChange"
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          prop="id"
          type="selection"
          width="50">
      </el-table-column>
      <el-table-column
          fixed
          prop="id"
          label="库柜编号"
          width="130">
      </el-table-column>
      <el-table-column
          prop="name"
          label="库柜名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="type"
          label="存储类型"
          width="130">
      </el-table-column>
      <el-table-column
          prop="maxCapacity"
          label="存储最大容量"
          width="130">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="primary"
              @click="edit(scope.row.id)">编辑
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
      key: '',
      value: '',
      formData: {
        id: '',
        name: undefined,
        type: undefined,
        maxCapacity: undefined,
      },
      ruleForm: {
        name: '',
        type: '',
        maxCapacity: '',
        pageNum: '',
        size: 7
      },
      multipleSelection:[],
      rules: {
        name: [{
          required: true,
          message: '请输入库柜名称',
          trigger: 'blur'
        }],
        type: [{
          required: true,
          message: '请输入存储类型',
          trigger: 'blur'
        }],
        maxCapacity: [{
          required: true,
          message: '请输入存储最大容量',
          trigger: 'blur'
        }],
      },
      typeOption: ["","原料", "产品", "设备"],
    }
  },
  methods: {
    edit(id) {
      axios.get('http://localhost:8181/container/query/' + id)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.visibleDialog = true;
              //等待dom渲染后在赋值
              setTimeout(() => {
                this.formData = data.data;
              }, 100)
            } else {
              this.$message.error(data.message);
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    del() {
      this.$confirm('此操作将永久删除你所选的所有选项, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let url = 'http://localhost:8181/container?';
        for (let i = 0; i < this.multipleSelection.length; i++) {
          url += "ids=" + this.multipleSelection[i].id + "&";
        }
        url = url.substring(0, url.length - 1);
        axios.delete(url)
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
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        if (this.formData.id == '') {
          axios.post('http://localhost:8181/container', this.formData)
              .then(resp => {
                let data = resp.data;
                let code = data.code;
                if (code == 200) {
                  this.$message.success(data.message);
                  this.page();
                } else {
                  this.$message.error(data.message)
                }
                this.close();
                this.visibleDialog = false;
              })
              .catch(error => {
                this.$message.error(error.message)
              })
        } else {
          axios.put('http://localhost:8181/container', this.formData)
              .then(resp => {
                let data = resp.data;
                let code = data.code;
                if (code == 200) {
                  this.$message.success(data.message);
                  this.page();
                } else {
                  this.$message.error(data.message);
                }
                this.close()
                this.visibleDialog = false;
              })
              .catch(error => {
                this.$message.error(error.message);
              })
        }


      })
    },
    page() {
      if (this.ruleForm.name == '' && this.ruleForm.type == '' && this.ruleForm.maxCapacity == '') {
        this.initTable()
      } else {
        this.submitForm(this.currentPage);
      }
    },
    submitForm(pageNum) {
            this.ruleForm.pageNum = pageNum;
            axios.get('http://localhost:8181/container/query',{params:this.ruleForm})
                  .then(resp=>{
                    let data = resp.data;
                    let code = data.code;
                    if (code == 200){
                        this.$message.success(data.message);
                        this.tableData = data.data.data;
                        this.total = data.data.total;
                    }else{
                        this.$message.error(data.message)
                    }
                  })
                  .catch(error=>{
                        this.$message.error(error.message)
                  })
    },
    initTable() {
      axios.get('http://localhost:8181/container/selectAll/' + this.currentPage)
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