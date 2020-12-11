package com.microserve.authService.service;

import com.microserve.authService.model.User;
import com.microserve.authService.model.UserCredentials;
import com.microserve.authService.repository.UserRepository;
import com.microserve.authService.validator.UserJWT;
import com.microserve.authService.validator.UserValidationErrors;
import com.microserve.authService.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCrypt;
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

    public boolean nameInUse(String username) {
        return database.countByUsername(username) > 0;
    }

    public User save(User user) {
        return database.save(user);
    }

    public List<User> findAll() {
        return database.findAll();
    }

    public Object loginUser(UserCredentials credentials, String jwtSecret) {
        try {
            String credential = credentials.credential;
            String password = credentials.password;
            List<User> userQueryResults;
            if (UserValidator.isEmail(credential))
                userQueryResults = database.findByEmail(credential);
            else
                userQueryResults = database.findByUsername(credential);

            if (userQueryResults.size() == 1 ) {

                User foundUser = userQueryResults.get(0);

                boolean credentialsMatch = BCrypt.checkpw(password, foundUser.password);

                if (credentialsMatch)
                    return new UserJWT().createJWT(foundUser, jwtSecret);
                else
                    return UserValidationErrors.credentialsDoNotMatch();
            } else {

                return UserValidationErrors.credentialsDoNotMatch();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return UserValidationErrors.loggingInServerError();
        }

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
