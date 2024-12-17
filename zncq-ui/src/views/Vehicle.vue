<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="车辆名称:" style="position: relative;left: 10px">
        <el-input v-model="ruleForm.name" placeholder="请输入关键字" style="width: 160px;"></el-input>
        <span style="position: relative;left: 10px">类型:</span>
        <el-input v-model="ruleForm.admin" placeholder="请输入关键字"
                  style="width: 160px;position: relative;left: 20px"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -63px;left: 450px">
        <el-form-item label="车牌号:">
          <el-input v-model="ruleForm.vehicleNo" placeholder="请输入关键字" style="width: 160px;"></el-input>
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
          <el-dialog :visible.sync="visibleDialog" v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose"
                     :title="dialogTitle">
            <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
              <el-form-item label="车辆名称" prop="name">
                <el-input v-model="formData.name" placeholder="请输入车辆名称" clearable :style="{width: '100%'}">
                </el-input>
              </el-form-item>
              <el-form-item label="车牌号" prop="vehicleNo">
                <el-input v-model="formData.vehicleNo" placeholder="请输入车牌号" clearable :style="{width: '100%'}">
                </el-input>
              </el-form-item>
              <el-form-item label="司机名称" prop="admin">
                <el-input v-model="formData.admin" placeholder="请输入司机名称" clearable :style="{width: '100%'}">
                </el-input>
              </el-form-item>
              <el-form-item label="联系电话" prop="phone">
                <el-input v-model="formData.phone" placeholder="请输入联系电话" clearable :style="{width: '100%'}">
                </el-input>
              </el-form-item>
              <el-form-item label="最大装载量" prop="maxNum">
                <el-input v-model="formData.maxNum" placeholder="请输入最大装载量" clearable :style="{width: '100%'}">
                </el-input>
              </el-form-item>
              <el-form-item label="上传图片" prop="files">
                <el-upload
                    class="upload-demo"
                    action="#"
                    :http-request="handleSuccess"
                    :before-upload="filesBeforeUpload"
                    :file-list="fileList"
                    multiple
                    list-type="picture">
                  <el-button size="small" type="primary">点击上传</el-button>
                  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过5M</div>
                </el-upload>
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
          label="车辆编号"
          width="130">
      </el-table-column>
      <el-table-column
          prop="name"
          label="车辆名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="vehicleNo"
          label="车牌号"
          width="130">
      </el-table-column>
      <el-table-column
          prop="admin"
          label="司机名称"
          width="130">
      </el-table-column>
      <el-table-column
          prop="phone"
          label="联系电话"
          width="130">
      </el-table-column>
      <el-table-column
          prop="maxNum"
          label="最大装载量"
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
              @click="pic(scope.row.id)">图片
          </el-button>
          <el-dialog title="图片" :visible.sync="visiblePic" width="80%"  append-to-body>
            <el-carousel :interval="4000" type="card" width="600" :change="changPic">
              <el-carousel-item v-for="item in photo" :key="item"  width="600">
                  <el-image
                      style="height: 300px;width: 600px"
                      :src="item"
                  ></el-image>
              </el-carousel-item>
            </el-carousel>
          </el-dialog>
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
      picIndex:'',
      dialogTitle: '添加车辆信息',
      visiblePic: false,
      visibleDialog: false,
      tableData: [{}],
      currentPage: 1,
      pageSize: 7,
      total: null,
      key: '',
      value: '',
      formData: {
        id: '',
        name: '',
        vehicleNo: '',
        admin: '',
        phone: '',
        maxNum: '',
        files: null,
      },
      ruleForm: {
        name: '',
        admin: '',
        vehicleNo: '',
        pageNum: '',
        size: 7
      },

      rules: {
        name: [{
          required: true,
          message: '请输入车辆名称',
          trigger: 'blur'
        }],
        vehicleNo: [{
          required: true,
          message: '请输入车牌号',
          trigger: 'blur'
        }],
        admin: [{
          required: true,
          message: '请输入司机名称',
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
        maxNum: [{
          required: true,
          message: '请输入最大装载量',
          trigger: 'blur'
        }],
      },
      multipleSelection:[],
      fileList: [],
      photo: []
    }
  },
  methods: {
    changPic(index){
      this.picIndex = index;
      console.log(index);
    },
    pic(id){
      axios.get('http://localhost:8181/vehicle/query/' + id)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              let pic = data.data.pic;
              let tmp = pic.split(",");
              for (let i = 0; i < tmp.length; i++) {
                this.photo[i] = 'http://localhost:8181'+tmp[i];
              }
              this.visiblePic = true;
            } else {
              this.$message.error(data.message);
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })

    },
    page() {
      if (this.ruleForm.name == '' && this.ruleForm.admin == '' && this.ruleForm.vehicleNo == '') {
        this.initTable()
      } else {
          this.submitForm(this.currentPage)
      }
    },
    del(){
      let url = 'http://localhost:8181/vehicle?'
      for (let i = 0; i < this.multipleSelection.length; i++) {
        url = url+"ids=" + this.multipleSelection[i].id + "&";
      }
      url = url.substring(0,url.length-1);
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete(url)
            .then(resp=>{
              let data = resp.data;
              let code = data.code;
              if (code == 200){
                this.$message.success(data.message);
                this.page();
              }else{
                this.$message.error(data.message);
              }
            })
            .catch(error=>{
              this.$message.error(error.message);
            })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    edit(id) {
      axios.get('http://localhost:8181/vehicle/query/' + id)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              setTimeout(() => {
                this.formData = data.data;
              }, 100)
              this.visibleDialog = true;
            } else {
              this.$message.error(data.message);
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    onOpen() {
    },
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    close() {
      this.$emit('update:visible', false)
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return

        this.formData.files = this.fileList;
        // console.log(this.formData);
        let data = new FormData();
        for (let key in this.formData) {
          if (key == 'files') {
            for (let i = 0; i < this.formData.files.length; i++) {
              data.append(key, this.formData.files[i]);
            }
          } else {
            data.append(key, this.formData[key])
          }
        }
        for (let key in this.formData) {
          console.log(key + ":" + this.formData[key])
        }
        console.log(data)
        if (this.formData.id == '') {
          // console.log(this.fileList);
          axios.post('http://localhost:8181/vehicle', data)
              .then(resp => {
                let data = resp.data;
                let code = data.code;
                if (code == 200) {
                  this.$message({
                    type: 'success',
                    message: data.message
                  });
                  this.page();
                } else {
                  this.$message.error(data.message);
                }
                this.visibleDialog = false;
                this.$refs['elForm'].resetFields();
              })
              .catch(error => {
                this.$message.error(error.message);
              })
        } else {
          axios.put('http://localhost:8181/vehicle', data)
              .then(resp => {
                let data = resp.data;
                let code = data.code;
                if (code == 200) {
                  this.$message.success(data.message);
                  this.page();
                } else {
                  this.$message.error(data.message);
                }
                this.visibleDialog = false;
                this.$refs['elForm'].resetFields();
              })
              .catch(error => {
                this.$message.error(error.message);
              })
        }
      })
      this.fileList  = [];
    },
    handleSuccess(param) {
      this.fileList.push(param.file);
    },
    filesBeforeUpload(file) {
      let isRightSize = file.size / 1024 / 1024 < 5
      if (!isRightSize) {
        this.$message.error('文件大小超过 5MB')
      }
      let isAccept = new RegExp('image/*').test(file.type)
      if (!isAccept) {
        this.$message.error('应该选择image/*类型的文件')
      }
      return isRightSize && isAccept
    },
    initTable() {
      axios.get("http://localhost:8181/vehicle/" + this.currentPage)
          .then(resp => {
            let data = resp.data;
            console.log(data.data)
            let code = data.code;
            if (code == 200) {
              this.total = data.data.total;
              this.tableData = data.data.data;
            } else {
              this.$message.error(data.message)
            }
          }).catch(error => {
        this.$message.error(error.message)
      })
    },
    submitForm(pageNum){
      this.ruleForm.pageNum = pageNum;
      axios.get('http://localhost:8181/vehicle/query',{params:this.ruleForm})
            .then(resp=>{
              let data = resp.data;
              let code = data.code;
              if (code == 200){
                this.total = data.data.total;
                this.tableData  = data.data.data;
              }else{
                this.$message.error(data.message)
              }
            })
            .catch(error=>{
              this.$message.error(error.message);
            })
    },
  },

  created() {
    this.initTable();
  }
}
</script>