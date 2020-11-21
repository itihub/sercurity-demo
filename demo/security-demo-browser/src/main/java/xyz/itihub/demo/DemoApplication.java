package xyz.itihub.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: TODO
 * @author: Administrator
 * @date: 2018/08/24 0024
 */
@SpringBootApplication
@ComponentScan(basePackages = {"xyz.itihub", "xyz.itihub.demo"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
