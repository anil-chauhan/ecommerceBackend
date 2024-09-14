package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
}
