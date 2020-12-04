package com.microserve.authService.model;

public class StrippedUser {

    public String email;

    protected StrippedUser( User user ) {
        this.email = user.email;
    }

}