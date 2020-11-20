package xyz.itihub.demo.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @ApiOperation(value = "Hello Word")
    @RequestMapping("hello")
    public String hello() {
        return "Hello Spring Security";
    }
}
