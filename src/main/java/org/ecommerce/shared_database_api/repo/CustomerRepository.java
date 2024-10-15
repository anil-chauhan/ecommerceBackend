package org.ecommerce.shared_database_api.repo;


import org.ecommerce.shared_database_api.models.Customer;
import org.ecommerce.shared_database_api.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select cust from Customer as cust where cust.email=:email")
    Customer findCustomerByEmailId(@Param("email") String email);

}
