package com.zby.bookshop.controller;

import com.zby.bookshop.pojo.Book;
import com.zby.bookshop.pojo.User;
import com.zby.bookshop.service.BookService;
import com.zby.bookshop.service.RootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RootController  {

    @Autowired
    private RootService rootService;
    @Autowired
    private BookService bookService;


    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getRoot")
    public List<User> getRoot()
    {
        return rootService.getRoot();
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/makeRoot")
    public int makeRoot(int uid)
    {
        return rootService.makeRoot(uid);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/deleteRoot")
    public int deleteRoot(int uid)
    {
        return rootService.deleteRoot(uid);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getUser")
    public List<User> getUser()
    {
        return rootService.getUser();
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/deleteUser")
    public int deleteUser(int uid)
    {
        return rootService.deleteUser(uid);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/countUser")
    public int countUser()
    {
        return rootService.count();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/countBook")
    public int getBookCount()
    {
        return rootService.bookCount();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getBook")
    public List<Book> getBook()
    {
        return bookService.get50Books();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getBookCountByCategoryId")
    public int getBookCountByCategoryId(int categoryId)
    {
        return rootService.getBookCountByCategoryId(categoryId);
    }
}
