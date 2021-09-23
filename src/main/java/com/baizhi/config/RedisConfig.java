package com.baizhi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> template =new RedisTemplate<>();
        //template .setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Object> jacksonJsonRedisSerializer=new Jackson2JsonRedisSerializer<Object>(Object.class);
        template.setValueSerializer(jacksonJsonRedisSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonJsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
            return template;
    }

  
}