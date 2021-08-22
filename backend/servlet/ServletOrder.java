package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ListIterator;
import Entity.OrderlistEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

@WebServlet(name = "ServletOrder",urlPatterns = "/ServletOrder")
public class ServletOrder extends HttpServlet {
    protected void processrequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException{
        PrintWriter out = response.getWriter();
        try{
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("from OrderlistEntity");
            List orderList = query.list();
            ListIterator iter = orderList.listIterator();
            String username = request.getParameter("username");
            if(request.getParameter("action")!=null){
                int Totalcash = Integer.parseInt(request.getParameter("TotalCash"));
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = df.format(new Date());
                Transaction transaction = session.beginTransaction();
                OrderlistEntity orderlist = new OrderlistEntity();
                orderlist.setUsername(username);
                orderlist.setTime(time);
                orderlist.setTotalcash(Totalcash);
                session.save(orderlist);
                transaction.commit();
                response.sendRedirect("/ServletOrderItem?action=clear&username=" + username + "&time=" + time);
            }else{
                String time;
                int TotalCash;
                int orderId;
                JSONArray jsonArray = new JSONArray();
                while(iter.hasNext()){
                    OrderlistEntity Orderlist= (OrderlistEntity)iter.next();
                    if(Orderlist.getUsername().equals(username)){
                        time = Orderlist.getTime();
                        TotalCash = Orderlist.getTotalcash();
                        orderId = Orderlist.getOrderId();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("second",username);
                        jsonObject.put("third",time);
                        jsonObject.put("fourth",TotalCash);
                        jsonObject.put("first",orderId);
                        jsonArray.add(jsonObject);
                    }
                }

                session.close();
                sessionFactory.close();
                serviceRegistry.close();
                out.println(jsonArray);
                out.flush();
            }
        }catch (Throwable e){
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
