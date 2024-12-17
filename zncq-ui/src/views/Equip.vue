<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
    <el-form style="margin-left: -25px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="设备名称:" >
        <el-input v-model="ruleForm.goodName" placeholder="请输入关键字" style="width: 160px;"></el-input>
        <span style="position: relative;left: 10px">单价:</span>
        <el-input v-model="ruleForm.price" placeholder="请输入关键字" style="width: 160px;position: relative;left: 20px"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 450px">
        <el-form-item label="供应商:">
          <el-input v-model="ruleForm.supplierName" placeholder="请输入关键字" style="width: 160px;"></el-input>
          <el-button type="primary" icon="el-icon-search" style="position: relative;left: 10px;" @click="submitForm(1)">搜索</el-button>
        </el-form-item>
        <div style="position: relative ;left: -424px">
          <el-button
              size="mini"
              type="success"
              @click="add()">添加</el-button>
          <el-button
              :disabled="multipleSelection.length == 0"
              size="mini"
              type="danger"
              @click="del()">删除</el-button>
          <el-button size="mini" type="primary" icon="el-icon-refresh" plain @click="page">刷新</el-button>
          <el-dialog :visible.sync="visibleDialog" v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" title="添加设备">
            <el-row :gutter="15">
              <el-form ref="elForm" :model="formData" :rules="rules" size="medium"  label-width="100px">
                <el-col :span="14">
                  <el-form-item label="设备名称" prop="goodName">
                    <el-input v-model="formData.goodName" placeholder="请输入设备名称" clearable
                              prefix-icon='el-icon-success' :style="{width: '100%'}"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="14">
                  <el-form-item label="单价" prop="price">
                    <el-input v-model="formData.price" placeholder="请输入单价" clearable prefix-icon='el-icon-mobile'
                              :style="{width: '100%'}"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="14">
                  <el-form-item label="供应商" prop="supplierId">
                    <el-select v-model="formData.supplierId" placeholder="请选择供应商" clearable
                               :style="{width: '100%'}">
                      <el-option v-for="(item, index) in supplierNameOptions" :key="index" :label="item.name"
                                 :value="item.id" :disabled="item.disabled"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label="描述" prop="detail">
                    <el-input v-model="formData.detail" type="textarea" placeholder="请输入描述"
                              :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="14" >
                  <el-form-item label="上传图片" prop="files" >
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
                </el-col>
              </el-form>
            </el-row>
            <div slot="footer">
              <el-button @click="close">重置</el-button>
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
          prop="goodId"
          type="selection"
          width="50">
      </el-table-column>
      <el-table-column
          fixed
          prop="goodId"
          label="设备编号"
          width="80">
      </el-table-column>
      <el-table-column
          prop="goodName"
          label="设备名称"
          width="200">
      </el-table-column>
      <el-table-column
          prop="price"
          label="设备单价"
          width="100">
      </el-table-column>
      <el-table-column
          prop="supplier.name"
          label="供应商"
          width="200">
      </el-table-column>
      <el-table-column
          prop="detail"
          label="设备描述"
          width="300">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="primary"
              @click="edit(scope.row.goodId)">编辑</el-button>
          <el-button
              size="mini"
              type="warning"
              @click="pic(scope.row.goodId)" >图片</el-button>

          <el-dialog title="图片" :visible.sync="visiblePic" width="70%" append-to-body>
            <el-carousel :interval="4000" type="card" height="250px" width="600">
              <el-carousel-item v-for="item in photo" :key="item" width="600">
                <el-image
                    style="height: 250px;width: 600px"
                    :src="item"
                ></el-image>
              </el-carousel-item>
            </el-carousel>
          </el-dialog>

          <el-button style="position: relative;left: 10px"
                     size="mini"
                     type="warning"
                     @click="detail(scope.row.goodId)" icon="el-icon-s-order" plain>详情</el-button>
          <el-drawer append-to-body
                     title="货物详情"
                     :visible.sync="drawer"
                     :direction="direction"
                     :before-close="handleClose">
            <el-descriptions title="" direction="vertical" :column="2" border>
              <el-descriptions-item label="货物名称" >{{goodDetail.goodName}}</el-descriptions-item>
              <el-descriptions-item label="货物类型">设备</el-descriptions-item>
              <el-descriptions-item label="描述":span="2">{{goodDetail.detail}}</el-descriptions-item>
              <el-descriptions-item label="单价" >{{goodDetail.price}}</el-descriptions-item>
              <el-descriptions-item label="供应商">
                <el-tag size="small">{{goodDetail.supplier.name}}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="地址">{{goodDetail.supplier.address}}</el-descriptions-item>
              <el-descriptions-item label="联系人">{{goodDetail.supplier.linkman}}</el-descriptions-item>
              <el-descriptions-item label="电话">{{goodDetail.supplier.phone}}</el-descriptions-item>
              <el-descriptions-item label="邮箱">{{goodDetail.supplier.email}}</el-descriptions-item>
              <el-descriptions-item label="备注" :span="2">{{goodDetail.supplier.remarks}}</el-descriptions-item>
            </el-descriptions>
          </el-drawer>
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
      visiblePic:false,
      drawer:false,
      visibleDialog:false,
      direction: 'rtl',
      photo:['http://localhost:8181/pic/1.jpg','http://localhost:8181/pic/2.jpg','http://localhost:8181/pic/3.jpg'],
      goodDetail:{
        goodName:'',
        goodType:'',
        detail:'',
        price:'',
        supplier:{
          name:'',
          address:'',
          linkman:'',
          phone:'',
          email:'',
          remarks:''
        },

      },
      formData: {
        goodId:'',
        goodName: '',
        price: '',
        supplierId: undefined,
        detail: undefined,
        files: [],
      },
      tableData: [{

      }],
      currentPage: 1,
      pageSize: 7,
      total: null,
      key: '',
      value: '',
      ruleForm: {
        goodName: '',
        price:'',
        supplierName:'',
        pageNum: '',
        size: 7
      },
      multipleSelection:[],
      rules: {
        goodName: [{
          required: true,
          message: '请输入设备名称',
          trigger: 'blur'
        }],
        price: [{
          required: true,
          message: '请输入单价',
          trigger: 'blur'
        }],
        supplierName: [{
          required: true,
          message: '请选择供应商',
          trigger: 'change'
        }],
        detail: [{
          required: true,
          message: '请输入描述',
          trigger: 'blur'
        }],
      },
      fileList: [],
      supplierNameOptions: [],
    }
  },
  methods:{
    pic(id){
      let data = [];
      for (let i = 0; i < this.tableData.length; i++) {
        console.log(this.tableData[i].goodId);
        console.log(id)
        if (this.tableData[i].goodId == id){
          data = this.tableData[i].pic.split(",");
          break;
        }
      }
      for (let i = 0; i < data.length; i++) {
        data[i] = 'http://localhost:8181'+data[i];
      }
      this.photo = data;
      this.visiblePic = true;
    },
    edit(id){
      axios.get('http://localhost:8181/supplier/all/')
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            if (code == 200){
              this.supplierNameOptions = data.data;
              axios.get('http://localhost:8181/good/raw/'+id)
                  .then(resp=>{
                    let data = resp.data;
                    let code = data.code;
                    if (code == 200){
                      setTimeout(()=>{
                        this.formData = data.data;
                      },100)
                      this.visibleDialog = true;
                    }else{
                      this.$message.error(data.message);
                    }
                  })
                  .catch(error=>{
                    this.$message.error(error.message);
                  })
            }else {
              this.$message.error(data.message)
            }
          })
          .catch(error=>{
            this.$message.error(error.message)
          })
    },
    add(){
      console.log(this.formData.goodId)
      axios.get('http://localhost:8181/supplier/all/')
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            if (code == 200){
              this.supplierNameOptions = data.data;
              this.visibleDialog = true;
            }else {
              this.$message.error(data.message)
            }
          })
          .catch(error=>{
            this.$message.error(error.message)
          })
    },
    del(){
      console.log(this.multipleSelection);
      let url = 'http://localhost:8181/good/raw?';
      for (let i = 0; i < this.multipleSelection.length; i++) {
        url = url + 'id=' + this.multipleSelection[i].goodId + "&";
      }
      url = url.substring(0,url.length - 1);
      console.log(url);
      axios.delete(url)
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            if (code == 200){
              this.$message({
                type:"success",
                message:data.message
              })
              this.initTable();
            }else {
              this.$message.error(data.message)
            }
          })
          .catch(error=>{
            this.$message.error(error.message);
          });
    },
    page(){
      if (this.ruleForm.price == '' && this.ruleForm.goodName == '' && this.ruleForm.supplierName ==''){
        this.initTable();
      }else{
        this.submitForm(this.currentPage);
      }
    },
    detail(id){
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].goodId == id){
          this.goodDetail = this.tableData[i];
          break;
        }
      }
      console.log(this.goodDetail)
      this.drawer = true;
    },
    handleSelectionChange(val){
      this.multipleSelection = val;
    },
    handleClose(done){
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    onOpen() {},
    onClose() {
      for(let key in this.formData){
        // console.log(this.formData[key])
        this.formData[key] = '';
      }
    },
    close() {
      this.$refs['elForm'].resetFields();
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        let data = new FormData();
        for(let key in this.formData){
          // console.log(this.formData[key])
          data.append(key , this.formData[key])
        }
        console.log(this.fileList);
        for (let i = 0; i < this.fileList.length; i++) {
          data.append("files",this.fileList[i]);
        }
        for(let key in this.formData){
          console.log(this.formData[key])
        }
        if (this.formData.goodId == ''){
          axios.post('http://localhost:8181/good/equip/',data)
              .then(resp=>{
                let data = resp.data;
                let code = data.code;
                if (code == 200){
                  this.$message({
                    type:'success',
                    message:data.message
                  });
                  this.page();
                }else {
                  this.$message.error(data.message);
                }
                this.visibleDialog = false;
                this.$refs['elForm'].resetFields();
                this.formData.goodId = '';
              })
              .catch(error=>{
                this.$message.error(error.message);
              })
        }else {
          axios.put('http://localhost:8181/good/raw',data)
              .then(resp=>{
                let data = resp.data;
                let code = data.code;
                if (code == 200){
                  this.$message({
                    type:'success',
                    message:data.message
                  })
                  this.page();
                }else{
                  this.$message.error(data.message);
                }
                this.visibleDialog = false;
                this.$refs['elForm'].resetFields();
                this.formData.goodId = '';
              })
              .catch(error=>{
                this.$message.error(error.message)
                this.close()
              })
        }
        this.fileList = [];
      })
    },
    handleSuccess(param){
      // console.log(param.fileList);
      this.fileList.push(param.file)
    },
    // handleChange(response, file, fileList){
    //   console.log(fileList);
    //   this.formData.files = fileList;
    // },
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
      axios.get("http://localhost:8181/good/equip/selectAll/"+this.currentPage)
          .then(resp=>{
            let data = resp.data;
            // console.log(data.data)
            let code = data.code;
            if (code == 200){
              this.total = data.data.total;
              this.tableData = data.data.data;
            }else {
              this.$message.error(data.message)
            }
          }).catch(error=>{
        this.$message.error(error.message)
      })
    },
    submitForm(pageNum){
      this.ruleForm.pageNum = pageNum;
      // console.log(this.currentPage)
      axios.get('http://localhost:8181/good/equip/query',{params:this.ruleForm})
          .then(resp=>{
            let data = resp.data;
            let code = data.code;
            if (code == 200){
              this.total = data.data.total;
              this.tableData = data.data.data;
              this.currentPage = data.data.currentPage;
            }else{
              this.$message.error(data.message)
            }
          })
          .catch(error=>{
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
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>