package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
}
