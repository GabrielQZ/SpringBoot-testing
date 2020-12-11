package com.microserve.authService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class UserCredentials {
    public String credential;
    public String password;

    @JsonProperty("credentials")
    public void unpackData(Map<String, String> user) {
        credential = user.get("credential");
        password = user.get("password");
    }

    public void sanitizeData () {
        credential = credential != null ? credential.strip() : null;
        password = password != null ? password.strip() : null;

    }

}
