package com.microserve.authService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

import javax.persistence.*;
import java.beans.Transient;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    public UUID id;
    @Column
    public String email;
    @Column
    public String password;

    @JsonProperty("user")
    public void unpackData(Map<String, String> user) {
        email = user.get("email");
        password = user.get("password");
    }

    public StrippedUser strip() {
        return new StrippedUser(this);
    }

}

