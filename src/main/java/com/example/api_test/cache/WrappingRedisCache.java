//package com.example.api_test.cache;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.cache.RedisCache;
//
//import java.io.IOException;
//
//@Slf4j
//public class WrappingRedisCache extends RedisCache {
//
//    private final Class targetReturnClass;
//    private final ObjectMapper objectMapper;
//    protected WrappingRedisCache(RedisCache redisCache, Class targetReturnClass, final ObjectMapper objectMapper) {
//        super(redisCache.getName(), redisCache.getNativeCache(), redisCache.getCacheConfiguration());
//        this.targetReturnClass = targetReturnClass;
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    protected Object deserializeCacheValue(byte[] value) {
//        log.debug("deserializeCacheValue targetClass : {}", targetReturnClass);
//        try {
//            return objectMapper.readValue(value, targetReturnClass);
//        } catch (IOException e) {
//            log.error("redis cache deserialize fail.");
//            throw new RuntimeException(e);
//        }
//    }
//}
