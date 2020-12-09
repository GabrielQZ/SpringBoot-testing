package com.microserve.authService.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
//    @Type(type = "pg-uuid")
    @GeneratedValue
    public UUID id;
//    public long id;

//    @Column
    public String email;
//    @Column
    public String password;

    public User (String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public StrippedUser strip() {
        return new StrippedUser(this);
    }


}

