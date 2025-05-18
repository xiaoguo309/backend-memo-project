package com.xiaoguo.memo.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    
    @GetMapping("/")
    public String index() {
        return "MyMemo API is running...";
    }
    
    @GetMapping("/test")
    public String test() {
        return "Test endpoint is working!";
    }
} 