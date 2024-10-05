package org.ecommerce.shared_database_api.repo;


import org.ecommerce.shared_database_api.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CountryRepository extends JpaRepository<Country, Integer> {
}
