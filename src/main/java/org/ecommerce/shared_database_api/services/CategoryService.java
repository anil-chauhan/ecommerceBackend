package org.ecommerce.shared_database_api.services;


import org.ecommerce.shared_database_api.dto.CategoryDto;
import org.ecommerce.shared_database_api.dto.CategoryTreeDto;
import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

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

    public  List<Category> getSubCategoryByParentId(int parentCategoryId){

        List<Category> save = categoryRepository.findSubCategoryByParentCategoryId(parentCategoryId);
        if(save!=null){
            return save;
        }
        else {
            return null;
        }



    }



    public  Category getCategoryByName(String categoryName){

        Category save = categoryRepository.findCategoryByCategoryName(categoryName);
        if(save!=null){
            return save;
        }
        else {
            return null;
        }



    }

    public  List<CategoryDto> getAllCategories(){

        List<Category> all = categoryRepository.findAll();

        List<CategoryDto> categoryDtos=new ArrayList<>();
        for(Category category : all){

            CategoryDto categoryDto=new CategoryDto();
            categoryDto.setCategoryName(category.getCategoryName());
            categoryDto.setStatus(category.getStatus());
            categoryDto.setUrlSlug(category.getUrlSlug());

            try {
                categoryDto.setParentCatCategoriesId(category.getParentCat().getCategoryId());
            }catch (Exception e){
                categoryDto.setParentCatCategoriesId(0);
            }


            categoryDtos.add(categoryDto);

        }
        return categoryDtos;



    }


    public  boolean isSubCategoryAvailable(CategoryDto categoryDto){



        Category category = getCategoryByName(categoryDto.getCategoryName());
        // find if any subcategory is available or not
        if(category.getParentCat() == null) {
            List<Category> subCategoryByParent = getSubCategoryByParentId(category.getCategoryId());
            if(subCategoryByParent!=null){
                return true;
            }

            //Category parentCat = category.getParentCat();
            //return Optional.ofNullable(parentCat);

        }
        return false;

    }

    /*public List<CategoryDto> getMenuCategories() {
        List<Category> rootCategories = categoryRepository.findByParentCatIsNull();
        return buildCategoryTree(rootCategories);
    }

    private List<CategoryDto> buildCategoryTree(List<Category> categories) {

        List<CategoryDto> categoryDtos = new ArrayList<>();


        for (Category category : categories) {
            List<Category> subCategories = categoryRepository.findByParentCat(category);
            category.setSubCategories(subCategories);
            buildCategoryTree(subCategories); // Recursive call for nested subcategories


            CategoryDto categoryDto = new CategoryDto();
            //categoryDto.set(category.getCategoryId());
            categoryDto.setCategoryName(category.getCategoryName());
            categoryDto.setUrlSlug(category.getUrlSlug());
            categoryDto.setStatus(category.getStatus());

            // Fetch subcategories recursively
            List<Category> subCategories1 = categoryRepository.findByParentCat(category);
            if (subCategories != null && !subCategories1.isEmpty()) {
                categoryDto.setSubCategories(buildCategoryTree(subCategories1)); // Recursive call
            }

            categoryDtos.add(categoryDto);

        }

        return categoryDtos;
    }*/


    public List<CategoryTreeDto> getMenuCategories() {
        List<Category> allCategories = categoryRepository.findAll(); // Fetch all categories
        return buildCategoryTree(allCategories);
    }

    private List<CategoryDto> buildCategory(List<Category> categories) {
        Map<Integer, CategoryDto> categoryDtoMap = new HashMap<>();
        List<CategoryDto> rootCategoryDtos = new ArrayList<>();

        // Create DTOs and populate the map
        for (Category category : categories) {
            CategoryDto categoryDto = new CategoryDto();
            //categoryDto.setCategoryId(category.getCategoryId()); // Set the category ID
            categoryDto.setCategoryName(category.getCategoryName());
            categoryDto.setUrlSlug(category.getUrlSlug());
            categoryDto.setStatus(category.getStatus());
            categoryDto.setExpanded(false); // Initialize isExpanded to false
            categoryDtoMap.put(category.getCategoryId(), categoryDto);

            // If the category has no parent, add it to the root list
            if (category.getParentCat() == null) {
                rootCategoryDtos.add(categoryDto);
            }
        }

        // Build the hierarchy
        for (Category category : categories) {
            if (category.getParentCat() != null) {
                CategoryDto parentDto = categoryDtoMap.get(category.getParentCat().getCategoryId());
                if (parentDto != null) {
                    if (parentDto.getSubCategories() == null) {
                        parentDto.setSubCategories(new ArrayList<>());
                    }
                    parentDto.getSubCategories().add(categoryDtoMap.get(category.getCategoryId()));
                    parentDto.setExpanded(false);
                }
            }

        }

        //rootCategoryDtos.remove(0);

        return rootCategoryDtos; // Return the top-level categories
    }


    private List<CategoryTreeDto> buildCategoryTree(List<Category> categories) {
        Map<Integer, CategoryTreeDto> categoryDtoMap = new HashMap<>();
        List<CategoryTreeDto> rootCategoryDtos = new ArrayList<>();

        // Create DTOs and populate the map
        for (Category category : categories) {
            CategoryTreeDto categoryDto = new CategoryTreeDto();
            //categoryDto.setCategoryId(category.getCategoryId()); // Set the category ID
            categoryDto.setCategoryName(category.getCategoryName());
            //categoryDto.setUrlSlug(category.getUrlSlug());
            //categoryDto.setStatus(category.getStatus());
            //categoryDto.setExpanded(false); // Initialize isExpanded to false

            categoryDto.setCategoryId(category.getCategoryId()); // Initialize isExpanded to false
            categoryDtoMap.put(category.getCategoryId(), categoryDto);

            // If the category has no parent, add it to the root list
            if (category.getParentCat() == null) {
                rootCategoryDtos.add(categoryDto);
            }
        }

        // Build the hierarchy
        for (Category category : categories) {
            if (category.getParentCat() != null) {
                CategoryTreeDto parentDto = categoryDtoMap.get(category.getParentCat().getCategoryId());
                if (parentDto != null) {
                    if (parentDto.getSubCategories() == null) {
                        parentDto.setSubCategories(new ArrayList<>());
                    }
                    parentDto.getSubCategories().add(categoryDtoMap.get(category.getCategoryId()));
                    //parentDto.setExpanded(false);
                }
            }

        }

        //rootCategoryDtos.remove(0);

        return rootCategoryDtos; // Return the top-level categories
    }



    /*

    private List<CategoryDto> buildCategoryTree(List<Category> categories) {
        Map<Integer, CategoryDto> categoryDtoMap = new HashMap<>();
        List<CategoryDto> rootCategoryDtos = new ArrayList<>();

        // Create DTOs and populate the map
        for (Category category : categories) {
            CategoryDto categoryDto = new CategoryDto();
            //categoryDto.setCategoryId(category.getCategoryId());
            categoryDto.setCategoryName(category.getCategoryName());
            categoryDto.setUrlSlug(category.getUrlSlug());
            categoryDto.setStatus(category.getStatus());
            categoryDtoMap.put(category.getCategoryId(), categoryDto);

            // If the category has no parent, add it to the root list
            if (category.getParentCat() == null) {
                rootCategoryDtos.add(categoryDto);
            }
        }

        // Build the hierarchy
        for (Category category : categories) {
            if (category.getParentCat() != null) {
                CategoryDto parentDto = categoryDtoMap.get(category.getParentCat().getCategoryId());
                if (parentDto != null) {
                    if (parentDto.getSubCategories() == null) {
                        parentDto.setSubCategories(new ArrayList<>());
                    }
                    parentDto.getSubCategories().add(categoryDtoMap.get(category.getCategoryId()));
                }
            }
        }

        return rootCategoryDtos; // Return the top-level categories
    }

*/
}
