package com.microserve.authService.controller;

import com.microserve.authService.model.StrippedUser;
import com.microserve.authService.model.User;
import com.microserve.authService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private Environment env;

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
    public StrippedUser getUserById(@PathVariable long id) {
//        return service User.User-clone(id);
    }

    @DeleteMapping("/id/{id}")
    public void deleteUserById(@PathVariable long id) {
        System.out.println(service.deleteById(id););

    }

    @DeleteMapping("/deleteAll/{adminKey}") //@
    public boolean deleteAll(@PathVariable String adminKey) {
        System.out.println(adminKey);
        String adminSecret = env.getProperty("admin_secret");
        System.out.println(adminSecret);
//        if (!adminKey.equals(adminSecret))
//            return false;
//        else
            return true;
//      return service.deleteAll();
//        return false;
    }

    @PostMapping("/")
    public Object postUser(
            @RequestBody User user
    ) {
        return service.save(user);
    }


}
