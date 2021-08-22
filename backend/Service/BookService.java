package Service;

import Entity.BookEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

public interface BookService {

    ArrayList<BookEntity> findAllBooks();

    BookEntity findBook(String bookName);

    void AddBook(String name,String author,int price,int stock,int isbn,String intro,String press);

    void DeleteBook(String username);

    void AmendBookPrice(String name,int price);

    void AmendBookStock(String name,int stock);

    void AmendBookIntro(String name,String intro);

    void AmendBookName(String name,String newName);

    void AmendBookIsbn(String name,int isbn);

}
