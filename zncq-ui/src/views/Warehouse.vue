<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="仓库名称:" style="position: relative;left: 10px">
        <el-input v-model="ruleForm.name" placeholder="请输入关键字" style="width: 160px;"></el-input>
        <span style="position: relative;left: 10px">地点:</span>
        <el-input v-model="ruleForm.address" placeholder="请输入关键字"
                  style="width: 160px;position: relative;left: 20px"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -62px;left: 450px">
        <el-form-item label="负责人:">
          <el-input v-model="ruleForm.admin" placeholder="请输入关键字" style="width: 160px;"></el-input>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;"
                     @click="submitForm(1)">搜索
          </el-button>
        </el-form-item>
        <div style="position: relative ;left: -410px">
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
        </div>
        <el-dialog :visible.sync="visibleDialog" v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose"
                   title="添加仓库信息">
          <el-row :gutter="15">
            <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
              <el-col :span="14">
                <el-form-item label-width="150px" label="仓库名称" prop="name">
                  <el-input v-model="formData.name" placeholder="请输入仓库名称" clearable
                            :style="{width: '100%'}"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="14">
                <el-form-item label-width="150px" label="仓库负责人" prop="admin">
                  <el-input v-model="formData.admin" placeholder="请输入仓库负责人" clearable
                            :style="{width: '100%'}"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="14">
                <el-form-item label-width="150px" label="联系方式" prop="phone">
                  <el-input v-model="formData.phone" placeholder="请输入联系方式" clearable :style="{width: '100%'}">
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="14">
                <el-form-item label-width="150px" label="仓库地址" prop="address">
                  <el-input v-model="formData.address" placeholder="请输入仓库地址" clearable :style="{width: '100%'}">
                  </el-input>
                </el-form-item>
              </el-col>
            </el-form>
          </el-row>
          <div slot="footer" style="position: relative ; left: -300px">
            <el-button @click="close">重置</el-button>
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
          fixed
          prop="id"
          label="仓库编号"
          width="130">
      </el-table-column>
      <el-table-column
          prop="name"
          label="仓库名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="admin"
          label="仓库负责人"
          width="130">
      </el-table-column>
      <el-table-column
          prop="phone"
          label="联系方式"
          width="130">
      </el-table-column>
      <el-table-column
          prop="address"
          label="仓库地址"
          width="130">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="primary"
              @click="edit(scope.row.id)">编辑
          </el-button>
          <el-button
              size="mini"
              type="warning"
              @click="editContainer(scope.row.id)">容器配置
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="容器配置" :visible.sync="visibleContainer" width="60%">
      <el-button
          size="mini"
          type="success"
          @click="addContainer()">添加
      </el-button>
      <el-table :data="gridData">
        <el-table-column prop="name" label="容器名称" width="150"></el-table-column>
        <el-table-column prop="type" label="容器类型" width="150"></el-table-column>
        <el-table-column prop="maxCapacity" label="最大容量" width="150"></el-table-column>
        <el-table-column prop="num" label="数量" width="100"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
                size="mini"
                type="danger"
                @click="delContainer(scope.row.id)">删除
            </el-button>
            <el-button
                size="mini"
                type="primary"
                @click="editNum(scope.row.id)">修改数量
            </el-button>
          </template>
        </el-table-column>
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

    <el-dialog append-to-body :visible.sync="visibleContainerForm" v-bind="$attrs" v-on="$listeners"
               @open="onOpenContainer" @close="onCloseContainer" title="添加容器">
      <el-row :gutter="15">
        <el-form ref="containForm" :model="containForm" :rules="rulesC" size="medium" label-width="100px">
          <el-col :span="14">
            <el-form-item label="货物名称" prop="containerId">
              <el-select v-model="containForm.containerId" placeholder="请选择容器" clearable :style="{width: '100%'}">
                <el-option v-for="(item, index) in containOption" :key="index" :label="item.name"
                           :value="item.id" :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item label="数量" prop="num">
              <el-input-number v-model="containForm.num" placeholder="数量"></el-input-number>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer">
        <el-button type="primary" @click="handleConfirmContainer">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog
        append-to-body
        title="修改数量"
        :visible.sync="visibleNum"
        width="30%">
      <el-input-number style="position: relative;left: 110px" v-model="num" @change="handleChange" :min="0"  label="描述文字"></el-input-number>
      <div slot="footer">
        <el-button type="primary" @click="handleConfirmNum">确定</el-button>
      </div>
    </el-dialog>
  </div>


