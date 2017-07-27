package com.lucifer.cache;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by liufx on 16/1/5.
 */
@Service
public class AppCache {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public  <T> T find(String key, CacheProvider provider){
        Object object = redisTemplate.opsForValue().get(key);
        if (null != object) {
            return (T) object;
        }
        object = provider.getData();
        if (object == null) {
           return null;
        }
        redisTemplate.opsForValue().set(key,object);
        return (T) object;
    }
    
    public void set(String key,Object object){
    	redisTemplate.opsForValue().set(key,object);
    }

    public void remove(String key){
        redisTemplate.delete(key);
    }

    /**
     * 删除所有匹配
     * @param key 例如 user:*
     */
    @SuppressWarnings("unchecked")
    public void removeAll(String key){       
	Set<String> keys = redisTemplate.keys(key);
        for(String aKey : keys) {
            redisTemplate.delete(aKey);
        }
    }


}
