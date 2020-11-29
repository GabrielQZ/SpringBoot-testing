package com.gateway.msgateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GatewayRouter {

    @Autowired
    private Environment env;

    //TESTING ROUTES
    @RequestMapping("/testmsg")
    public String getHelloMessage(){
        return "Hello there user!";
    }

    @GetMapping("/testenvprop")
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

    //PUBLIC ROUTE

}


