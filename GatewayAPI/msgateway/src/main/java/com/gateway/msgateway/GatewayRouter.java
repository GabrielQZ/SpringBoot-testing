package com.gateway.msgateway;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayRouter {
    @RequestMapping("/testmsg")
    public String getHelloMessage(){
        return "Hello there user!";
    }

    @PutMapping("/update")
    public String putTestUpdate(){
        return "updating";
    }

}


