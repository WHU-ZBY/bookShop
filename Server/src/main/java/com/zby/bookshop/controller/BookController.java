package com.zby.bookshop.controller;

import com.zby.bookshop.pojo.Book;
import com.zby.bookshop.pojo.BookCategory;
import com.zby.bookshop.pojo.BookInfo;
import com.zby.bookshop.pojo.BookWrap;
import com.zby.bookshop.service.BookService;
import com.zby.bookshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/createBook")
    public int createBook(@RequestBody BookWrap bookWrap)
    {
        return bookService.insertBook(bookWrap);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/deleteBook")
    public int deleteBook(int bookId)
    {
        return bookService.deleteBook(bookId);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/viewBook")
    public BookWrap viewBook(int bookId)
    {
        return bookService.getBookWrapByBookId(bookId);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/get50Books")
    public List<Book> get50Books()
    {
        return bookService.get50Books();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getBookWrapsByCategory")
    public List<BookWrap> getBookCategoryListByCategory(int categoryid)
    {
        List<BookCategory> bookCategories =  categoryService.selectBookCategoryByCategoryId(categoryid);
        List<BookWrap> bookWraps = bookService.getBookWrapByCategoryList(bookCategories);
        return bookWraps;
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getBooksByCategory")
    public List<Book> getBooksByCategory(int categoryid)
    {
        return bookService.getBooksByCategoryId(categoryid);
    }


    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/likeBook")
    public int likeBook(int bookId)
    {
       return bookService.addlikeCount(bookId);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/notLikeBook")
    public int notLikeBook(int bookId)
    {
        return bookService.decreaseLikeCount(bookId);
    }


    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getBookInfo")
    public BookInfo getBookInfo(int bookId)
    {
        return bookService.getBookInfo(bookId);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/insertBookCategory")
    public int insertBookCategory(@RequestBody BookCategory bookCategory)
    {
        return categoryService.insertBookCategory(bookCategory);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getBookWrapByBookId")
    public BookWrap getBookWrapByBookId(int bookId)
    {
        return bookService.getBookWrapByBookId(bookId);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/updateBook")
    public int updateBook(@RequestBody BookWrap bookWrap)
    {
        return bookService.updateBook(bookWrap);
    }


}
