package com.microserve.authService.model;

public class UserCredentials {
    public String credential;
    public String password;

    public UserCredentials( String credential, String password ) {
        this.credential = credential;
        this.password = password;
    }

}
