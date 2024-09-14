package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
}
