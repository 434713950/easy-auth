package com.github.easy.auth.authentication.store;

import com.github.easy.auth.core.authentication.Authentication;
import com.github.easy.auth.core.authentication.store.TokenStore;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author cheng.peng
 * @date 2019/8/30
 */
public class RedisTokenStore implements TokenStore {

    private RedisTemplate redisTemplate;

    public RedisTokenStore(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Authentication readAuthentication(String storeKey) {
        Object authInfo = redisTemplate.opsForValue().get(storePrefix() + storeKey);
        if (authInfo != null) {
            return (Authentication) authInfo;
        }
        return null;
    }

    @Override
    public void storeAuthentication(String storeKey, Authentication authentication,Long time) {
        if (time == null) {
            time = expire();
        }
        redisTemplate.opsForValue().set(storePrefix()+ storeKey,authentication,time,TimeUnit.SECONDS);
    }

    @Override
    public void removeAuthentication(String storeKey) {
        redisTemplate.delete(storePrefix() + storeKey);
    }

    @Override
    public void refreshExpire(String storeKey,Long time) {
        if (time == null) {
            time = expire();
        }
        redisTemplate.expire(storeKey,time,TimeUnit.SECONDS);
    }

    @Override
    public String storePrefix() {
        return "easy_auth:";
    }

    @Override
    public Long expire() {
        return null;
    }
}
