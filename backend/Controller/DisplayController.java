package Controller;

import Entity.BookEntity;
import EntityJsonTransform.BookTransform;
import Service.BookService;
import Service.PicService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;

@Controller
@RequestMapping("/DisplayController")
public class DisplayController {

    @Autowired
    private BookService bookService;

    @Autowired
    private PicService picService;

    @RequestMapping("/display")
    public void display(HttpServletResponse response){
        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            BookTransform bookTransform = new BookTransform();
            JSONArray jsonArray = new JSONArray();
            ArrayList<BookEntity> books = bookService.findAllBooks();
            Iterator iter = books.iterator();
            while(iter.hasNext()){
                BookEntity book = (BookEntity) iter.next();
                JSONObject pic = picService.getBookPic(book.getName());
                JSONObject jsonObject = bookTransform.getJsonFromEntity(book);
                jsonObject.put("img",pic);
                jsonArray.add(jsonObject);
            }
            response.getWriter().println(jsonArray);
            response.getWriter().flush();
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @RequestMapping("/redirectToBookDetail")
    public String redirectToBookDetail(HttpServletRequest request, @RequestParam("name")String name) {
        try {
            request.setAttribute("name",name);
            return "bookDetails";
        } catch (Throwable e) {
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/redirectToCart")
    public String redirectToCart() {
        try {
            return "orderItem";
        } catch (Throwable e) {
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/redirectToOrderList")
    public String redirectToOrderList(HttpServletRequest request, HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");
            request.setAttribute("username", username);
            return "ManageDisplay";
        } catch (Throwable e) {
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }
}
