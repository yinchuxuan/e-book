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
import java.util.regex.Pattern;
import Entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

@WebServlet(name = "ServletRegister",urlPatterns = "/ServletRegister")
public class ServletRegister extends HttpServlet {
    protected void processrequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException{
        PrintWriter out = response.getWriter();
        try{
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("from UserEntity ");
            List userList = query.list();
            ListIterator iter = userList.listIterator();

            String Username = request.getParameter("username");
            String Password = request.getParameter("password");
            String Repassword = request.getParameter("repassword");
            String Email = request.getParameter("e-mail");

            String regexEmail  = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";


            while(iter.hasNext()){
                UserEntity user = (UserEntity) iter.next();

                if(user.getUsername().equals(Username)) {
                    response.sendRedirect("register.jsp?action=username");
                    break;
                }

                if (user.getEmail().equals(Email)) {
                    response.sendRedirect("register.jsp?action=Email-e");
                    break;
                }
            }

            if(!Password.equals(Repassword)) {
                response.sendRedirect("register.jsp?action=password");
            }

            if(!Pattern.matches(regexEmail,Email)){
                response.sendRedirect("register.jsp?action=Email-i");
            }

            UserEntity user = new UserEntity();
            user.setUsername(Username);
            user.setPassword(Password);
            user.setEmail(Email);
            user.setIsAd(false);
            user.setIsBanned(false);
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();

            session.close();
            sessionFactory.close();
            serviceRegistry.close();
            response.sendRedirect("index.jsp?action=register");
        }catch (Exception e){
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
