package com.zby.bookshop.dao;

import com.zby.bookshop.pojo.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface BookMapper {

    Book selectByPrimaryKey(int id);

    int deleteByPrimaryKey(int id);

    int updateByPrimaryKey(Book book);

    int insert(Book book);

    Boolean isRepeatBook(String name);

    List<Book> get50Books();

    int  count();

    int countByCategoryId(int categoryId);
}