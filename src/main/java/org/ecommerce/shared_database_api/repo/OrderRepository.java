package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
