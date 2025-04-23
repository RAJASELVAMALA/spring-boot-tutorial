package com.example.myapp.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringBasicsApplication {

    public static void main(String[] args) {
//		Default line
//		SpringApplication.run(SpringBasicsApplication.class, args);

//		Completely Disable the Banner
        SpringApplication app = new SpringApplication(SpringBasicsApplication.class);
        app.setBannerMode(Banner.Mode.OFF); // To remove
        app.run(args);

        log.info("***************************");
        log.info("****Started Application****");
        log.info("***************************");


    }
}
