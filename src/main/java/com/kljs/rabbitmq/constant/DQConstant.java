package com.kljs.rabbitmq.constant;

public final class DQConstant {

    public static String CLICK_QUEUE_NAME = "clickQueue";
    public static String CLICK_EXCHANGE_NAME = "clickExchange";
    public static String CLICK_ROUTING_KEY = "clickKey";

    public static String CLICK_RETRY_QUEUE_NQME = CLICK_QUEUE_NAME+"@retry";
    public static String CLICK_RETRY_EXCHANGE_NAME = CLICK_EXCHANGE_NAME+"@retry";
    public static String CLICK_RETRY_ROUTING_KEY = CLICK_ROUTING_KEY;

    /**
     *  x-dead-letter-exchange:	clickExchange
     *  x-dead-letter-routing-key:	clickKey
     *  x-message-ttl:	30000
     *  durable:	true
     */
    public static String CLICK_FAILED_QUEUE_NQME = CLICK_QUEUE_NAME+"@failed";
    public static String CLICK_FAILED_EXCHANGE_NAME = CLICK_EXCHANGE_NAME+"@failed";
    public static String CLICK_FAILED_ROUTING_KEY = CLICK_ROUTING_KEY+"@failed";

}
