package org.example.shixi.config.redis;

import jakarta.annotation.PostConstruct;
import me.zhyd.oauth.cache.AuthCacheConfig;
import me.zhyd.oauth.cache.AuthStateCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class AuthStateRedisCache implements AuthStateCache {
    @Autowired
    private RedisTemplate<String,String>redisTemplate;

    private ValueOperations <String,String> valueOperations;
    @PostConstruct
    public void init(){
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void cache(String key,String value){
        valueOperations.set(key, value, AuthCacheConfig.timeout, TimeUnit.SECONDS);
    }
    @Override
    public void cache(String key,String value,long timeout){
        valueOperations.set(key, value, timeout, TimeUnit.SECONDS);
    }
    @Override
    public String get(String key){
        return valueOperations.get(key);
    }
    @Override
    public boolean containsKey(String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

}
