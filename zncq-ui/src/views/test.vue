<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen1" @close="onClose1" title="修改用户信息">
      <el-row :gutter="15">
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
      </el-row>
      <div slot="footer">
        <el-button @click="close1">取消</el-button>
        <el-button type="primary" @click="handleConfirm1">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  inheritAttrs: false,
  components: {},
  props: [],
  data() {
    return {
      formData1: {
        userName: undefined,
        nickName: undefined,
        phone: undefined,
        email: undefined,
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
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    onOpen1() {},
    onClose1() {
      this.$refs['elForm1'].resetFields()
    },
    close1() {
      this.$emit('update:visible', false)
    },
    handleConfirm1() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.close()
      })
    },
  }
}

</script>
<style>
</style>
