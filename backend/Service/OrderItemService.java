package Service;

import Entity.OrderitemEntity;

import java.util.ArrayList;

public interface OrderItemService {

    OrderitemEntity findOrderItem(int orderId);

    ArrayList<OrderitemEntity> findAllOrderItem();

    void AddOrderItem(String username,String bookSet,String time);

    void DeleteOrderItem(int orderId);

}
