<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="用户状态:" prop="key" style="position:relative;left: 10px ">
        <el-select v-model="ruleForm.state" clearable style="width: 160px;float: left" placeholder="请选择状态">
          <el-option label="正常" value="1"></el-option>
          <el-option label="停用" value="0"></el-option>
        </el-select>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="用户昵称:">
          <el-input v-model="ruleForm.username" placeholder="请输入关键字" style="width: 160px;"></el-input>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;"
                     @click="submitForm(1)">搜索
          </el-button>
        </el-form-item>
        <div style="position: relative ;left: -230px">
          <el-button
              size="mini"
              type="success"
              plain
              @click="add()">添加
          </el-button>
          <el-button
              size="mini"
              type="danger"
              plain
              @click="del()">删除
          </el-button>
          <el-button size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
          <el-dialog :visible.sync="visibleDialog" v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose"
                     title="创建用户">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="用户管理" name="first">
                <el-row :gutter="15">
                  <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
                    <el-col :span="15">
                      <el-form-item label="用户昵称:" prop="nickName">
                        <el-input v-model="formData.nickName" placeholder="请输入用户昵称" clearable :style="{width: '100%'}">
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="15">
                      <el-form-item label="登入账号:" prop="userName">
                        <el-input v-model="formData.userName" placeholder="请输入登入账号" clearable :style="{width: '100%'}">
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="15">
                      <el-form-item label="登入密码:" prop="password">
                        <el-input type="password" v-model="formData.password" placeholder="请输入登入密码" clearable
                                  :style="{width: '100%'}">
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="15">
                      <el-form-item label="确认密码:" prop="repassword">
                        <el-input type="password" v-model="formData.repassword" placeholder="请输入确认密码" clearable
                                  :style="{width: '100%'}">
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="15">
                      <el-form-item label="联系电话:" prop="phone">
                        <el-input v-model="formData.phone" placeholder="请输入联系电话"
                                  :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :span="15">
                      <el-form-item label="邮箱:" prop="email">
                        <el-input v-model="formData.email" placeholder="请输入邮箱"
                                  :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-form>
                </el-row>
              </el-tab-pane>
              <el-tab-pane label="角色管理" name="second">
                <el-table
                    ref="multipleTable"
                    :data="roleData"
                    tooltip-effect="dark"
                    style="width: 100%"
                    :header-cell-style="{background:'#eef1f6', color:'black', 'line-hight':'50px' ,'text-align': 'center'}"
                    @selection-change="handleSelectionChange">
                  <el-table-column
                      prop="id"
                      type="selection"
                      width="55">
                  </el-table-column>
                  <el-table-column
                      prop="roleName"
                      align="center"
                      label="角色名称"
                      width="320">
                  </el-table-column>
                  <el-table-column
                      prop="roleKey"
                      align="center"
                      label="角色关键字"
                      width="320">
                  </el-table-column>
                </el-table>
              </el-tab-pane>
            </el-tabs>

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
        @selection-change="handleSelectionChange"
        :header-cell-style="{background:'#eef1f6', color:'black', 'line-hight':'50px'}"
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          fixed
          prop="id"
          label="用户编号"
          width="160">
      </el-table-column>
      <el-table-column
          prop="nickName"
          label="用户名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="phone"
          align="center"
          label="手机号码"
          width="160">
      </el-table-column>
      <el-table-column
          prop="email"
          align="center"
          label="邮箱"
          width="250">
      </el-table-column>
      <el-table-column
          prop="state"
          label="状态"
          width="100">
        <template slot-scope="scope">
          <el-switch
              @change="changeState(scope.row.state,scope.row.id)"
              v-model="scope.row.state == 1"
              active-color="#13ce66"
              inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column
          prop="createTime"
          align="center"
          label="创建时间"
          width="160">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="primary"
              plain
              @click="edit(scope.row.id)">编辑
          </el-button>
          <el-button
              size="mini"
              type="warning"
              plain
              @click="editPwd(scope.row.id)">修改密码
          </el-button>
        </template>
      </el-table-column>
      <el-dialog append-to-body :visible.sync="visibleDialog2" v-bind="$attrs" v-on="$listeners" @open="onOpen1" @close="onClose1" title="修改用户信息">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="用户信息" name="first"><el-row :gutter="15">
            <el-form ref="elForm1" :model="formData1" :rules="rules" size="medium" label-width="100px">
              <el-col :span="15">
                <el-form-item label="账号" prop="userName">
                  <el-input v-model="formData1.userName" readonly :disabled='true' clearable
                            prefix-icon='el-icon-s-unfold' :style="{width: '100%'}"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="15">
                <el-form-item label="用户昵称" prop="nickName">
                  <el-input v-model="formData1.nickName" placeholder="请输入用户昵称" clearable prefix-icon='el-icon-user'
                            :style="{width: '100%'}"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="15">
                <el-form-item label="联系电话" prop="phone">
                  <el-input v-model="formData1.phone" placeholder="请输入联系电话" clearable prefix-icon='el-icon-phone'
                            :style="{width: '100%'}"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="15">
                <el-form-item label="邮箱" prop="email">
                  <el-input v-model="formData1.email" placeholder="请输入邮箱" clearable prefix-icon='el-icon-s-comment'
                            :style="{width: '100%'}"></el-input>
                </el-form-item>
              </el-col>
            </el-form>
          </el-row></el-tab-pane>
          <el-tab-pane label="角色管理" name="second">
            <el-table
                ref="multipleTable2"
                :data="roleData2"
                tooltip-effect="dark"
                style="width: 100%"
                :header-cell-style="{background:'#eef1f6', color:'black', 'line-hight':'50px' ,'text-align': 'center'}"
                @selection-change="handleSelectionChange2">
              <el-table-column
                  prop="id"
                  type="selection"
                  width="55">
              </el-table-column>
              <el-table-column
                  prop="roleName"
                  align="center"
                  label="角色名称"
                  width="320">
              </el-table-column>
              <el-table-column
                  prop="roleKey"
                  align="center"
                  label="角色关键字"
                  width="320">
              </el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>

        <div slot="footer">
          <el-button @click="close1">取消</el-button>
          <el-button type="primary" @click="handleConfirm1">确定</el-button>
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
      activeName: 'first',
      visibleDialog: false,
      visibleDialog2:false,
      formData: {
        nickName: undefined,
        userName: undefined,
        password: undefined,
        repassword: undefined,
        roleIds: [],
        email: undefined,
        phone: undefined,
      },
      formData1: {
        id:'',
        userName: undefined,
        nickName: undefined,
        phone: undefined,
        email: undefined,
        roleIds: [],
      },
      tableData: [{}],
      roleData: [],
      roleData2:[],
      currentPage: 1,
      pageSize: 7,
      total: null,
      ruleForm: {
        state: null,
        username: null,
        pageNum: '',
        size: 7
      },
      multipleSelection: [],
      rules: {
        nickName: [{required: true, message: '请输入用户昵称', trigger: 'blur'}],
        userName: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}],
        repassword: [{required: true, message: '确认密码', trigger: 'blur'}],
        phone: [{required: true, message: '请输入联系电话', trigger: 'blur'}],
        email: [{required: true, message: '请输入邮件地址', trigger: 'blur'}],
      },
      rules1: {
        userName: [{
          required: true,
          message: '',
          trigger: 'blur'
        }],
        nickName: [{
          required: true,
          message: '请输入用户昵称',
          trigger: 'blur'
        }],
        phone: [{
          required: true,
          message: '请输入联系电话',
          trigger: 'blur'
        }],
        email: [{
          required: true,
          message: '请输入邮箱',
          trigger: 'blur'
        }],
      },
    }
  },
  methods: {
    onOpen1() {},
    onClose1() {
      this.$refs['elForm1'].resetFields()
      this.activeName = 'first'
    },
    close1() {
      this.$emit('update:visible', false)
    },
    handleSelectionChange2(val){
      this.multipleSelection2 = val;
    },
    handleConfirm1() {
      this.$refs['elForm1'].validate(valid => {
        if (!valid) return
        let rids = [];
        for (let i = 0; i < this.multipleSelection2.length; i++) {
          rids.push(this.multipleSelection2[i].id)
        }
        let form ={
          id : this.formData1.id,
          userName:  this.formData1.userName,
          nickName:  this.formData1.nickName,
          phone:  this.formData1.phone,
          email:  this.formData1.email,
          roleIds: rids,
        }
        console.log(form)
        axios.put('http://localhost:8181/userRole',form)
              .then(resp=>{
                let data = resp.data;
                let code = data.code;
                if (code == 200){
                  this.$message.success(data.message);
                }else {
                  this.$message.error(data.message)
                }
                this.page();
              })
              .catch(error=>{
                this.$message.error(error.message)
              })
        this.visibleDialog2 = false;
        this.close()
      })
    },
    handleClick(){

    },
    changeState(state, id) {
      axios.put('http://localhost:8181/user', {
        id: id,
        state: state == 1 ? 0 : 1
      }).then(resp => {
        let data = resp.data;
        let code = data.code;
        if (code == 200) {
          for (let i = 0; i < this.tableData.length; i++) {
            if (this.tableData[i].id == id) {
              this.tableData[i].state = state == 1 ? 0 : 1
            }
          }
        } else {
          this.$message.error("修改失败")
        }
      })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    del(){
      let url = 'http://localhost:8181/user?ids=' + this.multipleSelection[0].id;
      for (let i = 1; i < this.multipleSelection.length; i++) {
        url = url+'&ids=' + this.multipleSelection[i].id;
      }
      axios.delete(url)
            .then(resp=>{
              let data = resp.data;
              let code = data.code;
              if (code == 200){
                this.$message.success(data.message);
                this.page();
              }else {
                this.$message.error(data.message);
              }
            })
            .catch(error=>{
              this.$message.error(error.message);
            })
    },
    edit(id) {
      axios.get('http://localhost:8181/role/findAll')
          .then(resp => {
            let code = resp.data.code;
            if (code == 200) {
              this.roleData2 = resp.data.data;
              axios.get('http://localhost:8181/user/findVo/'+id)
                  .then(resp=>{
                    let data = resp.data;
                    let code = data.code;
                    if (code == 200){
                      this.formData1 = data.data;
                      if (data.data.list){
                        data.data.list.forEach(row => {
                          for (let i = 0; i < this.roleData2.length; i++) {
                            if (this.roleData2[i].id == row.id){
                              this.$nextTick(()=>{
                                this.$refs.multipleTable2.toggleRowSelection(this.roleData2[i]);
                              })
                            }
                          }
                        });
                      }
                      this.activeName = "first"
                      this.visibleDialog2 = true;
                    }else {
                      this.$message.error(data.message)
                    }
                  })
                  .catch(error=>{
                    this.$message.error(error.message)
                  })
            }else{
              this.roleData2 = [];
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })

    },

    editPwd(id){
      this.$prompt('请输入新密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({ value }) => {
        let password = value;
        this.$prompt('请输入你的密码', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        }).then(({ value }) => {
          axios.put('http://localhost:8181/user/rootPwd',{
            rootPassword:value,
            password : password,
            id : id
          })
              .then(resp=>{
                let data = resp.data;
                let code = data.code;
                if (code == 200){
                  this.$message.success(data.message)
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
            message: '取消修改'
          });
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消修改'
        });
      });
    },
    onOpen() {
    },
    onClose() {
      this.$refs['elForm'].resetFields()
      this.activeName = 'first'
    },
    close() {
      this.$emit('update:visible', false)
    },
    add() {
      axios.get('http://localhost:8181/role/findAll')
          .then(resp => {
            let code = resp.data.code;
            if (code == 200) {
              this.roleData = resp.data.data;
              console.log(this.roleData)
            }else{
              this.roleData = [];
            }
          })
          .catch(error => {
              this.$message.error(error.message)
          })
      this.visibleDialog = true;
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        if (this.formData.password != this.formData.repassword){
          this.$message.error("确认密码错误")
          this.visibleDialog = false;
        }
        for (let i = 0; i < this.multipleSelection.length; i++) {
          this.formData.roleIds.push(this.multipleSelection[i].id)
        }
        axios.post('http://localhost:8181/user', this.formData)
            .then(resp => {
              let data = resp.data;
              let code = data.code;
              if (code == 200) {
                this.$message.success(data.message)
              } else {
                this.$message.error(data.message)
              }
              this.page()
              this.visibleDialog = false;
            })
            .catch(error => {
              this.$message.error(error.message)
            })
        this.close()
      })
    },
    page() {
      if (this.ruleForm.state == null && this.ruleForm.username == null){
        this.initTable();
      }else{
        this.submitForm(this.currentPage)

      }

    },
    initTable() {
      axios.get('http://localhost:8181/user/findAll/' + this.currentPage)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.tableData = data.data.data;
              this.total = data.data.total;
              console.log(this.tableData)
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].createTime = new Date(this.tableData[i].createTime).Format('yyyy-MM-dd hh:mm:ss');
              }
              this.$message.success(data.message);
            } else {
              this.tableData = [];
              this.total = 0;
              this.$message.error(data.message)
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    submitForm(pageNum){
        this.ruleForm.pageNum = pageNum;
      axios.get('http://localhost:8181/user/findAll',{params:this.ruleForm})
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.tableData = data.data.data;
              this.total = data.data.total;
              console.log(this.tableData)
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].createTime = new Date(this.tableData[i].createTime).Format('yyyy-MM-dd hh:mm:ss');
              }
              this.$message.success(data.message);
            } else {
              this.tableData = [];
              this.total = 0;
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
.a {
  color: bisque;
}
</style>