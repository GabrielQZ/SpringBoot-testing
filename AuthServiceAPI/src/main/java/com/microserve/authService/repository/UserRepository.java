package com.microserve.authService.repository;

import com.microserve.authService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    int countByUsername(String username);

    int countByEmail(String email);

    List<User> findByEmail(String email);

    List<User> findByUsername(String username);

}
