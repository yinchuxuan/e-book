package DAOImpl;

import DAO.OrderItemDao;
import Entity.OrderitemEntity;
import Entity.Databaseimpl;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.ListIterator;

@Repository
public class OrderItemDaoImpl implements OrderItemDao{

    @Override
    public OrderitemEntity findOrderItem(int orderId){
        try {
            Databaseimpl databaseimpl = new Databaseimpl();
            ListIterator iter = databaseimpl.getIter("orderItem");
            while (iter.hasNext()) {
                OrderitemEntity orderItem = (OrderitemEntity) iter.next();
                if (orderItem.getOrderId()==orderId) {
                    break;
                }
            }
            iter.previous();
            OrderitemEntity orderItem = (OrderitemEntity) iter.next();
            return orderItem;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<OrderitemEntity> findAllOrderItem(){
        try{
            ArrayList<OrderitemEntity> orderItems = new ArrayList<>();
            Databaseimpl databaseimpl = new Databaseimpl();
            ListIterator iter = databaseimpl.getIter("orderItem");
            while(iter.hasNext()){
                OrderitemEntity orderItem = (OrderitemEntity) iter.next();
                orderItems.add(orderItem);
            }
            return orderItems;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean SaveOrderItem(OrderitemEntity orderItem){
        try{
            Databaseimpl databaseimpl = new Databaseimpl();
            Session session = databaseimpl.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(orderItem);
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
    public Boolean DeleteOrderItem(int orderId){
        return true;
    }
}
