package com.microserve.msgateway;

import java.util.HashMap;


public class RequestMap {
    static final String authSystemEndPoint = "authSystem.endpoint";

    static final String createUser = REQUEST_TYPES.CREATE_USER.toString();
    static final String loginUser = REQUEST_TYPES.LOGIN_USER.toString();
    static final String getOneUser = REQUEST_TYPES.GET_ONE_USER.toString();
    static final String deleteOneUser = REQUEST_TYPES.DELETE_ONE_USER.toString();
    static final String getAllUsers = REQUEST_TYPES.GET_ALL_USERS.toString();

    static final HashMap<String, MSRequest> reqMap = new HashMap<>() {
        {
            put(createUser, new MSRequest(true,"POST", authSystemEndPoint));
            put(loginUser, new MSRequest(true, "PUT", authSystemEndPoint, "login"));
            put(getOneUser, new MSRequest(true, "GET", authSystemEndPoint, "id"));
            put(deleteOneUser, new MSRequest(true, "DELETE", authSystemEndPoint, "id"));
            put(getAllUsers, new MSRequest(true, "GET", authSystemEndPoint, "/all"));

        }
    };
}
