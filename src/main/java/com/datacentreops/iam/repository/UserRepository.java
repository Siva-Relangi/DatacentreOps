package com.datacentreops.iam.repository;

import com.datacentreops.iam.entity.Role;
import com.datacentreops.iam.entity.User;
import com.datacentreops.iam.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);

    List<User> findByOrganisationId(Long organisationId);

    List<User> findByStatus(UserStatus status);

    boolean existsByEmail(String email);
}