package Service;

import net.sf.json.JSONObject;

public interface PicService {

    void addBookPic(String bookName,String picUrl);

    JSONObject getBookPic(String bookName);

    void deleteBookPic(String bookName);

}
