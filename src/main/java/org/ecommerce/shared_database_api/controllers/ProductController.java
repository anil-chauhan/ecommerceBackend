package org.ecommerce.shared_database_api.controllers;


import org.ecommerce.shared_database_api.dto.ProductDto;
import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.models.Product;
import org.ecommerce.shared_database_api.services.CategoryService;
import org.ecommerce.shared_database_api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {


    private final ProductService productService;
    private final CategoryService categoryService;


    @Autowired
    public ProductController(ProductService productService,CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }


    @PostMapping("/add_product")
    //@PostAuthorize("hasRole('ADMIN')")
    public String addProduct( @RequestBody ProductDto productDto) {

        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setUrlSlug(productDto.getUrlSlug());
        product.setDescription(productDto.getDescription());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setStatus(productDto.getStatus());
        product.setBrand(productDto.getBrand());
        product.setProductImageUrl(productDto.getProductImageUrl());
        Category categoryById = categoryService.getCategoryById(productDto.getCategoryId());
        product.setCat(categoryById);
        return productService.addProduct(product);
    }


}
