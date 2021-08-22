package servlet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import java.util.List;
import java.util.ListIterator;
import Entity.UserEntity;

@WebServlet(name = "LoginServlet" ,urlPatterns="/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void processrequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("from UserEntity ");
            List userList = query.list();
            ListIterator iter = userList.listIterator();

            String username = request.getParameter("username");
            String password = request.getParameter("password");


            while (iter.hasNext()) {
                UserEntity user = (UserEntity) iter.next();
                if (user.getUsername().equals(username))
                    break;
            }
            if (!iter.hasNext()) {
                response.sendRedirect("index.jsp?error=username");
            } else {
                iter.previous();
                UserEntity user = (UserEntity) iter.next();
                if (user.getIsBanned()) {
                    response.sendRedirect("index.jsp?error=isBanned");
                }

                if (!user.getPassword().equals(password)) {
                    response.sendRedirect("index.jsp?error=password");
                } else {
                    if (user.getIsAd()) {
                        response.sendRedirect("admanager.jsp?username=" + username);
                    } else {
                        response.sendRedirect("book-display.jsp?username=" + username);
                    }
                }
            }
        } catch (Exception e) {
            out.println(e);
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processrequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processrequest(request, response);
    }
}


