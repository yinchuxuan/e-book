package DAO;

import Entity.OrderlistEntity;

import java.util.ArrayList;

public interface OrderDao {

    OrderlistEntity findOrder(int orderId);

    ArrayList<OrderlistEntity> findAllOrders();

    Boolean SaveOrder(OrderlistEntity order);

    Boolean DeleteOrder(int orderId);

}
