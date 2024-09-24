package com.hotstrip.flow.worker.config;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // 配置 mapper 以支持 JSONObject
        Module module = new SimpleModule().addDeserializer(JSONObject.class, new JsonDeserializer<JSONObject>() {
            @Override
            public JSONObject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                Map<String, Object> map = p.getCodec().readValue(p, Map.class);
                return new JSONObject(map);
            }
        });
        mapper.registerModule(module);
        return mapper;
    }
}
