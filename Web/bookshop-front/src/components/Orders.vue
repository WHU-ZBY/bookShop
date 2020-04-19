<template>
  <div>
  <div>
    <div class="myaction">
      <div class="first">
        <div class="left">
          <el-form :inline="true" class="demo-form-inline">
            <el-form-item label="搜索订单" class="myelform">
              <el-input v-model="searchContent" placeholder="请输入订单号"></el-input>
            </el-form-item>
            <el-form-item  class="myelform">
              <el-button type="primary" @click="onSubmit" icon="el-icon-search">搜索</el-button>
            </el-form-item>
          </el-form>
        </div>

        <div class="clearfix"></div>
      </div>
      <div class="clearfix"></div>

      <div class="clearfix"></div>
    </div>

    <el-table
      :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
      @selection-change="handleSelectionChange"
      border
      stripe
      style="width: 100%"
      :height="tableHeight"
      max-height="450"
      highlight-current-row
      :cell-style="styleFunc">

      <el-table-column
        prop="order_num"
        label="订单号"
        width="100"
        align="center"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="username"
        label="用户"
        width="140"
        align="center"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="totalPrice"
        label="总价"
        width="80"
        align="center"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="address"
        label="收货地址"
        width="250"
        align="center"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="state"
        label="支付状态"
        width="80"
        align="center"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="180"
        align="center"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="payment"
        label="支付方式"
        width="150"
        align="center"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="payTime"
        label="支付时间"
        width="150"
        align="center"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        label="管理"
        width="200"
        align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="light" content="编辑" placement="bottom-end">
            <el-button size="mini" type="primary" @click="handleEdit(scope.row)"><i class="el-icon-edit"></i></el-button>
          </el-tooltip>
          <el-tooltip class="item" effect="light" content="删除" placement="bottom-end">
            <el-button  size="mini" type="danger" @click="handleDel(scope.row.order_num)"><i class="el-icon-delete"></i></el-button>
          </el-tooltip>

        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="count">
      </el-pagination>
    </div>
  </div>
  <editOrder></editOrder>
  </div>
</template>

<script>


  export default {
    data () {
      return {
        pageSize:5,
        tableHeight:200,
        currentPage:1,
        count:'',
        currentLimit:200,
        searchContent:"",
        multipleSelection: [],
        form:"",
        tableData:[],
        isSerach:false,  //是否为提交搜索
        isLast:false,
        loading:true,
        userEntity:{},
        user_id:1,
      }
    },

    methods:{

      onSubmit(){  //提交搜索内容
        if ( this.searchContent == "") {
          this.$message.error('搜索内容不能为空！');
        }else{
          this.loading = true;
          this.isSerach = true;
          this.axios.get('http://localhost:8200/order/searchOrder',{
            params: {
              id:this.searchContent
            }
          })
            .then( res => {
              this.loading = false;
              this.tableData = [];
              this.tableData = res.data.data;
              this.count = res.data.count;
              this.currentPage = 1;
            })
            .catch( res => {
              this.loading = false;
              console.log("error");
            })
        }
      },
      handleDel(id){   //删除一条记录
        this.$confirm('删除该订单？, 是否继续?', '提示',{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true;
          var params = new URLSearchParams();
          params.append("id",id);
          this.axios.post('http://localhost:8200/order/delOrder',params)
            .then( res => {
              this.$message.success('移除成功！');
              //删除成功后重新请求当前页
              this.axios.get('http://localhost:8200/order/getOrders',{
                params: {
                }
              })
                .then( res => {
                  this.loading = false;
                  if (res.data.total != 0) {
                    this.tableData = res.data.data;
                    this.count = res.data.data.length;
                  }
                })
                .catch( res => {
                  this.loading = false;
                  console.log("error");
                })
            })
            .catch( res => {
              this.loading = false;
              this.$message.error('删除失败！');
            })
        }).catch(() => {
        });
      },
      handleSizeChange(val) {  //更改每页的数量,重新发起请求
        this.loading = true;
        this.currentLimit = val;
        this.tableHeight = val * 40;
      },
      handleCurrentChange(val) {  //更改当前页,重新发起请求
        this.loading = true;
        this.currentPage = val;
      },
      styleFunc(row,rowIndex){   //更改样式
        return {padding:'5px 0',hieght:'23px',lineHeight:'23px'}
      },
      handleSelectionChange(val) {   //处理多选框变化
        this.multipleSelection = val;
      },
    },
    mounted(){ //加载页面时默认请求第一类型数据
      var userJsonStr = sessionStorage.getItem('userInfo');
      this.userEntity = JSON.parse(userJsonStr);
      console.log(this.userEntity.id);
      this.axios.get('http://localhost:8200/order/getOrders',{
        params: {
          userId:this.userEntity.id
        }
      })
        .then( res => {
          this.loading = false;
          if (res.data.total != 0) {
            this.tableData = res.data.data;
            this.count = res.data.data.length;
          }

        })
        .catch( res => {
          this.loading = false;
          console.log("error");
        })

      var target = this;
      //监听增加题目发来的信息
    },
  }
</script>

<style scoped>

  .myaction {
    margin-top: 15px;
  }

  .first {
    vertical-align: top;
  }

  .left {
    margin-left: 10px;
  }

  .left div {
    display: inline-block;
  }

  .left span,
  .left div,
  .left button {
    float: left;
    margin-left: 20px;
  }

  .left span {
    display: inline-block;
    height: 38px;
    line-height: 38px;
  }

  .right {
    float: right;
    margin-right: 25px;
  }

  .bottom {
    margin: 10px 20px;
  }

  .bottom button {
    margin-right: 10px;
  }
  .clearfix{
    clear:both;
  }

  .myelform{
    margin-bottom: 5px;
  }

  .block{
    margin:10px;
  }


</style>

