package EntityJsonTransform;

import Entity.BookEntity;
import net.sf.json.JSONObject;

public class BookTransform {

    public JSONObject getJsonFromEntity(BookEntity book){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", book.getName());
            jsonObject.put("price", book.getPrice());
            jsonObject.put("stock", book.getStock());
            jsonObject.put("isbn", book.getIsbn());
            jsonObject.put("author", book.getAuthor());
            jsonObject.put("press", book.getPress());
            jsonObject.put("intro", book.getIntro());
            return jsonObject;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getJsonManagerFromEntity(BookEntity book){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("first", book.getName());
            jsonObject.put("second", book.getPrice());
            jsonObject.put("third", book.getStock());
            jsonObject.put("fourth", book.getIsbn());
            return jsonObject;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    public BookEntity getEntityFromJson(JSONObject book){
        try{
            BookEntity Book = new BookEntity();
            Book.setName((String)book.get("name"));
            Book.setAuthor((String)book.get("author"));
            Book.setPrice((int)book.get("price"));
            Book.setStock((int)book.get("stock"));
            Book.setPress((String)book.get("press"));
            Book.setIsbn((int)book.get("isbn"));
            Book.setIntro((String)book.get("intro"));
            return Book;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }
}
