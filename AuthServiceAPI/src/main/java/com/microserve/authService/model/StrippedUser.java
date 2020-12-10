package com.microserve.authService.model;

public class StrippedUser {

    public String email;
    public String username;

    protected StrippedUser( User user ) {
        this.email = user.email;
        this.username = user.username;
    }

}