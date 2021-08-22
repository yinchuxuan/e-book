package Service;

import Entity.OrderlistEntity;

import java.util.ArrayList;

public interface OrderService {

    ArrayList<OrderlistEntity> findAllOrders();

    OrderlistEntity findOrder(int orderId);

    void AddOrder(String username,int TotalCash,String time);

    void DeleteOrder(int orderId);

}
