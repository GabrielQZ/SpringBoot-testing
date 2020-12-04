package com.microserve.msgateway;

public class MSRequest {

    private final String method;

    private final String urlKey;

    private final String urlExtension;

    private final boolean isPublic;

    public MSRequest(boolean isPublic, String method, String urlKey, String urlExtension) {
        this.urlExtension = urlExtension;
        this.isPublic = isPublic;
        this.method = method;
        this.urlKey = urlKey;
    }

    public MSRequest(boolean isPublic, String method, String urlKey) {
        this.urlExtension = "";
        this.isPublic = isPublic;
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

    public boolean isPublic() {
        return isPublic;
    }
}
