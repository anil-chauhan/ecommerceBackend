package org.ecommerce.shared_database_api.services;

import jakarta.transaction.Transactional;
import org.ecommerce.shared_database_api.dto.AddressDTO;
import org.ecommerce.shared_database_api.dto.Purchase;
import org.ecommerce.shared_database_api.dto.PurchaseResponse;
import org.ecommerce.shared_database_api.models.*;
import org.ecommerce.shared_database_api.repo.*;
import org.ecommerce.shared_database_api.services.CheckoutService;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {


    //private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final AddressRepository addressRepository;

    private final CustomerService customerService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;


    public CheckoutServiceImpl(
            OrderItemRepository orderItemRepository,
            AddressRepository addressRepository,
            CustomerService customerService,
            OrderService orderService, OrderRepository orderRepository) {
        //this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.addressRepository = addressRepository;
        this.customerService = customerService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {


        // populate customer with order
        Customer customer = purchase.getCustomer();

        Customer customerByEmailId = customerService.findCustomerByEmailId(customer.getEmail());

        Address billingAddress = purchase.getBillingAddress();

        if (customerByEmailId != null) {
            // save to the database
            customer = customerByEmailId;

            String hashCode = billingAddress.getHashCode();

            billingAddress.setAddressHash(hashCode);
            billingAddress=addressRepository.findAddressByHash(hashCode);
            Set<Address> addressSet = new HashSet<>();
            addressSet.add(billingAddress);
            customer.setAddressSet(addressSet);

            //customer.setAddressSet();


        }else {


            String hashCode  = billingAddress.getHashCode();
            billingAddress.setAddressHash(hashCode);
            //save to database
            try {
                billingAddress = addressRepository.save(billingAddress);
            }catch (Exception e) {
                billingAddress=addressRepository.findAddressByHash(hashCode);
            }

            Set<Address> addressSet = new HashSet<>();
            addressSet.add(billingAddress);
            customer.setAddressSet(addressSet);


        }


        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();

        for (OrderItem orderItem : orderItems) {

            orderItem.setImageUrl("");
        }

        orderItems.forEach(order::add);

        //save to database
        orderItemRepository.saveAll(orderItems);

        //ArrayList<Address> shippingAddresses = new ArrayList<>();



        //shippingAddressRepository.save(shippingAddress);

        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(billingAddress);
        //order.setShippingAddress(shippingAddress);


        customer.add(order);
        // save to the database
        try {
            if(customer.getId()==null) {
                customerService.saveCustomer(customer);
            }
        }catch (Exception e) {
            //customer.setAddressSet(null);
            //customerService.saveCustomer(customer);
        }





        //set order payment status
        order.setPaymentStatus("pending");


        //set order payment status
        order.setRazorpayPaymentId("pending");

        //set order payment status
        order.setOrderStatus("created");

        //order.setCustomer(customer);

        //order.setBillingAddress(billingAddress);

        //save to database


        try {
            order.setCustomer(null);
            order.setBillingAddress(null);



            orderRepository.save(order);

            Integer i = orderRepository.updateCustomerIdAndOrderId(customer.getId(), billingAddress.getId(), order.getId());
            billingAddress.setCustomer(customer);
            billingAddress.setOrder(order);
            addressRepository.save(billingAddress);
        }catch (Exception e) {

            e.printStackTrace();
        }





        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

        // generate a random UUID number (UUID version-4)
        // For details see: https://en.wikipedia.org/wiki/Universally_unique_identifier
        //
        return UUID.randomUUID().toString();
    }

    public AddressDTO getAddressByCustomerEmail(String customerEmail) {

        Address addressByCustomerId = addressRepository.getAddressByCustomerId(customerEmail);

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setId(addressByCustomerId.getId());
        addressDTO.setHouseNo(addressByCustomerId.getHouseNo());
        addressDTO.setStreet(addressByCustomerId.getStreet());
        addressDTO.setCity(addressByCustomerId.getCity());
        addressDTO.setState(addressByCustomerId.getState());
        addressDTO.setZipCode(addressByCustomerId.getZipCode());
        addressDTO.setCountry(addressByCustomerId.getCountry());


        return addressDTO;

    }

}









