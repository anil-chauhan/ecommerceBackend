package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.OrderShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderShippingAddressRepository extends JpaRepository<OrderShippingAddress, Integer> {
}
