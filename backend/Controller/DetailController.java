package Controller;

import Entity.BookEntity;
import EntityJsonTransform.BookTransform;
import Service.BookService;
import Service.PicService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/DetailController")
public class DetailController {

    @Autowired
    private BookService bookService;

    @Autowired
    private PicService picService;

    @RequestMapping("/showDetail")
    public void showDetail(@RequestParam("name") String name, HttpServletResponse response){
        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            BookTransform bookTransform = new BookTransform();
            BookEntity book = bookService.findBook(name);
            JSONObject jsonObject = bookTransform.getJsonFromEntity(book);
            JSONObject pic = picService.getBookPic(book.getName());
            jsonObject.put("img",pic);
            response.getWriter().println(jsonObject);
            response.getWriter().flush();
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
