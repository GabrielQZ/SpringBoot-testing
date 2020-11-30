package com.gateway.msgateway;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
public class GatewayRouter {

    String MISSING_DATA_ERROR = "{error: \"No 'action' OR 'data' present in request\"}";

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

            JSONObject nextRequest = new JSONObject();
            nextRequest.put("data", reqData);
            nextRequest.put("method", requestMethod);
            nextRequest.put("endpoint", requestURL);

            System.out.println(nextRequest);

            return nextRequest.toString();


        } catch (JSONException e ) {
            return MISSING_DATA_ERROR;
        }
    }

}


