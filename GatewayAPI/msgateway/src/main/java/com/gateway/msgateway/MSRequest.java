package com.gateway.msgateway;

public class MSRequest {

    private final String method;

    private final String urlKey;

    private final String urlExtension;

    public MSRequest(String method, String urlKey, String urlExtension) {
        this.urlExtension = urlExtension;
        this.method = method;
        this.urlKey = urlKey;
    }

    public MSRequest(String method, String urlKey) {
        this.urlExtension = "";
        this.method = method;
        this.urlKey = urlKey;
    }

    public String getMethod() {
        return method;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public String getUrlExtension() {
        return urlExtension;
    }
}
