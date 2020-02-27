package com.zby.bookshop.controller;

import com.zby.bookshop.pojo.Book;
import com.zby.bookshop.pojo.Shopping;
import com.zby.bookshop.service.BookService;
import com.zby.bookshop.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;
    @Autowired
    private BookService bookService;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "buyBook")
    public int buyBook(@RequestBody Shopping shopping){
        return shoppingService.insertShopping(shopping);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "deleteShopping")
    public int deleteShopping(int id)
    {
        return shoppingService.deleteShoppingById(id);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "getUserShoppings")
    public List<Book> getUserShoppings(int uid)
    {
        List<Shopping> shoppings =  shoppingService.selectShoppingByUid(uid);
        List<Book> books = new ArrayList<>();
        if (shoppings!=null)
        {
            for (int i=0;i<shoppings.size();i++)
            {
                books.add(bookService.selectBookByBookId(shoppings.get(i).getBookid()));
            }
        }
        return books;
    }
}
