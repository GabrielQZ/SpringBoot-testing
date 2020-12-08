package com.microserve.authService.controller;

import org.springframework.core.env.Environment;

public class UserControllerTools {

    protected String getEnvVar (Environment env, String key) {
        return env.getProperty(key);
    }

}
