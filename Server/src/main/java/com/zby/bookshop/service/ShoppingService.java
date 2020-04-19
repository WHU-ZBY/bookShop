package com.zby.bookshop.service;

import com.zby.bookshop.dao.ShoppingMapper;
import com.zby.bookshop.pojo.Shopping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingService {
    @Autowired
    private ShoppingMapper shoppingMapper;

    public int insertShopping(Shopping shopping){
        return shoppingMapper.insert(shopping);
    }
    public int deleteShoppingById(int  bookId,int uid)
    {
        return shoppingMapper.deleteByIdAndUid(bookId,uid);
    }
    public List<Shopping> selectShoppingByUid(int uid){
        return shoppingMapper.selectByUid(uid);
    }
    public List<Shopping> selectShoppingByBookId(int bookId){
        return shoppingMapper.selectByBookId(bookId);
    }
}
