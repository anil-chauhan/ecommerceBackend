package org.ecommerce.shared_database_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;


    @Column(name="order_tracking_number")
    private String orderTrackingNumber;

    @Column(name="total_quantity")
    private int totalQuantity;

    @Column(name="total_price")
    private Double totalPrice;

    @Column(name="order_status")
    private String orderStatus;

    @Column(name="payment_status")
    private String paymentStatus;

    @Column(name="razorpay_payment_id")
    private String razorpayPaymentId;

    @Column(name="razorpay_order_id")
    public String razorpayOrderId;

    @Column(name="date_created")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name="last_updated")
    @UpdateTimestamp
    private Date lastUpdated;


    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems = new HashSet<>();


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "shipping_addresses_id", referencedColumnName = "shipping_addresses_id")
    //private ShippingAddress shippingAddress;


    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;

    public void add(OrderItem item) {

        if (item != null) {
            if (orderItems == null) {
                orderItems = new HashSet<>();
            }

            orderItems.add(item);
            item.setOrder(this);
        }
    }
}









