package com.zby.bookshop.dao;

import com.zby.bookshop.pojo.Comment;

import java.util.List;

public interface CommentMapper {
    int insert(Comment comment);
    int deleteById(int id);
    List<Comment> selectByBookId(int bookId);
    List<Comment> selectByUid(int uid);

}
