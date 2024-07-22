package io.umid.order.controller;

import io.umid.order.entity.Order;
import io.umid.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class OrderRestV1Controller {

    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<Iterable<Order>> getUserOrders(Principal principal) {

        var username = fetchName(principal);
        log.info("Fetching orders of user {}", username);

        var orders = orderService.findAllUserOrders(username);
        log.info("Found orders: {}", orders);

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/order/{id:\\d+}")
    public ResponseEntity<Order> getOrderById(Principal principal,
                                              @PathVariable Integer id) {
        var username = fetchName(principal);
        log.info("Fetching order with id {} of user {}", id, username);

        var order = orderService.findOrderById(id, username);
        log.info("Order: {}", order);

        return ResponseEntity.ok(order);
    }


//   TODO костыль чтобы достать правильный юзернейм
    private String fetchName(Principal principal) {
        return ((JwtAuthenticationToken) principal).getTokenAttributes().get("given_name").toString();
    }

}
