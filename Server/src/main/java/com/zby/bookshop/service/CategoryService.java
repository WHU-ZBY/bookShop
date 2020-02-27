package com.zby.bookshop.service;

import com.zby.bookshop.dao.BookCategoryMapper;
import com.zby.bookshop.dao.CategoryMapper;
import com.zby.bookshop.pojo.BookCategory;
import com.zby.bookshop.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private BookCategoryMapper bookCategoryMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    public int insertBookCategory(BookCategory bookCategory)
    {
        return bookCategoryMapper.insert(bookCategory);
    }
    public int deleteBookCategoryById(long id)
    {
        return bookCategoryMapper.deleteByPrimaryKey(id);
    }

    public List<BookCategory> selectBookCategoryByBookId(int bookId)
    {
        return bookCategoryMapper.selectByBookId(bookId);
    }

    public List<BookCategory> selectBookCategoryByCategoryId(int categoryid)
    {
        return bookCategoryMapper.selectByCategoryId(categoryid);
    }

    public Category selectCategoryByCategoryId(int categoryId)
    {
        return categoryMapper.selectByPrimaryKey(categoryId);
    }
}
