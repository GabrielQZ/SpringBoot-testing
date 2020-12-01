package com.gateway.msgateway;

import java.util.HashMap;


public class RequestMap {
    static final String authSystemEndPoint = "authSystem.endpoint";

    static final String createUser = REQUEST_TYPES.CREATE_USER.toString();
    static final String loginUser = REQUEST_TYPES.LOGIN_USER.toString();
    static final String getOneUser = REQUEST_TYPES.GET_ONE_USER.toString();
    static final String deleteOneUser = REQUEST_TYPES.DELETE_ONE_USER.toString();

    static final HashMap<String, MSRequest> reqMap = new HashMap<>() {
        {
            put(createUser, new MSRequest("POST", authSystemEndPoint));
            put(loginUser, new MSRequest("PUT", authSystemEndPoint, "login"));
            put(getOneUser, new MSRequest("GET", authSystemEndPoint, "id"));
            put(deleteOneUser, new MSRequest("DELETE", authSystemEndPoint, "id"));
        }
    };
}
