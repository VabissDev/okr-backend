package com.vabiss.okrbackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class DemoController {

    @GetMapping("/demo")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/demo2")
    public String demo() {
        return "blah blah";
    }

}
