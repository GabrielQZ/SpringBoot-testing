package com.gateway.msgateway;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@RestController
public class GatewayRouter {


    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Autowired
    private Environment env;


    //MAIN ROUTES
    //this gateway acts as the one entry point the the entire micro-service system. Some routes are public and some are authorized.
    //authorized routes will pass along headers (where auth keys will be stored) and public routes will not
    //endpoints to other MS servers will be stored privately in the gateways code

    //AUTHORIZED ROUTE
    @PutMapping("/auth")
    public void authorizedRoute(){

    }

    //PUBLIC ROUTE
    @PutMapping(
            value = "/public",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )

    public String publicRoute(@RequestBody String rawData){
        try {
            
            JSONObject rawRequest = new JSONObject(rawData);

            //these two declarations will trigger exceptions if not present in the request
            // 'data' for get and deletes will be url extensions,
            // while put and post will be json that get sent to the service
            String reqData = rawRequest.get("data").toString();
            String reqAction = rawRequest.get("action").toString();

            MSRequest requestDetails = RequestMap.reqMap.get(reqAction);

            String requestMethod = requestDetails.getMethod();
            String requestURL = env.getProperty(requestDetails.getUrlKey());

            if (requestURL == null)
                return GatewayErrors.REQUEST_URL_MISMATCH;

            return sendRequest(reqData, requestURL, requestMethod);

        } catch (JSONException e ) {
//            e.printStackTrace();
            return GatewayErrors.MISSING_DATA_ERROR;
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("\nError with request being sent from Gateway\n");
            return GatewayErrors.BAD_REQUEST_ERROR;
        }
    }

    private String sendRequest( String data, String endpoint, String method) throws Exception {

        //the 'body' only gets used for POST and PUT request
        var body = HttpRequest.BodyPublishers.ofString(data);
        var requestBuilder = HttpRequest.newBuilder();

        switch (method) {
            case "POST" -> requestBuilder = requestBuilder.POST(body);
            case "PUT" -> requestBuilder = requestBuilder.PUT(body);
            case "DELETE" -> requestBuilder = requestBuilder.DELETE();
            case "GET" -> requestBuilder = requestBuilder.GET();
            default -> System.out.println("\nMETHOD NOT PROVIDED (ln 97 GatewayRouter)");
        }

        HttpRequest builtReq = requestBuilder
                .uri(URI.create(
                        method.equals("GET") || method.equals("DELETE")
                        ? endpoint + data : endpoint
                ))
                .header("Content-Type", "application/json")
                .build();

        try {

            HttpResponse<String> response = httpClient.send(builtReq, HttpResponse.BodyHandlers.ofString());
            //System.out.println("\nStatus Code from request: " + response.statusCode());
            return response.body();

        } catch ( ConnectException e ) {
            return GatewayErrors.CONNECTION_ERROR;
        }

    }

}


