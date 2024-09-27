package org.ecommerce.shared_database_api.controllers;


import org.ecommerce.shared_database_api.dto.CategoryDto;
import org.ecommerce.shared_database_api.dto.ProductDto;
import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.models.Product;
import org.ecommerce.shared_database_api.services.CategoryService;
import org.ecommerce.shared_database_api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "http://localhost:4200")
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

    @GetMapping("/get_all_product_from_a_category_by_id")
    //@PostAuthorize("hasRole('ADMIN')")
    public List<ProductDto> getAllProductFromACategoryById( @RequestBody ProductDto productDto) {

        Category categoryById = categoryService.getCategoryById(productDto.getCategoryId());

        return productService.getAllProductByCategoryId(categoryById);
    }

    @PostMapping("/get_all_product_from_a_category_by_name")
    //@PostAuthorize("hasRole('ADMIN')")
    public Optional<Object> getAllProductFromACategoryByName(@RequestBody CategoryDto categoryDto) {
        Category category = categoryService.getCategoryByName(categoryDto.getCategoryName());
        // find if any subcategory is available or not
        if(category.getParentCat() == null) {
            List<Category> subCategoryByParent = categoryService.getSubCategoryByParentId(category.getCategoryId());
            if(subCategoryByParent!=null){
                return Optional.ofNullable(subCategoryByParent);
            }
            //Category parentCat = category.getParentCat();
            //return Optional.ofNullable(parentCat);

        }
        return Optional.ofNullable(productService.getAllProductByCategoryId(category));
    }


}
