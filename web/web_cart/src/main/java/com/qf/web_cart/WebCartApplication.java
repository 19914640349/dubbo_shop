package com.qf.web_cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
public class WebCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebCartApplication.class, args);
    }

}
