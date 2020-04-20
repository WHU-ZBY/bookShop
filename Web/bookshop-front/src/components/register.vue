<template>
  <el-form
    :model="form"
    :rules="rules2"
    ref="form"
    label-position="left"
    label-width="0px"
    class="demo-ruleForm login-container"
  >
    <h3 class="title">网上书店注册</h3>
    <el-form-item prop="nickname">
      <el-input type="text" v-model="form.nickname" auto-complete="off" placeholder="昵称"></el-input>
    </el-form-item>
    <el-form-item prop="text">
      <el-input type="text" v-model="form.email" auto-complete="off" placeholder="邮箱"></el-input>
    </el-form-item>
    <el-form-item prop="checkPass">
      <el-input type="password" v-model="form.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item prop="checkPass">
      <el-input type="password" v-model="form.repassword" auto-complete="off" placeholder="重新输入密码"></el-input>
    </el-form-item>
    <el-form-item style="width:100%;">
      <br />
      <p></p>
      <el-button type="primary" style="width:100%;" @click="register" :loading="loading">注册</el-button>
      <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
    </el-form-item>
  </el-form>
</template>


<script>
//   import { requestLogin } from '../api/api';
import NProgress from "nprogress";
import jwt_decode from "jwt-decode";
export default {
  data() {
    return {
      userData: {},
      userInfo: {},
      loading: false,
      isRoot: false,
      form: {
        email: "",
        password: "",
        repassword: "",
        nickname: ""
      },
      rules2: {
        name: [
          { required: true, message: "请输入用户名", trigger: "blur" }
          //{ validator: validaePass }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" }
          //{ validator: validaePass2 }
        ]
      },
      checked: true
    };
  },
  watch: {
    "this.form": function(newValue) {
      console.log(newValue);
    }
  },
  methods: {
    login() {
      //登录
      if (this.form.account == "") {
        this.$message.error("用户名不能为空！");
      } else if (this.form.password == "") {
        this.$message.error("密码不能为空！");
      } else var params = new URLSearchParams();
      this.axios
        .get("http://106.15.182.82:8080/login", {
          params: { uid: this.form.account, password: this.form.password }
        })
        .then(res => {
          if (res.data != false) {
            this.$uid.value = this.form.account;
            console.log(res.data);
            this.userToken = res.data;
            this.logining = true;
            this.axios
              .get("http://106.15.182.82:8080/isRoot?0", {
                params: { uid: this.form.account }
              })
              .then(res => {
                sessionStorage.clear();
                sessionStorage.setItem("uid", this.$uid.value);
              });
            this.$message.success("登录成功！");
            NProgress.start();
            setTimeout(() => {
              this.logining = false;
              NProgress.done();
              this.$router.push({
                name: "Home"
              });
            }, 1000);
          } else {
            this.$message.error("账户或密码错误！");
            this.logining = false;
            console.log("error");
          }
        })
        .catch(res => {
          this.$message.error("账户或密码错误！");
          this.logining = false;
          console.log(res);
        });
    },
    eeelogin(ev) {
      var _this = this;
      this.$refs.form.validate(valid => {
        if (valid) {
          let loginParams = {
            username: this.form.username,
            password: this.form.password
          };
          this.logining = true;
          NProgress.start();
          setTimeout(() => {
            _this.logining = false;
            NProgress.done();
            _this.$router.push({ path: "/Home" });
          }, 1000);
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    register() {
      var failstr = "未知";
      if (this.form.email == "") {
        this.$message.error("邮箱不能为空！");
      } else if (this.form.password == "") {
        this.$message.error("密码不能为空！");
      } else if (this.form.password == "") {
        this.$message.error("密码不能为空！");
      }else 
      {this.axios
        .get("http://106.15.182.82:8080/register", {
          params: {
            nickname: this.form.nickname,
            password: this.form.password,
            re_password: this.form.repassword,
            email: this.form.email
          }
        })
        .then(res => {
          if (res.data != false) {
            switch (res.data) {
              case 1:
                failstr = "昵称已被注册";
                break;

              case 2:
                failstr = "邮箱格式有误";
                break;

              case 3:
                failstr = "密码验证出错";
                break;
            }
            this.$uid.value = this.form.account;
            console.log(res.data);
            this.userToken = res.data;
            const decoded = jwt_decode(res.data);
            console.log(decoded);
            this.$uid.value = decoded.userId;
            this.$nickname.value = decoded.loginName;
            this.$confirm(
              "注册成功，您账号ID为" + decoded.userId + "请保留ID，并记住密码",
              "提示",
              {
                confirmButtonText: "前往书店",
                type: "success"
              }
            ).then(() => {
              sessionStorage.clear();
              sessionStorage.setItem("uid", this.$uid.value);
              sessionStorage.setItem("nickname", this.$nickname.value);
              this.$router.push({
                name: "Home"
              });
            }).catch(()=>{
              this.$router.push({
                path: '/'
              });
              });

          }
        })
        .catch(res => {
          this.loading = false;
          console.log("error");
          this.$message.error("注册失败,原因：" + failstr);
        });}
    }
  }
};
</script>

<style lang="scss" scoped>
.login-container {
  /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
  -webkit-border-radius: 5px;
  border-radius: 5px;
  -moz-border-radius: 5px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
  .title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
  .remember {
    margin: 0px 0px 35px 0px;
  }
}
</style>
