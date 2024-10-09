package org.ecommerce.shared_database_api.controllers;

import lombok.Data;
import org.ecommerce.shared_database_api.dto.CategoryDetailsDto;
import org.ecommerce.shared_database_api.dto.CategoryDto;
import org.ecommerce.shared_database_api.dto.CategoryTreeDto;
import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.models.User;
import org.ecommerce.shared_database_api.services.CategoryService;
import org.ecommerce.shared_database_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class CategoryController {



    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Data
    class CategoryResponseMessage{
        String message;
    }


    @PostMapping("/create_category")
    //@PostAuthorize("hasRole('ADMIN')")
    public CategoryResponseMessage createCategory( @RequestBody CategoryDto categoryDto) {

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

        String category1 = categoryService.createCategory(category);

        CategoryResponseMessage responseMessage = new CategoryResponseMessage();
        responseMessage.message = category1;

        return responseMessage;
    }






    @PostMapping("/create_category_by_name")
    //@PostAuthorize("hasRole('ADMIN')")
    public CategoryResponseMessage createCategoryByName( @RequestBody CategoryDto categoryDto) {

        Category category = new Category();
        Category categoryById = categoryService.getCategoryByName(categoryDto.getParentCatCategoriesName());
        category.setParentCat(categoryById);
        category.setCategoryName(categoryDto.getCategoryName());
        category.setUrlSlug(categoryDto.getUrlSlug());
        if(categoryDto.getUrlSlug()==null) {
            category.setUrlSlug("");
        }
        category.setStatus(categoryDto.getStatus());

        String category1 = categoryService.createCategory(category);

        CategoryResponseMessage responseMessage = new CategoryResponseMessage();
        responseMessage.message = category1;

        return responseMessage;
    }



    @GetMapping("/get_all_category")
    //@PostAuthorize("hasRole('ADMIN')")
    public List<CategoryTreeDto> getAllCategory() {
        //return categoryService.getAllCategories();
        return categoryService.getMenuCategories();

    }


    @GetMapping("/get_all_category_details")
    //@PostAuthorize("hasRole('ADMIN')")
    public ArrayList<CategoryDetailsDto> getAllCategoryDetails() {
        //return categoryService.getAllCategories();
        return categoryService.getMenuCategoriesDetails();

    }


    @PostMapping("/is_sub_category_available")
    //@PostAuthorize("hasRole('ADMIN')")
    public boolean isSubCategoryAvailable(@RequestBody CategoryDto categoryDto) {
            return categoryService.isSubCategoryAvailable(categoryDto);
    }

}
