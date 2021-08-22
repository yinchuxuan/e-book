package Controller;

import Entity.OrderlistEntity;
import EntityJsonTransform.OrderTransform;
import Service.OrderService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@Controller
@RequestMapping("/OrderController")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/showOrderList")
    public void showOrderList(HttpServletResponse response,HttpSession httpSession){
     try{
         response.setHeader("Access-Control-Allow-Origin", "*");
         String username = (String)httpSession.getAttribute("username");
         ArrayList<OrderlistEntity> orders = orderService.findAllOrders();
         Iterator iter = orders.iterator();
         JSONArray jsonArray = new JSONArray();
         OrderTransform orderTransform = new OrderTransform();
         while(iter.hasNext()){
             OrderlistEntity order = (OrderlistEntity)iter.next();
             if(order.getUsername().equals(username)) {
                 JSONObject jsonObject = orderTransform.getJsonManagerFromEntity(order);
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

    @RequestMapping("/addOrder")
    public String addOrder(HttpSession httpSession){
        try{
            String username = (String)httpSession.getAttribute("username");
            int TotalCash = (int)httpSession.getAttribute("TotalCash");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df.format(new Date());
            httpSession.setAttribute("time",time);
            orderService.AddOrder(username,TotalCash,time);
            return "redirect:/OrderItemController/addOrderItem";
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }
}
