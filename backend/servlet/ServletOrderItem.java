package servlet;

import Entity.BookEntity;
import Entity.OrderitemEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@WebServlet(name = "ServletOrderItem",urlPatterns = "/ServletOrderItem")
public class ServletOrderItem extends HttpServlet {
    protected void processrequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException{
        PrintWriter out = response.getWriter();
        try{
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
            Session session1 = sessionFactory.openSession();
            Session session2 = sessionFactory.openSession();
            Session session3 = sessionFactory.openSession();
            Query query1 = session1.createQuery("from OrderitemEntity ");
            List orderItemList = query1.list();
            ListIterator orderItemIter = orderItemList.listIterator();
            Query query2 = session2.createQuery("from BookEntity ");
            List bookList = query2.list();
            ListIterator bookIter = bookList.listIterator();
            if(request.getParameter("action")!=null){
                String username = request.getParameter("username");
                String time = request.getParameter("time");
                ArrayList<String> cart = (ArrayList<String>) request.getSession().getAttribute("cart");
                String[] bookSet = cart.toArray(new String[cart.size()]);
                String bookset = String.join(",",bookSet);
                Transaction transaction1 = session3.beginTransaction();
                OrderitemEntity orderItem = new OrderitemEntity();
                orderItem.setBookSet(bookset);
                orderItem.setUsername(username);
                orderItem.setTime(time);
                session3.save(orderItem);
                transaction1.commit();
                for(int i = 0;i < bookSet.length;i++){
                    BookEntity book= (BookEntity)bookIter.next();
                    if(book.getName().equals(bookSet[i])) {
                        int stock = book.getStock();
                        stock = stock - 1;
                        Transaction transaction2 = session3.beginTransaction();
                        book.setStock(stock);
                        session3.save(book);
                        transaction2.commit();
                    }else{
                        while (bookIter.hasNext()){
                            BookEntity Book= (BookEntity)bookIter.next();
                            if(Book.getName().equals(bookSet[i])) {
                                int stock = Book.getStock();
                                stock = stock - 1;
                                Transaction transaction2 = session3.beginTransaction();
                                Book.setStock(stock);
                                session3.save(Book);
                                transaction2.commit();
                                break;
                            }
                        }
                    }
                }
                request.getSession().setAttribute("cart",null);
                response.sendRedirect("/orderItem.jsp?action=clear");
            }else{
                String delimeter = ",";
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                while(orderItemIter.hasNext()){
                    OrderitemEntity orderItem = (OrderitemEntity)orderItemIter.next();
                    if(orderId==orderItem.getOrderId()){
                        break;
                    }
                }
                OrderitemEntity orderItem = (OrderitemEntity)orderItemIter.next();
                String[] bookSet = orderItem.getBookSet().split(delimeter);
                JSONArray jsonArray = new JSONArray();
                String name;
                int price;
                int stock;
                for (int i = 0; i < bookSet.length; i++) {
                    BookEntity book= (BookEntity)bookIter.next();
                    if (bookSet[i].equals(book.getName())) {
                        name = book.getName();
                        price = book.getPrice();
                        stock = book.getStock();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", name);
                        jsonObject.put("price", price);
                        jsonObject.put("stock", stock);
                        jsonArray.add(jsonObject);
                        continue;
                    }
                    while (bookIter.hasNext()) {
                        book= (BookEntity)bookIter.next();
                        if (bookSet[i].equals(book.getName())) {
                            name = book.getName();
                            price = book.getPrice();
                            stock = book.getStock();
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("name", name);
                            jsonObject.put("price", price);
                            jsonObject.put("stock", stock);
                            jsonArray.add(jsonObject);
                            break;
                        }
                    }
                }
                session1.close();
                session2.close();
                session3.close();
                sessionFactory.close();
                serviceRegistry.close();
                out.println(jsonArray);
                out.flush();
            }
        }catch (Throwable e){
            out.println(e);
            System.err.println(e);
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

