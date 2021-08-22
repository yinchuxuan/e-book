package DAO;

import Entity.OrderitemEntity;

import java.util.ArrayList;

public interface OrderItemDao{

    OrderitemEntity findOrderItem(int orderId);

    ArrayList<OrderitemEntity> findAllOrderItem();

    Boolean SaveOrderItem(OrderitemEntity orderItem);

    Boolean DeleteOrderItem(int orderId);

}
