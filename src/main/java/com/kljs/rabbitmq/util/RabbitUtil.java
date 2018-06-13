package com.kljs.rabbitmq.util;

import com.kljs.rabbitmq.help.MapperHelper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitUtil {

    @Autowired
    private RabbitClient rabbitClient;

    /**
     * 发送消息
     * @param exchange
     * @param routingKey
     * @param message
     * @throws Exception
     */
    public void send(String exchange, String routingKey, Object message) throws Exception {
        //获取channel
        Channel channel = rabbitClient.createChannel();
        //发送
        channel.basicPublish(exchange, routingKey, null, MapperHelper.writeValueAsString(message).getBytes());
    }

    /**
     * 发送消息
     * @param exchange
     * @param routingKey
     * @param data
     * @throws Exception
     */
    public void send(String exchange, String routingKey, AMQP.BasicProperties properties, byte[] data) throws IOException {
        //获取channel
        Channel channel = rabbitClient.createChannel();
        //发送
        channel.basicPublish(exchange, routingKey, properties, data);
    }


    /**
     * 获取消息
     * @param queue
     * @param autoAck
     * @return
     * @throws Exception
     */
    public GetResponse fetch(String queue, boolean autoAck) throws Exception {
        Channel channel = rabbitClient.createChannel();
        GetResponse getResponse = channel.basicGet(queue, autoAck);
        return getResponse;
    }

    /**
     * 消息确认机制
     * @param getResponse
     * @throws IOException
     */
    public void ack(GetResponse getResponse) throws IOException {
        if(null != getResponse) {
            Channel channel = rabbitClient.createChannel();
            channel.basicAck(getResponse.getEnvelope().getDeliveryTag(), false);
        }
    }

    /**
     * 消息重入队列
     * @param getResponse
     * @throws IOException
     */
    public void nack(GetResponse getResponse) throws IOException {
        if(null != getResponse) {
            Channel channel = rabbitClient.createChannel();
            channel.basicNack(getResponse.getEnvelope().getDeliveryTag(), false, true);
        }
    }


}
