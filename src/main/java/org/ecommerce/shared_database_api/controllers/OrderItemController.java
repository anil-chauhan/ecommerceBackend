package org.ecommerce.shared_database_api.controllers;

import lombok.Data;
import org.ecommerce.shared_database_api.dto.OrderItemsResponseDto;
import org.ecommerce.shared_database_api.models.Order;
import org.ecommerce.shared_database_api.models.OrderItem;
import org.ecommerce.shared_database_api.services.OrderItemService;
import org.ecommerce.shared_database_api.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;

@RestController
@CrossOrigin
public class OrderItemController {


    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }


    @Data
    public static class OrderRequestDto implements Serializable {
        Long orderId;
    }


    @PostMapping("/get_order_items_by_order_id")
    //@PostAuthorize("hasRole('ADMIN')")
    public ArrayList<OrderItemsResponseDto> getOrderItemsByOrderId(@RequestBody OrderRequestDto orderRequestDto) {
        Long orderId = orderRequestDto.getOrderId();
        ArrayList<OrderItemsResponseDto> orders = orderItemService.getOrderItemsByOrderId(orderId);
        ArrayList<OrderItemsResponseDto> responseDto = new ArrayList<>();
        return orders;
    }


}

