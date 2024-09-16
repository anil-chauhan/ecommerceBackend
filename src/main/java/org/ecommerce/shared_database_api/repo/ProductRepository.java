package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select pdt from Product as pdt where pdt.productId=:productId")
    Product findProductByCategoryId(@Param("productId") Integer productId);
}
