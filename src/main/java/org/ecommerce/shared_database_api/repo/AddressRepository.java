package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.Address;
import org.ecommerce.shared_database_api.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = "select adrs from Address as adrs where adrs.addressHash=:address_hash")
    Address findAddressByHash(@Param("address_hash") String address_hash);


    @Query(value = "select adrs from Address as adrs where adrs.customer.email=:email")
    Address getAddressByCustomerId(@Param("email") String email);

}
