package com.gateway.msgateway;

public class MSRequest {

    private final String method;

    private final String urlKey;

    public MSRequest(String method, String urlKey) {
        this.method = method;
        this.urlKey = urlKey;
    }

    public String getMethod() {
        return method;
    }

    public String getUrlKey() {
        return urlKey;
    }

}
