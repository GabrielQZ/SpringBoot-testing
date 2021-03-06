package com.microserve.authService.controller;

import com.microserve.authService.model.User;
import com.microserve.authService.model.UserCredentials;
import com.microserve.authService.service.UserService;
import com.microserve.authService.validator.UserValidationErrors;
import com.microserve.authService.validator.UserValidator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    Environment env;

    @Autowired
    UserService service;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/all")
    public Object getAll() {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    public Object getUserById(@PathVariable UUID id) {
//        return service User.User-clone(id);
        User foundUser = service.findById(id);
        if (foundUser == null)
            return "no user found";
        else
            return foundUser.strip();
    }

    @DeleteMapping("/id/{id}")
    public String deleteUserById(@PathVariable UUID id) {
        boolean wasDeleted = service.deleteById(id);
        System.out.println(wasDeleted);
        if (wasDeleted)
            return "User deleted successfully";
        else
            return "Error deleting user or user not found";
    }

    @DeleteMapping("/deleteAll/{adminKey}") //@
    public boolean deleteAll(@PathVariable String adminKey) {
        String adminSecret = env.getProperty("admin.secret");

        //System.out.println("AuthKey(User): " + adminKey + "\nAuthKey(Server): " + adminSecret);
        if (!adminKey.equals(adminSecret))
            return false;
        else
            return service.deleteAll();
    }

    @PostMapping("/")
    public Object postUser(
            @RequestBody User user
    ) {
        user.sanitizeData();
        JSONObject newUserErrors = UserValidator.validateNewUser(user, service);
        //System.out.println(newUserErrors);
        if (!newUserErrors.isEmpty())
            return newUserErrors.toString();
        else
            return service.save(user);
    }

    @PutMapping("/login")
    public Object loginUser (
            @RequestBody UserCredentials credentials
    ) {
        try {

            credentials.sanitizeData();
            return service.loginUser(credentials);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return UserValidationErrors.unhandledError(e.getMessage());
        }
    }

    @GetMapping("/testjwt")
    public Object testJWT (
            @RequestHeader(value = "capstone-user-auth") String jwt
    ) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(env.getProperty("jwt.secret").getBytes());

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt);
            return true;
        } catch (Exception e ) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
