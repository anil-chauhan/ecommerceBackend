package org.ecommerce.shared_database_api.repo;


import org.ecommerce.shared_database_api.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
}
