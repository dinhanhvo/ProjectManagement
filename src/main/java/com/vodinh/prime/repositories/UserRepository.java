package com.vodinh.prime.repositories;

import com.vodinh.prime.entities.Role;
import com.vodinh.prime.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByDeletedFalse(Pageable pageable);

//    Page<User> findByDeletedFalseAndRoles(Pageable pageable, Role role);

    Page<User> findByDeletedFalseAndCompanyNameIsNotNull(Pageable pageable);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

//    Page<User> findByIdIn(Pageable pageable, Page<Long> userIds);
//
//    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

//    Page<User> findByPhoneOrCompanyPhoneIs(String phone);
}