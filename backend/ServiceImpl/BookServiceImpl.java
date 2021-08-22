package ServiceImpl;

import Service.BookService;
import Entity.BookEntity;
import DAO.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao  bookDao;

    @Override
    public ArrayList<BookEntity> findAllBooks(){
        try {
            ArrayList<BookEntity> books = bookDao.findAllBooks();
            return books;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BookEntity findBook(String bookName){
        try{
            BookEntity book = bookDao.findBook(bookName);
            return book;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void AddBook(String name,String author,int price,int stock,int isbn,String intro,String press){
        try{
            BookEntity Book = new BookEntity();
            Book.setName(name);
            Book.setAuthor(author);
            Book.setPrice(price);
            Book.setIsbn(isbn);
            Book.setStock(stock);
            Book.setIntro(intro);
            Book.setPress(press);
            bookDao.SaveBook(Book);
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteBook(String name){
        try{
            bookDao.DeleteBook(name);
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void AmendBookPrice(String name,int price){
        try{
            BookEntity book = bookDao.findBook(name);
            book.setPrice(price);
            bookDao.updateBook(book);
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void AmendBookStock(String name,int stock){
        try{
            BookEntity book = bookDao.findBook(name);
            book.setStock(stock);
            bookDao.updateBook(book);
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void AmendBookIntro(String name,String intro){
        try{
            BookEntity book = bookDao.findBook(name);
            book.setIntro(intro);
            bookDao.updateBook(book);
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void AmendBookName(String name,String newName){
        try{
            BookEntity book = bookDao.findBook(name);
            book.setName(newName);
            bookDao.updateBook(book);
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void AmendBookIsbn(String name,int isbn){
        try{
            BookEntity book = bookDao.findBook(name);
            book.setIsbn(isbn);
            bookDao.updateBook(book);
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
