package DAOImpl;

import DAO.OrderDao;
import Entity.OrderlistEntity;
import Entity.Databaseimpl;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.ListIterator;

@Repository
public class OrderDaoImpl implements OrderDao{

    @Override
    public OrderlistEntity findOrder(int orderId){
        try {
            Databaseimpl databaseimpl = new Databaseimpl();
            ListIterator iter = databaseimpl.getIter("order");
            while (iter.hasNext()) {
                OrderlistEntity order = (OrderlistEntity) iter;
                if (order.getOrderId()==orderId) {
                    break;
                }
            }
            iter.previous();
            OrderlistEntity order = (OrderlistEntity) iter.next();
            return order;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<OrderlistEntity> findAllOrders(){
        try {
            ArrayList<OrderlistEntity> orders = new ArrayList<>();
            Databaseimpl databaseimpl = new Databaseimpl();
            ListIterator iter = databaseimpl.getIter("order");
            while (iter.hasNext()) {
                OrderlistEntity order = (OrderlistEntity) iter.next();
                orders.add(order);
            }
            return orders;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean SaveOrder(OrderlistEntity order){
        try{
            Databaseimpl databaseimpl = new Databaseimpl();
            Session session = databaseimpl.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            session.close();
            return true;
        }catch(Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean DeleteOrder(int orderId){
        return true;
    }
}
