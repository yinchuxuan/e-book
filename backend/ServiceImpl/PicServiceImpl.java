package ServiceImpl;

import DAO.PicDao;
import Service.PicService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PicServiceImpl implements PicService {

    @Autowired
    private PicDao picDao;

    @Override
    public JSONObject getBookPic(String bookName){
        try{

            return picDao.getBookPic(bookName);

        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addBookPic(String bookName,String picUrl){
        try{

            picDao.addBookPic(bookName,picUrl);

        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBookPic(String bookName){
        try{

            picDao.deleteBookPic(bookName);

        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
