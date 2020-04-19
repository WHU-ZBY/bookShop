<template>
  <div>
    <div>
      <div v-if="showSongs">
        <div class="myaction">
          <div class="first">
            <div class="left">
              <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="搜索图书" class="myelform">
                  <el-input v-model="searchContent2" placeholder="请输入图书名称"></el-input>
                </el-form-item>
                <el-form-item class="myelform">
                  <el-button type="primary" icon="el-icon-search" @click="onSubmit2">查询</el-button>
                </el-form-item>
              </el-form>
            </div>

            <div class="clearfix"></div>
          </div>
          <div class="clearfix"></div>

          <div class="bottom" v-if="showOption">
            <div class="left">
              <el-button icon="el-icon-plus" type="primary">添加图书</el-button>
            </div>

            <div class="clearfix"></div>
          </div>
          <div class="clearfix"></div>
        </div>

        <el-table
          :data="bookData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
          @selection-change="handleSelectionChange"
          @sort-change="tableSortChange"
          border
          stripe
          style="width: 100%"
          :height="tableHeight"
          max-height="450"
          highlight-current-row
          :cell-style="styleFunc"
          :default-sort="{prop: 'id', order: 'ascending'}"
        >
          <el-table-column prop="id" label="ID" width="50" align="center" show-overflow-tooltip></el-table-column>

          <el-table-column prop="owned" label="状态" width="50" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <i v-if="scope.row.owned" style="color: #11b95c" class="el-icon-success"></i>
              <i v-if="!scope.row.owned" style="color: #6F8A91" class="el-icon-remove"></i>
            </template>
          </el-table-column>

          <el-table-column
            prop="bookName"
            label="图书名字"
            width="150"
            align="center"
            show-overflow-tooltip
          ></el-table-column>

          <el-table-column
            prop="author"
            label="作者"
            width="100"
            align="center"
            show-overflow-tooltip
          ></el-table-column>

          <el-table-column
            prop="price"
            label="价格"
            width="100"
            align="center"
            sortable="'custom'"
            show-overflow-tooltip
          ></el-table-column>

          <el-table-column prop="intro" label="简介" width="150" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column
            prop="createTime"
            label="创建时间"
            width="250"
            align="center"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column v-if="showCart" label="查看详情" width="100" align="center">
            <template slot-scope="scope">
              <el-tooltip class="item" effect="light" content="查看详情" placement="bottom-end">
                <el-button size="mini" type="primary" @click="viewComment(scope.row)">
                  <i class="el-icon-more-outline"></i>
                </el-button>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column v-if="showCart" label="加入购物车" width="100" align="center">
            <template slot-scope="scope">
              <el-tooltip class="item" effect="light" content="加入购物车" placement="bottom-end">
                <el-button size="mini" type="primary" @click="addShopcart(scope.row.id)">
                  <i class="el-icon-circle-plus-outline"></i>
                </el-button>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column v-if="showOption" label="管理" width="182" align="center">
            <template slot-scope="scope">
              <el-tooltip class="item" effect="light" content="编辑" placement="bottom-end">
                <el-button size="mini" type="primary">
                  <i class="el-icon-edit"></i>
                </el-button>
              </el-tooltip>
              <el-tooltip class="item" effect="light" content="删除" placement="bottom-end">
                <el-button size="mini" type="danger">
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
            :page-sizes="[10, 15, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="count"
          ></el-pagination>
        </div>
        <el-menu
          class="el-menu-header"
          mode="horizontal"
          background-color="darkolivegreen"
          text-color="#fff"
          active-text-color="#ffd04b"
        ></el-menu>
      </div>
      <el-dialog :visible.sync="showComment" width="60%">
        <span>
          <div v-if="commentList.length" style="height: 50vh;">
            <el-scrollbar style="height:100%">
              <el-card class="box-card" v-for="item in commentList" :key="item.id">
                <span class="badge">{{item.uid}}: {{ item.content }}</span>
              </el-card>
            </el-scrollbar>
          </div>
          <div align="center" v-else>
            <el-card>暂无评论</el-card>
          </div>
        </span>
        <div v-if="theSelectedOne.owned">
          <span>您已经购买该书籍：可以评论：</span>
          <span>
            <el-input type="textarea" :rows="5" placeholder="请输入评论" v-model="commentContent"></el-input>
          </span>

          <div style="float: right;  ">
            <span slot="footer" class="dialog-footer">
              <el-button type="primary" @click="sendComment">提交评论</el-button>
              <el-button type="primary" @click="showComment = false">关闭评论</el-button>
            </span>
          </div>
        </div>
        <div align="center" v-else>
          <span>您未购买该书籍，不可以评论，可以加入购物车购买后再评论</span>
             <span slot="footer" class="dialog-footer" style="float: right;  " >
              <el-button type="primary" @click="showComment = false">关闭评论</el-button>
            </span>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      commentList: [],
      theSelectedOne: 0,
      commentContent: "",
      showComment: false,
      searchContent2: "",
      showCart: true,
      tableHeight: "400",
      cateID: 0,
      pageSize: "10",
      showFlag: true,
      showCategory: false,
      showSongs: true,
      pageSize: 10,
      showOption: false,
      currentPage: 1,
      count: 20,
      currentLimit: 100,
      searchCatename: "",
      searchContent: "",
      multipleSelection: [],
      form: "",
      tableData: [],
      bookData: [],
      isSerach: false, //是否为提交搜索
      region: "0",
      isLast: false,
      loading: true,
      songData2: [],
      songList: {},
      cateID2: 0,
      addTime: new Date(),
      isRoot: false
    };
  },
  methods: {
    tableSortChange: function(mes) {
      console.log(mes);
      var prop = mes.prop;
      //this.tableData.sort;
      if (mes.order == "descending") {
        this.bookData = this.bookData.sort((a, b) => b[prop] - a[prop]);
      } else if (mes.order == "ascending") {
        this.bookData = this.bookData.sort((a, b) => a[prop] - b[prop]);
      } else {
        this.bookData = this.bookData.sort((a, b) => a["id"] - b["id"]);
      }
    },
    onSubmit2() {
      if (this.searchContent2 == "") {
        this.$message.info("搜索内容为空");
        this.get50Books();
      } else {
        this.loading = true;
        this.isSerach = true;
        this.bookData = this.bookData.filter(
          t => t.bookName.indexOf(this.searchContent2) != -1
        );
      }
    },

    sendComment() {
      var url = "http://106.15.182.82:8080/writeComment";
      var params = {
        bookid: this.theSelectedOne.id,
        uid: this.$uid.value,
        content: this.commentContent
      };
      this.axios
        .post(url, params, {})
        .then(res => {
          console.log(res.data);
          this.count = this.tableData.length;
          this.$message.success("评论成功");
          this.refreshComment();
          this.commentContent ='';
        })
        .catch(res => {
          // console.log(this.tableData);
          this.loading = false;
          console.log("error");
        });
    },
    refreshComment(){
       this.axios
        .get("http://106.15.182.82:8080/getCommentByBookId", {
          params: {
            bookId: this.theSelectedOne.id
          }
        })
        .then(res => {
          console.log(res.data);
          this.commentList = res.data;
        })
        .catch(res => {
          // console.log(this.tableData);
          this.loading = false;
          console.log("error");
        });
    },
    viewComment(seleted) {
      this.showComment = true;
      this.theSelectedOne = seleted;
      this.axios
        .get("http://106.15.182.82:8080/getCommentByBookId", {
          params: {
            bookId: this.theSelectedOne.id
          }
        })
        .then(res => {
          console.log(res.data);
          this.commentList = res.data;
        })
        .catch(res => {
          // console.log(this.tableData);
          this.loading = false;
          console.log("error");
        });
    },
    get50Books() {
      this.axios
        .get("http://106.15.182.82:8080/get50Books")
        .then(res => {
          console.log(res.data);
          this.loading = false;
          if (res.data.length != 0) {
            this.bookData = [];
            this.bookData = res.data;
            this.count = res.data.length;
            this.currentPage = 1;

            var temList = [];
            this.bookData.forEach(book => {
              this.axios
                .get("http://106.15.182.82:8080/getBookWrapByBookId", {
                  params: {
                    bookId: book.id
                  }
                })
                .then(res => {
                  var temp = res.data;
                  temp.owned = false;
                  this.$myBook.value.forEach(book => {
                    if (book.id == temp.id) {
                      temp.owned = true;
                    }
                  });
                  console.log(temp);
                  temList.push(temp);
                })
                .catch(res => {
                  // console.log(this.tableData);
                  this.loading = false;
                  console.log("error");
                });
            });
            this.bookData = temList;
            console.log(this.bookData);
          } else {
            this.bookData = [];
            this.showCategory = false;
            this.showSongs = true;
            this.$message.error("暂时没有图书!");
          }
        })
        .catch(res => {
          this.loading = false;
          console.log("error");
        });
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
        })
        .catch(res => {
          // console.log(this.tableData);
          this.loading = false;
          console.log("error");
        });
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
    },
    styleFunc(row, rowIndex) {
      //更改样式
      return { padding: "5px 0", hieght: "23px", lineHeight: "23px" };
    },
    handleSelectionChange(val) {
      //处理多选框变化
      this.multipleSelection = val;
    },

    addShopcart(id) {
      console.log(id);
      //var bookid = this.bookData[id].id
      // var myDate = new Date();
      // myDate.toLocaleDateString(); //获取当前日期
      if (this.$shopCart.value.indexOf(id) > -1) {
        this.$message.info("购物车已有该电子书，不必重复添加");
      } else if (this.$myBook.value.filter(book => book.id == id).length > 0) {
        this.$message.info("您已购买该电子书,不必重复购买");
      } else {
        this.$shopCart.value.push(id);
        console.log(this.$shopCart.value);
      }
    }
  },
  mounted() {
    //判断登录用户类型，管理员显示操作
    var str = sessionStorage.getItem("isRoot");
    if ((str = "true")) {
      this.isRoot = true;
    }
    this.refreshOrders();
    //加载页面时默认请求第一类型数据
    this.get50Books();

    var target2 = this;
    var lastPage = 0;

    //监听增加书籍发来的信息
   
  }
};
</script>

<style scoped>
.table1 {
  height: 500px;
}
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

a:link {
  text-decoration: none;
}
a {
  font-size: 20px;
  color: black;
}
.el-menu-header {
  left: 0;
  position: fixed;
  bottom: 0;
  width: 100%;
  z-index: 100;
}
</style>

