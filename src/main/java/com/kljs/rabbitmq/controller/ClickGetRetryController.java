package com.kljs.rabbitmq.controller;

import com.kljs.rabbitmq.constant.DQConstant;
import com.kljs.rabbitmq.util.RabbitUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClickGetRetryController {

    @Autowired
    private RabbitUtil rabbitUtil;

    @RequestMapping(value = "/event/click/consumer/retry", method = RequestMethod.POST)
    public void retry() throws IOException {
        //消息消费
        GetResponse getResponse = null;
        try {
            getResponse = rabbitUtil.fetch(DQConstant.CLICK_QUEUE_NAME, false);
            /**
             * 业务处理
             */
            throw new RuntimeException("错粗了");
        } catch (Exception e) {
            if(null != getResponse) {
                long retryCount = getRetryCount(getResponse.getProps());
                if(retryCount > 3) {
                    //重试超过3次的，直接存入失败队列
                    AMQP.BasicProperties properties = getResponse.getProps();
                    Map<String, Object> headers = properties.getHeaders();
                    if(null == headers) {
                        headers = new HashMap<>();
                    }
                    headers.put("x-orig-routing-key", getOrigRoutingKey(getResponse.getProps(), getResponse.getEnvelope().getRoutingKey()));
                    properties.builder().headers(headers);
                    rabbitUtil.send(DQConstant.CLICK_FAILED_EXCHANGE_NAME, DQConstant.CLICK_FAILED_ROUTING_KEY, properties, getResponse.getBody());
                } else {
                    //重试不超过3次的，加入到重试队列
                    AMQP.BasicProperties properties = getResponse.getProps();
                    Map<String, Object> headers = properties.getHeaders();
                    if(null == headers) {
                        headers = new HashMap<>();
                    }
                    headers.put("x-orig-routing-key", getOrigRoutingKey(getResponse.getProps(), getResponse.getEnvelope().getRoutingKey()));
                    properties.builder().headers(headers);
                    rabbitUtil.send(DQConstant.CLICK_RETRY_EXCHANGE_NAME, DQConstant.CLICK_RETRY_ROUTING_KEY, properties, getResponse.getBody());
                }
            }
        }
        if(null != getResponse) {
            rabbitUtil.ack(getResponse);
        }
    }

    /**
     * 获取消息的重试次数
     * @param properties
     * @return
     */
    private long getRetryCount(AMQP.BasicProperties properties) {
        long retryCount = 0;
        Map<String, Object> headers = properties.getHeaders();
        if(null != headers) {
            if(headers.containsKey("x-death")) {
                List<Map<String, Object>> deathList = (List<Map<String, Object>>) headers.get("x-death");
                if(!deathList.isEmpty()) {
                    Map<String, Object> deathEntry = deathList.get(0);
                    retryCount = (Long)deathEntry.get("count");
                }
            }
        }
        return retryCount;
    }

    /**
     * 获取原来的routingKey
     * @param properties
     * @param defaultValue
     * @return
     */
    private String getOrigRoutingKey(AMQP.BasicProperties properties, String defaultValue) {
        String routingKey = defaultValue;
        Map<String, Object> headers = properties.getHeaders();
        if(null != headers) {
            routingKey = headers.getOrDefault("x-orig-routing-key", defaultValue).toString();
        }
        return routingKey;
    }

}