</template>

<script>
export default {
  data() {
    return {
      visibleNum:false,
      num:0,
      visibleContainerForm: false,
      visibleContainer: false,
      visibleDialog: false,
      tableData: [{}],
      currentPage: 1,
      pageSize: 7,
      total: null,
      key: '',
      value: '',
      gridData: [{}],
      containForm: {
        warehouseId: '',
        containerId: '',
        num: 0,
      },
      formData: {
        id: '',
        name: '',
        admin: '',
        phone: '',
        address: '',
      },
      ruleForm: {
        name: '',
        admin: '',
        address: '',
        pageNum: '',
        size: 7
      },
      multipleSelection: [],
      rulesC: {
        containerId: [{
          required: true,
          message: '请选择容器名称',
          trigger: 'blur'
        }],
        num: [{
          required: true,
          message: '请输入数量',
          trigger: 'blur'
        }]
      },
      rules: {
        name: [{
          required: true,
          message: '请输入仓库名称',
          trigger: 'blur'
        }],
        admin: [{
          required: true,
          message: '请输入仓库负责人',
          trigger: 'blur'
        }],
        phone: [{
          required: true,
          message: '请输入联系方式',
          trigger: 'blur'
        }, {
          pattern: /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/,
          message: '手机号格式错误',
          trigger: 'blur'
        }],
        address: [{
          required: true,
          message: '请输入仓库地址',
          trigger: 'blur'
        }],
      },
      containOption: [],
    }
  },
  methods: {
    onCloseContainer() {
    },
    onOpenContainer() {
    },
    closeContainer() {
    },
    editNum(id){
      this.containForm.containerId = id;
      let list = this.gridData;
      for (let i = 0; i < list.length; i++) {
        if (id == list[i].id){
          this.num = list[i].num;
          break;
        }
      }
      this.visibleNum = true;
    },
    handleConfirmNum(){
      this.containForm.num = this.num;
      axios.put('http://localhost:8181/warehouse/container',this.containForm)
            .then(resp=>{
              let data = resp.data;
              let code = data.code;
              if (code == 200){
                this.$message.success(data.message);
              }else{
                this.$message.error(data.message);
              }
              this.editContainer(this.containForm.warehouseId);
              this.$refs['containForm'].resetFields();
              console.log(this.containForm);
              this.visibleNum = false;
            })
            .catch(error=>{
              this.$message.error(error.message)
            })
    },
    handleConfirmContainer() {
      if (this.containForm.containerId == '') {
        this.$message.error("请选择容器")
        return
      }
      let form = {
        containerId: this.containForm.containerId,
        warehouseId: this.containForm.warehouseId,
        num: this.containForm.num
      }
      this.$refs['containForm'].validate(valid => {
        if (!valid) return
        axios.post('http://localhost:8181/warehouse/container', form)
            .then(resp => {
              let data = resp.data;
              let code = data.code;
              if (code == 200) {
                this.$message.success(data.message);
              } else {
                this.$message.error(data.message);
              }
              this.visibleContainerForm = false;
              this.editContainer(this.containForm.warehouseId);
              this.$refs['containForm'].resetFields();
              console.log(this.containForm);
            })
            .catch(error => {
              this.$message.error(error.message)
            })
      })
    },
    addContainer() {
      console.log(this.gridData)
      let url = 'http://localhost:8181/container/findAll?';
      for (let i = 0; i < this.gridData.length; i++) {
        url = url + "ids=" + this.gridData[i].id + "&"
      }
      url = url.substring(0, url.length - 1);
      console.log(url)
      axios.get(url)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.containOption = data.data;
              this.visibleContainerForm = true;
            } else {
              this.$message.error(data.message)
            }
          })
          .catch(error => {
            this.$message.error(error.message);
          })
    },
    editContainer(id) {
      this.containForm.warehouseId = id;
      axios.get('http://localhost:8181/warehouse/container/' + id)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              setTimeout(() => {
                this.gridData = data.data.containerVos;
              }, 100)
            } else {
              this.gridData = [];
            }
            this.visibleContainer = true;
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    delContainer(id) {
      axios.delete('http://localhost:8181/warehouse/container',{data:{
          "warehouseId":this.containForm.warehouseId,
          "containerId":id
        }})
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.$message.success(data.message);
            } else {
              this.$message.error(data.message)
            }
            this.editContainer(this.containForm.warehouseId)
          })
          .catch(error => {
            this.$message.error(error.message);
          })

    },
    handleClick() {

    },
    edit(id) {
      axios.get('http://localhost:8181/warehouse/query/' + id)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.formData = data.data
            } else {
              this.$message.error(data.message);
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
      this.visibleDialog = true;
    },
    del() {
      let url = 'http://localhost:8181/warehouse?';
      for (let i = 0; i < this.multipleSelection.length; i++) {
        url = url + 'ids=' + this.multipleSelection[i].id + "&";
      }
      url = url.substr(0, url.length - 1)
      this.$confirm('此操作将永久删除你所选的所有选项, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete(url)
            .then(resp => {
              let data = resp.data;
              let code = data.code;
              if (code == 200) {
                this.initTable();
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                });
              } else {
                this.$message.error(data.message);
              }
            })
            .catch(error => {
              this.$message.error(error.message)
            });
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
      this.formData.id = '';
      // console.log(this.formData)
    },
    close() {
      this.$refs['elForm'].resetFields()
      // this.formData.id = '';
      this.$emit('update:visible', false)
    },
    handleChange() {

    },
    handleConfirm() {
      // for(let key in this.formData){
      //     console.log(this.formData[key])
      // }
      // console.log(this.formData)
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        if (this.formData.id == '') {
          axios.post('http://localhost:8181/warehouse', this.formData)
              .then(resp => {
                let data = resp.data;
                let code = data.code;
                if (code == 200) {
                  this.$message({
                    type: "success",
                    message: data.message,
                  })
                  this.page();
                  this.visibleDialog = false;
                  this.close()
                } else {
                  this.$message.error(data.message)
                  this.close()
                }
              })
              .catch(error => {
                this.$message.error(error.message)
                this.close()
              })
        } else {
          axios.put('http://localhost:8181/warehouse', this.formData)
              .then(resp => {
                let data = resp.data;
                let code = data.code;
                if (code == 200) {
                  this.page()
                  this.visibleDialog = false;
                  this.close();
                  console.log(this.formData)
                  this.$message.success(data.message)
                } else {
                  this.visibleDialog = false;
                  this.close();
                  this.$message.error(data.message)
                }
              })
              .catch(error => {
                this.$message.error(error.message)
              })

        }

      })
    },
    page() {
      if (this.ruleForm.name == '' && this.ruleForm.admin == '' && this.ruleForm.address == '') {
        this.initTable();
      } else {
        this.submitForm(this.currentPage);
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    initTable() {
      axios.get('http://localhost:8181/warehouse/' + this.currentPage)
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
    submitForm(pageNum) {
      this.ruleForm.pageNum = pageNum;
      console.log(this.ruleForm)
      axios.get('http://localhost:8181/warehouse/query', {params: this.ruleForm})
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.total = data.data.total;
              this.tableData = data.data.data;
            } else {
              this.$message.error(data.message)
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })

    }
  },
  created() {
    this.initTable();
  }
}
</script>
<style>
.el-upload__tip {
  line-height: 1.2;
}
</style>