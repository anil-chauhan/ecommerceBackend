package org.ecommerce.shared_database_api.services;

import jakarta.transaction.Transactional;
import org.ecommerce.shared_database_api.dto.Purchase;
import org.ecommerce.shared_database_api.dto.PurchaseResponse;
import org.ecommerce.shared_database_api.models.Address;
import org.ecommerce.shared_database_api.models.Customer;
import org.ecommerce.shared_database_api.models.Order;
import org.ecommerce.shared_database_api.models.OrderItem;
import org.ecommerce.shared_database_api.repo.AddressRepository;
import org.ecommerce.shared_database_api.repo.CustomerRepository;
import org.ecommerce.shared_database_api.repo.OrderItemRepository;
import org.ecommerce.shared_database_api.repo.OrderRepository;
import org.ecommerce.shared_database_api.services.CheckoutService;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    private OrderRepository orderRepository;

    private OrderItemRepository orderItemRepository;

    private AddressRepository addressRepository;







    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               OrderRepository orderRepository,
                               OrderItemRepository orderItemRepository,
                               AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.addressRepository = addressRepository;

    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        //save to database
        orderItemRepository.saveAll(orderItems);

        ArrayList<Address> shippingAddresses = new ArrayList<>();

        Address billingAddress = purchase.getBillingAddress();
        Address shippingAddress = purchase.getShippingAddress();

        shippingAddresses.add(billingAddress);
        shippingAddresses.add(shippingAddress);

        //save to database
        addressRepository.saveAll(shippingAddresses);

        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(billingAddress);
        order.setShippingAddress(shippingAddress);

        // populate customer with order
        Customer customer = purchase.getCustomer();
        customer.add(order);

        // save to the database
        customerRepository.save(customer);


        //save to database
        orderRepository.save(order);



        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

        // generate a random UUID number (UUID version-4)
        // For details see: https://en.wikipedia.org/wiki/Universally_unique_identifier
        //
        return UUID.randomUUID().toString();
    }
}









