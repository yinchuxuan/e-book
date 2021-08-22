package servlet;

import Entity.BookEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;



@WebServlet(name = "ServletDisplay")
public class ServletDisplay extends HttpServlet {
    protected void processrequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException{
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();

            String name;
            int price;
            int stock;
            JSONArray jsonArray = new JSONArray();
            Query query = session.createQuery("from BookEntity ");
            List BookList = query.list();

            for(Iterator iter = BookList.iterator(); iter.hasNext();){
                BookEntity book= (BookEntity)iter.next();
                name = book.getName();
                price = book.getPrice();
                stock = book.getStock();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name",name);
                jsonObject.put("price",price);
                jsonObject.put("stock",stock);
                jsonObject.put("img","./pic/" + name + ".jpg");
                jsonArray.add(jsonObject);
            }
            session.close();
            sessionFactory.close();
            serviceRegistry.close();

            out.println(jsonArray);
            out.flush();
        }catch(Throwable e){
            System.err.println(e);
            out.println(e);
            e.printStackTrace();
        }
        finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        processrequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        processrequest(request, response);
    }
}
