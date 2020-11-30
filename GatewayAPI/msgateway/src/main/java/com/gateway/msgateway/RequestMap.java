package com.gateway.msgateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.util.HashMap;


public class RequestMap {
    static final String authSystemEndPoint = "authSystem.endpoint";

    static final String postUser = REQUEST_TYPES.POST_USER.toString();
    static final String loginUser = REQUEST_TYPES.LOGIN_USER.toString();
    static final String getUser = REQUEST_TYPES.GET_USER.toString();
    static final String deleteUser = REQUEST_TYPES.DELETE_USER.toString();

    static final HashMap<String, MSRequest> reqMap = new HashMap<>() {
        {
            put(postUser, new MSRequest("POST", authSystemEndPoint));
            put(loginUser, new MSRequest("PUT", authSystemEndPoint));
            put(getUser, new MSRequest("GET", authSystemEndPoint));
            put(deleteUser, new MSRequest("DELETE", authSystemEndPoint));
        }
    };
}
