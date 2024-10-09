package org.ecommerce.shared_database_api.services;


import org.ecommerce.shared_database_api.dto.CategoryDetailsDto;
import org.ecommerce.shared_database_api.dto.CategoryDto;
import org.ecommerce.shared_database_api.dto.CategoryTreeDto;
import org.ecommerce.shared_database_api.dto.ProductDto;
import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
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

    public ArrayList<CategoryDetailsDto> getMenuCategoriesDetails() {
        List<Category> allCategories = categoryRepository.findAll(); // Fetch all categories
        return buildCategoryTreeDetails(allCategories);
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




    private  ArrayList<CategoryDetailsDto> buildCategoryTreeDetails(List<Category> categories) {


        Map<Integer, CategoryTreeDto> categoryDtoMap = new HashMap<>();


        List<CategoryTreeDto> rootCategoryDtos = new ArrayList<>();

        // Create DTOs and populate the map
        for (Category category : categories) {
            CategoryTreeDto categoryDto = new CategoryTreeDto();

            categoryDto.setCategoryName(category.getCategoryName());
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

        List<CategoryTreeDto> rootCategoryDtosNew = new ArrayList<>();
        List<CategoryTreeDto> subCategoriesLevel0 = rootCategoryDtos.get(0).getSubCategories();


        ArrayList<CategoryDetailsDto> categoryDetails = getCategoryDetails(subCategoriesLevel0);


        return categoryDetails; // Return the top-level categories
    }


    public ArrayList<CategoryDetailsDto> getCategoryDetails(List<CategoryTreeDto> subCategoriesLevel0){


        ArrayList<CategoryDetailsDto> categoryDetailsDtos = new ArrayList<>();

        ArrayList<Integer> allSubCategoryIds = new ArrayList<>();



        for(CategoryTreeDto categoryTreeDto:subCategoriesLevel0){
            CategoryDetailsDto categoryDetailsDto=new CategoryDetailsDto();
            String categoryName = categoryTreeDto.getCategoryName();
            categoryDetailsDto.setCategoryName(categoryName);
            //categoryDetailsDtos.add(categoryDetailsDto);

            List<CategoryTreeDto> subCategoriesLevel1 = categoryTreeDto.getSubCategories();

            Integer categoryId = categoryTreeDto.getCategoryId();
            allSubCategoryIds.add(categoryId);
            allSubCategoryIds = getAllChildCategoryId(subCategoriesLevel1, allSubCategoryIds);


            CategoryDetailsDto productCountByCategoryId = getProductCountByCategoryId(allSubCategoryIds, categoryDetailsDto);
            categoryDetailsDtos.add(productCountByCategoryId);
        }


        return categoryDetailsDtos;
    }



    public CategoryDetailsDto getProductCountByCategoryId(ArrayList<Integer> allSubCategoryIds, CategoryDetailsDto categoryDetailsDto){


        for (Integer categoryId : allSubCategoryIds) {

            List<ProductDto> allProductCountByCategoryId = productService.getAllProductCountByCategoryId(categoryId);

            if(allProductCountByCategoryId.size()>0){
                categoryDetailsDto.setProductCountInCategory(allProductCountByCategoryId.size());
                categoryDetailsDto.setProductRandomImageUrl(allProductCountByCategoryId.get(0).getProductImageUrl());
            }

        }

        return  categoryDetailsDto;

    }




    public ArrayList<Integer> getAllChildCategoryId(List<CategoryTreeDto> subCategoriesLevel1, ArrayList<Integer> allSubCategoryIds){

        for(CategoryTreeDto categoryTreeDto:subCategoriesLevel1){
            //CategoryDetailsDto categoryDetailsDto=new CategoryDetailsDto();
            Integer categoryId = categoryTreeDto.getCategoryId();
            allSubCategoryIds.add(categoryId);
            if(categoryTreeDto.getSubCategories()!=null){
                List<CategoryTreeDto> subCategories1 = categoryTreeDto.getSubCategories();

                getAllChildCategoryId(subCategories1,allSubCategoryIds);
            }
        }

        return allSubCategoryIds;


    }


    private List<CategoryTreeDto> buildCategoryTree(List<Category> categories) {
        Map<Integer, CategoryTreeDto> categoryDtoMap = new HashMap<>();
        List<CategoryTreeDto> rootCategoryDtos = new ArrayList<>();

        // Create DTOs and populate the map
        for (Category category : categories) {
            CategoryTreeDto categoryDto = new CategoryTreeDto();

            categoryDto.setCategoryName(category.getCategoryName());
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
