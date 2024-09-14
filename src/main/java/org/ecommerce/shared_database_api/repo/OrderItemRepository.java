package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
