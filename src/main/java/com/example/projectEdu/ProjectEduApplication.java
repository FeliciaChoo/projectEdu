package com.example.projectEdu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.projectEdu.model")  // Explicitly scan your entity package
public class ProjectEduApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectEduApplication.class, args);
    }
}


