<template>
  <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;">
    <el-form style="margin-left: -40px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="采购编号:" prop="purchaseNo" style="position: relative;left: 10px">
        <el-input v-model="ruleForm.purchaseNo" placeholder="请输入关键字" style="width: 160px"></el-input>
      </el-form-item>
      <div style="border: 0px solid red;width: 400px;height: 60px;position: relative;top: -64px;left: 270px">
        <el-form-item label="时间范围:" width="130">
          <el-date-picker
              v-model="date"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-button type="primary" icon="el-icon-search" style="position: relative;left: 470px;top: -63px"
                   @click="submitForm(1)">搜索
        </el-button>
        <el-button style="position: relative;top: -10px;left: -328px"
                   size="mini"
                   type="success"
                   @click="visibleDialog1 = true">创建采购订单
        </el-button>
        <el-button style="position: relative;top: -10px;left: -320px" size="mini" type="primary" icon="el-icon-refresh"
                   plain @click="page">刷新
        </el-button>
        <div style="position: relative ;left: -230px">

          <el-dialog :visible.sync="visibleDialog1" v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose1"
                     title="创建采购订单">
            <el-row :gutter="15">
              <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
                <el-col :span="15">
                  <el-form-item label="创建人名称" prop="createName">
                    <el-input v-model="formData.createName" placeholder="请输入创建人名称" clearable
                              prefix-icon='el-icon-user-solid' :style="{width: '100%'}"></el-input>
                  </el-form-item>
                </el-col>
              </el-form>
            </el-row>
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
        style="width: 100%;position: relative;top:-30px">
      <el-table-column
          fixed
          prop="purchaseNo"
          label="采购编号"
          width="100">
      </el-table-column>
      <el-table-column
          prop="orderNo"
          label="订单编号"
          width="310">
      </el-table-column>
      <el-table-column
          prop="createName"
          label="创建人"
          width="130">
      </el-table-column>

      <el-table-column
          prop="createdate"
          label="创建时间"
          width="200">
      </el-table-column>
      <el-table-column
          prop="num"
          label="数量"
          width="130">
      </el-table-column>
      <el-table-column
          prop="total"
          label="总价"
          width="130">
      </el-table-column>

      <el-table-column label="操作" width="250">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="danger"
              @click="edit(scope.row.purchaseNo)">编辑订单
          </el-button>
          <el-button
              size="mini"
              type="warning"
              @click="commit(scope.row.purchaseNo)">确认提交
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog append-to-body title="采购清单" :visible.sync="visibleDialog2" @close="gridDataOnClose" width="80%">
      <div style="position: relative ;left:0">
        <el-button
            size="mini"
            type="success"
            @click="add()">添加
        </el-button>
        <el-dialog append-to-body :visible.sync="visibleDialog3" v-bind="$attrs" v-on="$listeners" @open="onOpen"
                   @close="onClose" title="修改信息">
          <el-row :gutter="15">
            <el-form ref="GoodFormData" :model="GoodFormData" :rules="GoodRules" size="medium"
                     label-width="100px">
              <el-col :span="14">
                <el-form-item label="货物名称" prop="goodId">
                  <el-select v-model="GoodFormData.goodId" placeholder="请选择货物名称" clearable
                             :style="{width: '100%'}">
                    <el-option v-for="(item, index) in good_idOptions" :key="index" :label="item.goodName"
                               :value="item.goodId" :disabled="item.disabled"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="14">
                <el-form-item label="数量" prop="num">
                  <el-input-number min="0" v-model="GoodFormData.num" placeholder="数量"></el-input-number>
                </el-form-item>
              </el-col>
            </el-form>
          </el-row>
          <div slot="footer">
            <el-button @click="close">取消</el-button>
            <el-button type="primary" @click="GoodHandleConfirm">确定</el-button>
          </div>
        </el-dialog>
        <el-dialog append-to-body :visible.sync="visibleDialog4" v-bind="$attrs" v-on="$listeners" @open="onOpen"
                   @close="onClose" title="修改信息">
          <el-row :gutter="15">
            <el-form ref="GoodFormData" :model="GoodFormData" :rules="GoodRules" size="medium"
                     label-width="100px">
              <el-col :span="14">
                <el-form-item label="货物名称" prop="goodId">
                  <el-select v-model="GoodFormData.goodId" placeholder="请选择货物名称" clearable :disabled="true"
                             :style="{width: '100%'}">
                    <el-option v-for="(item, index) in good_idOptions" :key="index" :label="item.goodName"
                               :value="item.goodId" :disabled="item.disabled"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="14">
                <el-form-item label="数量" prop="num">
                  <el-input-number v-model="GoodFormData.num" placeholder="数量"></el-input-number>
                </el-form-item>
              </el-col>
            </el-form>
          </el-row>
          <div slot="footer">
            <el-button @click="close">取消</el-button>
            <el-button type="primary" @click="changeHandleConfirm">确定</el-button>
          </div>
        </el-dialog>
      </div>
      <el-table :data="gridData">
        <el-table-column property="goodName" label="货物名称" width="200"></el-table-column>
        <el-table-column property="price" label="单价"></el-table-column>
        <el-table-column property="num" label="数量"></el-table-column>
        <el-table-column property="supplier.name" label="供应商"></el-table-column>
        <el-table-column>
          <template slot-scope="scope">
            <el-button
                size="mini"
                type="primary"
                @click="edit2(scope.row.goodId)">修改
            </el-button>
            <el-button
                size="mini"
                type="danger"
                @click="del(scope.row.goodId)">删除
            </el-button>
            <el-button
                size="mini"
                type="warning"
                @click="detail(scope.row.goodId)">详情
            </el-button>
            <el-drawer append-to-body
                       title="货物详情"
                       :visible.sync="drawer"
                       :direction="direction"
                       :before-close="handleClose">
              <el-descriptions title="" direction="vertical" :column="2" border>
                <el-descriptions-item label="货物名称">{{ goodDetail.goodName }}</el-descriptions-item>
                <el-descriptions-item label="货物类型">原料</el-descriptions-item>
                <el-descriptions-item label="描述" :span="2">{{ goodDetail.detail }}</el-descriptions-item>
                <el-descriptions-item label="单价">{{ goodDetail.price }}</el-descriptions-item>
                <el-descriptions-item label="供应商">
                  <el-tag size="small">{{ goodDetail.supplier.name }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="地址">{{ goodDetail.supplier.address }}</el-descriptions-item>
                <el-descriptions-item label="联系人">{{ goodDetail.supplier.linkman }}</el-descriptions-item>
                <el-descriptions-item label="电话">{{ goodDetail.supplier.phone }}</el-descriptions-item>
                <el-descriptions-item label="邮箱">{{ goodDetail.supplier.email }}</el-descriptions-item>
                <el-descriptions-item label="备注" :span="2">{{ goodDetail.supplier.marks }}</el-descriptions-item>
              </el-descriptions>
            </el-drawer>
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
  </div>

</template>

<script>

export default {
  data() {
    return {
      date: null,
      size: 'medium',
      drawer: false,
      direction: 'rtl',
      visibleDialog1: false,
      visibleDialog2: false,
      visibleDialog3: false,
      visibleDialog4: false,
      goodDetail: {
        supplier: {}
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },

      gridData: [{}],
      tableData: [{}],
      currentPage: 1,
      pageSize: 7,
      total: null,
      key: '',
      value: '',
      ruleForm: {
        fromDate: null,
        toDate: null,
        purchaseNo: '',
        pageNum: '',
        size: 7
      },
      GoodFormData: {
        purchaseNo: null,
        goodId: null,
        num: null
      },
      GoodRules: {
        goodId: [{
          required: true,
          message: '选择货物',
          trigger: 'blur'
        }],
        num: [{
          required: true,
          type: 'number',
          message: '请输入数量',
          trigger: 'blur'
        }],
      },
      formData: {
        createName: null,
      },
      rules: {
        createName: [{
          required: true,
          message: '请输入创建人名称',
          trigger: 'blur'
        }],
      },
      good_idOptions: [],
    }
  },
  methods: {
    onClose1() {

    },
    gridDataOnClose() {
      this.gridData = [];
    },
    changeHandleConfirm() {
      axios.put('http://localhost:8181/purchase/good', this.GoodFormData)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.$message.success(data.message)
              this.edit(this.GoodFormData.purchaseNo);
              this.page();
            } else {
              this.$message.error(data.message);
            }
            this.visibleDialog4 = false;

          })
          .catch(error => {
            this.$message.error(error.message);
          })
    },
    GoodHandleConfirm() {
      if (this.GoodFormData.goodId == null) {
        this.$message.error("请选择货物")
        return
      }
      axios.post('http://localhost:8181/purchase/good', this.GoodFormData)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            // console.log(data)
            if (code == 200) {
              this.$message.success(data.message)
              this.edit(this.GoodFormData.purchaseNo);
              this.page();
            } else {
              this.$message.error(data.message);
            }
            this.visibleDialog3 = false;
          })
          .catch(error => {
            this.$message.error(error.message);
          })

    },
    detail(id) {
      for (let i = 0; i < this.gridData.length; i++) {
        if (this.gridData[i].goodId == id) {
          this.goodDetail = this.gridData[i];
          break;
        }
      }
      this.drawer = true;
    },
    add() {
      this.showOption();
      this.visibleDialog3 = true;
    },
    del(goodId) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete('http://localhost:8181/purchase/good', {
          data: {
            goodId: goodId,
            purchaseNo: this.GoodFormData.purchaseNo,
          }
        })
            .then(resp => {
              let data = resp.data;
              let code = data.code;
              if (code == 200) {
                this.$message.success(data.message)
                this.edit(this.GoodFormData.purchaseNo);
                this.page();
              } else {
                this.$message.error(data.message);
              }
            })
            .catch(error => {
              this.$message.error(error.message);
            })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    showOption() {
      axios.get('http://localhost:8181/good/raw/query/purchase', {params: {purchaseNo: this.GoodFormData.purchaseNo}})
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.good_idOptions = data.data
            } else {
              this.$message.error(data.message)
            }
          })
          .catch(error => {
            this.$message.error(error.message);
          })
    },
    commit(purchaseNo) {
      this.$confirm('提交后无法更改, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.put('http://localhost:8181/purchase/' + purchaseNo)
            .then(resp => {
              let data = resp.data;
              let code = data.code;
              if (code == 200) {
                this.$message.success(data.message)
                axios.get('http://localhost:8181/purchase/query/' + purchaseNo)
                    .then(resp => {
                      let data = resp.data;
                      let code = data.code;
                      if (code == 200) {
                        setTimeout(() => {
                          this.gridData = data.data.goods;
                        }, 100)
                      } else {
                        this.$message.error(data.message);
                      }
                    })
                    .catch(error => {
                      this.$message.error(error.message);
                    })
                this.page();
              } else {
                this.$message.error(data.message);
              }
            })
            .catch(error => {
              this.$message.error(error.message);
            })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    submitForm(pageNum) {
      this.ruleForm.pageNum = pageNum;
      if (this.date != null) {
        this.ruleForm.fromDate = new Date(this.date[0]).Format('yyyy-MM-dd hh:mm:ss')
        this.ruleForm.toDate = new Date(this.date[1]).Format('yyyy-MM-dd hh:mm:ss')
      }
      console.log(this.ruleForm)
      axios.get('http://localhost:8181/purchase/query', {params: this.ruleForm})
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            console.log(data)
            if (code == 200) {
              this.tableData = data.data.data;
              this.total = data.data.total;
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].createdate = new Date(this.tableData[i].createdate).Format('yyyy-MM-dd hh:mm:ss')
              }
            } else {
              this.tableData = [];
              this.$message.error(data.message);
            }
            this.ruleForm.fromDate = null;
            this.ruleForm.toDate = null;
          })
          .catch(error => {
            this.$message.error(error.message);
          })
    },
    page() {
      if (this.ruleForm.purchaseNo == '' && this.ruleForm.fromDate == null && this.ruleForm.toDate == null) {
        this.initTable();
      } else {
        this.submitForm(this.currentPage);
      }
    },
    edit(No) {
      this.GoodFormData.purchaseNo = No;
      axios.get('http://localhost:8181/purchase/query/' + No)
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              setTimeout(() => {
                this.gridData = data.data.goods;
              }, 100)
            } else {
              this.$message.error(data.message);
            }
          })
          .catch(error => {
            this.$message.error(error.message);
          })
      this.visibleDialog2 = true;
    },
    edit2(id) {
      this.GoodFormData.goodId = id;
      axios.get('http://localhost:8181/purchase', {params: this.GoodFormData})
          .then(resp => {
            let data = resp.data;
            let code = data.code;
            if (code == 200) {
              this.GoodFormData = data.data;
              this.good_idOptions = [{
                goodId: data.data.goodId,
                goodName: data.data.goodName
              }];
            } else {
              this.$message.error(data.message)
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
      this.visibleDialog4 = true;
    },
    onOpen() {

    },
    onClose() {
      this.GoodFormData.num = '';
      this.GoodFormData.goodId = null
    },
    close() {
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      // console.log(this.formData);
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        axios.post('http://localhost:8181/purchase', this.formData)
            .then(resp => {
              let data = resp.data;
              let code = data.code;
              if (code == 200) {
                this.$message.success(data.message)
              } else {
                this.$message.error(data.message)
              }
              this.page();
              this.visibleDialog1 = false;
              this.$refs['elForm'].resetFields()
            })
            .catch(error => {
              this.$message.error(error.message)
            })
        this.close()
      })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    },
    initTable() {
      axios.get('http://localhost:8181/purchase/' + this.currentPage)
          .then(resp => {
            let data = resp.data;
            console.log(data)
            let code = data.code;
            if (code == 200) {
              this.$message.success(data.message);
              this.total = data.data.total;
              this.tableData = data.data.data;
              for (let i = 0; i < this.tableData.length; i++) {
                this.tableData[i].createdate = new Date(this.tableData[i].createdate).Format('yyyy-MM-dd hh:mm:ss')
              }
            } else {
              this.$message.error(data.message)
            }
          })
          .catch(error => {
            this.$message.error(error.message)
          })
    },
    total() {

    },
  },
  created() {
    this.initTable();
  }
}
</script>