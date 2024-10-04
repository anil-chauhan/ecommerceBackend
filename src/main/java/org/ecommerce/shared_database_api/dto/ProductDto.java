package org.ecommerce.shared_database_api.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class ProductDto implements Serializable {
    private Integer productId;
    String productName;
    String urlSlug;
    int categoryId;
    String description;
    Double price;
    Integer stockQuantity;
    String status;
    String brand;
    String productImageUrl;


}
