package com.example.spring_todo_api.service;

import com.example.spring_todo_api.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ValidationService validationService;

    public void save(String key, Object value) {
        try  {
            validationService.validate(value);

            String data = objectMapper.writeValueAsString(value);

            stringRedisTemplate.opsForValue().set(key, data, 1L, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There's an error while serializing data: " + e.getMessage());
        }
    }

    public Object get(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);

        if(value == null || value.isEmpty()) {
            log.warn("The key is not found or data is empty in Redis: {}", key);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There's an error while deserializing data");
        }

        return deserialize(value);
    }

    private User deserialize(String data) {
        try {
           return objectMapper.readValue(data, User.class);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There's an error while deserializing data: " + e.getMessage());
        }
    }
}
