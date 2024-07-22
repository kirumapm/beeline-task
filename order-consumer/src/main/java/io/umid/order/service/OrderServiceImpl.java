package io.umid.order.service;

import io.umid.commons.dto.CreateOrderDTO;
import io.umid.commons.dto.UpdateOrderDTO;
import io.umid.order.entity.Order;
import io.umid.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Iterable<Order> findAllUserOrders(String username) {
        log.debug("Searching for all order of user: {}", username);

        return orderRepository.findAllByUsername(username);
    }

    @Override
    public Order findOrderById(Integer orderId, String username) {
        log.debug("Searching for order with id {} of user {}", orderId, username);

        return orderRepository.findByIdAndUsername(orderId, username).orElseThrow(
                () -> new NoSuchElementException("Order with id %d, username %s wasn't found"
                        .formatted(orderId, username)));
    }

    @Override
    @Transactional
    public Order createOrder(CreateOrderDTO createOrderDTO, String username) {
        Order order = new Order(createOrderDTO.name(), createOrderDTO.details(), username);
        log.debug("Saving new order: {}", order);

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void updateOrder(UpdateOrderDTO updateOrderDTO, String username) {
        log.debug("Updating order: {}", updateOrderDTO);

        int affectedRows = orderRepository.updateOrder(updateOrderDTO.id(), username,
                updateOrderDTO.name(), updateOrderDTO.details());
        log.debug("Affected rows: {}", affectedRows);
    }

    @Override
    @Transactional
    public void deleteOrder(Integer orderId, String username) {

        log.debug("Removing order with id: {} by user: {}", orderId, username);

        int affectedRows = orderRepository.deleteOrder(orderId, username);
        log.debug("Affected rows: {}", affectedRows);
    }
}
