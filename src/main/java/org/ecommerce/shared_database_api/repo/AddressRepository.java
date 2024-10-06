package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
