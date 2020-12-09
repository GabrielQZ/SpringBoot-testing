package com.microserve.authService.model;

import org.json.JSONObject;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    public UUID id;
    @Transient
    public Object user;
    @Column
    public String email;
    @Column
    public String password;

    public StrippedUser strip() {
        return new StrippedUser(this);
    }


}

