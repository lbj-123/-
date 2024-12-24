<template>
  <div>
    <el-form :rules="rules"
             v-loading="loading"
             element-loading-text="正在注册...."
             element-loading-spinner="el-icon-loading"
             element-loading-background="rgba(0, 0, 0, 0.8)"
             ref="registerForm"
             :model="registerForm"
             class="registerContainer">
      <h3 class="registerTitle">商城系统注册</h3>
      <el-form-item prop="username">
        <el-input type="text" auto-complete="false" v-model="registerForm.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" auto-complete="false" v-model="registerForm.password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item prop="role">
        <el-select v-model="registerForm.role" placeholder="请选择角色">
          <el-option label="商家" value="seller"></el-option>
          <el-option label="顾客" value="customer"></el-option>
          <el-option label="游客" value="guest"></el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" style="width: 100%; margin-bottom: 15px" @click="submitRegisterForm">注册</el-button>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      registerForm: {
        username: '',
        password: '',
        role: '',
        enabled: true
      },
      loading: false,
      rules: {
        username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}],
        role: [{required: true, message: '请选择角色', trigger: 'blur'}],
      }
    };
  },
  methods: {
    // 提交注册表单
    async submitRegisterForm() {
      // 开始加载动画
      this.loading = true;

      // 表单验证通过后，执行请求
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          // 使用封装的 postRequest 方法发送请求
          this.postRequest('/users/add', this.registerForm).then(resp => {
            if(resp){
              this.loading = false;
              this.$router.replace('/');
            }else{
              this.$message.error('注册请求失败');
            }
          })
        } else {
          // 表单验证失败
          this.$message.error('请填写完整信息');
        }
      });
    }
  }
}
</script>

<style scoped>
.registerContainer{
      border-radius: 15px;
      background-clip: padding-box;
      margin: 180px auto;
      width: 350px;
      padding: 15px 35px 15px 35px;
      background: #fff;
      border:1px solid #eaeaea;
      box-shadow: 0 0 25px #cac6c6;
}

.registerTitle{
  margin: 0px auto 40px auto;
  text-align: center;
}
</style>
