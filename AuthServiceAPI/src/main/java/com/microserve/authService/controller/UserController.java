package com.microserve.authService.controller;

import com.microserve.authService.model.StrippedUser;
import com.microserve.authService.model.User;
import com.microserve.authService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final static int DEFAULT_PAGE_SIZE = 30;

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

    @GetMapping("/page")
    public Object getPage(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        int _page = page != null ? page : 0;
        int _size = size != null ? size : 0;

        return service.getPage(_page, _size);
    }

    @GetMapping("/id")
    public StrippedUser getUserById(@PathVariable long id) {
        return service.findById(id).strip();
    }

    @DeleteMapping("/id")
    public void deleteUserById(@PathVariable long id) {
        service.deleteById(id);
    }

    @PostMapping("/")
    public Object postUser(
            @RequestBody User user
    ) {
        return service.save(user);
    }


}
