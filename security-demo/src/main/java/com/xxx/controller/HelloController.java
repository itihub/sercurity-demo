package com.xxx.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: TODO
 * @author: Administrator
 * @date: 2018/08/24 0024
 */
@RestController
public class HelloController {

    @RequestMapping("hello")
    public String hello() {
        return "Hello Spring Security";
    }
}
