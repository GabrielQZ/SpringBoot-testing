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

    private final String CONNECTION_ERROR = "{error: \"Request failed because server could not connect to API\"}";
    private final String MISSING_DATA_ERROR = "{error: \"No 'action' OR 'data' present in request\"}";
    private final String BAD_REQUEST_ERROR = "{error: \"Request failed\"}";
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Autowired
    private Environment env;

    //TESTING ROUTES
    @RequestMapping("/testmsg")
    public String getHelloMessage(){
        return "Hello there user!";
    }

    @GetMapping("/test")
    public String getProps(){

        return env.getProperty("authSystem.endpoint");
    }

    @PutMapping("/update")
    public String putTestUpdate(){
        return "updating";
    }

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
            String reqData = rawRequest.get("data").toString();
            String reqAction = rawRequest.get("action").toString();

            MSRequest requestDetails = RequestMap.reqMap.get(reqAction);

            String requestMethod = requestDetails.getMethod();

            String requestURL = env.getProperty(requestDetails.getUrlKey());

//            JSONObject nextRequest = new JSONObject();
//            nextRequest.put("data", reqData);
//            nextRequest.put("method", requestMethod);
//            nextRequest.put("endpoint", requestURL);


            return sendRequest(reqData, requestURL, requestMethod);
//            System.out.println(nextRequest);
//
//            return nextRequest.toString();


        } catch (JSONException e ) {
//            e.printStackTrace();
            return MISSING_DATA_ERROR;
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("\nError with request being sent from Gateway\n");
            return BAD_REQUEST_ERROR;
        }
    }

    private String sendRequest( String data, String endpoint, String method) throws Exception {

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
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .build();

        try {

            HttpResponse<String> response = httpClient.send(builtReq, HttpResponse.BodyHandlers.ofString());
            System.out.println("\nStatus Code from request: " + response.statusCode());

            return response.body();

        } catch ( ConnectException e ) {
            return CONNECTION_ERROR;
        }
//
//        // print status code
//
//        // print response body
//        System.out.println(response.body());

    }

}


