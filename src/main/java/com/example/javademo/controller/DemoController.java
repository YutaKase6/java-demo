package com.example.javademo.controller;

import com.example.javademo.logging.Logging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DemoController {

    @Logging
    @GetMapping
    public String hello(@RequestParam(required = false) String err) {
        if ("true".equals(err)) {
            throw new RuntimeException("error");
        }
        return "{\"message\":\"hello\"}";
    }
}
