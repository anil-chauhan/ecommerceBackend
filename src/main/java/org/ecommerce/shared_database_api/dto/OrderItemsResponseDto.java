package org.ecommerce.shared_database_api.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;


@Data
public class OrderItemsResponseDto implements Serializable {

    private Integer productId;
    String productName;
    String productImageUrl;
    private Double price;
    private int quantity;
    String brand;


    public OrderItemsResponseDto(Integer productId, String productName, String productImageUrl, Double price, int quantity, String brand) {
        this.productId = productId;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
    }

    public OrderItemsResponseDto() {
    }
}
