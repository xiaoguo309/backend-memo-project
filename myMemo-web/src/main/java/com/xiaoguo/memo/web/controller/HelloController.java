package com.xiaoguo.memo.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

/**
 * @author siye.gzc
 * @date 2025/03/02
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        // 获取今天的日期
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Hello！ Today is: " + sdf.format(date);
    }
}
