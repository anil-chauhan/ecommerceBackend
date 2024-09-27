package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select pdt from Product as pdt where pdt.productId=:productId")
    Product findProductByProductId(@Param("productId") Integer productId);

    @Query(value = "select pdt from Product as pdt where pdt.cat.categoryId=:categoryId")
    List<Product> findProductByCategoryId(@Param("categoryId") Integer categoryId);
}
