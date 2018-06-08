package com.kljs.rabbitmq.util;

import com.kljs.rabbitmq.constant.DQConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@Component
public class RabbitClient {

    private ConnectionFactory connectionFactory;
    private volatile Connection connection;
    private volatile Channel channel;

    /**
     * 创建连接工厂
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws URISyntaxException
     */
    @PostConstruct
    private ConnectionFactory createConnectionFactory() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        this.connectionFactory = connectionFactory;
        try {
            repairChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.connectionFactory;
    }

    /**
     * 创建连接
     * @throws IOException
     * @throws TimeoutException
     */
    private Connection createConnection() throws IOException, TimeoutException {
        if(null != this.connection && this.connection.isOpen()) {
            return this.connection;
        }
        Connection connection = this.connectionFactory.newConnection();
        this.connection = connection;
        return this.connection;
    }

    /**
     * 创建通道
     * @throws IOException
     */
    public Channel createChannel() throws IOException {
        if(null != this.channel && this.channel.isOpen()) {
            return this.channel;
        }
        Channel channel = this.connection.createChannel();
        this.channel = channel;
        return this.channel;
    }

    public void repairChannel() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    createConnection();
                    createChannel();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }



}
