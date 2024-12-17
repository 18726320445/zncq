<template>
  <el-container class="home_container" >
    <el-header class="home_header" height="80px">
      <div class="home_title">智能厂区监管系统</div>
      <div class="home_userinfoContainer">
        <iframe  frameborder="no" border="0" marginwidth="0" marginheight="0" width=298 height=52 src="https://music.163.com/outchain/player?type=0&id=8237947666&auto=1&height=32"></iframe>
        <el-dropdown>
                  <span class="el-dropdown-link home_userinfo" >
                    {{username}}<i class="el-icon-arrow-down el-icon--right home_userinfo"></i>
                  </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
            <el-dropdown-item @click.native="dialogFormVisible = true">修改密码</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <el-container>

      <el-aside class="home_aside" width="200px">

        <el-menu :unique-opened="true" router class="el-menu-vertical-demo"background-color="#000000" text-color="#fff"
                 active-text-color="#ffd04b">
<!--       el-submenu 中的index 是用来识别导航模块  -->
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-s-platform"></i>系统管理</template>
<!--      el-menu-item 中的 index为跳转路由的属性      -->
            <el-menu-item index="/main/userManager" >
              <i class="el-icon-folder-add"></i>用户管理
            </el-menu-item>
            <el-menu-item index="/main/roleManager" :class="$route.path=='/dormitoryAdminManager'?'is-active':''">
              <i class="el-icon-document-copy"></i>角色管理
            </el-menu-item>
            <el-menu-item index="/main/logManager" :class="$route.path=='/dormitoryAdminManager'?'is-active':''">
              <i class="el-icon-document-copy"></i>日志信息
            </el-menu-item>
<!--            <el-menu-item index="/dormitoryAdminManager" :class="$route.path=='/dormitoryAdminManager'?'is-active':''">-->
<!--              <i class="el-icon-document-copy"></i>部门岗位管理-->
<!--            </el-menu-item>-->
          </el-submenu>

          <el-submenu index="2">
            <template slot="title"><i class="el-icon-user-solid"></i>合约管理</template>
            <el-menu-item index="/main/contractManager" :class="$route.path=='/studentAdd'?'is-active':''">
              <i class="el-icon-folder-add"></i>合同维护
            </el-menu-item>
<!--            <el-menu-item index="/main/ticketManager">-->
<!--              <i class="el-icon-document-copy"></i>发票管理-->
<!--            </el-menu-item>-->
            <el-menu-item index="/main/contractSign">
              <i class="el-icon-document-copy"></i>合同签署
            </el-menu-item>
            <el-menu-item index="/main/customer">
              <i class="el-icon-document-copy"></i>客户管理
            </el-menu-item>
          </el-submenu>

          <el-submenu index="3">
            <template slot="title"><i class="el-icon-s-grid"></i>采购管理</template>
            <el-menu-item index="/main/purchase">
              <i class="el-icon-folder-add"></i>采购订单
            </el-menu-item>
            <el-menu-item index="/main/purchaseOrder">
              <i class="el-icon-folder-add"></i>订单审核
            </el-menu-item>
            <el-menu-item index="/main/raw">
              <i class="el-icon-document-copy"></i>原料信息
            </el-menu-item>
            <el-menu-item index="/main/supplier">
              <i class="el-icon-document-copy"></i>供应商
            </el-menu-item>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title"><i class="el-icon-s-home"></i>仓库管理</template>
            <el-menu-item index="/main/warehouse">
              <i class="el-icon-document-copy"></i>仓库信息
            </el-menu-item>
            <el-menu-item index="/main/goods">
              <i class="el-icon-document-copy"></i>物品管理
            </el-menu-item>
            <el-menu-item index="/main/transfer">
              <i class="el-icon-document-copy"></i>移库管理
            </el-menu-item>
            <el-menu-item index="/main/container">
              <i class="el-icon-document-copy"></i>库柜管理
            </el-menu-item>
