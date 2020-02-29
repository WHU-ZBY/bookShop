package com.zby.bookshop.service;

import com.zby.bookshop.dao.BookCategoryMapper;
import com.zby.bookshop.dao.BookInfoMapper;
import com.zby.bookshop.dao.BookMapper;
import com.zby.bookshop.dao.CategoryMapper;
import com.zby.bookshop.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookInfoMapper bookInfoMapper;
    @Autowired
    private BookCategoryMapper bookCategoryMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    public boolean test (String name)
    {
        return bookMapper.isRepeatBook(name);
    }

    public int insertBook(BookWrap bookWrap)
    {
//        boolean re = bookMapper.isRepeatBook(bookWrap.getBookName());

            Book book = new Book();
            book.setAuthor(bookWrap.getAuthor());
            book.setBookName(bookWrap.getBookName());
            book.setCreateTime(bookWrap.getCreateTime());
            book.setIntro(bookWrap.getIntro());
            int re_1 = bookMapper.insert(book);

            int bookId = book.getId();

            BookInfo bookInfo = new BookInfo();
            bookInfo.setBookid(bookId);
            bookInfo.setLikecount(0);
            bookInfo.setPrice(bookWrap.getPrice());
            int re_2= bookInfoMapper.insert(bookInfo);

            BookCategory bookCategory = new BookCategory();
            List<Category> categoryList = new ArrayList<>();
            categoryList = bookWrap.getBookCategoryList();
            for (int i = 0; i<categoryList.size();i++)
            {
                bookCategory.setCategoryid(categoryList.get(i).getCategoryId());
                bookCategory.setBookid(bookId);
                bookCategoryMapper.insert(bookCategory);
            }

            return re_1;



    }
    public int deleteBook(int bookId)
    {
        Book re = bookMapper.selectByPrimaryKey(bookId);

        if (re != null)
        {
             bookMapper.deleteByPrimaryKey(bookId);
             bookCategoryMapper.deleteByBookId(bookId);
             bookInfoMapper.deleteByPrimaryKey(bookId);
             return 1;
        }
        else
            return 2;//表示不存在书
    }
    public int updateBook(Book book)
    {
        return bookMapper.updateByPrimaryKey(book);
    }
    public Book selectBookByBookId(int bookId)
    {
        return bookMapper.selectByPrimaryKey(bookId);
    }

    public List<Book> get50Books()
    {
        return bookMapper.get50Books();
    }

    public int addlikeCount(int bookId)
    {
        BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(bookId);
        int likeCount = bookInfo.getLikecount() + 1;
        return bookInfoMapper.setLikeCount(likeCount,bookId);
    }

    public int decreaseLikeCount(int bookId)
    {
        BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(bookId);
        int likeCount = bookInfo.getLikecount() - 1;
        return bookInfoMapper.setLikeCount(likeCount,bookId);
    }

    public BookInfo getBookInfo(int bookId)
    {
        return bookInfoMapper.selectByPrimaryKey(bookId);
    }

    public BookWrap getBookWrapByBookId(int bookId)
    {
        BookWrap bookWrap = new BookWrap();
        Book book = bookMapper.selectByPrimaryKey(bookId);
        BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(bookId);
        List<Category> categoryList = new ArrayList<>();
        List<BookCategory> bookCategoryList = bookCategoryMapper.selectByBookId(bookId);
        if (bookCategoryList != null)
        {
            for (int i = 0; i<bookCategoryList.size();i++)
            {
                Category category = categoryMapper.selectByPrimaryKey(bookCategoryList.get(i).getCategoryid());
                categoryList.add(category);
            }
        }

        bookWrap.setId(book.getId());
        bookWrap.setAuthor(book.getAuthor());
        bookWrap.setBookName(book.getBookName());
        bookWrap.setCreateTime(book.getCreateTime());
        bookWrap.setIntro(book.getIntro());
        bookWrap.setPrice(bookInfo.getPrice());
        bookWrap.setLikecount(bookInfo.getLikecount());
        bookWrap.setBookCategoryList(categoryList);
        return bookWrap;
    }
    public List<BookWrap> getBookWrapByCategoryList(List<BookCategory> bookCategories)
    {
        List<BookWrap> bookWraps = new ArrayList<>();
        for(int i = 0; i<bookCategories.size();i++)
        {
            bookWraps.add(getBookWrapByBookId(bookCategories.get(i).getBookid()));
        }
        return bookWraps;
    }

    public List<BookWrap> getBook()
    {
        List<Book> books = bookMapper.get50Books();
        List<BookWrap> bookWraps = new ArrayList<>();
        for(int i = 0; i<books.size();i++)
        {
            bookWraps.add(getBookWrapByBookId(books.get(i).getId()));
        }
        return bookWraps;
    }

    public List<Book> getBooksByCategoryId(int categoryid)
    {
        List<BookCategory> bookCategories = new ArrayList<>();
        bookCategories = bookCategoryMapper.selectByCategoryId(categoryid);
        List<Book> books = new ArrayList<>();
        for (int i = 0;i<bookCategories.size();i++)
        {
            books.add(selectBookByBookId(bookCategories.get(i).getBookid()));
        }
        return books;
    }

}
