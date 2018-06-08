package com.kljs.rabbitmq.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClickEventMessage implements Serializable{

    private long userId;
    private long index;

    public ClickEventMessage() {
    }

    @Override
    public String toString() {
        return "ClickEventMessage{" +
                "userId=" + userId +
                ", index=" + index +
                '}';
    }
}
