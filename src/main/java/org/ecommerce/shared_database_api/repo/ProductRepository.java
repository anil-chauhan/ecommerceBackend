package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.Category;
import org.ecommerce.shared_database_api.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select pdt from Product as pdt where pdt.productId=:productId")
    Product findProductByProductId(@Param("productId") Integer productId);

    @Query(value = "select pdt from Product as pdt where pdt.cat.categoryId=:categoryId")
    List<Product> findProductByCategoryId(@Param("categoryId") Integer categoryId);


    @Query(value = "select count (*) from Product as pdt where pdt.cat.categoryId=:categoryId")
    Integer findProductCountByCategoryId(@Param("categoryId") Integer categoryId);


    @Query("SELECT p FROM Product p WHERE  p.brand  like concat('%',:productName,'%') or p.productName  like concat('%',:productName,'%')")
    Page<Product> findProductByName1(@Param("productName") String productName, Pageable pageable);


    @Query("SELECT p FROM Product p WHERE LOWER(p.brand) LIKE LOWER(CONCAT('%', :productName, '%')) OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :productName, '%'))")
    Page<Product> findProductByName(@Param("productName") String productName, Pageable pageable);

    @Query("SELECT p FROM Product p")
    Page<Product> findProducts( Pageable pageable);


    @Query("SELECT p FROM Product p")
    ArrayList<Product> findTrendyProducts();




}
