package Entity;

import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import java.util.*;


public class Databaseimpl{

    public Session getSession(){
        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();
            return session;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    public ListIterator getIter(String usage){
        try {
            Session session = getSession();
            Query query;
            if(usage.equals("book")){
                query = session.createQuery("from BookEntity");
            }else if(usage.equals("user")){
                query = session.createQuery("from UserEntity");
            }else if(usage.equals("order")){
                query = session.createQuery("from OrderlistEntity");
            }else if(usage.equals("orderItem")){
                query = session.createQuery("from OrderitemEntity");
            }else {
                System.err.println("the usage is not exist!");
                return null;
            }
            List userList = query.list();
            ListIterator iter = userList.listIterator();
            return iter;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }
}
