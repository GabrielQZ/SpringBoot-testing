package com.microserve.authService.service;

import com.microserve.authService.model.User;
import com.microserve.authService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository database;

    public User findById(UUID id) {
        return database.findById(id).orElse(null);
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
