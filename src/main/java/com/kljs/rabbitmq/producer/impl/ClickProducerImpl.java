package com.kljs.rabbitmq.producer.impl;

import com.kljs.rabbitmq.constant.DQConstant;
import com.kljs.rabbitmq.producer.DQProducer;
import com.kljs.rabbitmq.util.RabbitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("clickProducer")
public class ClickProducerImpl implements DQProducer{

    @Autowired
    private RabbitUtil rabbitUtil;

    @Override
    public void send(Object message) throws Exception {
        rabbitUtil.send(DQConstant.CLICK_EXCHANGE_NAME, DQConstant.CLICK_ROUTING_KEY, message);
    }
}
