package servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ListIterator;
import Entity.BookEntity;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

@WebServlet(name = "ServletDetail", urlPatterns="/ServletDetail")
public class ServletDetail extends HttpServlet {
    protected void processrequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException{
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("from BookEntity ");
            List bookList = query.list();
            ListIterator iter = bookList.listIterator();

            String name = request.getParameter("name");

            while(iter.hasNext()){
                BookEntity book= (BookEntity)iter.next();
                if(book.getName().equals(name))
                    break;
            }

            BookEntity book= (BookEntity)iter.next();
            String author = book.getAuthor();
            String press = book.getPress();
            String intro = book.getIntro();
            int price = book.getPrice();
            int isbn = book.getIsbn();
            int stock = book.getStock();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",name);
            jsonObject.put("author",author);
            jsonObject.put("press",press);
            jsonObject.put("intro",intro);
            jsonObject.put("price",price);
            jsonObject.put("isbn",isbn);
            jsonObject.put("stock",stock);

            session.close();
            sessionFactory.close();
            serviceRegistry.close();
            out.println(jsonObject);
            out.flush();
        }catch (Throwable e){
            System.err.println(e);
            out.println(e);
            e.printStackTrace();
        }finally {
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
