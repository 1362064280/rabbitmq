package com.kljs.rabbitmq.consumer.impl;

import com.kljs.rabbitmq.constant.DQConstant;
import com.kljs.rabbitmq.consumer.DQConsumer;
import com.kljs.rabbitmq.util.RabbitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("clickConsumer")
public class ClickConsumerImpl implements DQConsumer{

    @Autowired
    private RabbitUtil rabbitUtil;

    @Override
    public String consumer() throws Exception {
        return rabbitUtil.fetch(DQConstant.CLICK_QUEUE_NAME, false);
    }
}
