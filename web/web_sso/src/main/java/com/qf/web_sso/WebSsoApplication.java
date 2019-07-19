package com.qf.web_sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
public class WebSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSsoApplication.class, args);
    }

}
