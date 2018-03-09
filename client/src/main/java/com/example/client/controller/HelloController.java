package com.example.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RefreshScope
@RestController
@RequestMapping("/hello")
public class HelloController {

    private final String message;
    private final String common;

    public HelloController(@Value("${message}") String message, @Value("${common}") String common) {
        this.message = message;
        this.common = common;
    }

    @GetMapping
    public Map<String, String> hello() {
        HashMap<String, String> map = new HashMap<>();
        map.put("message", message);
        map.put("common", common);
        return map;
    }
}
