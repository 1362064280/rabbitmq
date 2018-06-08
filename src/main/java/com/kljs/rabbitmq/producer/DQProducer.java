package com.kljs.rabbitmq.producer;

public interface DQProducer {

    public void send(Object message) throws Exception;

}