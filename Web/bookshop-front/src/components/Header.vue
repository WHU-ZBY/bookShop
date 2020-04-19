<template>
  <el-menu
    :default-active="activeIndex2"
    class="el-menu-header"
    mode="horizontal"
    background-color="#55AAAA"
    text-color="#fff"
    active-text-color="#ffd04b"
  >
    <div id="title2">WHU网上书店</div>
    <div id="title3">[当前账户{{this.nickname}}]</div>
    <div class="div2">
      <el-button type="danger" @click="tuichu">退出登录</el-button>
    </div>
  </el-menu>
</template>

<script>
export default {
  data() {
    return {
      isRoot: false,
      nickname: 0,
      activeIndex: "1",
      activeIndex2: "1"
    };
  },
  methods: {
    tuichu() {
      sessionStorage.clear();
      this.$router.push({
                path: '/'
              });
    }
  },
  mounted() {
    var str1 = sessionStorage.getItem("uid");
    this.$uid.value = str1;
    var str2 = sessionStorage.getItem("nickname");
    this.$nickname.value = str2;
    this.nickname = str2;
    console.log(str2);
  }
};
</script>

<style scoped>
.div2 {
  float: right;
  margin-right: 30px;
  margin-top: 10px;
}
a:link {
  text-decoration: none;
}
a {
  font-size: 20px;
  color: black;
}
.el-menu-header {
  width: 100%;
  height: 7%;
  float: right;
}

img {
  height: 40px;
  width: 40px;
  border-radius: 50px;
}
#title {
  height: 60px;
  line-height: 60px;
  font-size: 28px;
  float: left;
  color: aqua;
  margin-left: 30px;
}
#title2 {
  height: 60px;
  line-height: 60px;
  font-size: 28px;
  float: left;
  color: #ffffff;
  margin-left: 30px;
}
#title3 {
  height: 40px;
  line-height: 60px;
  font-size: 28px;
  float: left;
  color: #ffffff;
  margin-left: 30px;
}
#notice {
  float: right;
  margin-right: 60px;
  margin-top: 10px;
}
</style>

