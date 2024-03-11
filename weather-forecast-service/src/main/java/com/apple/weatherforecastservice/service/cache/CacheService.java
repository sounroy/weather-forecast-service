package com.apple.weatherforecastservice.service.cache;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * Cache Service
 *
 * @author sroy
 */
@Service
public class CacheService {

    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    RedisScript<Boolean> script;

    private String getZipCodeCacheKey(final String zipCode){
        return String.format("COUNTRY:%s",zipCode);
    }

    public void setForecastDataByZipCode(final String zipCode, String weatherForeCastData,Duration ttl){
        String cacheKey = getZipCodeCacheKey(zipCode);
        redisTemplate.opsForValue().set(cacheKey,weatherForeCastData,ttl);
        redisTemplate.expire(cacheKey, ttl.getSeconds(), TimeUnit.SECONDS);
    }

    public String getForecastDataByZipCode(final String zipCode){
        String cacheKey = getZipCodeCacheKey(zipCode);
        Object val = redisTemplate.opsForValue().get(cacheKey);
        logger.debug(" cacheKey : {}, value from cache {}",cacheKey,val);
        if (Objects.nonNull(val)){
            return val.toString();
        }else {
            return StringUtils.EMPTY;
        }
    }
}
