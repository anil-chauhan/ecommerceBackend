package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
}
