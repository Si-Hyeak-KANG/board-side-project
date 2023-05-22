package com.project.boardsideproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class BoardSideProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardSideProjectApplication.class, args);
    }

}
