package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select cat from Category as cat where cat.categoryId=:categoryId")
    Category findCategoryByCategoryId(@Param("categoryId") Integer categoryId);

    @Query(value = "select cat from Category as cat where cat.categoryName=:categoryName")
    Category findCategoryByCategoryName(@Param("categoryName") String categoryName);

    @Query(value = "select cat from Category as cat where cat.parentCat.categoryId=:parentCategoryId")
    List<Category> findSubCategoryByParentCategoryId(@Param("parentCategoryId") int parentCategoryId);

    List<Category> findByParentCatIsNull(); // Root categories
    List<Category> findByParentCat(Category parentCat); // Subcategories for a specific parent
}

