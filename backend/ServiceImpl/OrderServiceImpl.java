package ServiceImpl;

import Service.OrderService;
import Entity.OrderlistEntity;
import DAO.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDao orderDao;

    @Override
    public ArrayList<OrderlistEntity> findAllOrders(){
        try {
            ArrayList<OrderlistEntity> orders = orderDao.findAllOrders();
            return orders;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OrderlistEntity findOrder(int orderId){
        try{
            OrderlistEntity order = orderDao.findOrder(orderId);
            return order;
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void AddOrder(String username,int TotalCash,String time){
        try{
            OrderlistEntity Order = new OrderlistEntity();
            Order.setUsername(username);
            Order.setTime(time);
            Order.setTotalcash(TotalCash);
            orderDao.SaveOrder(Order);
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteOrder(int orderId){

    }
}
