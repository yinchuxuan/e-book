package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import Entity.UserEntity;
import Entity.BookEntity;
import Entity.OrderlistEntity;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

@WebServlet(name = "ServletManager" ,urlPatterns="/ServletManager")
public class ServletManager extends HttpServlet {
    protected void processrequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException{
        PrintWriter out = response.getWriter();
        try{
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();

            String usage=request.getParameter("usage");
            JSONArray jsonArray = new JSONArray();

           if(usage.equals("book")){
               Query query = session.createQuery("from BookEntity ");
               List BookList = query.list();
               String bookName;
               int price;
               int stock;
               int isbn;

               for(Iterator iter = BookList.iterator();iter.hasNext();){
                   BookEntity book= (BookEntity)iter.next();
                   bookName = book.getName();
                   price = book.getPrice();
                   stock = book.getStock();
                   isbn = book.getIsbn();
                   JSONObject jsonObject = new JSONObject();
                   jsonObject.put("first",bookName);
                   jsonObject.put("second",price);
                   jsonObject.put("third",stock);
                   jsonObject.put("fourth",isbn);
                   jsonArray.add(jsonObject);
               }
               session.close();
           }
           else if(usage.equals("user")) {
               Query query = session.createQuery("from UserEntity ");
               List userList = query.list();
               String username;
               String password;
               String email;
               Boolean isBanned;
               for(Iterator iter = userList.iterator();iter.hasNext();){
                   UserEntity user= (UserEntity)iter.next();
                   username = user.getUsername();
                   password = user.getPassword();
                   email = user.getEmail();
                   isBanned = user.getIsBanned();
                   JSONObject jsonObject = new JSONObject();
                   jsonObject.put("first",username);
                   jsonObject.put("second",password);
                   jsonObject.put("third",email);
                   jsonObject.put("fourth",isBanned);
                   jsonArray.add(jsonObject);
               }
               session.close();
           }
           else if(usage.equals("order")){
               Query query = session.createQuery("from OrderlistEntity");
               List OrderList = query.list();
               int orderId;
               String username;
               String time;
               int TotalCash;
               for(Iterator iter = OrderList.iterator();iter.hasNext();){
                   OrderlistEntity Orderlist= (OrderlistEntity)iter.next();
                   orderId = Orderlist.getOrderId();
                   username = Orderlist.getUsername();
                   time = Orderlist.getTime();
                   TotalCash = Orderlist.getTotalcash();
                   JSONObject jsonObject = new JSONObject();
                   jsonObject.put("first",orderId);
                   jsonObject.put("second",username);
                   jsonObject.put("third",time);
                   jsonObject.put("fourth",TotalCash);
                   jsonArray.add(jsonObject);
               }
               session.close();
           }
           sessionFactory.close();
           serviceRegistry.close();
           out.println(jsonArray);
           out.flush();
        }catch (Throwable e){
            System.err.println(e);
            out.println(e);
            e.printStackTrace();
        }finally {
            out.close();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processrequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processrequest(request,response);
    }
}
