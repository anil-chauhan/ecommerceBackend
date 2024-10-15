package org.ecommerce.shared_database_api.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "order_shipping_addresses")
@Data
public class OrderShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_shipping_addresses_id", nullable = false)
    private Integer OrderShippingAddressId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id", nullable = false)
    private Order orderOld;

    //@ManyToOne(fetch = FetchType.LAZY, optional = false)
    //@JoinColumn(name = "shipping_address_id", nullable = false)
    //private ShippingAddress shippingAddress;

    @Column(name = "full_address", nullable = false, length = Integer.MAX_VALUE)
    private String fullAddress;

    @Column(name = "state", nullable = false, length = 100)
    private String state;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "zip_code", nullable = false, length = 20)
    private String zipCode;



}
