package com.microserve.msgateway.router;

public class GatewayErrors {

    public static final String CONNECTION_ERROR = "{error: \"Request failed: Gateway could not connect to API Service\"}";
    public static final String MISSING_DATA_ERROR = "{error: \"Request not made: No 'action' OR 'data' present in request\"}";
    public static final String UNKNOWN_REQUEST_ERROR = "{error: \"Request failed: Unknown reason\"}";
    public static final String REQUEST_URL_MISMATCH = "{error: \"Request not made: There is no url to make the given action\"}";
    public static final String INTERNAL_SERVER_ERROR = "{error: \"Request failed: Internal Server Error In Another Server\"}";
    public static final String REQUEST_ACTION_MISMATCH = "{error: \"Request not made: There is no request to match the given action\"}";
    public static final String REQUEST_NEEDS_AUTH = "{error: \"Request not made: This request needs authorization to be made\"}";
    public static final String CHECK_PROTOCOL_ERROR = "{error: \"Request failed: Check URI protocol\"}";
    public static final String BAD_REQUEST_DATA = "{error: \"Request not made: Request data caused error in Gateway\"}";
    public static final String SERVER_ENVIRONMENT_NULL = "{error: \"Request not made: Gateway environment needs to be set\"}";
}
