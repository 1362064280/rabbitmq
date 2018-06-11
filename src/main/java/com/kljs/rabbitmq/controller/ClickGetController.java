package com.kljs.rabbitmq.controller;

import com.kljs.rabbitmq.consumer.DQConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClickGetController {

    @Autowired
    private DQConsumer clickConsumer;

    @RequestMapping(value = "/event/click/consumer", method = RequestMethod.POST)
    public String findOneCity() throws Exception {
        String val = clickConsumer.consumer();
        return val;
    }

}
