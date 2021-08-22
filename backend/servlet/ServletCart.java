package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import Entity.BookEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

@WebServlet(name = "ServletCart",urlPatterns = "/ServletCart")
public class ServletCart extends HttpServlet {
    protected void joinCart(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response,String name,PrintWriter out,String username)
            throws javax.servlet.ServletException, IOException{
        if(request.getSession().getAttribute("cart")!=null) {
            ArrayList<String> cart = (ArrayList<String>) request.getSession().getAttribute("cart");
            cart.add(name);
            request.getSession().setAttribute("cart", cart);
            response.sendRedirect("orderItem.jsp?username=" + username + "&action=joinCart");
        }else{
            ArrayList<String> cart = new ArrayList<>();
            cart.add(name);
            request.getSession().setAttribute("cart", cart);
            response.sendRedirect("orderItem.jsp?username=" + username + "&action=joinCart");
        }
    }
    protected void deleteCartItem(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response,String name,PrintWriter out,String username)
            throws javax.servlet.ServletException, IOException {
        ArrayList<String> cart = (ArrayList<String>) request.getSession().getAttribute("cart");
        Iterator<String> iter = cart.iterator();
        while(iter.hasNext()) {
            String item = iter.next();
            if(item.equals(name)){
                iter.remove();
            }
        }
        if(cart.size()==0) {
            request.getSession().setAttribute("cart",null);
            response.sendRedirect("orderItem.jsp?username=" + username + "&action=delete");
        }else {
            request.getSession().setAttribute("cart", cart);
            response.sendRedirect("orderItem.jsp?username=" + username + "&action=delete");
        }
    }
    protected void processrequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException{
        PrintWriter out = response.getWriter();
        try{
            if(request.getParameter("name")!=null){
                if(request.getParameter("action").equals("join")) {
                    String name = request.getParameter("name");
                    String user = request.getParameter("username");
                    joinCart(request, response, name, out, user);
                }else if(request.getParameter("action").equals("delete")){
                    String name = request.getParameter("name");
                    String user = request.getParameter("username");
                    deleteCartItem(request, response, name, out, user);
                }
            }else if(request.getSession().getAttribute("cart")!=null){
                    ArrayList<String> cart = (ArrayList<String>) request.getSession().getAttribute("cart");
                    int size = cart.size();
                    String[] bookSet = cart.toArray(new String[size]);
                    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
                    SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
                    Session session = sessionFactory.openSession();
                    Query query = session.createQuery("from BookEntity ");
                    List bookList = query.list();
                    ListIterator iter = bookList.listIterator();
                    JSONArray jsonArray = new JSONArray();
                    String name;
                    int price;
                    int stock;
                    for (int i = 0; i < bookSet.length; i++) {
                            BookEntity book= (BookEntity)iter.next();
                            if(bookSet[i].equals(book.getName())){
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
                            while(iter.hasNext()) {
                                BookEntity Book= (BookEntity)iter.next();
                                if(bookSet[i].equals(Book.getName())) {
                                    name = Book.getName();
                                    price = Book.getPrice();
                                    stock = Book.getStock();
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("name", name);
                                    jsonObject.put("price", price);
                                    jsonObject.put("stock", stock);
                                    jsonArray.add(jsonObject);
                                    break;
                                }
                            }
                    }
                    session.close();
                    sessionFactory.close();
                    serviceRegistry.close();
                    out.println(jsonArray);
                    out.flush();
            }else{
            }
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
