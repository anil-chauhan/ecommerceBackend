package org.ecommerce.shared_database_api.repo;


import org.ecommerce.shared_database_api.models.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StateRepository extends JpaRepository<State, Integer> {



    @Query(value = "select st from State as st where st.country.id=:code")
    List<State> findByCountryCode1(@Param("code") Integer code);

}
