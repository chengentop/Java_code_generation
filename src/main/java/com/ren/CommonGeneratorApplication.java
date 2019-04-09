package com.ren;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.ren"})
public class CommonGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonGeneratorApplication.class, args);
    }

}

