package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
