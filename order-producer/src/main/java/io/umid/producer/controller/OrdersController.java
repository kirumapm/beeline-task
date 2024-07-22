package io.umid.producer.controller;


import io.umid.commons.dto.CreateOrderDTO;
import io.umid.commons.dto.UpdateOrderDTO;
import io.umid.producer.service.OrderClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final OrderClient orderClient;

    @GetMapping("/list")
    public String getListPage(Model model,
                              Principal auth) {
        var username = auth.getName();
        log.info("Searching all orders of user: {}", username);

        var orders = orderClient.findAllUserOrders(username);
        model.addAttribute("orders", orders);
        model.addAttribute("username", username);

        return "list";
    }

    @PostMapping("/create")
    public String createOrder(Principal auth,
                              CreateOrderDTO createOrderDto) {
        log.info("Creating order {} for user: {}", createOrderDto, auth);
        orderClient.createOrder(auth.getName(), createOrderDto);

        return "redirect:/orders/list";
    }

    @PostMapping("/edit")
    public String editOrder(Principal auth,
                            UpdateOrderDTO updateOrderDTO) {
        log.info("Updating order {} for user {}", updateOrderDTO, auth);
        orderClient.updateOrder(auth.getName(), updateOrderDTO);

        return "redirect:/orders/list";
    }

    @PostMapping("/{orderId:\\d+}/remove")
    public String removeOrder(Principal auth,
                              @PathVariable Integer orderId) {
        log.info("Removing order with id {} for user {}", orderId, auth);
        orderClient.deleteOrder(auth.getName(), orderId);

        return "redirect:/orders/list";
    }

    @GetMapping("/{orderId:\\d+}/edit")
    public String getEditPage(Principal auth,
                              @PathVariable Integer orderId,
                              Model model) {
        var getOrderDto = orderClient.findOrderById(auth.getName(), orderId);
        model.addAttribute("order", getOrderDto);
        
        return "edit";
    }


    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(NoSuchElementException e,
                                 HttpServletResponse response,
                                 Model model) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", e.getMessage());

        return "errors/404";
    }
    
}
