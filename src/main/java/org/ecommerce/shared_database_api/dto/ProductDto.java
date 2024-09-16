package org.ecommerce.shared_database_api.dto;

import jakarta.persistence.Column;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link org.ecommerce.shared_database_api.models.Product}
 */
@Value
public class ProductDto implements Serializable {
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
