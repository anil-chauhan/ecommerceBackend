package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select cat from Category as cat where cat.categoryId=:categoryId")
    Category findCategoryByCategoryId(@Param("categoryId") Integer categoryId);
}

