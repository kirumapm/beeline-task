package io.umid.producer.service;

import io.umid.commons.dto.CreateOrderDTO;
import io.umid.commons.dto.UpdateOrderDTO;
import io.umid.producer.dto.Order;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
@Setter
public class OrderClientImpl implements OrderClient {

    private final RestTemplate restTemplate;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${client.order-consumer-uri}")
    private String orderApiRootUri;

    @Value("${spring.kafka.template.default-topic}")
    private String ordersTopicName;

    @Override
    public Iterable<Order> findAllUserOrders(String username) {
        log.debug("Fetching orders from external resource for user: {}", username);

        String path = "orders-api/v1/orders";
        String uri = orderApiRootUri + "/" + path;


        return restTemplate.exchange(uri, HttpMethod.GET, null,
                        new ParameterizedTypeReference<Iterable<Order>>() {})
                .getBody();
    }

    @Override
    public Order findOrderById(String username, Integer orderId) {
        log.debug("Fetching order with id {} from external resource for user {}", orderId, username);

        try {
            String path = "orders-api/v1/order/{orderId}";
            String fullUri = orderApiRootUri + "/" + path;
            return restTemplate.getForEntity(fullUri,
                    Order.class, orderId).getBody();
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Error during fetching order. Detail: {}", e.getMessage());
            throw new NoSuchElementException(e);
        }
    }

    @Override
    public void deleteOrder(String username, Integer orderId) {
        log.debug("Sending delete event: {}", orderId);

        kafkaTemplate.send(ordersTopicName, username, orderId);
    }

    @Override
    public void createOrder(String username, CreateOrderDTO createOrderDTO) {
        log.debug("Sendting create event: {}", createOrderDTO);

        kafkaTemplate.send(ordersTopicName, username, createOrderDTO);
    }

    @Override
    public void updateOrder(String username, UpdateOrderDTO updateOrderDTO) {
        log.debug("Sending update event: {}", updateOrderDTO);

        kafkaTemplate.send(ordersTopicName, username, updateOrderDTO);
    }
}
