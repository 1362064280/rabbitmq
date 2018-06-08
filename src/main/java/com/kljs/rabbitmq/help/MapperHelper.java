package com.kljs.rabbitmq.help;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kljs.rabbitmq.deserializer.LocalDateDeserializer;
import com.kljs.rabbitmq.deserializer.LocalDateTimeDeserializer;
import com.kljs.rabbitmq.deserializer.LocalTimeDeserializer;
import com.kljs.rabbitmq.serializer.LocalDateSerializer;
import com.kljs.rabbitmq.serializer.LocalDateTimeSerializer;
import com.kljs.rabbitmq.serializer.LocalTimeSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class MapperHelper {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        SimpleModule module = new SimpleModule();
//        module.addSerializer(LocalDate.class, new LocalDateSerializer());
//        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
//        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
//        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
//        module.addSerializer(LocalTime.class, new LocalTimeSerializer());
//        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer());

        mapper.registerModule(module);
    }

    public static String writeValueAsString(Object obj) {
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (Exception e) {

        }
        return json;
    }

    public static  <T> T readValue(String content, Class<T> valueType) throws IOException{
        try {
            return mapper.readValue(content, valueType);
        } catch (IOException e) {
            throw e;
        }
    }

}
