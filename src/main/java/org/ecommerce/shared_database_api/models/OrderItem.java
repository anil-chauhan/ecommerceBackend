package org.ecommerce.shared_database_api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;

@Entity
@Table(name="order_item")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="image_url" ,length=2000)
    private String imageUrl;

    @Column(name="unit_price")
    private Double unitPrice;

    @Column(name="quantity")
    private int quantity;

    @Column(name="product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}








