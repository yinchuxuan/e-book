package DAOImpl;

import DAO.PicDao;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import net.sf.json.JSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Repository
public class picDaoImpl implements PicDao {

    public MongoDatabase getConnection(){
     try{
        MongoClient mongoClient = new MongoClient("localhost",27017);
        return mongoClient.getDatabase("pic");
     }catch(Throwable e){
         System.err.println(e);
         e.printStackTrace();
         return null;
        }
    }

    @Override
    public JSONObject getBookPic(String bookName){
        try{
            MongoDatabase db = getConnection();
            MongoCollection<Document> collection = db.getCollection("book_pic");
            Bson filter = Filters.eq("bookName",bookName);
            FindIterable findIterable = collection.find(filter);
            MongoCursor mongoCursor = findIterable.iterator();
            return JSONObject.fromObject(mongoCursor.next());
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addBookPic(String bookName,String picUrl){
        try{
            Document document = new Document("bookName",bookName)
                    .append("picUrl",picUrl);
            MongoDatabase db = getConnection();
            MongoCollection<Document> collection = db.getCollection("book_pic");
            collection.insertOne(document);
            }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBookPic(String bookName){
        try{
            MongoDatabase db = getConnection();
            MongoCollection<Document> collection = db.getCollection("book_pic");
            collection.deleteOne(Filters.eq("bookName",bookName));
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
