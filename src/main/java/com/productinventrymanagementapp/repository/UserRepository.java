package com.productinventrymanagementapp.repository;

import com.productinventrymanagementapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(@Param("email") String email);
}
