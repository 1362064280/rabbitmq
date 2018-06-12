package com.kljs.rabbitmq.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class DemoController {

    @RequestMapping(value = "/demo/get", method = RequestMethod.POST)
    public String getDemo() {
        return LocalDateTime.now().toString();
    }

}
