<template>
  <div id="app">
    <div >
      <div id="title">网上书店</div>
       <el-menu mode="horizontal" background-color="0"></el-menu>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    startHacking () {
      this.$notify({
        title: 'It works!',
        type: 'success',
        message: 'We\'ve laid the ground work for you. It\'s time for you to build something epic!',
        duration: 5000
      })
    }
  }
}
</script>

<style>
#app {
  font-family: Helvetica, sans-serif;
  text-align: center;
  background: #ffffff;
}
#title {
  font-family: Helvetica, sans-serif;
  text-align: center;
  font-size: 12px;

}
</style>
