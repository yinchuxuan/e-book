package DAO;

import Entity.BookEntity;

import java.util.ArrayList;

public interface BookDao {

    BookEntity findBook(String bookName);

    ArrayList<BookEntity> findAllBooks();

    Boolean SaveBook(BookEntity book);

    void updateBook(BookEntity book);

    void DeleteBook(String bookName);

}
