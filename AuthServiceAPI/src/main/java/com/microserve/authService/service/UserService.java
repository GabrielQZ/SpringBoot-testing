package com.microserve.authService.service;

import com.microserve.authService.model.User;
import com.microserve.authService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository database;

    public User findById(UUID id) {
        return database.findById(id).orElse(null);
    }

    public boolean emailInUse(String email) {
        return database.countByEmail(email) > 0;
    }

    public boolean nameInUse(String name) {
        return database.countByName(name) > 0;
    }

    public User save(User user) {
        return database.save(user);
    }

    public List<User> findAll() {
        return database.findAll();
    }

    public boolean deleteAll() {
        try {
            database.deleteAll();
            return true;
        } catch ( Exception e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(UUID id) {
        try {
            database.deleteById(id);
            return true;
        } catch ( Exception e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
            return false;
        }

    }


}
