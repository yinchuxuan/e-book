package DAO;

import net.sf.json.JSONObject;

public interface PicDao {

    void addBookPic(String bookName,String picUrl);

    JSONObject getBookPic(String bookName);

    void deleteBookPic(String bookName);

}
