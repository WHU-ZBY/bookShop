<template>
    <el-menu
      :default-active="activeMenu"

      class="el-menu-vertical"
      @select="handleSelect"
      @open="handleOpen"
      @close="handleClose"
      background-color= #5E94A2
      text-color="#fff"
      active-text-color="#ffd04b">
      <el-menu-item index="1">
        <i class="el-icon-upload"></i>
        <span slot="title">首页</span>
      </el-menu-item>


      <!-- <el-menu-item index="3" v-if="showMenu">
          <i class="el-icon-location"></i>
          <span slot="title">用户管理</span>
      </el-menu-item> -->

      <!-- <el-menu-item index="4">
          <i class="el-icon-menu"></i>
          <span slot="title">图书更新消息</span>
      </el-menu-item> -->
      <el-menu-item index="5" v-if="showMenu2">
        <i class="el-icon-menu"></i>
        <span slot="title">购物车</span>
      </el-menu-item>
      <!-- <el-menu-item index="6" v-if="showMenu2">
        <i class="el-icon-menu"></i>
        <span slot="title">地址管理</span>
      </el-menu-item> -->
      <el-menu-item index="7" v-if="showMenu2">
        <i class="el-icon-menu"></i>
        <span slot="title">我的订单</span>
      </el-menu-item>
      <!-- <el-menu-item index="8" v-if="showMenu2">
        <i class="el-icon-menu"></i>
        <span slot="title">个人资料</span>
      </el-menu-item> -->
    </el-menu>


</template>

<script>
    export default {
    data(){
      return {
        showMenu:true,
        showMenu2:true,
        activeMenu: '1'
      }
    },
    mounted(){
      var userJsonStr = sessionStorage.getItem('userInfo');
      this.userEntity = JSON.parse(userJsonStr);
      // console.log(this.userEntity.type);
      // if (this.userEntity.qq == 2) {
      //   this.showMenu = true;
      // }
      // else if(this.userEntity.qq == 1){
      //   this.showMenu2 = true;
      // }
      if(sessionStorage.getItem('curRouter'))this.activeMenu = '' + sessionStorage.getItem('curRouter')
      this.$router.push(sessionStorage.getItem('curRouter') || this.activeMenu)
    },
    methods: {
      handleSelect(key, keyPath){
        this.activeMenu = '' + key
        sessionStorage.setItem('curRouter', this.activeMenu)
        this.$router.push(this.activeMenu)
      },
      handleOpen(key, keyPath) {

      },
      handleClose(key, keyPath) {

      }
    }
  }
</script>

<style scoped>
  .el-menu-vertical{
    width: 100%;
    height: 100%;
    overflow-x: hidden;
   overflow-y: auto;
  }
</style>
