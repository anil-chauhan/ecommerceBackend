package org.ecommerce.shared_database_api.controllers;

import org.ecommerce.shared_database_api.dto.CategoryDto;
import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.models.User;
import org.ecommerce.shared_database_api.services.CategoryService;
import org.ecommerce.shared_database_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {



    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/create_category")
    //@PostAuthorize("hasRole('ADMIN')")
    public String createCategory( @RequestBody CategoryDto categoryDto) {

        Category category = new Category();
        if(categoryDto.getParentCatCategoriesId()==0){
            category.setParentCat(null);
        }else {
            Category categoryById = categoryService.getCategoryById(categoryDto.getParentCatCategoriesId());
            category.setParentCat(categoryById);
        }
        category.setCategoryName(categoryDto.getCategoryName());
        category.setUrlSlug(categoryDto.getUrlSlug());
        category.setStatus(categoryDto.getStatus());

        return categoryService.createCategory(category);
    }

}
