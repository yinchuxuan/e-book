package ServiceImpl;

import Service.OrderItemService;
import Entity.OrderitemEntity;
import DAO.OrderItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    private OrderItemDao orderItemDao;

    @Override
    public OrderitemEntity findOrderItem(int orderId){
        try{
            OrderitemEntity orderItem = orderItemDao.findOrderItem(orderId);
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
            return orderItemDao.findAllOrderItem();
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void AddOrderItem(String username,String bookSet,String time){
        try{
            OrderitemEntity OrderItem = new OrderitemEntity();
            OrderItem.setUsername(username);
            OrderItem.setBookSet(bookSet);
            OrderItem.setTime(time);
            orderItemDao.SaveOrderItem(OrderItem);
        }catch (Throwable e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteOrderItem(int orderId){

    }
}
