package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
