package org.ecommerce.shared_database_api.services;


import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public  String createCategory(Category category){

        Category save = categoryRepository.save(category);
        if(save != null){
            return "Category created successfully";
        }
        else {
            return "Category creation failed";
        }



    }

    public  Category getCategoryById(int categoryId){

        Category save = categoryRepository.findCategoryByCategoryId(categoryId);
        if(save!=null){
            return save;
        }
        else {
            return null;
        }



    }

}
