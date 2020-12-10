package com.microserve.authService.repository;

import com.microserve.authService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    int countByName(String name);

    int countByEmail(String email);

}
