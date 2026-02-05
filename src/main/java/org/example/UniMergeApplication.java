package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniMergeApplication {
    public static void main(String[] args) {
        // Force headless mode so Java doesn't even look for a screen
        System.setProperty("java.awt.headless", "true");
        SpringApplication.run(UniMergeApplication.class, args);
    }
}