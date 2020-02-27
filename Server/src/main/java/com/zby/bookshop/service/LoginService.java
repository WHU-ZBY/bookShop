package com.zby.bookshop.service;

import com.zby.bookshop.dao.UserMapper;
import com.zby.bookshop.pojo.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LoginService {

    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value = "isUser",notes = "判断该用户输入的账号和密码是否正确！")
    public Boolean isUser(int uid, String password){
        User user = userMapper.selectByPrimaryKey(uid);

        if (user.getPassword().equals(password))
        {
            System.out.println("is True User!");
            System.out.println(user.getPassword());
            return true;
        }
        else
        {
            System.out.println("False Password or Uid!");
            return false;
        }
    }
    public User getUserByUid(int uid)
    {
        return userMapper.selectByPrimaryKey(uid);
    }
    @ApiOperation(value = "isNickNameRepeat", notes = "判断nickname是否重复")
    public Boolean isNickNameRepeat(String nick_name)
    {
        User result = userMapper.isNickNameRepeat(nick_name);
        if (result!=null)
        {
            System.out.println("repeat nick_name! select another name!");
            return false;
        }
        else
        {
            System.out.println("nick_name can use! not repeat!");
            return true;
        }
    }

    @ApiOperation(value = "isEmail",notes = "判断输入的是不是邮箱格式")
    public Boolean isEmail(String email)
    {
        String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @ApiOperation(value = "isPasswordSame",notes = "判断两次的密码输入是否一致")
    public Boolean isPasswordSame(String password,String re_Password)
    {
        if (password.equals(re_Password))
        {
            return true;
        }
        else
            return false;
    }

    @ApiOperation(value = "insertUser",notes = "校验合格之后，将用户插入到数据库中")
    public User insertUser(String nickname,String password,String email,String avatar)
    {
        User user = new User();
        user.setNickname(nickname);
        user.setPassword(password);
        user.setEmail(email);
        user.setAvatar("1");
        user.setAuthority(false);
        userMapper.insert(user);

        return user;





    }
}