<!--            <el-menu-item index="/main/availableContainer">-->
<!--              <i class="el-icon-document-copy"></i>可用储位-->
<!--            </el-menu-item>-->
          </el-submenu>
          <el-submenu index="5">
            <template slot="title"><i class="el-icon-s-home"></i>运输管理</template>
            <el-menu-item index="/main/vehicle">
              <i class="el-icon-folder-add"></i>运输车辆信息
            </el-menu-item>
            <el-menu-item index="/main/vehicleDispatching">
              <i class="el-icon-document-copy"></i>派车信息
            </el-menu-item>
            <el-menu-item index="/main/receipt">
              <i class="el-icon-document-copy"></i>回单管理
            </el-menu-item>
            <el-menu-item index="/main/map">
              <i class="el-icon-document-copy"></i>订单监控
            </el-menu-item>
          </el-submenu>
          <el-submenu index="6">
            <template slot="title"><i class="el-icon-s-home"></i>出货管理</template>
            <el-menu-item index="/main/checkout">
              <i class="el-icon-folder-add"></i>出库登记
            </el-menu-item>
            <el-menu-item index="/main/checkoutNotice">
              <i class="el-icon-folder-add"></i>出库通知
            </el-menu-item>
            <el-menu-item index="/main/warehouseTotal">
              <i class="el-icon-document-copy"></i>库存总表
            </el-menu-item>
          </el-submenu>
          <el-submenu index="7">
            <template slot="title"><i class="el-icon-s-home"></i>进货管理</template>
            <el-menu-item index="/main/checkin">
              <i class="el-icon-folder-add"></i>入库登记
            </el-menu-item>
            <el-menu-item index="/main/checkinNotice">
              <i class="el-icon-folder-add"></i>入库通知
            </el-menu-item>
          </el-submenu>
          <el-submenu index="8">
            <template slot="title"><i class="el-icon-s-home"></i>库存预警</template>
            <el-menu-item index="/main/rawWarn">
              <i class="el-icon-folder-add"></i>预警管理
            </el-menu-item>
          </el-submenu>
          <el-submenu index="9">
            <template slot="title"><i class="el-icon-s-home"></i>设备生产管理</template>
            <el-menu-item index="/main/equip">
              <i class="el-icon-folder-add"></i>设备信息
            </el-menu-item>
            <el-menu-item index="/main/equipStatistics">
              <i class="el-icon-folder-add"></i>设备统计明细
            </el-menu-item>
          </el-submenu>
        </el-menu>

      </el-aside>

      <el-container>
        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/main/demov' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>
          </el-breadcrumb>
          <!---->
          <router-view></router-view>
        </el-main>
        <el-footer class="home_footer">2023©ZNCQ</el-footer>
      </el-container>

    </el-container>
    <el-dialog title="修改密码" :visible.sync="dialogFormVisible" width="40%" @close="onClose()">
      <el-form :model="form" ref="elForm" :rules="rules">
        <el-form-item label="以前的密码" prop="oldPassword">
          <el-input type="password" show-password v-model="form.oldPassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input type="password" show-password v-model="form.newPassword"  autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="rePassword">
          <el-input type="password" show-password v-model="form.rePassword" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="pwd">确 定</el-button>
      </div>
    </el-dialog>
  </el-container>
</template>
<script>
export default{
  methods: {
    onClose() {
      this.$refs['elForm'].resetFields()
    },
    pwd(){
        this.$refs['elForm'].validate(valid => {
          if (!valid)return
          if (this.form.rePassword != this.form.newPassword){
            this.$message.error("密码不一致")
            return;
          }
          axios.put('http://localhost:8181/user/pwd',this.form)
                .then(resp=>{
                  let data = resp.data;
                  let code = data.code;
                  if (code == 200){
                   this.$message.success(data.message)
                    this.dialogFormVisible = false;
                    window.localStorage.clear();
                    this.$router.replace("/")
                    this.$message.success("请你重新登入")
                  }else{
                    this.$message.error(data.message)
                  }

                })
                .catch(error=>{
                  this.$message.error(error.message)
                })
        })
    },
    logout(){
      let _this = this;
      this.$confirm('注销登录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        window.localStorage.clear();
        axios.get('http://localhost:8181/logout')
            .then(resp=>{
              let data = resp.data;
              console.log(data)
              if (data.code == 200){
                console.log(data.message);
                _this.$message.success(data.message)
              }
            })
            .catch(error=>{
              _this.$message.error(error.message)
            })
        _this.$router.replace('/')
      })
    }
  },
  data(){
    return {
      dialogFormVisible:false,
      username:'',
      form:{
        oldPassword:null,
        rePassword:null,
        newPassword:null,
      },
      rules:{
        rePassword: [{
          required: true,
          message: '请输入确认密码',
          trigger: 'blur'
        }],
        newPassword: [{
          required: true,
          message: '请输入新密码',
          trigger: 'blur'
        }],
        oldPassword: [{
          required: true,
          message: '请输入旧密码',
          trigger: 'blur'
        }],
      }
    }
  },
  created() {
    axios.get('http://localhost:8181/username')
          .then(resp=>{
            let data = resp.data;
            if (data.code == 200){
              this.username = data.data;
              window.localStorage.setItem('username',data.data);
              this.$notify({
                title: '登入成功',
                message: '欢迎回来' + window.localStorage.getItem('username'),
                type: 'success'
              });
            }
          })
          .catch(error=>{
            this.$message.error(error.message)
          })
    this.$router.replace('/main/demov')
  },

}
</script>
<style>
.home_container {
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
}

.home_header {
  background-color: #2B2B2B;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.home_title {
  color: #C2C2C2;
  font-size: 22px;
  display: inline;
}

.home_userinfo {
  color: #fff;
  cursor: pointer;
}

.home_userinfoContainer {
  display: inline;
  margin-right: 20px;
}

.home_aside {
  background-color: #FFFFFF;
}

.home_footer {
  background-color: #FFFFFF;
  color: #000000;
  font-size: 18px;
  line-height: 60px;
  text-align: center;
}
</style>