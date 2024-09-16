package org.ecommerce.shared_database_api.services;


import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.repo.CategoryRepository;
import org.ecommerce.shared_database_api.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public  void createCategory(Category category){

        categoryRepository.save(category);

    }

}
