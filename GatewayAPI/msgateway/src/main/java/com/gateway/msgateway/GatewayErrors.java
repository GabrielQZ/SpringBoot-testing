package com.gateway.msgateway;

public class GatewayErrors {

    public static final String CONNECTION_ERROR = "{error: \"Request failed: Server could not connect to API\"}";
    public static final String MISSING_DATA_ERROR = "{error: \"Request not made: No 'action' OR 'data' present in request\"}";
    public static final String BAD_REQUEST_ERROR = "{error: \"Request failed: Unknown reason\"}";
    public static final String REQUEST_URL_MISMATCH = "{error: \"Request not made: There is no url to make the given action\"}";

}
