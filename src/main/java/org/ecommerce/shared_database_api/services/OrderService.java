package org.ecommerce.shared_database_api.services;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.ecommerce.shared_database_api.dto.RozerPayPaymentRequestModelDTO;
import org.ecommerce.shared_database_api.dto.RozerPayPaymentResponseModelDTO;
import org.ecommerce.shared_database_api.models.Address;
import org.ecommerce.shared_database_api.models.Order;
import org.ecommerce.shared_database_api.repo.AddressRepository;
import org.ecommerce.shared_database_api.repo.CategoryRepository;
import org.ecommerce.shared_database_api.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;

    public OrderService(OrderRepository orderRepository,AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
    }

    public String updateOrderPaymentStatus(RozerPayPaymentRequestModelDTO rozerPayPaymentResponseModelDTO) {


        String razorpayPaymentId = rozerPayPaymentResponseModelDTO.getRazorpayPaymentId();
        String razorpayOrderId = rozerPayPaymentResponseModelDTO.getRazorpayOrderId();
        String paymentStatus="paid";

        Integer b = orderRepository.updateOrderPayment(razorpayPaymentId, paymentStatus, razorpayOrderId);

        if (b==1) {
            return "update_success";
        }
        else {
            return "update_failed";
        }

    }



    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveOrder(Order order) {
        Address billingAddress = order.getBillingAddress();

        Address existingAddress=null;
        try {

            String addressHash = billingAddress.getAddressHash();

            // Check if the address already exists
            existingAddress = addressRepository.findAddressByHash(addressHash);
        } catch (Exception e) {

            e.printStackTrace();
        }



        Address shippingAddress = null;
        if (existingAddress!=null) {
            // Use the existing address
            shippingAddress = existingAddress;
        } else {
            // Merge new address to ensure it’s managed
            shippingAddress = entityManager.merge(shippingAddress);
        }

        order.setBillingAddress(shippingAddress);
        orderRepository.save(order); // Save the order
    }


    public ArrayList<Order> getOrdersByCustomerEmail(String customerEmail) {

        ArrayList<Order> ordersByCustomerEmail = orderRepository.getOrdersByCustomerEmail(customerEmail);

        return ordersByCustomerEmail;
    }




}
