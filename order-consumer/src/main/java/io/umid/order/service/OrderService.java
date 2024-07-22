package io.umid.order.service;

import io.umid.commons.dto.CreateOrderDTO;
import io.umid.commons.dto.UpdateOrderDTO;
import io.umid.order.entity.Order;


public interface OrderService {

    Iterable<Order> findAllUserOrders(String username);
    Order findOrderById(Integer orderId, String username);
    Order createOrder(CreateOrderDTO createOrderDTO, String username);
    void updateOrder(UpdateOrderDTO updateOrderDTO, String username);
    void deleteOrder(Integer orderId, String username);


}
