package com.kljs.rabbitmq.consumer.impl;

import com.kljs.rabbitmq.constant.DQConstant;
import com.kljs.rabbitmq.consumer.DQConsumer;
import com.kljs.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("clickConsumer")
public class ClickConsumerImpl implements DQConsumer{

    @Autowired
    private RabbitUtil rabbitUtil;

    @Override
    public String consumer() throws Exception {
        GetResponse getResponse = rabbitUtil.fetch(DQConstant.CLICK_QUEUE_NAME, false);
        if(null != getResponse) {
            //业务处理
            try {
                String val = new String(getResponse.getBody());
                rabbitUtil.ack(getResponse);
                return val;
            } catch (Exception e) {
                rabbitUtil.nack(getResponse);
                return null;
            }
        } else {
            return null;
        }
    }
}
