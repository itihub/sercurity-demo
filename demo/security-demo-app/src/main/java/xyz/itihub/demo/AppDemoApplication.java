package xyz.itihub.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"xyz.itihub", "xyz.itihub.demo"})
@SpringBootApplication
public class AppDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppDemoApplication.class, args);
    }

}
