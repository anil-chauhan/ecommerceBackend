package org.ecommerce.shared_database_api.controllers;

import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.models.User;
import org.ecommerce.shared_database_api.services.CategoryService;
import org.ecommerce.shared_database_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {



    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping(value = "/create_category")
    //@PostAuthorize("hasRole('ADMIN')")
    public void createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
    }

}
