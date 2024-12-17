<template class="body">
    <div class="login-container">
      <el-form :model="ruleForm" :rules="rules"
               status-icon
               ref="ruleForm"
               label-position="left"
               label-width="0px"
               class="demo-ruleForm login-page">
        <h3 class="title">智能厂区系统登录</h3>
        <!--      prop 用来和对应属性绑定使该属性对应的框非空-->
        <el-form-item prop="username">
          <el-input type="text"
                    v-model="ruleForm.username"
                    placeholder="用户名"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password"
                    v-model="ruleForm.password"
                    placeholder="密码"
          ></el-input>
        </el-form-item>
        <el-form-item style="width:100%;">
          <el-button type="primary" style="width:100%;" @click="handleSubmit" :loading="logining">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script>
export default {
  name: "Login",
  data(){
    return{
      logining: false,
      ruleForm: {
        username: 'admin1',
        password: '123456'
      },
      rules: {
        username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}]
      },
    }
  },
  methods: {
    handleSubmit(){
      this.$refs.ruleForm.validate((valid) => {
        if(valid){
          this.logining = true
          // let _this = this
          let login = this.$qs.stringify(this.ruleForm);
          axios.post('http://localhost:8181/login',login,{
            headers:{
              'Content-Type':'application/x-www-form-urlencoded'
            }
          }).then(resp=>{
            let data =  resp.data
            let code = data.code;
            if (code == 200){
              window.localStorage.setItem("token",data.data);
              this.$router.replace('/main')
            }else{
              this.$message.error(data.message);
              this.logining = false;
            }
          }).catch(error=>{
            console.log(error.message)
          })
        }
      })
    }
  }
};
</script>

<style scoped>
.login-container {
  width: 100%;
  height: 100%;
}
.login-page {
  -webkit-border-radius: 5px;
  border-radius: 5px;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
label.el-checkbox.rememberme {
  margin: 0px 0px 15px;
  text-align: left;
}
</style>