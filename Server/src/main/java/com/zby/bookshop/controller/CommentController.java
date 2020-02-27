package com.zby.bookshop.controller;

import com.zby.bookshop.pojo.Comment;
import com.zby.bookshop.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "writeComment")
    public int writeComment(@RequestBody Comment comment)
    {
        return commentService.insert(comment);
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "getCommentByBookId")
    public List<Comment> getCommentByBookId(int bookId)
    {
        return commentService.selectByBookId(bookId);
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "getCommentByUId")
    public List<Comment> getCommentByUId(int uid)
    {
        return commentService.selectByUid(uid);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "deleteCommentById")
    public int deleteCommentById(int id)
    {
        return commentService.deleteById(id);
    }





}
