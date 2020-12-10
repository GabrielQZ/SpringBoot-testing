package com.microserve.authService.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    public UUID id;
    @Column
    public String username;
    @Column
    public String email;
    @Column
    public String password;

    @JsonProperty("user")
    public void unpackData(Map<String, String> user) {
        email = user.get("email");
        password = user.get("password");
        username = user.get("username");
    }

    public StrippedUser strip() {
        return new StrippedUser(this);
    }

    public void sanitizeData () {
        username = username != null ? username.strip() : null;
        email = email != null ? email.strip() : null;
        password = password != null ? password.strip() : null;
    }

}

