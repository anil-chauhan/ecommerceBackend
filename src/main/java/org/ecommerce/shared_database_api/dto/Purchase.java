package org.ecommerce.shared_database_api.dto;

import lombok.Data;
import org.ecommerce.shared_database_api.models.Address;
import org.ecommerce.shared_database_api.models.Customer;
import org.ecommerce.shared_database_api.models.Order;
import org.ecommerce.shared_database_api.models.OrderItem;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
