package org.ecommerce.shared_database_api.controllers;

import lombok.Data;
import org.ecommerce.shared_database_api.models.Order;
import org.ecommerce.shared_database_api.models.State;
import org.ecommerce.shared_database_api.repo.StateRepository;
import org.ecommerce.shared_database_api.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class OrderController {


    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @Data
    public static class OrderRequestDto implements Serializable {
        String customerEmail;
    }


    @PostMapping("/find_orders_by_email")
    //@PostAuthorize("hasRole('ADMIN')")
    public ArrayList<Order> findByCustomerEmail(@RequestBody OrderRequestDto orderRequestDto) {
        String customerEmail= orderRequestDto.getCustomerEmail();
        ArrayList<Order> orders = orderService.getOrdersByCustomerEmail(customerEmail);
        return orders;
    }


}
