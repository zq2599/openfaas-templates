package com.bolingcavalry.jdk8mavenspringboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Description: 来自模板的demo
 * @author: willzhao E-mail: zq2599@gmail.com
 * @date: 2021/3/7 15:24
 */
@RestController
public class Hello {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello world, " + new Date();
    }
}
