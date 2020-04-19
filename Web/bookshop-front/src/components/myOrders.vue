<template>
  <div>


    <el-table
      :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
      @selection-change="handleSelectionChange"
      border
      stripe
      style="width: 100%"
      :height="tableHeight"
      max-height="450"
      highlight-current-row
      :cell-style="styleFunc"
    >
      <el-table-column prop="id" label="书id" width="140" align="center" show-overflow-tooltip></el-table-column>
      <el-table-column prop="bookName" label="书名" width="140" align="center" show-overflow-tooltip></el-table-column>

      <el-table-column prop="author" label="作者" width="140" align="center" show-overflow-tooltip></el-table-column>

      <el-table-column
        prop="createTime"
        label="书创建时间"
        width="180"
        align="center"
        show-overflow-tooltip
      ></el-table-column>
      <el-table-column prop="intro" label="书的简介" width="150" align="center" show-overflow-tooltip></el-table-column>

      <el-table-column width="130" label="删除订单" align="center">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="light" content="删除" placement="bottom-end">
            <el-button size="mini" type="danger" @click="handleDel(scope.row)">
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
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="count"
      ></el-pagination>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      commentContent: '',
      showComment: false,
      pageSize: 5,
      tableHeight: 300,
      currentPage: 1,
      count: 10,
      currentLimit: 20,
      searchContent: "",
      multipleSelection: [],
      form: "",
      tableData: [],
      isSerach: false, //是否为提交搜索
      isLast: false,
      loading: true,
      userEntity: {},
      user_id: 1
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
    toComment(val) {
      this.showComment = true;
    },
    handleDel(val) {
      //删除一条记录

      this.$confirm("删除该订单？请确保已保存电子书至本地, 是否继续", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.loading = true;
          this.axios
            .get("http://106.15.182.82:8080/deleteShopping", {
              params: { bookId: val.id, uid: this.$uid.value }
            })
            .then(res => {
              if (res.data == 1) {
                this.$message.success("移除成功！");
              }
              this.refreshOrders();
            });
        })
        .catch(() => {});
    },
    handleSizeChange(val) {
      //更改每页的数量,重新发起请求
      this.loading = true;
      this.currentLimit = val;
      this.pageSize = val;
      this.tableHeight = val * 40;
    },
    handleCurrentChange(val) {
      //更改当前页,重新发起请求
      this.loading = true;
      this.currentPage = val;
    },
    styleFunc(row, rowIndex) {
      //更改样式
      return { padding: "5px 0", hieght: "23px", lineHeight: "23px" };
    },
    handleSelectionChange(val) {
      //处理多选框变化
      this.multipleSelection = val;
    },
    refreshOrders() {
      this.tableData = [];

      this.axios
        .get("http://106.15.182.82:8080/getUserShoppings", {
          params: {
            uid: this.$uid.value
          }
        })
        .then(res => {
          console.log(res.data);
          this.$myBook.value = res.data;
          this.tableData = res.data;
          this.count = this.tableData.length;
        })
        .catch(res => {
          // console.log(this.tableData);
          this.loading = false;
          console.log("error");
        });
      console.log(this.tableData);
      this.loading = false;
    }
  },
  mounted() {
    //加载页面时默认请求第一类型数据
    this.refreshOrders();
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

