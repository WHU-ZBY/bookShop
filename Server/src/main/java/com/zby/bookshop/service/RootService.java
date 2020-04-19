package com.zby.bookshop.service;

import com.zby.bookshop.dao.BookMapper;
import com.zby.bookshop.dao.UserMapper;
import com.zby.bookshop.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RootService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookMapper bookMapper;

    int authority = 1;
    int deleteAuthority = 0;
    public List<User> getRoot()
    {

        return userMapper.getRooter(authority);
    }

    public int makeRoot(int uid)
    {

        return userMapper.makeRoot(uid,authority);
    }

    public int deleteRoot(int uid)
    {

        return userMapper.deleteRoot(uid,deleteAuthority);
    }

    public List<User> getUser()
    {
        return userMapper.getUser();
    }
    public int deleteUser(int uid)
    {
        return userMapper.deleteByPrimaryKey(uid);
    }

    public int count()
    {
        return userMapper.count();
    }

    public int bookCount()
    {
        return bookMapper.count();
    }

    public boolean isRoot(int uid)
    {
        int authority = userMapper.isRoot(uid);
        if (authority ==1)
        {
            return true;
        }
        else
            return false;
    }

    public int getBookCountByCategoryId(int categoryId)
    {
        return bookMapper.countByCategoryId(categoryId);
    }
}
