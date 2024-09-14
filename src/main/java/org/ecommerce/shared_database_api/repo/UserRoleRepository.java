package org.ecommerce.shared_database_api.repo;

import org.ecommerce.shared_database_api.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
