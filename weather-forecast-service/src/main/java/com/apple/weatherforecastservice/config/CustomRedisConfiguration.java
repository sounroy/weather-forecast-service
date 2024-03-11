package com.apple.weatherforecastservice.config;

import com.google.common.primitives.Primitives;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.DefaultScriptExecutor;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Field;


/**
 * Configuration for Redis
 *
 * @author sroy
 */
@Configuration
public class CustomRedisConfiguration {

    @Value("${redis.host}")
    public String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.timeout:6000}")
    public int redisTimeout;

    @Bean(name = "factory")
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        JedisConnectionFactory jedisConFactory
                = new JedisConnectionFactory(config);
        return jedisConFactory;
    }

    @Bean
    public RedisTemplate <String, Object> redisTemplate(@Qualifier("factory") JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        JedisPoolConfig config = new JedisPoolConfig();
        redisTemplate.setKeySerializer(new StringRedisSerializer ());
        GenericToStringSerializer<Object> valueSerializer = new GenericToStringSerializer<>(Object.class);
        valueSerializer.setTypeConverter(new TypeConverter() {
            @Override
            @SuppressWarnings("unchecked")
            public <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException {
                if(value == null) {
                    return null;
                }
                Class<?> valueClass = value.getClass();
                if(!(valueClass.isPrimitive() || Primitives.isWrapperType(valueClass) || value instanceof String)){
                    throw new IllegalArgumentException(String.format("cannot convert non primitive not string in this converter %s %s ", valueClass, requiredType));
                }

                if (requiredType == String.class) {
                    return (T) String.valueOf(value);
                }

                return (T) value;
            }

            @Override
            public <T> T convertIfNecessary(Object value, Class<T> requiredType, MethodParameter methodParam) throws TypeMismatchException {
                throw new UnsupportedOperationException();
            }

            @Override
            public <T> T convertIfNecessary(Object value, Class<T> requiredType, Field field) throws TypeMismatchException {
                throw new UnsupportedOperationException();
            }
        });
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setScriptExecutor(new DefaultScriptExecutor <> (redisTemplate));
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisScript<Boolean> script() {
        //CustRedisScriptLoader
        DefaultRedisScript script = new DefaultRedisScript<>();
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("ratelimiter.lua")));
        script.setResultType(Long.class);
        return script;
    }

}
