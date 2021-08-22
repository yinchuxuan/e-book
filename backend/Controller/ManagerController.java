package Controller;

import Entity.BookEntity;
import Entity.OrderlistEntity;
import Entity.UserEntity;
import EntityJsonTransform.BookTransform;
import EntityJsonTransform.OrderTransform;
import EntityJsonTransform.UserTransform;
import Service.BookService;
import Service.OrderService;
import Service.PicService;
import Service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;

@Controller
@RequestMapping("/ManagerController")
public class ManagerController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PicService picService;

    @RequestMapping("/book")
    public void BookManager(HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            BookTransform bookTransform = new BookTransform();
            JSONArray jsonArray = new JSONArray();
            ArrayList<BookEntity> books = bookService.findAllBooks();
            Iterator iter = books.iterator();
            while (iter.hasNext()) {
                BookEntity book = (BookEntity) iter.next();
                JSONObject jsonObject = bookTransform.getJsonManagerFromEntity(book);
                jsonArray.add(jsonObject);
            }
            response.getWriter().println(jsonArray);
            response.getWriter().flush();
        } catch (Throwable e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @RequestMapping("/user")
    public void UserManager(HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            UserTransform userTransform = new UserTransform();
            JSONArray jsonArray = new JSONArray();
            ArrayList<UserEntity> users = userService.findAllUsers();
            Iterator iter = users.iterator();
            while (iter.hasNext()) {
                UserEntity user = (UserEntity) iter.next();
                JSONObject jsonObject = userTransform.getJsonManagerFromEntity(user);
                jsonArray.add(jsonObject);
            }
            response.getWriter().println(jsonArray);
            response.getWriter().flush();
        } catch (Throwable e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @RequestMapping("/order")
    public void OrderManager(HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            OrderTransform orderTransform = new OrderTransform();
            JSONArray jsonArray = new JSONArray();
            ArrayList<OrderlistEntity> orders = orderService.findAllOrders();
            Iterator iter = orders.iterator();
            while (iter.hasNext()) {
                OrderlistEntity order = (OrderlistEntity) iter.next();
                JSONObject jsonObject = orderTransform.getJsonManagerFromEntity(order);
                jsonArray.add(jsonObject);
            }
            response.getWriter().println(jsonArray);
            response.getWriter().flush();
        } catch (Throwable e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @RequestMapping("/addBook")
    public void addBook(@RequestParam("bookName") String bookName,@RequestParam("price") String price,
                          @RequestParam("stock") String stock,@RequestParam("isbn") String isbn,@RequestParam("author") String author,
                          @RequestParam("press") String press,@RequestParam("intro") String intro,@RequestParam("picUrl") String picUrl, HttpServletResponse response) {
        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            bookService.AddBook(bookName,author,Integer.parseInt(price),Integer.parseInt(stock),Integer.parseInt(isbn),intro,press);
            picService.addBookPic(bookName,picUrl);
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @RequestMapping("/amendBook")
    public void amendBook(@RequestParam("info") String info,@RequestParam("col") String col,@RequestParam("bookName") String bookName,HttpServletResponse response){
        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            if(col.equals("name")){
                bookService.AmendBookName(bookName,info);
            }else if(col.equals("price")){
                bookService.AmendBookPrice(bookName,Integer.parseInt(info));
            }else if(col.equals("stock")){
                bookService.AmendBookStock(bookName,Integer.parseInt(info));
            }else if(col.equals("isbn")){
                bookService.AmendBookIsbn(bookName,Integer.parseInt(info));
            }
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @RequestMapping("/amendUser")
    public void amendUser(@RequestParam("username") String username,HttpServletResponse response){
        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            userService.AmendUserIsBanned(username);
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @RequestMapping("/deleteBook")
    public void deleteBook(@RequestParam("name") String name,HttpServletResponse response){
        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            bookService.DeleteBook(name);
            picService.deleteBookPic(name);
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
