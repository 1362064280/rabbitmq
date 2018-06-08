package com.kljs.rabbitmq.controller;

import com.kljs.rabbitmq.entity.ClickEventMessage;
import com.kljs.rabbitmq.producer.DQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClickController {

    @Autowired
    private DQProducer clickProducer;

    @RequestMapping(value = "/event/click", method = RequestMethod.POST)
    public void findOneCity(@RequestBody ClickEventMessage clickEventMessage) throws Exception {
        clickProducer.send(clickEventMessage);
    }

}
