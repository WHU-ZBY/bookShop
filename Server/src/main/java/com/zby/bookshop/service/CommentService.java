package com.zby.bookshop.service;

import com.zby.bookshop.dao.CommentMapper;
import com.zby.bookshop.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public int insert(Comment comment)
    {
        return commentMapper.insert(comment);
    }

    public  int deleteById(int id)
    {
        return commentMapper.deleteById(id);
    }

    public List<Comment> selectByBookId(int bookId)
    {
        return commentMapper.selectByBookId(bookId);
    }

    public List<Comment> selectByUid(int uid)
    {
        return commentMapper.selectByUid(uid);
    }
}
