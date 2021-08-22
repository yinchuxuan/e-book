package Controller;

import Entity.BookEntity;
import Entity.OrderitemEntity;
import Entity.OrderlistEntity;
import EntityJsonTransform.BookTransform;
import EntityJsonTransform.OrderItemTransform;
import EntityJsonTransform.OrderTransform;
import Service.BookService;
import Service.OrderItemService;
import Service.OrderService;
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
@RequestMapping("/OrderItemController")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private BookService bookService;

    @Autowired
    private PicService picService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/showOrderItem")
    public void showOrderItem(@RequestParam("orderId") int orderId, HttpServletResponse response){
        try{
            response.setHeader("Access-Control-Allow-Origin", "*");
            String delimeter = ",";
            OrderitemEntity orderItem = orderItemService.findOrderItem(orderId);
            String[] bookSet = orderItem.getBookSet().split(delimeter);
            int size = bookSet.length;
            BookTransform bookTransform = new BookTransform();
            JSONArray jsonArray = new JSONArray();
            for(int i = 0;i < size;i++){
                BookEntity book = bookService.findBook(bookSet[i]);
                JSONObject jsonObject = bookTransform.getJsonFromEntity(book);
                JSONObject pic = picService.getBookPic(book.getName());
                jsonObject.put("img",pic);
                jsonArray.add(jsonObject);
            }
            response.getWriter().println(jsonArray);
            response.getWriter().flush();
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @RequestMapping("/oneBookOrderItem")
    public void oneBookOrderItem(@RequestParam("bookName") String bookName , HttpServletResponse response){
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            String delimeter = ",";
            ArrayList<OrderitemEntity> orderItems = orderItemService.findAllOrderItem();
            Iterator<OrderitemEntity> iter = orderItems.iterator();
            OrderItemTransform orderItemTransform = new OrderItemTransform();
            JSONArray jsonArray = new JSONArray();
            while (iter.hasNext()) {
                OrderitemEntity orderItem = iter.next();
                String[] bookSet = orderItem.getBookSet().split(delimeter);
                int size = bookSet.length;
                for (int i = 0; i < size; i++) {
                    if (bookName.equals(bookSet[i])) {
                        JSONObject jsonObject = orderItemTransform.getJsonFromEntity(orderItem);
                        jsonArray.add(jsonObject);
                    }
                }
            }
            response.getWriter().println(jsonArray);
            response.getWriter().flush();
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @RequestMapping("/oneUserOrderItem")
    public void oneUserOrderItem(@RequestParam("username") String username,HttpServletResponse response){
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            ArrayList<OrderlistEntity> orders = orderService.findAllOrders();
            Iterator<OrderlistEntity> iter = orders.iterator();
            OrderTransform orderTransform = new OrderTransform();
            JSONArray jsonArray = new JSONArray();
            while (iter.hasNext()) {
                OrderlistEntity order = iter.next();
                if (order.getUsername().equals(username)) {
                    JSONObject jsonObject = orderTransform.getJsonFromEntity(order);
                    jsonArray.add(jsonObject);
                }
            }
            response.getWriter().println(jsonArray);
            response.getWriter().flush();
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @RequestMapping("/addOrderItem")
    public String addOrderItem(HttpSession httpSession, HttpServletRequest request){
        try{
            ArrayList<String> cart = (ArrayList<String>) httpSession.getAttribute("cart");
            String[] BookSet = cart.toArray(new String[cart.size()]);
            int size = BookSet.length;
            for(int i = 0;i<size;i++){
                String bookName = BookSet[i];
                BookEntity book = bookService.findBook(bookName);
                int stock = book.getStock();
                bookService.AmendBookStock(bookName,stock-1);
            }
            String bookSet = String.join(",",BookSet);
            String username =(String)httpSession.getAttribute("username");
            String time = (String)httpSession.getAttribute("time");
            orderItemService.AddOrderItem(username,bookSet,time);
            httpSession.setAttribute("cart",null);
            request.setAttribute("action","clear");
            return "orderItem";
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }
}
