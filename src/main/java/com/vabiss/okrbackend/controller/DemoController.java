package com.vabiss.okrbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/demo2")
    public String demo() {
        return "bla bla";
    }

}
