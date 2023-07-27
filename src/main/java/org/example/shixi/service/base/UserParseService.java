package org.example.shixi.service.base;


import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.PostConstruct;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class UserParseService {
    @Autowired
    private RedisTemplate redisTemplate;

    private BoundHashOperations<String,String, AuthUser> valueOperations;

    @PostConstruct
    public void init(){
        valueOperations = redisTemplate.boundHashOps("JUSTAUTH::USERS");
    }

    public AuthUser save(AuthUser user){
        valueOperations.put(user.getUuid(),user);
        return user;
    }
    public AuthUser getByUuid(String uuid) {
        Object user =valueOperations.get(uuid);
        if(user == null){
            return null;
        }
        return JSONObject.parseObject(JSONObject.toJSONString(user),AuthUser.class);

    }

    public List<AuthUser> listAll() {
        return new LinkedList<>(Objects.requireNonNull(valueOperations.values()));
    }


    public void remove(String uuid) {
        valueOperations.delete(uuid);
    }
}
