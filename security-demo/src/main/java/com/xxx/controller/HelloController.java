package com.xxx.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: TODO
 * @author: Administrator
 * @date: 2018/08/24 0024
 */
@RestController
public class HelloController {

    @GetMapping("hello")
    @ApiOperation(value = "Hello Word")
    public String hello() {
        return "Hello Spring Security";
    }
}
