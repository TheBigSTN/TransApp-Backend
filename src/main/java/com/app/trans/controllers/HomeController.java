package com.app.trans.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String empty() {
        return "bun";
    }

//    @GetMapping("/showLogInForm")
//    public String showLogInForm() {
//        return "login";
//    }
//
//    // @RequestMapping({"", "/", "/index"})
//
//    @GetMapping("/login-error")
//    public String loginError() {
//        return "login-error";
//    }
//
//    @GetMapping("/access_denied")
//    public String accessDenied() {
//        return "access_denied";
//    }
//
//    @GetMapping("/error")
//    public String error() {
//        return "acces_denied";
//    }

}
