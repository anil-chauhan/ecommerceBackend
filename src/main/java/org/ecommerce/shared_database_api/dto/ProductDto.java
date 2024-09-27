package org.ecommerce.shared_database_api.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

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

    public String getProductName() {
        return productName;
    }

    public String getUrlSlug() {
        return urlSlug;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public String getStatus() {
        return status;
    }

    public String getBrand() {
        return brand;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setUrlSlug(String urlSlug) {
        this.urlSlug = urlSlug;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }
}
