<template>
  <div>
    <div class="myaction">
      <div class="first">
        <!--<div class="left">
          <el-form :inline="true" class="demo-form-inline">
            <el-form-item label="搜索购物车" class="myelform">
              <el-input v-model="searchContent" placeholder="请输入图书名称"></el-input>
            </el-form-item>
            <el-form-item  class="myelform">
              <el-button type="primary" @click="onSubmit" icon="el-icon-search">搜索</el-button>
            </el-form-item>
          </el-form>
        </div>-->

        <div class="clearfix"></div>
      </div>
      <div class="clearfix"></div>

      <div class="clearfix"></div>
    </div>

    <el-table
      :data="tableData"
      @selection-change="handleSelectionChange"
      border
      stripe
      style="width: 100%"
      :height="450"
      max-height="100000"
      highlight-current-row
      :cell-style="styleFunc"
    >
      <el-table-column type="selection" width="60" align="center"></el-table-column>
      <el-table-column prop="id" label="ID" width="70" align="center" show-overflow-tooltip></el-table-column>
      <el-table-column
        prop="bookName"
        label="图书名称"
        width="150"
        align="center"
        show-overflow-tooltip
      ></el-table-column>
      <el-table-column prop="author" label="作者" width="100" align="center" show-overflow-tooltip></el-table-column>
      <el-table-column prop="price" label="价格" width="100" align="center" show-overflow-tooltip></el-table-column>

      <el-table-column label="管理" width="200" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="light" content="删除" placement="bottom-end">
            <el-button size="mini" type="danger" @click="handleDel(scope.row.id)">
              <i class="el-icon-delete"></i>
            </el-button>
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
        :page-size="10000"
        layout="total"
        :total="count"
      ></el-pagination>
    </div>
    <h3>总价￥：{{totalPrice}}.00 元</h3>
    <el-button type="primary" @click="buyThis" icon="el-icon-success">确认购买</el-button>
  </div>
</template>

<script>
import qs from "qs";
export default {
  data() {
    return {
      pageSize: 5,
      tableHeight: 250,
      key: "",
      currentPage: 1,
      count: 0,
      currentLimit: 20,
      searchContent: "",
      multipleSelection: [],
      form: "",
      tableData: [],
      isSerach: false, //是否为提交搜索
      isLast: false,
      loading: true,
      userEntity: {},
      buyData: [],
      addressData: {},
      totalPrice: 0
    };
  },
  methods: {
    onSubmit() {
      //提交搜索内容
      if (this.searchContent == "") {
        this.$message.error("搜索内容不能为空！");
      } else {
        this.loading = true;
        this.isSerach = true;
        this.axios
          .get("http://localhost:8080/searchQuestions", {
            params: {
              page: this.currentPage,
              limit: this.currentLimit,
              data: this.searchContent
            }
          })
          .then(res => {
            this.loading = false;
            this.tableData = [];
            this.tableData = res.data.data;
            this.count = res.data.count;
            this.currentPage = 1;
          })
          .catch(res => {
            this.loading = false;
            console.log("error");
          });
      }
    },
    handleDel(id) {
      //删除一条记录
      this.$confirm("将该商品移除购物车？, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$shopCart.value.splice(this.$shopCart.value.indexOf(id), 1);
          this.$message({
            type: "success",
            message: "删除成功"
          });
          this.refreshTable();
        })
        .catch(() => {
          this.$message({
            type: "fail",
            message: "删除失败"
          });
        });
      // this.refreshTable();
    },

    styleFunc(row, rowIndex) {
      //更改样式
      return { padding: "5px 0", hieght: "23px", lineHeight: "23px" };
    },
    handleSelectionChange(val) {
      //处理多选框变化
      if (val == 0) return;
      this.multipleSelection = val;
      this.totalPrice = 0;
      console.log(this.multipleSelection);
      this.multipleSelection.forEach(book => {
        this.totalPrice = this.totalPrice + book.price;
      });
    },
    /*computeTotal(){
      var totalItem = this.multipleSelection;
      var count = totalItem.length;
      this.totalPrice = 0;
      if (count == 0){
        this.$message.error('未选中商品！！！');
      }
      else {
        for(var i = 0; i < count; i++){
          this.totalPrice += totalItem[i].price;
        }
      }
      },*/
    buyThis() {
      //批量删除
      var delTarget = this.multipleSelection;
      console.log(this.multipleSelection);
      var count = delTarget.length;
      var str = "";
      if (count == 0) {
        this.$message.error("请至少选择一项购买");
        return false;
      } else {
        this.$confirm("确定购买吗？一共"+count+"本电子书，总价"+this.totalPrice+"元钱", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          for (var i = 0; i < count; i++) {
            var url = "http://106.15.182.82:8080/buyBook";
            var params = {
              bookid: delTarget[i].id,
              uid: this.$uid.value
            };
            this.axios
              .post(url, params, {})
              .then(res => {
                console.log(res.data);
                this.count = this.tableData.length;
              })
              .catch(res => {
                console.log(res);
                // console.log(this.tableData);
                this.loading = false;
                console.log("error");
              });
            this.$shopCart.value = this.$shopCart.value.filter(
              t => t != delTarget[i].id
            );
          }
          this.refreshTable();
          this.totalPrice = 0;
        });
      }
    },
    refreshTable() {
      this.tableData = [];
      this.$shopCart.value.forEach(bookid => {
        this.axios
          .get("http://106.15.182.82:8080/getBookWrapByBookId", {
            params: {
              bookId: bookid
            }
          })
          .then(res => {
            console.log(res.data);
            this.tableData.push(res.data);
            this.count = this.tableData.length;
          })
          .catch(res => {
            // console.log(this.tableData);
            this.loading = false;
            console.log("error");
          });
      });
      console.log(this.tableData);
      this.loading = false;
    },
    handleSizeChange(val) {
      //更改每页的数量,重新发起请求
      this.loading = true;
      this.pageSize = val;
      this.tableHeight = val * 40;
    },
    handleCurrentChange(val) {
      //更改当前页,重新发起请求
      this.loading = true;
      this.currentPage = val;
    }
  },

  mounted() {
    //加载页面时默认请求第一类型数据
    this.refreshTable();
  }
};
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
.clearfix {
  clear: both;
}

.myelform {
  margin-bottom: 5px;
}

.block {
  margin: 10px;
}
</style>

