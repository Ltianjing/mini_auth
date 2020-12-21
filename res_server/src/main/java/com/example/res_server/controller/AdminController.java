package com.example.res_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class AdminController {
    @GetMapping("/admin/serverTime")
    Date getServerTime(){
        return new Date();
    }
}