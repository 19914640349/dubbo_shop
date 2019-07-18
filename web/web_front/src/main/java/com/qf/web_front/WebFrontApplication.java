package com.qf.web_front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
public class WebFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFrontApplication.class, args);
    }

}
