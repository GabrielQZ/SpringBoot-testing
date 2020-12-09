package com.microserve.msgateway;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLException;
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

    @GetMapping("/test")
    public String testGet() {
        return "test";
    }

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

            if (requestDetails == null) return GatewayErrors.REQUEST_ACTION_MISMATCH;

            if (!requestDetails.isPublic()) return GatewayErrors.REQUEST_NEEDS_AUTH;

            String requestMethod = requestDetails.getMethod();

            String requestURL = env.getProperty(requestDetails.getUrlKey());
            //the reqDetails url key should never point to a null prop. but in case it does, throw an error message
            if (requestURL == null)
                return GatewayErrors.REQUEST_URL_MISMATCH;
            else //append the url extension, this allows multiple POST/PUT requests to be made to the same service
                requestURL += "/" + requestDetails.getUrlExtension();

            //check that the environment is set in the application.properties, if so and the in development, log the request data
            try {
                if ( env.getProperty("server.environment").equals("development"))
                    logRequest(requestURL, requestMethod, reqData);
            } catch (NullPointerException e) { return GatewayErrors.SERVER_ENVIRONMENT_NULL; };

            //finally make the request to the service and send back its response
            return sendRequest(reqData, requestURL, requestMethod);

        } catch (JSONException e ) {
            //e.printStackTrace();
            return GatewayErrors.MISSING_DATA_ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("\nError with request being sent from Gateway\n");
            return GatewayErrors.UNKNOWN_REQUEST_ERROR;
        }
    }

    private void logRequest(String requestURL, String requestMethod, String reqData) {

        System.out.println(
                requestMethod.equals("GET") || requestMethod.equals("DELETE")
                        ? "\n"+requestMethod +": "+ requestURL + "/" + reqData
                        : "\n"+requestMethod +": "+ requestURL
        );

    }

    private String sendRequest( String data, String endpoint, String method) throws Exception {
            //System.out.println(method);
            //System.out.println(data);
            //System.out.println(endpoint);
        try {

            //the 'body' only gets used for POST and PUT request,
            // doing it here so i can keep the switch statement clean and it doesn't take much to process
            var body = HttpRequest.BodyPublishers.ofString(data);
            //start building the request
            var requestBuilder = HttpRequest.newBuilder();

            switch (method) { //listing methods in order of most frequently used
                case "PUT" -> requestBuilder = requestBuilder.PUT(body);
                case "GET" -> requestBuilder = requestBuilder.GET();
                case "POST" -> requestBuilder = requestBuilder.POST(body);
                case "DELETE" -> requestBuilder = requestBuilder.DELETE();
                default -> System.out.println("\nMETHOD NOT PROVIDED (GatewayRouter -> sendRequest)");
            }


            //once the method is determined the URI gets added along with headers
            // (content type doesn't hurt to add for GET/DELETE)
            HttpRequest builtReq = requestBuilder
                    .uri(URI.create(
                            //'data' gets utilized as a uri extension for GET/DELETE
                            //but post and put only need one route, if more than one put/post actions are needed,
                            //the request body can determine the actions of the receiving service
                            method.equals("GET") || method.equals("DELETE")
                            ? endpoint + "/" + data : endpoint
                    ))
                    .header("Content-Type", "application/json")
                    .build();

            //actually make request
            HttpResponse<String> response = httpClient.send(builtReq, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 500) return GatewayErrors.INTERNAL_SERVER_ERROR;
            //System.out.println("\nStatus Code from request: " + response.statusCode());
            return response.body();

        } catch ( ConnectException e ) {
            return GatewayErrors.CONNECTION_ERROR;
        } catch ( SSLException e ) {
            return GatewayErrors.CHECK_PROTOCOL_ERROR;
        } catch ( IllegalArgumentException e ) {
            return GatewayErrors.BAD_REQUEST_DATA;
        }

    }

}


