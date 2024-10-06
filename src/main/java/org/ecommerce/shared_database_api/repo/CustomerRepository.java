package org.ecommerce.shared_database_api.repo;


import org.ecommerce.shared_database_api.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
