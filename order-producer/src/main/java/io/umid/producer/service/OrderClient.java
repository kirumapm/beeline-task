package io.umid.producer.service;


import io.umid.commons.dto.CreateOrderDTO;
import io.umid.commons.dto.UpdateOrderDTO;
import io.umid.producer.dto.Order;

public interface OrderClient {

    Iterable<Order> findAllUserOrders(String username);

    Order findOrderById(String username, Integer orderId);

    void deleteOrder(String username, Integer orderId);

    void createOrder(String username, CreateOrderDTO createOrderDto);

    void updateOrder(String username, UpdateOrderDTO editOrderDto);

}
