package com.zby.bookshop.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BookWrap {
    private Integer id;//bookid

    private String bookName;

    private String author;

    private Date createTime;

    private String intro;

    List<Integer> CategoryList;

    private BigDecimal price;

    private Integer likecount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<Integer> getCategoryList() {
        return CategoryList;
    }

    public void setCategoryList(List<Integer> categoryList) {
        CategoryList = categoryList;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getLikecount() {
        return likecount;
    }

    public void setLikecount(Integer likecount) {
        this.likecount = likecount;
    }
}
