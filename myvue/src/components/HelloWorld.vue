<template>
  <div class="login">
    <div class="ms-title">Music 后台登陆</div>
    <div class="ms-login">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
      >
        <el-form-item prop="username">
          <el-input
            v-model="ruleForm.username"
            aria-placeholder="用户名"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            type="password"
            v-model="ruleForm.password"
            aria-placeholder="密码"
          ></el-input>
        </el-form-item>
      </el-form>
      <div class="btn">
        <el-button
          type="primary"
          @click="submitForm"
        >登录</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { getLoginStatus } from '../api/index'
import { mixin } from '../mixs/index'
export default {
  name: 'HelloWord',
  mixins: [mixin],
  data () {
    return {
      ruleForm: { username: 'admin', password: '123' },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入用户名', trigger: 'blur' }]
      }
    }
  },
  methods: {
    submitForm () {
      let params = new URLSearchParams()
      params.append('name', this.ruleForm.username)
      params.append('password', this.ruleForm.password)
      console.log(params)
      getLoginStatus(params).then((res) => {
        console.log(res)
        // eslint-disable-next-line eqeqeq
        if (res.code == 1) {
          localStorage.setItem('username', this.ruleForm.username)
          this.$router.push('/Info')
          this.notify('登录成功', 'success')
        } else {
          this.notify('登录失败', 'error')
        }
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.login {
  position: relative;
  background-image: url("../assets/img/lucky.jpg");
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
  height: 100vh;
  width: 100vw;
}
.ms-title {
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -230px;
  text-align: center;
  font-size: 30px;
  font-weight: 600;
  color: #fff;
}
.ms-login {
  position: absolute;
  top: 25%;
  left: 50%;
  width: 300px;
  height: 170px;
  margin-left: -175px;
  border-radius: 5px;
  padding: 40px;
  background-color: white;
}
.btn {
  text-align: center;
}
.btn button {
  width: 100%;
  height: 35px;
}
</style>
