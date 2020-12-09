package com.microserve.msgateway.request;

import java.util.HashMap;


public class RequestMap {
    protected static final String authSystemEndPoint = "authSystem.endpoint";

    protected static final String createUser = REQUEST_TYPES.CREATE_USER.toString();
    protected static final String loginUser = REQUEST_TYPES.LOGIN_USER.toString();
    protected static final String getOneUser = REQUEST_TYPES.GET_ONE_USER.toString();
    protected static final String deleteOneUser = REQUEST_TYPES.DELETE_ONE_USER.toString();
    protected static final String getAllUsers = REQUEST_TYPES.GET_ALL_USERS.toString();
    protected static final String deleteAllUsers = REQUEST_TYPES.DELETE_ALL_USERS.toString();


    public static final HashMap<String, MSRequest> reqMap = new HashMap<>() {
        {
            put(createUser, new MSRequest(true,"POST", authSystemEndPoint));
            put(loginUser, new MSRequest(true, "PUT", authSystemEndPoint, "login"));
            put(getOneUser, new MSRequest(true, "GET", authSystemEndPoint, "id"));
            put(deleteOneUser, new MSRequest(true, "DELETE", authSystemEndPoint, "id"));
            put(getAllUsers, new MSRequest(true, "GET", authSystemEndPoint, "all"));
            put(deleteAllUsers, new MSRequest(true, "DELETE", authSystemEndPoint, "deleteAll"));
        }
    };
}
