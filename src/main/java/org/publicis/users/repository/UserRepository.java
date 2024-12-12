package org.publicis.users.repository;

import org.publicis.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByRole(String role);
    Optional<UserEntity> findByIdOrSsn(Long id, String ssn);
    List<UserEntity> findByOrderByAgeAsc();
    List<UserEntity> findByOrderByAgeDesc();
}
