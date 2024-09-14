package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
