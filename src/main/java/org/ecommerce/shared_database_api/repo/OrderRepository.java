package org.ecommerce.shared_database_api.repo;

import jakarta.transaction.Transactional;
import org.ecommerce.shared_database_api.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {


    @Transactional
    @Modifying
    @Query(value = "update Order as ord set ord.razorpayPaymentId=:razorpayPaymentId,ord.paymentStatus=:paymentStatus where ord.razorpayOrderId=:razorpayOrderId")
    Integer updateOrderPayment(@Param("razorpayPaymentId") String razorpayPaymentId,
                            @Param("paymentStatus") String paymentStatus,
                            @Param("razorpayOrderId") String razorpayOrderId);



    @Transactional
    @Modifying
    @Query(value = "update Order as ord set ord.customer.id=:CustomerId,ord.billingAddress.id=:billingAddressId where ord.id=:OrderId")
    Integer updateCustomerIdAndOrderId(@Param("CustomerId") Long CustomerId,
                               @Param("billingAddressId") Long billingAddressId,
                               @Param("OrderId") Long OrderId);

    @Query(value = "select  ord from Order as  ord where ord.customer.email=:customerEmail order by ord.lastUpdated desc ")
    ArrayList<Order> getOrdersByCustomerEmail(@Param("customerEmail") String customerEmail);


    @Query(value = "select  ord from Order as  ord where ord.razorpayOrderId=:razorpayOrderId")
    Order getOrderByRozerPayOrderID(@Param("razorpayOrderId") String razorpayOrderId);



}
