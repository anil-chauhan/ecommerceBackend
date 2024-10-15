package org.ecommerce.shared_database_api.dto;

import lombok.Data;
import org.ecommerce.shared_database_api.models.*;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    //private ShippingAddress shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
