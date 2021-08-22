package DAOImpl;

import DAO.BookDao;
import Entity.BookEntity;
import Entity.Databaseimpl;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.ListIterator;

@Repository
public class BookDaoImpl implements BookDao{

    @Override
    public BookEntity findBook(String bookName){
        try {
            Databaseimpl databaseimpl = new Databaseimpl();
            ListIterator iter = databaseimpl.getIter("book");
            while (iter.hasNext()) {
                BookEntity book = (BookEntity) iter.next();
                if (book.getName().equals(bookName)) {
                    break;
                }
            }
            iter.previous();
            BookEntity book = (BookEntity) iter.next();
            return book;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<BookEntity> findAllBooks(){
        try {
            ArrayList<BookEntity> books = new ArrayList<>();
            Databaseimpl databaseimpl = new Databaseimpl();
            ListIterator iter = databaseimpl.getIter("book");
            while (iter.hasNext()) {
                BookEntity book = (BookEntity) iter.next();
                books.add(book);
            }
            return books;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean SaveBook(BookEntity book){
        try{
            Databaseimpl databaseimpl = new Databaseimpl();
            ListIterator iter = databaseimpl.getIter("book");
            int maxId = 0;
            while(iter.hasNext()){
                BookEntity Book = (BookEntity) iter.next();
                if(maxId < Book.getId()){
                    maxId = Book.getId();
                }
            }
            book.setId(maxId + 1);
            Session session = databaseimpl.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
            session.close();
            return true;
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void updateBook(BookEntity book){
        try{
            Databaseimpl databaseimpl = new Databaseimpl();
            Session session1 = databaseimpl.getSession();
            Transaction transaction = session1.beginTransaction();
            session1.update(book);
            transaction.commit();
            session1.close();
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteBook(String bookName){
        try {
            BookEntity book = findBook(bookName);
            Databaseimpl databaseimpl = new Databaseimpl();
            Session session = databaseimpl.getSession();
            Transaction transaction = session.beginTransaction();
            session.delete(book);
            transaction.commit();
            session.close();
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
