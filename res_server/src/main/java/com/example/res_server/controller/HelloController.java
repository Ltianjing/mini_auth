package com.example.res_server.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/api/hello")
    public String sayHello(Principal principal){
        return "hello" + principal.getName();
    }
    
}
