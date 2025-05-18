package com.xiaoguo.memo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * App Starter
 */
@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class SpringBootApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SpringBootApplication.class, args);
    }
}
